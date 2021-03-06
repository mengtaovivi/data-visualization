package com.taikang.dataVis.screen.web.rest;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taikang.dataVis.core.utils.GetRequestIP;
import com.taikang.dataVis.screen.service.CmdbLoadbalancerService;
import com.taikang.dataVis.screen.service.CmdbRDBService;

import io.swagger.annotations.ApiOperation;

/**
 * 项目名称:	[data-visualization]
 * 包:		[com.taikang.dataVis.screen.web.rest]
 * 类名称:	[CmdbRDBResource]
 * 创建人:	[itw_guodc]
 * 创建时间:	[2018年6月4日 下午4:06:24]
 * 修改人:	[itw_guodc]
 * 修改时间:	[2018年6月4日 下午4:06:24]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */ 
@RestController
@RequestMapping("/api")
public class CmdbRDBResource {

	@Autowired
	private CmdbRDBService cmdbRDBService;
	
	/** 
	* @Title: syncAlarmCoverage 
	* @Description: (监控覆盖度，数据库详情数据同步) 
	* @param @param id
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	@ApiOperation(value="数据库详情数据同步",tags={"CMDB相关数据采集"},notes="可传ip值，用于记录当前采集动作对应的ip-不传默认抓取HttpServletRequest的IP值")
	@GetMapping(value = "/sync/rdb/details")
	public  boolean syncrdbDetails(HttpServletRequest request,@RequestParam(required =false) String ip) {
		if (ip==null ||ip.equals("")) {
			//获取客户端IP值
			ip=GetRequestIP.getLocalIp(request);
		}
		return cmdbRDBService.syncrdbDetails(ip);
	}
}
