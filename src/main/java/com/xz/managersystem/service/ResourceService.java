package com.xz.managersystem.service;

import com.xz.managersystem.dao.ConditionParams;
import com.xz.managersystem.dao.ResourceMapper;
import com.xz.managersystem.entity.TImgSize;
import com.xz.managersystem.entity.TResourceInfo;
import freemarker.cache.URLTemplateLoader;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ResourceService {

    @Value("#{config['resource.path']}")
    private String resourcePath;

    @Value("#{config['ffmpeg.path']}")
    private String ffmpegPath;

    @Autowired
    ResourceMapper resMapper;

    public int getCount(ConditionParams params) {
        if (StringUtils.isBlank(params.getType())) {
            return resMapper.getCount(params.getGroup());
        }else{
            return resMapper.getTypeCount(params);
        }
    }

    public List<TResourceInfo> getResourceList(ConditionParams params) {
        if (StringUtils.isBlank(params.getType())) {
            if (params.getStart() == null || params.getRows() == null) {
                return resMapper.selectList(params.getGroup());
            } else {
                return resMapper.selectPage(params);
            }
        } else if ("media".equalsIgnoreCase(params.getType())) {
            if (params.getStart() == null || params.getRows() == null) {
                return resMapper.selectMediaList(params.getGroup());
            } else {
                return resMapper.selectMediaPage(params);
            }
        } else if ("pic".equalsIgnoreCase(params.getType())) {
            if (params.getStart() == null || params.getRows() == null) {
                return resMapper.selectPicList(params.getGroup());
            } else {
                return resMapper.selectPicPage(params);
            }
        } else {
            if (params.getStart() == null || params.getRows() == null) {
                return resMapper.selectTypeList(params);
            } else {
                return resMapper.selectTypePage(params);
            }
        }
    }

    public TResourceInfo getResource(String label) {
        if (StringUtils.isBlank(label)) {
            throw new RuntimeException("无效的资源名");
        }

        TResourceInfo resInfo = resMapper.selectByLabel(label);
        if (resInfo == null) {
            throw new RuntimeException("资源" + label +"不存在");
        }
        return resInfo;
    }

    public void addResource(MultipartFile multipartFile, TResourceInfo resInfo) {
        String fileName = multipartFile.getOriginalFilename();
        String fileSuffix = UtilTools.getFileSuffix(fileName);
        String fileType = UtilTools.getFileType(fileName);
        if (!"img".equalsIgnoreCase(fileType) && !"video".equalsIgnoreCase(fileType)) {
            throw new RuntimeException("不支持的文件格式");
        }

        String destPath = resourcePath + resInfo.getGroup() + "\\";
        String label = fileType.toUpperCase() + "_" + new Date().getTime();
        String name = label + "." + fileSuffix;
        String thumbnail = "";
        UtilTools.transferFile(multipartFile, destPath + name);

        if ("img".equalsIgnoreCase(fileType)) {
            thumbnailImage(destPath + name, 200, 150);
            thumbnail = "Thumbnail_" + name;
        } else if ("video".equalsIgnoreCase(fileType)) {
            thumbnail = "Thumbnail_" + label + ".jpg";
            String srcFile = destPath + name;
            thumbnailVideo(ffmpegPath, srcFile, 200, 150);
        }

        resInfo.setLabel(label);
        resInfo.setName(name);
        resInfo.setThumbnail(thumbnail);
        if (resInfo.getDes() == null)
            resInfo.setDes("");
        if (!"qr".equalsIgnoreCase(resInfo.getType()))
            resInfo.setType(fileType);
        if (resMapper.insert(resInfo) <= 0) {
            throw new RuntimeException("添加资源失败");
        }
    }

    public void updateResource(TResourceInfo resInfo) {
        TResourceInfo resBase = getResource(resInfo.getLabel());
        if (resInfo.getDes() != null)
            resBase.setDes(resInfo.getDes());
        if (resInfo.getUrl() != null)
            resBase.setUrl(resInfo.getUrl());
        if (resMapper.updateByLabel(resBase) <= 0) {
            throw new RuntimeException("更新资源" + resBase.getLabel() + "失败");
        }
    }

    public void deleteResource(String label){
        TResourceInfo resInfo = getResource(label);
        if (resMapper.deleteByLabel(label) <= 0) {
            throw new RuntimeException("删除资源" + label + "失败");
        }
    }

    private void thumbnailImage(String imagePath, int w, int h) {
        TImgSize imgSize = UtilTools.getImgSize(imagePath);
        UtilTools.getThumbnailSize(imgSize, w, h);
        Rectangle rect = UtilTools.getCutRect(imgSize, w, h);
        String thumbnailPath = UtilTools.getImgThumbnail(imagePath, imgSize, "temp_");
        String cutPath = UtilTools.getCutImg(thumbnailPath, rect);
        File tempFile =  new File(thumbnailPath);
        if (tempFile.exists())
            tempFile.delete();
    }

    private void thumbnailVideo(String ffmpegPath, String videoPath, int w, int h) {
        TImgSize imgSize = UtilTools.getVideoSize(videoPath, ffmpegPath);
        UtilTools.getThumbnailSize(imgSize, w, h);
        Rectangle rect = UtilTools.getCutRect(imgSize, w, h);
        String thumbnailPath = UtilTools.getVideoThumbnail(videoPath,ffmpegPath,imgSize,"temp_");
        String cutPath = UtilTools.getCutImg(thumbnailPath, rect);
        File tempFile =  new File(thumbnailPath);
        if (tempFile.exists())
            tempFile.delete();
//        try {
//            StringBuffer sb = new StringBuffer();
//            List<String> commend = new ArrayList<String>();
//            commend.add(ffmpegPath);
//            commend.add("-i");
//            commend.add(upFilePath);
//
//            ProcessBuilder builder = new ProcessBuilder();
//            builder.command(commend);
//            builder.redirectErrorStream(true);
//            Process p = builder.start();
//
//            BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            String line;
//            while ((line = buf.readLine()) != null) {
//                sb.append(line);
//            }
//            p.waitFor();
//            String strOutput = sb.toString();
//            int pos1 = strOutput.indexOf("kb/s", strOutput.indexOf("Video:"));
//            int pos2 = strOutput.lastIndexOf("x", pos1);
//            int pos3 = strOutput.lastIndexOf(",", pos2);
//            int pos4 = strOutput.indexOf(",", pos2);
//            if (pos4 == -1 || pos4 - pos2 > 5)
//                pos4 = strOutput.indexOf(" ", pos2);
//            int width = Integer.parseInt(strOutput.substring(pos3 + 1, pos2).trim());
//            int height = Integer.parseInt(strOutput.substring(pos2 + 1, pos4).trim());
//            double wScale = (width * 1.0) / w;
//            double hScale = (height * 1.0) / h;
//            if (wScale > hScale) {
//                h = Integer.parseInt(new java.text.DecimalFormat("0").format((height * 1.0) / wScale));
//            } else {
//                w = Integer.parseInt(new java.text.DecimalFormat("0").format((width * 1.0) / hScale));
//            }
//        } catch (Exception e) {
//            return;
//        }
//
//        List<String> cutpic = new ArrayList<String>();
//        cutpic.add(ffmpegPath);
//        cutpic.add("-i");
//        cutpic.add(upFilePath); // 同上（指定的文件即可以是转换为flv格式之前的文件，也可以是转换的flv文件）
//        cutpic.add("-y");
//        cutpic.add("-f");
//        cutpic.add("image2");
//        cutpic.add("-ss"); // 添加参数＂-ss＂，该参数指定截取的起始时间
//        cutpic.add("5"); // 添加起始时间为第17秒
//        cutpic.add("-t"); // 添加参数＂-t＂，该参数指定持续时间
//        cutpic.add("0.001"); // 添加持续时间为1毫秒
//        cutpic.add("-s"); // 添加参数＂-s＂，该参数指定截取的图片大小
//        cutpic.add(String.valueOf(w) + "*" + String.valueOf(h)); // 添加截取的图片大小
//        cutpic.add(mediaPicPath); // 添加截取的图片的保存路径
//
//        boolean mark = true;
//        ProcessBuilder builder = new ProcessBuilder();
//        try {
//
//            builder.command(cutpic);
//            builder.redirectErrorStream(true);
//            // 如果此属性为 true，则任何由通过此对象的 start() 方法启动的后续子进程生成的错误输出都将与标准输出合并，
//            //因此两者均可使用 Process.getInputStream() 方法读取。这使得关联错误消息和相应的输出变得更容易
//            builder.start();
//        } catch (Exception e) {
//            mark = false;
//            System.out.println(e);
//            e.printStackTrace();
//        }
    }
}
