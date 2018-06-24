package com.taikang.dataVis.screen.service;

/**
 * 项目名称:	[data-visualization]
 * 包:		[com.taikang.dataVis.screen.service]
 * 类名称:	[CmdbLoadbalancerService]
 * 创建人:	[itw_guodc]
 * 创建时间:	[2018年5月31日 下午1:46:15]
 * 修改人:	[itw_guodc]
 * 修改时间:	[2018年5月31日 下午1:46:15]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */ 
public interface CmdbLoadbalancerService {

	boolean syncLoadbalancerDetails(String ip);

}
