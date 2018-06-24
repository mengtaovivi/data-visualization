package com.taikang.dataVis.screen.service;


/** 
* @ClassName: AlarmCoverageService 
* @Description: (告警监控范围所有接口) 
* @author 郭德才  
* @date 2018年4月11日 下午3:22:04 
*  
*/
public interface AlarmCoverageService {

	boolean syncInstanceAlarmCoverage(String[] osSystems, String ip);

	boolean syncRdbAlarmCoverage(String[] osSystems, String ip);

}
