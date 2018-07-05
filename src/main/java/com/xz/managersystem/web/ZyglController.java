package com.xz.managersystem.web;

import com.xz.managersystem.entity.BasicEntity;
import com.xz.managersystem.entity.TZbxx;
import com.xz.managersystem.entity.TZyxx;
import com.xz.managersystem.service.ZyglService;
import com.xz.managersystem.dto.req.BasicTableReq;
import com.xz.managersystem.dto.res.BasicRes;
import com.xz.managersystem.dto.res.BasicTableRes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;

/**
 * 资源
 */
@Controller
@RequestMapping("/zygl")
public class ZyglController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ZyglService zyglService;

    @RequestMapping(value = "/zy", method = RequestMethod.GET)
    @ResponseBody
    private BasicEntity selectZyList(@RequestParam(name = "type", required = false) String type,
                                     @RequestParam(name = "page", required = false) Integer page,
                                     @RequestParam(name = "rows", required = false) Integer rows) {
        int zyCount = zyglService.selectZyCount(type);
        List<TZyxx> zyList;
        if (zyCount == 0) {
            zyList = new ArrayList<>();
        } else if (page == null || rows == null) {
            zyList = zyglService.selectZyList(type);
        } else {
            zyList = zyglService.selectZyPage(new BasicTableReq(page, rows, type));
        }
        return new BasicTableRes<>(zyCount, zyList);
    }

    @RequestMapping(value = "/zy/add", method = RequestMethod.POST)
    @ResponseBody
    private BasicEntity insertZy(@RequestParam(name = "file") MultipartFile multipartFile,
                                 @RequestParam(name = "des", required = false) String des) {
        zyglService.insertZy(multipartFile,des);
        return new BasicRes("添加资源成功");
    }

    @RequestMapping(value = "/zy/update", method = RequestMethod.POST)
    @ResponseBody
    private BasicEntity updateZy(@Valid TZyxx zyxx) {
        zyglService.updateZy(zyxx);
        return new BasicRes("修改资源成功");
    }

    @RequestMapping(value = "/zy/delete", method = RequestMethod.POST)
    @ResponseBody
    private BasicEntity deleteZy(@Valid TZyxx zyxx) {
        zyglService.deleteZy(zyxx);
        return new BasicRes("删除资源成功");
    }

    @RequestMapping(value = "/zy/{label}", method = RequestMethod.GET)
    @ResponseBody
    private BasicEntity selectZy(@PathVariable("label") String label) {
        return zyglService.selectZyByName(label);
    }

    //////////////////////////////////////// 分割线 ////////////////////////////////////////

    @RequestMapping(value = "/zb", method = RequestMethod.GET)
    @ResponseBody
    private BasicEntity selectZbList(@Valid BasicTableReq tr) {
        int zbCount = zyglService.selectZbCount();
        List<TZbxx> zbList;
        if (zbCount == 0) {
            zbList = new ArrayList<>();
        } else if (tr.getPage() == null || tr.getRows() == null) {
            zbList = zyglService.selectZbList();
        } else {
            zbList = zyglService.selectZbPage(tr);
        }
        return new BasicTableRes<>(zbCount, zbList);
    }

    @RequestMapping(value = "/zb/add", method = RequestMethod.POST)
    @ResponseBody
    private BasicEntity insertZb(@Valid TZbxx zbxx) {
        zyglService.insertZb(zbxx);
        return new BasicRes("添加直播信号成功");
    }

    @RequestMapping(value = "/zb/update", method = RequestMethod.POST)
    @ResponseBody
    private BasicEntity updateZb(@Valid TZbxx zbxx) {
        zyglService.updateZb(zbxx);
        return new BasicRes("修改直播信号成功");
    }

    @RequestMapping(value = "/zb/delete", method = RequestMethod.POST)
    @ResponseBody
    private BasicEntity deleteZb(@Valid TZbxx zbxx) {
        zyglService.deleteZb(zbxx);
        return new BasicRes("删除直播信号成功");
    }

    @RequestMapping(value = "/zb/{label}", method = RequestMethod.GET)
    @ResponseBody
    private BasicEntity selectZb(@PathVariable("label") String label) {
        return zyglService.selectZbByName(label);
    }

    @RequestMapping(value = "/zb/{label}/export", method = RequestMethod.GET)
    public ResponseEntity<byte[]> download(HttpServletRequest request,
                                           @PathVariable("label") String label) throws IOException {
        final Base64.Encoder encoder = Base64.getEncoder();
        final byte[] srcByte = label.getBytes("UTF-8");
        final byte[] destByte = encoder.encode(srcByte);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=" + label + ".conf");
        HttpStatus statusCode = HttpStatus.OK;
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(destByte, headers, statusCode);
        return entity;
    }
}
