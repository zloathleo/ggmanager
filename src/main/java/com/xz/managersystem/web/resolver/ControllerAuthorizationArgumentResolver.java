package com.xz.managersystem.web.resolver;

import java.lang.annotation.Annotation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class ControllerAuthorizationArgumentResolver implements HandlerMethodArgumentResolver {

//    @Autowired
//    private GWUserService gwUserService;

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
            throw new RuntimeException("参数不完整");
        }

//        AuthorizationUser auth = gwUserService.getAuthorization(clientCode, accessToken);
//        if (auth == null) {
//            // Token不存在 非法登录
//            throw  ShitouRuntimeExceptionConst.InfoParamAuthorizationMiss;
//        }
        return "OK";
    }

}

