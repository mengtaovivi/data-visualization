package com.taikang.dataVis.core.utils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author 王新亮
 * @date 2018/3/21
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class DictUtil {

    /**
     * 解析字典表的value值。
     * @param dictValue dict字典表的value值，value目前共三种形式. 第一种单值. 第二种多值以'&&||'分隔. 第三种json格式的字符串
     * @return 解析value字符串后得到List<String>或者List<Map>
     */
    public static List<Object> parseDictValue(String dictValue) {
        List<Object> result = new ArrayList<Object>();
        Gson gson = GsonUtil.getGson();
        String JSONRegex = "\\{{1}['\"]{1}.*['\"]{1}:{1}['\"]{1}.*['\"]{1}.*\\}{1}";    //JSON格式的正则
        String nomalRegex = ".*(&&\\|\\|){1}.*";    //&&||分隔的正则
        //匹配到JSON格式
        if(dictValue.matches(JSONRegex)){
            Map map = gson.fromJson(dictValue, Map.class);
            result.add(map);
            //匹配到&&||分隔符
        }else if(dictValue.matches(nomalRegex)){
            String[] values = dictValue.split("&&\\|\\|");
            for (String value : values) {
                result.add(value);
            }
            //单值
        }else{
            result.add(dictValue);
        }
        return result;
    }

}
