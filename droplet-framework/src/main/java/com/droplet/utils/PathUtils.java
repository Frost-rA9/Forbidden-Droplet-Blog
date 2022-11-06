package com.droplet.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 文件上传路径工具类
 */
public class PathUtils {
    public static String generateFilePath(String fileName) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd/");
        String datePath = simpleDateFormat.format(new Date());
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        int index = fileName.lastIndexOf(".");
        String fileType = fileName.substring(index);
        return datePath + uuid + fileType;
    }
}
