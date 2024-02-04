package com.genshin.ojcommon.utils;
 
/**
 * @description: 判断运行的系统是windows还是linux
 * @author: zhangyu
 */
public class JudgeSystem {
    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }
 
    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }
}