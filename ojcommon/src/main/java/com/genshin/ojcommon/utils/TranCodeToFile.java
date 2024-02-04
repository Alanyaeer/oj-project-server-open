package com.genshin.ojcommon.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

/**
 * @author 吴嘉豪
 * @date 2024/1/17 22:40
 */
public class TranCodeToFile {
    public static  String tranCode (String code, String fileName){
        File file = new File(fileName);
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
                writer.write(code);
                System.out.println("写入代码到文件中");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        return fileName;
    }
}
