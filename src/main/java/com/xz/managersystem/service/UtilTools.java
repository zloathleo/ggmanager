package com.xz.managersystem.service;

import com.xz.managersystem.dao.ConditionParams;
import com.xz.managersystem.dto.req.BasicTableReq;
import com.xz.managersystem.entity.TImgSize;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class UtilTools {

    private static Logger logger = LoggerFactory.getLogger(UtilTools.class);
    public static String getFileSuffix(String filePath) {
        int pos = filePath.lastIndexOf('.');
        return pos == -1 ? "" : filePath.substring(pos + 1);
    }

    public static String getFileType(String filePath) {
        String fileSuffix = getFileSuffix(filePath);
        if ("jpg".equalsIgnoreCase(fileSuffix) ||
                "png".equalsIgnoreCase(fileSuffix) ||
                "bmp".equalsIgnoreCase(fileSuffix)) {
            return "img";
        } else if ("mp4".equalsIgnoreCase(fileSuffix) ||
                "flv".equalsIgnoreCase(fileSuffix)) {
            return "video";
        } else if ("mp3".equalsIgnoreCase(fileSuffix)) {
            return "audio";
        } else {
            return "";
        }
    }

    public static void transferFile(MultipartFile multipartFile, String destFile) {
        try {
            Path path = Paths.get(destFile);
            File file = path.toFile();
            multipartFile.transferTo(file);
        } catch (Exception e) {
            logger.error("transferFile 转移文件 exception",e);
            throw new RuntimeException(e);
        }
    }

    public static String getUUID(int length) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        return uuid.substring(0, length);
    }

    public static ConditionParams convertFromTabelReq(BasicTableReq tr, String group) {
        ConditionParams params = new ConditionParams();
        if (tr.getRows() != null && tr.getPage() != null) {
            params.setStart((tr.getPage() - 1) * tr.getRows());
            params.setRows(tr.getRows());
        }

        if (StringUtils.isBlank(tr.getType()) || "all".equalsIgnoreCase(tr.getType()))
            params.setType("");
        else
            params.setType(tr.getType());
        params.setGroup(group);
        params.setFilter(tr.getFiltergroup());
        return params;
    }

    public static TImgSize getImgSize(String imgPath) {
        try {
            File imgFile = new File(imgPath);
            Image img = ImageIO.read(imgFile);

            TImgSize imgSize = new TImgSize();
            imgSize.setWidth(img.getWidth(null));
            imgSize.setHeight(img.getHeight(null));
            return imgSize;
        } catch (IOException e) {
            logger.error("TImgSize获取视频异常 exception",e);
            return null;
        }
    }

    public static TImgSize getVideoSize(String videoPath, String ffmpegPath) {
        try {
            StringBuffer sb = new StringBuffer();
            List<String> commend = new ArrayList<String>();
            commend.add(ffmpegPath);
            commend.add("-i");
            commend.add(videoPath);

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
            if (pos4 == -1 || pos4 - pos2 > 5)
                pos4 = strOutput.indexOf(" ", pos2);

            TImgSize imgSize = new TImgSize();
            imgSize.setWidth(Integer.parseInt(strOutput.substring(pos3 + 1, pos2).trim()));
            imgSize.setHeight(Integer.parseInt(strOutput.substring(pos2 + 1, pos4).trim()));
            return imgSize;
        } catch (Exception e) {
            logger.error("getVideoSize获取视频异常 exception",e);
            return null;
        }
    }

    public static void getThumbnailSize(TImgSize imgSize, Integer destWidth, Integer destHeight) {
        double wScale = (imgSize.getWidth() * 1.0) / destWidth;
        double hScale = (imgSize.getHeight() * 1.0) / destHeight;
        if (wScale < hScale) {
            imgSize.setWidth(destWidth);
            imgSize.setHeight(Integer.parseInt(new java.text.DecimalFormat("0").format((imgSize.getHeight() * 1.0) / wScale)));
        } else {
            imgSize.setHeight(destHeight);
            imgSize.setWidth(Integer.parseInt(new java.text.DecimalFormat("0").format((imgSize.getWidth() * 1.0) / hScale)));
        }
    }

    public static Rectangle getCutRect(TImgSize imgSize, Integer destWidth, Integer destHeigth) {
        Rectangle rect = new Rectangle();
        rect.setRect(0, 0, imgSize.getWidth(), imgSize.getHeight());
        if (destWidth < imgSize.getWidth()) {
            rect.x = (imgSize.getWidth() - destWidth) / 2;
            rect.width = destWidth;
        }
        if (destHeigth < imgSize.getHeight()) {
            rect.y = (imgSize.getHeight() - destHeigth) / 2;
            rect.height = destHeigth;
        }
        return rect;
    }

    public static String getImgThumbnail(String imgPath, TImgSize imgSize, String prefix) {
        try {
            String suffix = imgPath.substring(imgPath.lastIndexOf(".") + 1);
            File imgFile = new File(imgPath);
            Image img = ImageIO.read(imgFile);
            BufferedImage bi = new BufferedImage(imgSize.getWidth(), imgSize.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics g = bi.getGraphics();
            g.drawImage(img, 0, 0, imgSize.getWidth(), imgSize.getHeight(), Color.LIGHT_GRAY, null);
            g.dispose();
            String p = imgFile.getPath();
            // 将图片保存在原目录并加上前缀
            String destPath = p.substring(0, p.lastIndexOf(File.separator)) + File.separator + prefix + imgFile.getName();
            ImageIO.write(bi, suffix, new File(destPath));
            return destPath;
        } catch (IOException e) {
            logger.error("getImgThumbnail 获取缩略图 exception",e);
            return "";
        }
    }

    public static String getVideoThumbnail(String videoPath, String ffmpegPath, TImgSize imgSize, String prefix) {
        String fileName = videoPath.substring(videoPath.lastIndexOf("\\") + 1);
        String fileSuffix = UtilTools.getFileSuffix(videoPath);
        String imgName = prefix + fileName;
        imgName = imgName.replace(fileSuffix, "jpg");
        String imgPath = videoPath.replace(fileName, imgName);

        List<String> cutpic = new ArrayList<String>();
        cutpic.add(ffmpegPath);
        cutpic.add("-i");
        cutpic.add(videoPath); // 同上（指定的文件即可以是转换为flv格式之前的文件，也可以是转换的flv文件）
        cutpic.add("-y");
        cutpic.add("-f");
        cutpic.add("image2");
        cutpic.add("-ss"); // 添加参数＂-ss＂，该参数指定截取的起始时间
        cutpic.add("5"); // 添加起始时间为第17秒
        cutpic.add("-t"); // 添加参数＂-t＂，该参数指定持续时间
        cutpic.add("0.001"); // 添加持续时间为1毫秒
        cutpic.add("-s"); // 添加参数＂-s＂，该参数指定截取的图片大小
        cutpic.add(String.valueOf(imgSize.getWidth()) + "*" + String.valueOf(imgSize.getHeight())); // 添加截取的图片大小
        cutpic.add(imgPath); // 添加截取的图片的保存路径

        ProcessBuilder builder = new ProcessBuilder();
        try {
            builder.command(cutpic);
            builder.redirectErrorStream(true);
            // 如果此属性为 true，则任何由通过此对象的 start() 方法启动的后续子进程生成的错误输出都将与标准输出合并，
            //因此两者均可使用 Process.getInputStream() 方法读取。这使得关联错误消息和相应的输出变得更容易
            Process p = builder.start();
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("getVideoThumbnail 获取视频缩略图 exception",e);
        }
        return imgPath;
    }

    public static Boolean convertVideo2MP4(String videoPath, String ffmpegPath) {
        try {
            StringBuffer sb = new StringBuffer();
            List<String> command = new ArrayList<String>();
            command.add(ffmpegPath);
            command.add("-i");
            command.add(videoPath);

            ProcessBuilder builder = new ProcessBuilder();
            builder.command(command);
            builder.redirectErrorStream(true);
            Process p = builder.start();

            BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = buf.readLine()) != null) {
                sb.append(line);
            }
            p.waitFor();
            String strOutput = sb.toString();
            int pos1 = strOutput.indexOf("h264");
            if (pos1 == -1)
            {
                String fileName = videoPath.substring(videoPath.lastIndexOf("\\") + 1);
                String fileTempName = "tmp_" + fileName;
                String videoTempPath = videoPath.replace(fileName, fileTempName);

                List<String> command1 = new ArrayList<String>();
                command1.add(ffmpegPath);
                command1.add("-loglevel");
                command1.add("quiet");
                command1.add("-i");
                command1.add(videoPath);
                command1.add("-vcodec");
                command1.add("h264");
                command1.add(videoTempPath);

                ProcessBuilder builder1 = new ProcessBuilder();
                builder1.command(command1);
                Process p1 = builder1.start();
                p1.waitFor();

                File videoFile = new File(videoPath);
                File videoTempFile = new File(videoTempPath);
                videoFile.delete();
                videoTempFile.renameTo(videoFile);
            }
        } catch (Exception e) {
            logger.error("转换视频格式失败:", e);
            return false;
        }
        return true;
    }

    public static String getCutImg(String imgPath, Rectangle rect) {
        FileInputStream inputStream = null;
        ImageInputStream imageStream = null;
        try {
            String cutPath = imgPath.replace("temp_", "thumbnail_");
            inputStream = new FileInputStream(imgPath);
            imageStream = ImageIO.createImageInputStream(inputStream);

            String suffix = getFileSuffix(imgPath);
            if (!"jpg".equalsIgnoreCase(suffix) && "bmp".equalsIgnoreCase(suffix) && "png".equalsIgnoreCase(suffix)) {
                return "";
            }

            ImageReader reader = ImageIO.getImageReadersBySuffix(suffix).next();
            reader.setInput(imageStream, true);
            ImageReadParam param = reader.getDefaultReadParam();
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            ImageIO.write(bi, suffix, new File(cutPath));
            return cutPath;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("getCutImg 获取getCutImg exception",e);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("getCutImg 获取getCutImg exception",e);
        } finally {
            try {
                imageStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("getCutImg 获取getCutImg exception",e);
            }
        }
        return "";
    }
}
