package com.xz.managersystem.web;

import com.xz.managersystem.dao.TablePageParams;
import com.xz.managersystem.dto.req.BasicTableReq;
import com.xz.managersystem.dto.req.TDeviceAdd;
import com.xz.managersystem.dto.res.BasicTableRes;
import com.xz.managersystem.dto.res.SelectOp;
import com.xz.managersystem.entity.TDevice;
import com.xz.managersystem.entity.TGgmb;
import com.xz.managersystem.entity.TGgym;
import com.xz.managersystem.entity.TResource;
import com.xz.managersystem.service.GgmbService;
import com.xz.managersystem.service.GgymService;
import com.xz.managersystem.service.SbglService;
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
    GgymService ggymService;

    @Autowired
    GgmbService ggmbService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    private String search(Model model) {
        return "/Views/sbgl/search";
    }

    @RequestMapping(value = "/loadList", method = RequestMethod.POST)
    @ResponseBody
    private BasicTableRes<TDevice> loadList(@Valid BasicTableReq tr) {
        logger.info(tr.toString());
        List<TDevice> list = sbglService.selectPage(new TablePageParams((tr.getPage() - 1) * tr.getRows(), tr.getRows()));
        for (TDevice device : list) {
            Integer ymId = device.getGgymId();
            TGgym ym = ggymService.findOne(ymId);
            Integer mbId = ym.getGgmbId();
            TGgmb mb = ggmbService.findOne(mbId);
            device.setYmLabel(ym.getLabel());
            device.setMbId(mbId);
            device.setMbLabel(mb.getLabel());
        }
        int total = sbglService.getVisibleCount();
        return new BasicTableRes<TDevice>(total, list);
    }

    @RequestMapping(value = "/add_item", method = RequestMethod.POST)
    @ResponseBody
    private TDevice addItem(@Valid TDeviceAdd add) {
        TDevice one = new TDevice();
        one.setLabel(add.getLabel());
        one.setDes(add.getDes());
        one.setGgymId(add.getGgymId());
        one.setLocation(add.getLocation());

        TGgym ggym = ggymService.findOne(add.getGgymId());
        if (ggym == null) {
            throw new RuntimeException("页面ID ->" + add.getGgymId() + " 不存在");
        }

        TDevice exist = sbglService.findOneByName(add.getLabel());
        if (exist != null) {
            throw new EntityExistsException("名称 ->" + add.getLabel() + " 已经存在");
        }
        int count = sbglService.insert(one);
        TDevice result = sbglService.findOneByName(add.getLabel());
        return result;
    }

    @RequestMapping(value = "/update_item", method = RequestMethod.POST)
    @ResponseBody
    private TDevice updateItem(@Valid TDeviceAdd add) {
        TDevice one = new TDevice();
        one.setId(add.getId());
        one.setLabel(add.getLabel());
        one.setDes(add.getDes());
        one.setGgymId(add.getGgymId());
        one.setLocation(add.getLocation());

        int count = sbglService.updateByPrimaryKeySelective(one);
        TDevice result = sbglService.findOne(add.getId());
        return result;
    }

    @RequestMapping(value = "/delete_item", method = RequestMethod.POST)
    @ResponseBody
    private TDevice deleteItem(@Valid TDeviceAdd add) {
        int count = sbglService.deleteById(add.getId());
        TDevice result = sbglService.findOne(add.getId());
        return result;
    }

    @RequestMapping(value = "/open_operate", method = RequestMethod.GET)
    private String openOperate(Model model, @RequestParam(name = "cmd", required = false) String cmd, @RequestParam(name = "itemId", required = false) Integer itemId) {
        List<TGgym> ggymList = ggymService.selectVisibleAll();
        Stream<SelectOp> selectOpStream = ggymList.stream().map(n -> new SelectOp(n.getId(), n.getLabel()));
        List<SelectOp> selectOpList = selectOpStream.collect(Collectors.toList());

        if ("add".equalsIgnoreCase(cmd)) {
            int count = sbglService.getAllCount() + 1;
            model.addAttribute("count", count);
            model.addAttribute("selectOpList", selectOpList);
            return "/Views/sbgl/add";
        } else if ("edit".equalsIgnoreCase(cmd)) {
            TDevice one = sbglService.findOne(itemId);
            model.addAttribute("item", one);
            model.addAttribute("selectOpList", selectOpList);
            return "/Views/sbgl/edit";
        } else if ("delete".equalsIgnoreCase(cmd)) {
            TDevice one = sbglService.findOne(itemId);
            model.addAttribute("item", one);
            return "/Views/sbgl/delete";
        }
        return "/Views/sbgl/add";
    }

}
