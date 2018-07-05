package com.xz.managersystem.web;

import com.xz.managersystem.dto.req.BasicTableReq;
import com.xz.managersystem.dto.res.BasicTableRes;
import com.xz.managersystem.dto.res.BasicRes;
import com.xz.managersystem.entity.BasicEntity;
import com.xz.managersystem.entity.TYmxx;
import com.xz.managersystem.service.YmglService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 页面
 */
@Controller
@RequestMapping("/ymgl")
public class YmglController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    YmglService ymglService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    private BasicEntity selectYmList(@Valid BasicTableReq tr) {
        int ymCount = ymglService.selectYmCount();
        List<TYmxx> ymList;
        if (ymCount == 0) {
            ymList = new ArrayList<>();
        } else if (tr.getRows() == null || tr.getPage() == null) {
            ymList = ymglService.selectYmList();
        } else {
            ymList = ymglService.selectYmPage(tr);
        }
        return new BasicTableRes<>(ymCount, ymList);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    private BasicEntity insertYm(@Valid TYmxx ymxx) {
        ymglService.insertYm(ymxx);
        return new BasicRes("添加页面成功");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    private BasicEntity updateYm(@Valid TYmxx ymxx) {
        ymglService.updateYm(ymxx);
        return new BasicRes("修改页面成功");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    private BasicEntity deleteYm(@Valid TYmxx ymxx) {
        ymglService.deleteYm(ymxx);
        return new BasicRes("删除页面成功");
    }

    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    @ResponseBody
    private BasicEntity deleteYm(@RequestParam(name = "label") String label,
                                 @RequestParam(name = "sblist", required = false) String sbList,
                                 @RequestParam(name = "fzlist", required = false) String fzList) {
        ymglService.publishYm(label, sbList, fzList);
        return new BasicRes("发布页面成功");
    }

    @RequestMapping(value = "/{label}", method = RequestMethod.GET)
    @ResponseBody
    private BasicEntity selectYm(@PathVariable("label") String label) {
        return ymglService.selectYmByName(label);
    }
}
