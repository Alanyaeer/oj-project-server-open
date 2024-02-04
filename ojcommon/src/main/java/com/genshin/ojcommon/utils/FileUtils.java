package com.genshin.ojcommon.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author 吴嘉豪
 * @date 2024/1/21 21:26
 */
public class FileUtils {
    public static String getPrefix(String filePath){
        return filePath.substring(0, filePath.lastIndexOf("."));
    }
    public static void  delFileAndExecutable(String filePath){
        delFile(filePath);
        String prefix = getPrefix(filePath) + ".exe";
        delFile(prefix);
    }
    public static void delFile(String filePath){
        // 将文件路径转换为Path对象
        Path path = Paths.get(filePath);

        try {
            // 使用Files.delete方法删除文件
            Files.delete(path);
        } catch (IOException e) {
            // 处理可能出现的异常
        }
    }
    public static  void delFileAndClass(String filePath){
        delFile(filePath);
        String prefix =  getPrefix(filePath) + ".class";
        delFile(prefix);
    }
}
