package com.xz.managersystem.web;

import com.xz.managersystem.entity.TUserInfo;
import com.xz.managersystem.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Base64;

/**
 * 页面
 */
@Controller
@RequestMapping("/live")
public class LiveController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public ResponseEntity<byte[]> exportConfig(@RequestParam(name = "accessToken") String token) throws IOException {
        TUserInfo userInfo = userService.getUserByToken(token);
        if (userInfo == null) {
            throw new RuntimeException("无效的AccessToken");
        } else if (!"operator".equalsIgnoreCase(userInfo.getType())) {
            throw new RuntimeException("未授权的操作");
        } else if (StringUtils.isBlank(userInfo.getGroup())){
            throw new RuntimeException("用户" + userInfo.getName() + "没有分组");
        }

        final Base64.Encoder encoder = Base64.getEncoder();
        final byte[] srcByte = userInfo.getGroup().getBytes("UTF-8");
        final byte[] destByte = encoder.encode(srcByte);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=" + userInfo.getGroup() + ".conf");
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(destByte, headers, statusCode);
        return entity;
    }
}
