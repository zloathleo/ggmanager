package com.xz.managersystem.web;

import com.xz.managersystem.dao.TablePageParams;
import com.xz.managersystem.dto.req.BasicTableReq;
import com.xz.managersystem.dto.res.BasicRes;
import com.xz.managersystem.dto.res.BasicTableRes;
import com.xz.managersystem.entity.TFzxx;
import com.xz.managersystem.service.FzglService;
import com.xz.managersystem.service.YmglService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

/**
 * 分组
 */
@Controller
@RequestMapping("/Views/fzgl")
public class fzglController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    FzglService fzglService;

    @Autowired
    YmglService ymglService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    private String search(Model model) {
        return "/Views/fzgl/search";
    }

    @RequestMapping(value = "/load_list", method = RequestMethod.POST)
    @ResponseBody
    private BasicTableRes<TFzxx> loadList(@Valid BasicTableReq tr) {
        logger.info(tr.toString());
        List<TFzxx> list = fzglService.selectPage(new TablePageParams((tr.getPage() - 1) * tr.getRows(), tr.getRows()));
        int total = fzglService.getVisibleCount();
        return new BasicTableRes<>(total, list);
    }

    @RequestMapping(value = "/add_item", method = RequestMethod.POST)
    @ResponseBody
    private BasicRes addItem(@Valid TFzxx item) {
        TFzxx exist = fzglService.findOneByName(item.getLabel());
        if (exist != null) {
            throw new EntityExistsException("分组" + item.getLabel() + "已经存在");
        }

        int count = fzglService.insert(item);
        if (count < 1) {
            throw new EntityNotFoundException("新增分组" + item.getLabel() + "失败");
        }
        return new BasicRes("操作成功");
    }

    @RequestMapping(value = "/update_item", method = RequestMethod.POST)
    @ResponseBody
    private BasicRes updateItem(@Valid TFzxx item) {
        int count = fzglService.updateByPrimaryKeySelective(item);
        if (count < 1) {
            throw new EntityNotFoundException("修改设备" + item.getLabel() + "失败");
        }
        return new BasicRes("操作成功");
    }

    @RequestMapping(value = "/delete_item", method = RequestMethod.POST)
    @ResponseBody
    private BasicRes deleteItem(@Valid TFzxx item) {
        fzglService.deleteById(item.getId());
        return new BasicRes("操作成功");
    }

    @RequestMapping(value = "/open_operate", method = RequestMethod.GET)
    private String openOperate(Model model,
                               @RequestParam(name = "cmd", required = false) String cmd,
                               @RequestParam(name = "itemId", required = false) Integer itemId) {
        List<TFzxx> fzList = fzglService.selectVisibleAll();

        if ("add".equalsIgnoreCase(cmd)) {
            return "/Views/fzgl/add";
        } else if ("edit".equalsIgnoreCase(cmd)) {
            return "/Views/fzgl/edit";
        } else {
            return "/Views/fzgl/delete";
        }
    }
}
