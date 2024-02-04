package com.genshin.ojcommon.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2024/1/5 21:07
 */
public class LanguagePackUtils {
    private final static List<String> languages = new ArrayList<>();
    static {
        languages.add("CPP");
        languages.add("JAVA");
        languages.add("GO");
    }
    public static List<String> getLanuageList(){
        return languages;
    }
}
