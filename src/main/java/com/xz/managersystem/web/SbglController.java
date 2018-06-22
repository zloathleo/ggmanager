package com.xz.managersystem.web;

import com.xz.managersystem.dao.TablePageParams;
import com.xz.managersystem.dto.req.BasicTableReq;
import com.xz.managersystem.dto.res.BasicRes;
import com.xz.managersystem.dto.res.BasicTableRes;
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
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

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

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    private String searchSb(Model model) {
        return "/Views/sbgl/search";
    }

    @RequestMapping(value = "/load_list", method = RequestMethod.POST)
    @ResponseBody
    private BasicTableRes<TSbxx> loadList(@Valid BasicTableReq tr) {
        logger.info(tr.toString());
        List<TSbxx> list = sbglService.selectPage(new TablePageParams((tr.getPage() - 1) * tr.getRows(), tr.getRows()));
        int total = sbglService.getVisibleCount();
        return new BasicTableRes<>(total, list);
    }

    @RequestMapping(value = "/add_item", method = RequestMethod.POST)
    @ResponseBody
    private BasicRes addItem(@Valid TSbxx item) {
        TSbxx exist = sbglService.findOneByName(item.getLabel());
        if (exist != null) {
            throw new EntityExistsException("设备" + item.getLabel() + "已经存在");
        }

        int count = sbglService.insert(item);
        if (count < 1) {
            throw new EntityNotFoundException("新增设备" + item.getLabel() + "失败");
        }
        return new BasicRes("操作成功");
    }

    @RequestMapping(value = "/update_item", method = RequestMethod.POST)
    @ResponseBody
    private BasicRes updateItem(@Valid TSbxx item) {
        int count = sbglService.updateByPrimaryKeySelective(item);
        if (count < 1) {
            throw new EntityNotFoundException("修改设备" + item.getLabel() + "失败");
        }
        return new BasicRes("操作成功");
    }

    @RequestMapping(value = "/delete_item", method = RequestMethod.POST)
    @ResponseBody
    private BasicRes deleteItem(@Valid TSbxx item) {
        sbglService.deleteById(item.getId());
        return new BasicRes("操作成功");
    }

    @RequestMapping(value = "/open_operate", method = RequestMethod.GET)
    private String openOperate(Model model,
                               @RequestParam(name = "cmd", required = false) String cmd,
                               @RequestParam(name = "itemId", required = false) Integer itemId) {
        List<TFzxx> fzList = fzglService.selectVisibleAll();
        List<TYmxx> ymList = ymglService.selectVisibleAll();
        TSbxx item = sbglService.findOne(itemId);

        if ("add".equalsIgnoreCase(cmd)) {
            model.addAttribute("count", sbglService.getAllCount() + 1);
            model.addAttribute("fzs", fzList);
            model.addAttribute("yms", ymList);
            return "/Views/sbgl/add";
        } else if ("edit".equalsIgnoreCase(cmd)) {
            model.addAttribute("item", item);
            model.addAttribute("fzs", fzList);
            model.addAttribute("yms", ymList);
            return "/Views/sbgl/edit";
        } else {
            model.addAttribute("item", item);
            return "/Views/sbgl/delete";
        }
    }
}
