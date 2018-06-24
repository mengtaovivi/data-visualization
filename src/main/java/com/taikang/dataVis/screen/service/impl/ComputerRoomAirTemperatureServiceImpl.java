package com.taikang.dataVis.screen.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taikang.dataVis.mapper.ComputerRoomAirTemperatureMapper;
import com.taikang.dataVis.screen.service.ComputerRoomAirTemperatureService;

import net.sf.json.JSONArray;

/**
 * 项目名称:	[cmdb-core]
 * 包:		[com.taikang.dataVis.screen.service.impl]
 * 类名称:	[ComputerRoomAirTemperatureServiceImpl]
 * 创建人:	[itw_guodc]
 * 创建时间:	[2018年5月21日 下午6:13:56]
 * 修改人:	[itw_guodc]
 * 修改时间:	[2018年5月21日 下午6:13:56]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */ 
@Service
public class ComputerRoomAirTemperatureServiceImpl implements ComputerRoomAirTemperatureService{

	
	 @Autowired
	  private ComputerRoomAirTemperatureMapper computerRoomAirTemperatureMapper ;
	
	@Override
	public JSONArray findByMaxCreateTime() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list =computerRoomAirTemperatureMapper.findByMaxCreateTime();
		return JSONArray.fromObject(list);
		
	}

}
