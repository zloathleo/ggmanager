package com.xz.managersystem.web;

import com.xz.managersystem.dao.TablePageParams;
import com.xz.managersystem.dto.req.BasicTableReq;
import com.xz.managersystem.dto.res.BasicTableRes;
import com.xz.managersystem.entity.TMbxx;
import com.xz.managersystem.entity.TYmxx;
import com.xz.managersystem.service.MbglService;
import com.xz.managersystem.service.YmglService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

/**
 * 页面
 */
@Controller
@RequestMapping("/Views/ymgl")
public class YmglController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("#{config['resource.url.path']}")
    private String resourceUrlPath;

    @Autowired
    MbglService mbglService;

    @Autowired
    YmglService ymglService;

    @RequestMapping(value = "/search_ym", method = RequestMethod.GET)
    private String searchYm(Model model) {
        model.addAttribute("resourceUrlPath", resourceUrlPath);
        return "/Views/ymgl/search_ym";
    }

    @RequestMapping(value = "/search_mb", method = RequestMethod.GET)
    private String searchMb(Model model) {
        return "/Views/ymgl/search_mb";
    }

    @RequestMapping(value = "/load_ym", method = RequestMethod.POST)
    @ResponseBody
    private BasicTableRes<TYmxx> loadYm(@Valid BasicTableReq tr,
                                        @RequestParam(name = "type", required = false) String type) {
        logger.info(tr.toString());
        List<TYmxx> list = ymglService.selectPage(new TablePageParams((tr.getPage() - 1) * tr.getRows(), tr.getRows()));
        int total = ymglService.getVisibleCount();
        return new BasicTableRes<TYmxx>(total, list);
    }

    @RequestMapping(value = "/load_mb", method = RequestMethod.POST)
    @ResponseBody
    private BasicTableRes<TMbxx> loadMb(@Valid BasicTableReq tr) {
        logger.info(tr.toString());
        List<TMbxx> list = mbglService.selectPage(new TablePageParams((tr.getPage() - 1) * tr.getRows(), tr.getRows()));
        int total = mbglService.getVisibleCount();
        return new BasicTableRes<TMbxx>(total, list);
    }

    @RequestMapping(value = "/add_item", method = RequestMethod.POST)
    @ResponseBody
    private TYmxx addItem(@Valid TYmxx item) {
        TYmxx exist = ymglService.findOneByName(item.getLabel());
        if (exist != null) {
            throw new EntityExistsException("页面" + item.getLabel() + "已经存在");
        }

        ymglService.insert(item);
        TYmxx one = ymglService.findOneByName(item.getLabel());
        if (one == null) {
            throw new EntityNotFoundException("添加页面" + item.getLabel() + "失败");
        }
        return one;
    }

    @RequestMapping(value = "/update_item", method = RequestMethod.POST)
    @ResponseBody
    private TYmxx updateItem(@Valid TYmxx item) {
        ymglService.updateByPrimaryKeySelective(item);
        TYmxx one = ymglService.findOne(item.getId());
        if (one == null) {
            throw new EntityNotFoundException("修改页面" + item.getLabel() + "失败");
        }
        return one;
    }

    @RequestMapping(value = "/delete_item", method = RequestMethod.POST)
    @ResponseBody
    private TYmxx deleteItem(@Valid TYmxx item) {
        ymglService.deleteById(item.getId());
        return ymglService.findOne(item.getId());
    }

    @RequestMapping(value = "/open_operate", method = RequestMethod.GET)
    private String openOperate(Model model,
                               @RequestParam(name = "cmd", required = false) String cmd,
                               @RequestParam(name = "itemId", required = false) Integer itemId) {
        if ("add".equalsIgnoreCase(cmd)) {
            int count = ymglService.getAllCount() + 1;
            model.addAttribute("count", count);

            List<TMbxx> mbList = mbglService.selectVisibleAll();
            model.addAttribute("mbs", mbList);
            return "/Views/ymgl/add_ym";
        } else if ("edit".equalsIgnoreCase(cmd)) {
            TYmxx ymxx = ymglService.findOne(itemId);
            model.addAttribute("item", ymxx);

            List<TMbxx> mbList = mbglService.selectVisibleAll();
            model.addAttribute("mbs", mbList);

            return "/Views/ymgl/edit_ym";
        } else if ("delete".equalsIgnoreCase(cmd)) {
            TYmxx ggym = ymglService.findOne(itemId);
            model.addAttribute("item", ggym);
            return "/Views/ymgl/delete_ym";
        }
        return "/Views/ymgl/add_ym";
    }
}
