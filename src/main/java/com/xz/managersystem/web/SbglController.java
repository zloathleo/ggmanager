package com.xz.managersystem.web;

import com.xz.managersystem.dao.TablePageParams;
import com.xz.managersystem.dto.req.BasicTableReq;
import com.xz.managersystem.dto.req.TDeviceAdd;
import com.xz.managersystem.dto.res.BasicTableRes;
import com.xz.managersystem.dto.res.SelectOp;
import com.xz.managersystem.entity.TSbxx;
import com.xz.managersystem.entity.TFzxx;
import com.xz.managersystem.entity.TYmxx;
import com.xz.managersystem.service.YmglService;
import com.xz.managersystem.service.SbglService;
import com.xz.managersystem.service.FzglService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 设备
 */
@Controller
@RequestMapping("/Views/sbgl")
public class SbglController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SbglService sbglService;

    @Autowired
    FzglService fzglService;

    @Autowired
    YmglService ymglService;

    @RequestMapping(value = "/search_sb", method = RequestMethod.GET)
    private String searchSb(Model model) {
        return "/Views/sbgl/search_sb";
    }

    @RequestMapping(value = "/search_fz", method = RequestMethod.GET)
    private String searchFz(Model model) {
        return "/Views/sbgl/search_fz";
    }

    @RequestMapping(value = "/load_sb", method = RequestMethod.POST)
    @ResponseBody
    private BasicTableRes<TSbxx> loadSb(@Valid BasicTableReq tr) {
        logger.info(tr.toString());
        List<TSbxx> list = sbglService.selectPage(new TablePageParams((tr.getPage() - 1) * tr.getRows(), tr.getRows()));
        int total = sbglService.getVisibleCount();
        return new BasicTableRes<TSbxx>(total, list);
    }

    @RequestMapping(value = "/load_fz", method = RequestMethod.POST)
    @ResponseBody
    private BasicTableRes<TFzxx> loadFz(@Valid BasicTableReq tr) {
        logger.info(tr.toString());
        List<TFzxx> list = fzglService.selectPage(new TablePageParams((tr.getPage() - 1) * tr.getRows(), tr.getRows()));
        int total = fzglService.getVisibleCount();
        return new BasicTableRes<TFzxx>(total, list);
    }

    @RequestMapping(value = "/add_item", method = RequestMethod.POST)
    @ResponseBody
    private TSbxx addItem(@Valid TSbxx add) {
        TSbxx exist = sbglService.findOneByName(add.getLabel());
        if (exist != null) {
            throw new EntityExistsException("名称 ->" + add.getLabel() + " 已经存在");
        }

        int count = sbglService.insert(add);
        TSbxx result = sbglService.findOneByName(add.getLabel());
        return result;
    }

    @RequestMapping(value = "/update_item", method = RequestMethod.POST)
    @ResponseBody
    private TSbxx updateItem(@Valid TDeviceAdd add) {
        TSbxx one = new TSbxx();
        one.setId(add.getId());
        one.setLabel(add.getLabel());
        one.setDes(add.getDes());
        one.setYmId(add.getGgymId());
        one.setLocation(add.getLocation());

        int count = sbglService.updateByPrimaryKeySelective(one);
        TSbxx result = sbglService.findOne(add.getId());
        return result;
    }

    @RequestMapping(value = "/delete_item", method = RequestMethod.POST)
    @ResponseBody
    private TSbxx deleteItem(@Valid TDeviceAdd add) {
        int count = sbglService.deleteById(add.getId());
        TSbxx result = sbglService.findOne(add.getId());
        return result;
    }

    @RequestMapping(value = "/open_operate", method = RequestMethod.GET)
    private String openOperate(Model model,
                               @RequestParam(name = "cmd", required = false) String cmd,
                               @RequestParam(name = "itemId", required = false) Integer itemId) {
        List<TFzxx> fzList = fzglService.selectVisibleAll();
        List<TYmxx> ymList = ymglService.selectVisibleAll();

        if ("add".equalsIgnoreCase(cmd)) {
            int count = sbglService.getAllCount() + 1;
            model.addAttribute("count", count);
            model.addAttribute("fzs", fzList);
            model.addAttribute("yms", ymList);
            return "/Views/sbgl/add";
        } else if ("edit".equalsIgnoreCase(cmd)) {
            TSbxx one = sbglService.findOne(itemId);
            model.addAttribute("item", one);
            model.addAttribute("fzs", fzList);
            model.addAttribute("yms", ymList);
            return "/Views/sbgl/edit";
        } else if ("delete".equalsIgnoreCase(cmd)) {
            TSbxx one = sbglService.findOne(itemId);
            model.addAttribute("item", one);
            return "/Views/sbgl/delete";
        }
        return "/Views/sbgl/add";
    }
}
