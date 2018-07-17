package com.xz.managersystem.web.resolver;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import com.xz.managersystem.entity.TGroupInfo;
import com.xz.managersystem.entity.TUserInfo;
import com.xz.managersystem.service.GroupService;
import com.xz.managersystem.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class ControllerAuthorizationArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Annotation[] as = parameter.getParameterAnnotations();
        for (Annotation a : as) {
            if (a.annotationType() == Authorization.class) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        String accessToken = webRequest.getHeader("accessToken");
        if (StringUtils.isBlank(accessToken)) {
            // 参数不完整
            throw new RuntimeException("缺少参数accessToken");
        }

        TUserInfo userInfo = userService.getUserByToken(accessToken);
        if (userInfo == null) {
            throw new RuntimeException("无效的AccessToken");
        }

        TGroupInfo groupInfo = groupService.findGroup(userInfo.getGroup());
        if ("operator".equalsIgnoreCase(userInfo.getType()) && groupInfo == null) {
            throw new RuntimeException("用户无有效关联组");
        }

        HttpServletRequest httpRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        String url = httpRequest.getRequestURI();
        if (!"admin".equalsIgnoreCase(userInfo.getType()) &&
                (url.contains("/users/") || url.contains("/groups/"))) {
            throw new RuntimeException("未授权的操作");
        } else if ("admin".equalsIgnoreCase(userInfo.getType()) &&
                (url.contains("/pages/") || url.contains("/resources/") || url.contains("/devices/"))) {
            throw new RuntimeException("未授权的操作");
        }
        return userInfo;
    }

}

