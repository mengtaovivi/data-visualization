package com.taikang.dataVis.core.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 有关CMDB数据采集的工具类
 * 项目名称:	[data-visualization]
 * 包:		[com.taikang.dataVis.core.utils]
 * 类名称:	[CMDBUtil]
 * 创建人:	[itw_guodc]
 * 创建时间:	[2018年5月31日 下午1:57:44]
 * 修改人:	[itw_guodc]
 * 修改时间:	[2018年5月31日 下午1:57:44]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */ 
public class CMDBUtil {

	
	public static Map<String, String> getCmdbHeaderMap() {
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("host", "cmdb.easyops-only.com");
		headerMap.put("org", "1045");
		headerMap.put("user", "easyops");
		return headerMap;
	}
}
