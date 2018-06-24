package com.taikang.dataVis.screen.web.rest;


import com.taikang.dataVis.core.utils.GetRequestIP;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taikang.dataVis.screen.service.AlarmCoverageService;


/**
 * 监控覆盖度数据同步
 * 项目名称:	[cus]
 * 包:		[com.taikang.cus.cmp.web.rest]
 * 类名称:	[AlarmCoverageResource]
 * 创建人:	[itw_guodc]
 * 创建时间:	[2018年4月13日 上午9:25:32]
 * 修改人:	[itw_guodc]
 * 修改时间:	[2018年4月13日 上午9:25:32]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */ 
@RestController
@RequestMapping("/api")
public class AlarmCoverageResource {
	
	@Autowired
	private AlarmCoverageService alarmCoverageService;
	
	/** 
	* @Title: syncAlarmCoverage 
	* @Description: (监控覆盖度，主机数据同步) 
	* @param @param id
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	@GetMapping(value = "/sync/instance/alarm-coverage")
	public  boolean instanceAlarmCoverage(HttpServletRequest request,@RequestParam(required =false) String[] osSystems,@RequestParam(required =false) String ip) {
		if (ip==null ||ip.equals("")) {
			//获取客户端IP值
			ip= GetRequestIP.getLocalIp(request);
		}
		return alarmCoverageService.syncInstanceAlarmCoverage(osSystems,ip);
	}
	
	/** 
	* @Title: syncAlarmCoverage 
	* @Description: (监控覆盖度，主机数据同步) 
	* @param @param id
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws 
	*/
	@GetMapping(value = "/sync/rdb/alarm-coverage")
	public  boolean rdbAlarmCoverage(HttpServletRequest request,@RequestParam(required =false) String[] osSystems,@RequestParam(required =false) String ip) {
		if (ip==null ||ip.equals("")) {
			//获取客户端IP值
			ip=GetRequestIP.getLocalIp(request);
		}
		return alarmCoverageService.syncRdbAlarmCoverage(osSystems,ip);
	}
	
}
