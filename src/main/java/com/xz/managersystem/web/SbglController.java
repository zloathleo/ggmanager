package com.xz.managersystem.web;

import com.xz.managersystem.dto.req.BasicTableReq;
import com.xz.managersystem.dto.res.BasicRes;
import com.xz.managersystem.dto.res.BasicTableRes;
import com.xz.managersystem.entity.BasicEntity;
import com.xz.managersystem.entity.TSbxx;
import com.xz.managersystem.entity.TYmxx;
import com.xz.managersystem.service.SbglService;
import com.xz.managersystem.service.YmglService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备
 */
@Controller
@RequestMapping("/sbgl")
public class SbglController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SbglService sbglService;

    @Autowired
    YmglService ymglService;

    @RequestMapping(value = "/sb", method = RequestMethod.GET)
    @ResponseBody
    private BasicEntity selectSbList(@Valid BasicTableReq tr) {
        int sbCount = sbglService.selectSbCount();
        List<TSbxx> sbList;
        if (sbCount == 0) {
            sbList = new ArrayList<>();
        } else if (tr.getRows() == null || tr.getPage() == null) {
            sbList = sbglService.selectSbList();
        } else {
            sbList = sbglService.selectSbPage(tr);
        }
        return new BasicTableRes<>(sbCount, sbList);
    }

    @RequestMapping(value = "/sb/add", method = RequestMethod.POST)
    @ResponseBody
    private BasicEntity insertSb(@Valid TSbxx sbxx) {
        sbglService.insertSb(sbxx);
        return new BasicRes("添加设备成功");
    }

    @RequestMapping(value = "/sb/update", method = RequestMethod.POST)
    @ResponseBody
    private BasicEntity updateSb(@Valid TSbxx sbxx) {
        sbglService.updateSb(sbxx);
        return new BasicRes("修改设备成功");
    }

    @RequestMapping(value = "/sb/delete", method = RequestMethod.POST)
    @ResponseBody
    private BasicEntity deleteSb(@Valid TSbxx sbxx) {
        sbglService.deleteSb(sbxx);
        return new BasicRes("删除设备成功");
    }

    @RequestMapping(value = "/sb/{label}", method = RequestMethod.GET)
    @ResponseBody
    private BasicEntity selectSb(@PathVariable("label") String label,
                                 @RequestParam(name = "type", required = false) String type) {
        TSbxx sbxx = sbglService.selectSbByName(label);
        if ("detail".equalsIgnoreCase(type))
        {
            if (sbxx.getYmLabel() != null && !sbxx.getYmLabel().isEmpty())
            {
                TYmxx ymxx = ymglService.selectYmByName(sbxx.getYmLabel());
                if (ymxx != null) {
                    sbxx.setYmContent(ymxx.getContent());
                    sbxx.setYmUpdateTime(ymxx.getUpdateTime());
                }
            }
        }
        return sbxx;
    }
}
