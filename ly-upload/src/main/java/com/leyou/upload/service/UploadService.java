package com.leyou.upload.service;

import com.leyou.upload.controller.UploadController;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import java.util.logging.Logger;

@Service
public class UploadService {
//    public static final Logger logger = LoggerFactory.getLogger(UploadController.class);
    private static final List<String> suffixes = Arrays.asList("image/png", "image/jpeg");
    public static final String uploadDir = "/Users/damon/up/learning/upload/image";
    public String uploadImage(MultipartFile file) {
        try {
            String url = "";
            // 1. 图片校验
            // 检查图片类型
            String type = file.getContentType();
            if (!suffixes.contains(type)) {
                return null;
            }
            // 校验图片内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                return null;
            }
            // 2. 保存图片
            // 生成保存目录
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // 保存图片
            file.transferTo(new File(dir, file.getOriginalFilename()));
            // 拼接图片地址
            url = uploadDir + "/" + file.getOriginalFilename();
            return url;
        } catch (Exception e) {
            return null;
        }
    }
}
