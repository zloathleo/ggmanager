package com.xz.managersystem.service;

import com.xz.managersystem.entity.TZyxx;
import com.xz.managersystem.entity.TZbxx;
import com.xz.managersystem.dao.ZyMapper;
import com.xz.managersystem.dao.ZbMapper;
import com.xz.managersystem.dao.TablePageParams;
import com.xz.managersystem.dto.req.BasicTableReq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.*;
import java.awt.Image;
import java.awt.Color;
import java.awt.image.*;
import java.awt.Graphics;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class ZyglService {

    @Value("#{config['resource.path']}")
    private String resourcePath;

    @Value("#{config['ffmpeg.path']}")
    private String ffmpegPath;

    @Autowired
    ZyMapper zyMapper;

    @Autowired
    ZbMapper zbMapper;

    public int insertZy(MultipartFile multipartFile, String des) {
        String fileType;
        String fileName = multipartFile.getOriginalFilename();
        int pos = fileName.lastIndexOf('.');
        String fileSuffix = pos == -1 ? "" : fileName.substring(pos);
        if (".jpg".equalsIgnoreCase(fileSuffix) || ".png".equalsIgnoreCase(fileSuffix) || ".bmp".equalsIgnoreCase(fileSuffix)) {
            fileType = "img";
        } else if (".mp4".equalsIgnoreCase(fileSuffix) || ".flv".equalsIgnoreCase(fileSuffix)) {
            fileType = "video";
        } else if (".mp3".equalsIgnoreCase(fileSuffix)) {
            fileType = "audio";
        } else {
            throw new RuntimeException("不支持的文件格式");
        }

        String label = fileType.toUpperCase() + "_" + new Date().getTime();
        String localName = label + fileSuffix;
        String previewName = localName;
        try {
            Path path = Paths.get(resourcePath, localName);
            File file = path.toFile();
            //该方法首先进行重命名，如果不成功则进行流拷贝，如果成功则可以省下一次读、写操作
            multipartFile.transferTo(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if ("img".equalsIgnoreCase(fileType)) {
            thumbnailImage(resourcePath + localName, 300, 300, "Thumbnail_", false);
            previewName = "Thumbnail_" + localName;
        } else if ("video".equalsIgnoreCase(fileType)) {
            previewName = "Thumbnail_" + label + ".jpg";
            String srcFile = resourcePath + localName;
            String destFile = resourcePath + previewName;
            thumbnailVideo(ffmpegPath, srcFile, destFile, 300, 300);
        }

        TZyxx zyxx = new TZyxx();
        zyxx.setLabel(label);
        zyxx.setUrl(localName);
        zyxx.setPreview(previewName);
        zyxx.setDes(des);
        zyxx.setType(fileType);
        return zyMapper.insert(zyxx);
    }

    public int updateZy(TZyxx zyxx) {
        if (zyxx.getLabel() == null || zyxx.getLabel().trim().isEmpty()) {
            throw new RuntimeException("资源名称不能为空");
        }

        TZyxx zyExist = zyMapper.selectByName(zyxx.getLabel());
        if (zyExist == null) {
            throw new EntityNotFoundException("资源" + zyxx.getLabel() + "不存在");
        }

        if (zyxx.getDes() != null) {
            zyExist.setDes(zyxx.getDes());
        }
        return zyMapper.updateByName(zyExist);
    }

    public int deleteZy(TZyxx zyxx) {
        if (zyxx.getLabel() == null || zyxx.getLabel().trim().isEmpty()) {
            throw new RuntimeException("资源名称不能为空");
        }
        return zyMapper.deleteByName(zyxx.getLabel());
    }

    public TZyxx selectZyByName(String label) {
        return zyMapper.selectByName(label);
    }

    public int deleteZyByName(String label) {
        return zyMapper.deleteByName(label);
    }

    public List<TZyxx> selectZyList(String type) {
        if (type == null || "all".equalsIgnoreCase(type)) {
            return zyMapper.selectAll();
        }   else {
            return zyMapper.selectTypeAll(type);
        }
    }

    public List<TZyxx> selectZyPage(BasicTableReq tr) {
        TablePageParams pageParams = new TablePageParams();
        pageParams.setStart((tr.getPage() - 1) * tr.getRows());
        pageParams.setRows(tr.getRows());
        if (tr.getType() == null || "all".equalsIgnoreCase(tr.getType())) {
            return zyMapper.selectPage(pageParams);
        } else {
            pageParams.setType(tr.getType().toLowerCase());
            return zyMapper.selectTypePage(pageParams);
        }
    }

    public int selectZyCount(String type) {
        if (type == null || "all".equalsIgnoreCase(type)) {
            return zyMapper.getCount();
        }else{
            return zyMapper.getTypeCount(type);
        }
    }

    //////////////////////////////////////// 分割线 ////////////////////////////////////////

    public int insertZb(TZbxx zbxx) {
        if (zbxx.getLabel() == null || zbxx.getLabel().trim().isEmpty()) {
            throw new RuntimeException("直播信号名称不能为空");
        } else if (zbMapper.selectByName(zbxx.getLabel()) != null) {
            throw new EntityExistsException("直播信号" + zbxx.getLabel() + "已经存在");
        }
        return zbMapper.insert(zbxx);
    }

    public int updateZb(TZbxx zbxx) {
        if (zbxx.getLabel() == null || zbxx.getLabel().trim().isEmpty()) {
            throw new RuntimeException("设备名称不能为空");
        }

        TZbxx zbExist = zbMapper.selectByName(zbxx.getLabel());
        if (zbExist == null) {
            throw new EntityNotFoundException("设备" + zbxx.getLabel() + "不存在");
        }

        if (zbxx.getDes() != null) {
            zbExist.setDes(zbxx.getDes());
        }
        return zbMapper.updateByName(zbxx);
    }

    public int deleteZb(TZbxx zbxx) {
        if (zbxx.getLabel() == null || zbxx.getLabel().trim().isEmpty()) {
            throw new RuntimeException("直播信号名称不能为空");
        }
        return zbMapper.deleteByName(zbxx.getLabel());
    }

    public TZbxx selectZbByName(String label) {
        return zbMapper.selectByName(label);
    }

    public int deleteZbByName(String label) {
        return zbMapper.deleteByName(label);
    }

    public List<TZbxx> selectZbList() {
        return zbMapper.selectVisibleAll();
    }

    public List<TZbxx> selectZbPage(BasicTableReq tr) {
        return zbMapper.selectPage(new TablePageParams((tr.getPage() - 1) * tr.getRows(), tr.getRows()));
    }

    public int selectZbCount() {
        return zbMapper.getVisibleCount();
    }

    public void thumbnailImage(String imagePath, int w, int h, String prevfix, boolean force) {
        try {
            // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
            String suffix = imagePath.substring(imagePath.lastIndexOf(".") + 1);
            File imgFile = new File(imagePath);
            Image img = ImageIO.read(imgFile);
            if (!force) {
                // 根据原图与要求的缩略图比例，找到最合适的缩略图比例
                int width = img.getWidth(null);
                int height = img.getHeight(null);
                double wScale = (width * 1.0) / w;
                double hScale = (height * 1.0) / h;
                if (wScale > hScale) {
                    h = Integer.parseInt(new java.text.DecimalFormat("0").format((height * 1.0) / wScale));
                } else {
                    w = Integer.parseInt(new java.text.DecimalFormat("0").format(width * 1.0 / hScale));
                }
            }
            BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics g = bi.getGraphics();
            g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
            g.dispose();
            String p = imgFile.getPath();
            // 将图片保存在原目录并加上前缀
            ImageIO.write(bi, suffix, new File(p.substring(0, p.lastIndexOf(File.separator)) + File.separator + prevfix + imgFile.getName()));
        } catch (IOException e) {
        }
    }

    public void thumbnailVideo(String ffmpegPath, String upFilePath, String mediaPicPath, int w, int h) {
        try {
            StringBuffer sb = new StringBuffer();
            List<String> commend = new ArrayList<String>();
            commend.add(ffmpegPath);
            commend.add("-i");
            commend.add(upFilePath);

            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            builder.redirectErrorStream(true);
            Process p = builder.start();


            BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = buf.readLine()) != null) {
                sb.append(line);
            }
            p.waitFor();
            String strOutput = sb.toString();
            int pos1 = strOutput.indexOf("kb/s", strOutput.indexOf("Video:"));
            int pos2 = strOutput.lastIndexOf("x", pos1);
            int pos3 = strOutput.lastIndexOf(",", pos2);
            int pos4 = strOutput.indexOf(",", pos2);
            int width = Integer.parseInt(strOutput.substring(pos3 + 1, pos2).trim());
            int height = Integer.parseInt(strOutput.substring(pos2 + 1, pos4).trim());
            double wScale = (width * 1.0) / w;
            double hScale = (height * 1.0) / h;
            if (wScale > hScale) {
                h = Integer.parseInt(new java.text.DecimalFormat("0").format((height * 1.0) / wScale));
            } else {
                w = Integer.parseInt(new java.text.DecimalFormat("0").format((width * 1.0) / hScale));
            }
        } catch (Exception e) {
            return;
        }

        List<String> cutpic = new ArrayList<String>();
        cutpic.add(ffmpegPath);
        cutpic.add("-i");
        cutpic.add(upFilePath); // 同上（指定的文件即可以是转换为flv格式之前的文件，也可以是转换的flv文件）
        cutpic.add("-y");
        cutpic.add("-f");
        cutpic.add("image2");
        cutpic.add("-ss"); // 添加参数＂-ss＂，该参数指定截取的起始时间
        cutpic.add("5"); // 添加起始时间为第17秒
        cutpic.add("-t"); // 添加参数＂-t＂，该参数指定持续时间
        cutpic.add("0.001"); // 添加持续时间为1毫秒
        cutpic.add("-s"); // 添加参数＂-s＂，该参数指定截取的图片大小
        cutpic.add(String.valueOf(w) + "*" + String.valueOf(h)); // 添加截取的图片大小
        cutpic.add(mediaPicPath); // 添加截取的图片的保存路径

        boolean mark = true;
        ProcessBuilder builder = new ProcessBuilder();
        try {

            builder.command(cutpic);
            builder.redirectErrorStream(true);
            // 如果此属性为 true，则任何由通过此对象的 start() 方法启动的后续子进程生成的错误输出都将与标准输出合并，
            //因此两者均可使用 Process.getInputStream() 方法读取。这使得关联错误消息和相应的输出变得更容易
            builder.start();
        } catch (Exception e) {
            mark = false;
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
