package com.genshin.ojcommon.utils;

import com.alibaba.fastjson.JSON;
import com.genshin.ojcommon.domain.entity.SubmitRecords;
import com.genshin.ojcommon.domain.entity.User;

import java.util.List;

/**
 * @author 吴嘉豪
 * @date 2024/1/5 22:48
 */
public class JsonTransforUtils {

    public static  <T> List<T> JsonToArray(Class<T> type, String json){
        List<T> list = JSON.parseArray(json, type);
        return list;
    }
    public static <T> T JsonToObj(Class<T> type, String json){
        return   JSON.parseObject(json, type);
    }
    public static  <T> String ArrayToJson(List<T> list){
        return JSON.toJSONString(list);
    }

    public static <T> String ObjToJson(T newSubmit) {
        return JSON.toJSONString(newSubmit);
    }
}
