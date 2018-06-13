package com.xz.managersystem.web;

import com.xz.managersystem.dao.TablePageParams;
import com.xz.managersystem.dto.req.BasicTableReq;
import com.xz.managersystem.dto.req.GgymAdd;
import com.xz.managersystem.dto.res.BasicTableRes;
import com.xz.managersystem.entity.TGgym;
import com.xz.managersystem.service.GgymService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;
import java.util.List;

/**
 * 页面
 */
@Controller
public class GgymController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GgymService ggymService;

    //对外
    @RequestMapping(value = "/ym/{id}", method = RequestMethod.GET)
    @ResponseBody
    private TGgym getYm(@PathVariable("id") String id) {
        Integer oneId = Integer.parseInt(id);
        return ggymService.findOne(oneId);
    }

    @RequestMapping(value = "/Views/ggym/search", method = RequestMethod.GET)
    private String search(Model model) {
        return "/Views/ggym/search";
    }

    @RequestMapping(value = "/Views/ggym/loadList", method = RequestMethod.POST)
    @ResponseBody
    private BasicTableRes<TGgym> loadList(@Valid BasicTableReq tr) {
        logger.info(tr.toString());
        List<TGgym> list = ggymService.selectPage(new TablePageParams((tr.getPage() - 1) * tr.getRows(), tr.getRows()));
        int total = ggymService.getVisibleCount();
        return new BasicTableRes<TGgym>(total, list);
    }

    @RequestMapping(value = "/Views/ggym/add_item", method = RequestMethod.POST)
    @ResponseBody
    private TGgym addItem(@Valid GgymAdd add) {
        TGgym one = new TGgym();
        one.setLabel(add.getName());
        one.setStype(add.getStype());
        one.setVideoUrls(add.getVideoUrls());
        one.setImgUrls(add.getImgUrls());
        one.setDes(add.getDesc());
        one.setTextMsg(add.getTextMsg());

        TGgym exist = ggymService.findOneByName(add.getName());
        if (exist != null) {
            throw new EntityExistsException("名称 ->" + add.getName() + " 已经存在");
        }
        int count = ggymService.insert(one);
        TGgym result = ggymService.findOneByName(add.getName());
        return result;
    }

    @RequestMapping(value = "/Views/ggym/update_item", method = RequestMethod.POST)
    @ResponseBody
    private TGgym updateItem(@Valid GgymAdd add) {
        TGgym one = new TGgym();
        one.setId(add.getId());
        one.setStype(add.getStype());
        one.setVideoUrls(add.getVideoUrls());
        one.setImgUrls(add.getImgUrls());
        one.setDes(add.getDesc());
        one.setTextMsg(add.getTextMsg());

        int count = ggymService.updateByPrimaryKeySelective(one);
        TGgym result = ggymService.findOne(add.getId());
        return result;
    }

    @RequestMapping(value = "/Views/ggym/delete_item", method = RequestMethod.POST)
    @ResponseBody
    private TGgym deleteItem(@Valid GgymAdd add) {
        int count = ggymService.deleteById(add.getId());
        TGgym result = ggymService.findOne(add.getId());
        return result;
    }

    @RequestMapping(value = "/Views/ggym/open_operate", method = RequestMethod.GET)
    private String openOperate(Model model, @RequestParam(name = "cmd", required = false) String cmd, @RequestParam(name = "itemId", required = false) Integer itemId) {
      
        if ("add".equalsIgnoreCase(cmd)) {
            int count = ggymService.getAllCount() + 1;
            model.addAttribute("count", count);
            return "/Views/ggym/add";
        } else if ("edit".equalsIgnoreCase(cmd)) {
            TGgym ggym = ggymService.findOne(itemId);
            model.addAttribute("item", ggym);
            return "/Views/ggym/edit";
        } else if ("delete".equalsIgnoreCase(cmd)) {
            TGgym ggym = ggymService.findOne(itemId);
            model.addAttribute("item", ggym);
            return "/Views/ggym/delete";
        }
        return "/Views/ggym/add";
    }

}
