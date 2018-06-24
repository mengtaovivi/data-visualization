package com.taikang.dataVis.screen.service.impl;

import com.taikang.dataVis.core.utils.ClientUtil;
import com.taikang.dataVis.core.utils.DateUtil;
import com.taikang.dataVis.domain.TemperatureAlarm;
import com.taikang.dataVis.mapper.TemperatureAlarmMapper;
import com.taikang.dataVis.screen.service.TemperatureAlarmService;
import com.taikang.dataVis.screen.web.rest.vm.TemperatureAlarmVM;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @author 孟涛
 * @description
 * @date 2018/5/18 17:05
 * @return
 */
@Service
public class TemperatureAlarmServiceImpl implements TemperatureAlarmService {

  private Logger logger = LoggerFactory.getLogger(this.getClass());
	
  @Autowired
  private TemperatureAlarmMapper temperatureAlarmMapper ;

  @Override
  public List<TemperatureAlarmVM> findAll(String json) {
    // 校验是否是json格式
    if (ClientUtil.isJson(json)) {
      Map<String,String> map = new HashMap<String,String>() ;
      JSONObject object = JSONObject.fromObject(json);
      String search = object.get("search").toString();
      map.put("search",search) ;
      List<TemperatureAlarmVM> list = temperatureAlarmMapper.findAll();
      return list ;
    }
    return new ArrayList<TemperatureAlarmVM>();
  }

  //大屏温度湿度等相关查询
  @Override
  public JSONObject findTemperatureAlarmAll() {
	// TODO Auto-generated method stub
	JSONObject object = new JSONObject();
	try {
		String nowtime=DateUtil.getCurrentZonedDateTime();
		String mothtime=DateUtil.getYerAndMothDate()+"-01 00:00:00";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("mothtime", mothtime);
		map.put("nowtime", nowtime);
		//获取当月湿度，温度，报警次数
	    List<Map<String, Object>> list =temperatureAlarmMapper.findMonthMaxAndMin(map);
	    //获取当前最新一条相关数据
	    List<Map<String, Object>> listtime=temperatureAlarmMapper.findMaxCreateTime();
		object.put("data_1", listtime);
		object.put("data_2",list );
		
	} catch (Exception e) {
		// TODO: handle exception
		logger.error("Exception:" + e);
		e.printStackTrace();
		object.put("message", "程序查询失败！");
		object.put("code", 3);
	}
	return object;
  }

@Override
public Integer save(TemperatureAlarm temperatureAlarm) {
	// TODO Auto-generated method stub
	return temperatureAlarmMapper.save(temperatureAlarm);
}

@Override
public Integer update(TemperatureAlarm temperatureAlarm) {
	// TODO Auto-generated method stub
	return temperatureAlarmMapper.update(temperatureAlarm);
}

@Override
public Integer deleteBatch(List<Integer> id) {
	// TODO Auto-generated method stub
	return temperatureAlarmMapper.deleteBatch(id);
}
}
