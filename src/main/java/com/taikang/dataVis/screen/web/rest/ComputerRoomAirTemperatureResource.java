package com.taikang.dataVis.screen.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taikang.dataVis.core.base.BaseResource;
import com.taikang.dataVis.screen.service.ComputerRoomAirTemperatureService;
import com.taikang.dataVis.screen.service.TemperatureAlarmService;

import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;

/**
 * 项目名称:	[cmdb-core]
 * 包:		[com.taikang.dataVis.screen.web.rest]
 * 类名称:	[ComputerRoomAirTemperatureResource]
 * 创建人:	[itw_guodc]
 * 创建时间:	[2018年5月21日 下午5:41:57]
 * 修改人:	[itw_guodc]
 * 修改时间:	[2018年5月21日 下午5:41:57]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */ 
@RestController
@RequestMapping("/api")
public class ComputerRoomAirTemperatureResource extends BaseResource{
	
	@Autowired
	private ComputerRoomAirTemperatureService computerRoomAirTemperatureService ;

	@ApiOperation(value = "查询九大机房送风回风信息（大屏接口）",tags = "大屏接口",notes = "无参数")
	@GetMapping(value = "/computer-room-air-temp")
	public JSONArray findAllTemp(){
		return computerRoomAirTemperatureService.findByMaxCreateTime() ;
	}
}
