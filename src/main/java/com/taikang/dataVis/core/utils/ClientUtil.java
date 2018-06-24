package com.taikang.dataVis.core.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 
 * 项目名称:	[cus]
 * 包:		[com.taikang.cus.core.utils]
 * 类名称:	[ClientUtil]
 * 类描述:	[常用的一些通用方法]
 * 创建人:	[itw_liangbo01]
 * 创建时间:	[2018年3月23日 下午1:13:07]
 * 修改人:	[itw_liangbo01]
 * 修改时间:	[2018年3月23日 下午1:13:07]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 *
 */
public class ClientUtil {
	/**
	 * 
	 * @MethodName: getRequestPayload
	 * @Description: 从payload中取出参数
	 * @param request
	 * @return
	 * @throws IOException
	 * @return String
	 * @throws
	 *
	 */
    public static String getRequestPayload(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader((ServletInputStream) request.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			if (sb.length() == 0) {
				return "";
			}
		} catch (Exception e) {
			return "";
		}
		return sb.toString();
	}
    /**
     * 
     * @MethodName: isJson
     * @Description: 校验string是否是json格式的数据
     * @param jsonString
     * @return
     * @return boolean
     * @throws
     *
     */
	public static boolean isJson(String jsonString) {
		boolean result = false;
		if (jsonString == null) {
			return result;
		} else {
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(jsonString);
			boolean isJsonArray = element.isJsonArray();
			boolean isJsonObject = element.isJsonObject();
			result = isJsonArray || isJsonObject;
		}
		return result;
	}
    /**
     * 毫秒转换
     * @MethodName: getTime
     * @param time
     * @return
     * @return String
     * @throws
     */
    public static String getTime(long time) {
    	String result = "";
		if(time>=0 && time<1000){
			result = time + "毫秒";
		}else if(time>=1000 && time<60000){
			long seconds = (time % (1000 * 60)) / 1000;
			result = String.valueOf(seconds) + "秒";
		}else if(time>=60000){
			long minutes = (time % (1000 * 60 * 60)) / (1000 * 60);
	        long seconds = (time % (1000 * 60)) / 1000;
	        result = String.valueOf(minutes) + "分" + String.valueOf(seconds) + "秒";
		}
		return result;
    }
    /**
     * 数据分割
     * @MethodName: getPage
     * @param total
     * @return
     * @return Map<Integer,Integer>
     * @throws
     */
	public static Map<Integer, Integer> getPage(int total ,int page_size) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		int page = total / page_size;
		int plus = total % page_size;
		for (int i = 0; i < page; i++) {
//			System.out.println("page: " + (i + 1) + "   " + page_size);
			map.put((i + 1), page_size);
		}
		if (plus != 0) {
//			System.out.println("page: " + (page + 1) + "   " + page_size);
			map.put((page + 1), page_size);
		}
		return map;
	}
	/**
	 * 生成32位uuid
	 * @MethodName: getUUID32
	 * @return
	 * @return String
	 * @throws
	 */
	public static String getUUID32() {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return uuid;
	}
}
