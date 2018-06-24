package com.taikang.dataVis.core.utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈GSON转换处理〉
 *
 * @author 王新亮
 * @date 2018/3/29
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class GsonUtil {

    /**
     * 〈获取GSON对象〉
     * 〈使用此GSON对象，将Json-string转换为Map时，可以避免整形自动转为浮点型的bug〉
     * @param []
     * @return  com.google.gson.Gson
     * @exception/throws [异常类型] [异常说明]
     * @since [起始版本]（可选）
     */
    public static Gson getGson(){
        final Gson gson = new GsonBuilder().
                registerTypeAdapter(Map.class, new JsonDeserializer<Map>() {
                    @Override
                    public Map deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        JsonObject jsonObject = json.getAsJsonObject();
                        Map p = new HashMap();
                        for (Map.Entry<String, JsonElement> e : jsonObject.entrySet()) {
                            if (e.getValue().isJsonPrimitive()) {
                                p.put(e.getKey(), e.getValue());
                            }
                            p.put(e.getKey(), e.getValue().getAsString());
                        }
                        return p;
                    }
                }).create();
        return gson;
    }
}
