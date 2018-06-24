package com.taikang.dataVis.core.utils;

import java.util.Map;

/**
 * 〈登录状态下获取当前用户的信息〉
 *
 * @author 王新亮
 * @date 2018/4/13
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class UserMessage {

    public static ThreadLocal<Map<String, String>> userMessage = new ThreadLocal<Map<String, String>>();

    /**
     * 〈获取userId〉
     * @param []
     * @return  java.lang.String
     * @exception/throws [异常类型] [异常说明]
     * @since [起始版本]（可选）
     */
    public static String getCurrentUserId(){
        return userMessage.get().get("userId");
    }

    /**
     * 〈获取id〉
     * @param []
     * @return  java.lang.String
     * @exception/throws [异常类型] [异常说明]
     * @since [起始版本]（可选）
     */
    public static Long getCurrentId(){
        return Long.parseLong(userMessage.get().get("id"));
    }

    /**
     * 〈获取default_role〉
     * @param []
     * @return  java.lang.String
     * @exception/throws [异常类型] [异常说明]
     * @since [起始版本]（可选）
     */
    public static String getDefaultRole(){
        return userMessage.get().get("defaultRoleCode");
    }

    /**
     * 〈获取default_role的汉语名称〉
     * @param []
     * @return  java.lang.String
     * @exception/throws [异常类型] [异常说明]
     * @since [起始版本]（可选）
     */
    public static String getDefaultRoleName(){
        return userMessage.get().get("defaultRoleName");
    }

    /**
     * 〈获取keystonetoken〉
     * @param []
     * @return  java.lang.String
     * @exception/throws [异常类型] [异常说明]
     * @since [起始版本]（可选）
     */
    public static String getKeystoneToken(){
        return userMessage.get().get("keystonetoken");
    }

}
