package com.taikang.dataVis.screen.service;

import java.util.List;
import java.util.Map;

import com.taikang.dataVis.domain.CloudEquipment;

import net.sf.json.JSONArray;

/**
 * 项目名称:	[data-visualization]
 * 包:		[com.taikang.dataVis.screen.service]
 * 类名称:	[CloudEquipmentService]
 * 创建人:	[itw_guodc]
 * 创建时间:	[2018年5月28日 上午11:15:59]
 * 修改人:	[itw_guodc]
 * 修改时间:	[2018年5月28日 上午11:15:59]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */ 
public interface CloudEquipmentService {

	List<String> findAllDate();

	List<CloudEquipment> findNewestAll();

	JSONArray findAllByCreateTime(String json);

	Map<String, String> saveBatch(String json);

	Map<String, String> updateBatch(String json);

}
