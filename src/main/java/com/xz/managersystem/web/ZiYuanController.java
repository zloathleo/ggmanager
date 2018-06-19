package com.xz.managersystem.web;

import com.xz.managersystem.dto.req.BasicTableReq;
import com.xz.managersystem.dto.res.BasicRes;
import com.xz.managersystem.dto.res.BasicTableRes;
import com.xz.managersystem.entity.TResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/Views/ziyuan")
public class ZiYuanController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("#{config['resource.url.path']}")
    private String resourceUrlPath;

    @Value("#{config['resource.path']}")
    private String resourcePath;

    @RequestMapping(value = "/search_image", method = RequestMethod.GET)
    private String search_image(Model model) {
        model.addAttribute("resourceUrlPath", resourceUrlPath);
        return "/Views/ziyuan/search_image";
    }

    @RequestMapping(value = "/search_video", method = RequestMethod.GET)
    private String search_video(Model model) {
        model.addAttribute("resourceUrlPath", resourceUrlPath);
        return "/Views/ziyuan/search_video";
    }

    @RequestMapping(value = "/search_rt", method = RequestMethod.GET)
    private String search_rt(Model model) {
        model.addAttribute("resourceUrlPath", resourceUrlPath);
        return "/Views/ziyuan/search_rt";
    }

    @RequestMapping(value = "/loadImageList", method = RequestMethod.POST)
    @ResponseBody
    private BasicTableRes<TResource> loadImageList(@Valid BasicTableReq tr) {
        File dirFile = new File(resourcePath);
        if (!dirFile.isDirectory()) {
            throw new NullPointerException("资源目录不存在");
        }
        String[] fileList = dirFile.list();

        //过滤指定文件类型
        Stream<String> filterStream = Arrays.stream(fileList).filter(
                f -> f.toLowerCase().endsWith(".jpg") ||
                        f.toLowerCase().endsWith(".bmp") ||
                        f.toLowerCase().endsWith(".png")
        );

        //对象化
        Stream<TResource> resourcesStream = filterStream.map(n -> new TResource(n, n));
        List<TResource> result = resourcesStream.collect(Collectors.toList());
        return new BasicTableRes<TResource>(result.size(), result);
    }

    @RequestMapping(value = "/loadVideoList", method = RequestMethod.POST)
    @ResponseBody
    private BasicTableRes<TResource> loadList(@Valid BasicTableReq tr) {
        File dirFile = new File(resourcePath);
        if (!dirFile.isDirectory()) {
            throw new NullPointerException("资源目录不存在");
        }
        String[] fileList = dirFile.list();

        //过滤指定文件类型
        Stream<String> filterStream = Arrays.stream(fileList).filter(
                f -> f.toLowerCase().endsWith(".mp4") ||
                        f.toLowerCase().endsWith(".flv")
        );

        //对象化
        Stream<TResource> resourcesStream = filterStream.map(n -> new TResource(n, n));
        List<TResource> result = resourcesStream.collect(Collectors.toList());
        return new BasicTableRes<TResource>(result.size(), result);
    }

    @RequestMapping(value = "/open_operate", method = RequestMethod.GET)
    private String openOperate(Model model, @RequestParam(name = "cmd", required = false) String cmd, @RequestParam(name = "type", required = false) String type, @RequestParam(name = "url", required = false) String url) {
        if ("add".equalsIgnoreCase(cmd)) {
            if("image".equalsIgnoreCase(type)){
                return "/Views/ziyuan/image_add";
            }else{
                return "/Views/ziyuan/video_add";
            }
        } else if ("delete".equalsIgnoreCase(cmd)) {
            model.addAttribute("url", url);
            if("image".equalsIgnoreCase(type)){
                return "/Views/ziyuan/image_delete";
            }else{
                return "/Views/ziyuan/video_delete";
            }
        }
        return "/Views/ziyuan/add";
    }

    @RequestMapping(value = "/delete_item", method = RequestMethod.POST)
    @ResponseBody
    private BasicRes deleteItem(@Valid TResource res) {
        logger.info(res.getUrl());
        return new BasicRes("删除成功");
    }

    @RequestMapping(value = "/add_item", method = RequestMethod.POST)
    @ResponseBody
    private BasicRes addItem(@RequestParam(value = "uploadFile") MultipartFile multipartFile) {

        if (multipartFile != null) {
            String fileName = multipartFile.getOriginalFilename();
            String newFileName = fileName;
            String fileTyle = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            if (".jpg".equalsIgnoreCase(fileTyle) || ".bmp".equalsIgnoreCase(fileTyle) || ".png".equalsIgnoreCase(fileTyle)) {
                newFileName = "IMG_" + new Date().getTime() + fileTyle;
            } else if (".mp4".equalsIgnoreCase(fileTyle) || ".flv".equalsIgnoreCase(fileTyle)) {
                newFileName = "VIDEO_" + fileName;
            } else {
                throw new RuntimeException("不支持的文件格式");
            }

            try {
                Path path = Paths.get(resourcePath, newFileName);
                File file = path.toFile();
                //该方法首先进行重命名，如果不成功则进行流拷贝，如果成功则可以省下一次读、写操作
                multipartFile.transferTo(file);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        logger.info("上传资源");
        return new BasicRes("上传成功");
    }
}
