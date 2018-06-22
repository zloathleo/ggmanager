package com.xz.managersystem.web;

import com.xz.managersystem.entity.TYmxx;
import com.xz.managersystem.service.ZyglService;
import com.xz.managersystem.dao.TablePageParams;
import com.xz.managersystem.dto.req.BasicTableReq;
import com.xz.managersystem.dto.res.BasicRes;
import com.xz.managersystem.dto.res.BasicTableRes;
import com.xz.managersystem.entity.TZyxx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 资源
 */
@Controller
@RequestMapping("/Views/zygl")
public class ZyglController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("#{config['resource.url.path']}")
    private String resourceUrlPath;

    @Value("#{config['resource.path']}")
    private String resourcePath;

    @Autowired
    ZyglService zyglService;

    @RequestMapping(value = "/search_tp", method = RequestMethod.GET)
    private String search_tp(Model model) {
        model.addAttribute("resourceUrlPath", resourceUrlPath);
        return "/Views/zygl/search_tp";
    }

    @RequestMapping(value = "/search_sp", method = RequestMethod.GET)
    private String search_sp(Model model) {
        model.addAttribute("resourceUrlPath", resourceUrlPath);
        return "/Views/zygl/search_sp";
    }

    @RequestMapping(value = "/search_zb", method = RequestMethod.GET)
    private String search_zb(Model model) {
        return "/Views/zygl/search_zb";
    }

    @RequestMapping(value = "/search_wz", method = RequestMethod.GET)
    private String search_wz(Model model) {
        return "/Views/zygl/search_wz";
    }

    @RequestMapping(value = "/load_tp", method = RequestMethod.POST)
    @ResponseBody
    private BasicTableRes<TZyxx> loadTp(@Valid BasicTableReq tr) {
        logger.info(tr.toString());
        List<TZyxx> list = zyglService.selectPage(new TablePageParams((tr.getPage() - 1) * tr.getRows(), tr.getRows(), 0));
        int total = zyglService.getVisibleCount();
        return new BasicTableRes<TZyxx>(total, list);
    }

    @RequestMapping(value = "/load_sp", method = RequestMethod.POST)
    @ResponseBody
    private BasicTableRes<TZyxx> loadSp(@Valid BasicTableReq tr) {
        logger.info(tr.toString());
        List<TZyxx> list = zyglService.selectPage(new TablePageParams((tr.getPage() - 1) * tr.getRows(), tr.getRows(),1));
        int total = zyglService.getVisibleCount();
        return new BasicTableRes<TZyxx>(total, list);
    }

    @RequestMapping(value = "/load_zb", method = RequestMethod.POST)
    @ResponseBody
    private BasicTableRes<TZyxx> loadZb(@Valid BasicTableReq tr) {
        logger.info(tr.toString());
        List<TZyxx> list = zyglService.selectPage(new TablePageParams((tr.getPage() - 1) * tr.getRows(), tr.getRows(),2));
        int total = zyglService.getVisibleCount();
        return new BasicTableRes<TZyxx>(total, list);
    }

    @RequestMapping(value = "/load_wz", method = RequestMethod.POST)
    @ResponseBody
    private BasicTableRes<TZyxx> loadWz(@Valid BasicTableReq tr) {
        logger.info(tr.toString());
        List<TZyxx> list = zyglService.selectPage(new TablePageParams((tr.getPage() - 1) * tr.getRows(), tr.getRows(),3));
        int total = zyglService.getVisibleCount();
        return new BasicTableRes<TZyxx>(total, list);
    }

    @RequestMapping(value = "/open_operate", method = RequestMethod.GET)
    private String openOperate(Model model,
                               @RequestParam(name = "cmd", required = false) String cmd,
                               @RequestParam(name = "type", required = false) String type,
                               @RequestParam(name = "itemId", required = false) Integer itemId) {
        if ("add".equalsIgnoreCase(cmd)) {
            model.addAttribute("type", type);
            return "/Views/zygl/add";
        } else if ("edit".equalsIgnoreCase(cmd)) {
            TZyxx zyxx = zyglService.findOne(itemId);
            model.addAttribute("item", zyxx);
            model.addAttribute("type", type);
            return "/Views/zygl/edit";
        } else if ("delete".equalsIgnoreCase(cmd)) {
            TZyxx zyxx = zyglService.findOne(itemId);
            model.addAttribute("item", zyxx);
            return "/Views/zygl/delete";
        }
        return "/Views/zygl/add";
    }

    @RequestMapping(value = "/add_item", method = RequestMethod.POST)
    @ResponseBody
    private BasicRes addItem(@RequestParam(name = "file") MultipartFile multipartFile,
                             @RequestParam(name = "label", required = false) String label,
                             @RequestParam(name = "content", required = false) String content,
                             @RequestParam(name = "des", required = false) String des,
                             @RequestParam(name = "link", required = false) String link,
                             @RequestParam(name = "type", required = false) String type){
        String newLabel = label;
        String newLink = link;
        String newContent = content;
        Integer newType = 0;
        String fileName = multipartFile.getOriginalFilename();
        if (!fileName.isEmpty()) {
            String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            if (".jpg".equalsIgnoreCase(fileType) || ".bmp".equalsIgnoreCase(fileType) || ".png".equalsIgnoreCase(fileType)) {
                newLabel = "IMG_" + fileName;
            } else if (".mp4".equalsIgnoreCase(fileType) || ".flv".equalsIgnoreCase(fileType)) {
                newLabel = "VIDEO_" + fileName;
            } else {
                throw new RuntimeException("不支持的文件格式");
            }
            newContent = newLabel;

            try {
                Path path = Paths.get(resourcePath, newLabel);
                File file = path.toFile();
                //该方法首先进行重命名，如果不成功则进行流拷贝，如果成功则可以省下一次读、写操作
                multipartFile.transferTo(file);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        logger.info("上传资源");

        if (!newLink.isEmpty() && newLink.indexOf("http", 0) == -1)
            newLink = "https://" + newLink;

        if ("tp".equalsIgnoreCase(type))
            newType = 0;
        else if ("sp".equalsIgnoreCase(type))
            newType = 1;
        else if ("zb".equalsIgnoreCase(type))
            newType = 2;
        else
            newType = 3;

        TZyxx one = new TZyxx();
        one.setLabel(newLabel);
        one.setContent(newContent);
        one.setDes(des);
        one.setLink(newLink);
        one.setType(newType);
        zyglService.insert(one);
        return new BasicRes("上传成功");
    }

    @RequestMapping(value = "/update_item", method = RequestMethod.POST)
    @ResponseBody
    private BasicRes updateItem(@Valid TZyxx item) {
        if (!item.getLink().isEmpty() && item.getLink().indexOf("http", 0) == -1)
            item.setLink("https://" + item.getLink());
        zyglService.updateByPrimaryKeySelective(item);
        return new BasicRes("修改成功");
    }

    @RequestMapping(value = "/delete_item", method = RequestMethod.POST)
    @ResponseBody
    private BasicRes deleteItem(@Valid TZyxx item) {
        zyglService.deleteById(item.getId());
        return new BasicRes("删除成功");
    }
}
