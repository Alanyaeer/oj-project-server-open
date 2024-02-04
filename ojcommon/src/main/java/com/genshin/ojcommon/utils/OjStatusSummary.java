package com.genshin.ojcommon.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 吴嘉豪
 * @date 2023/12/30 15:50
 */
public class OjStatusSummary {
    private final static Map<Long, String> ojSummary = new HashMap<>();

    static {
        ojSummary.put(-1L, "Accepted");
        ojSummary.put(0L, "Wrong Answer");
        ojSummary.put(1L, "Runtime Error");
        ojSummary.put(2L, "Compile Error");
        ojSummary.put(3L, "Time Limit Exceed");
        ojSummary.put(4L, "Memory Limit Exceed");
        ojSummary.put(5L, "Presentation Error");
        ojSummary.put(6L, "Output Limit Exceed");
        ojSummary.put(7L, "Unknown Error");
    }
    public static String getOJStatus(Long id){
        return ojSummary.get(id);
    }
}
