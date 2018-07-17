package com.xz.managersystem.web;

import com.xz.managersystem.dao.ConditionParams;
import com.xz.managersystem.dto.req.BasicTableReq;
import com.xz.managersystem.dto.res.BasicRes;
import com.xz.managersystem.dto.res.BasicTableRes;
import com.xz.managersystem.entity.BasicEntity;
import com.xz.managersystem.entity.TDeviceInfo;
import com.xz.managersystem.entity.TUserInfo;
import com.xz.managersystem.service.UtilTools;
import com.xz.managersystem.web.resolver.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

/**
 * 页面
 */
@Controller
@RequestMapping("/live")
public class LiveController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public ResponseEntity<byte[]> exportConfig(@Authorization TUserInfo userInfo) throws IOException {
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
