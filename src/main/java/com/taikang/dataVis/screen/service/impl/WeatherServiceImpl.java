package com.taikang.dataVis.screen.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taikang.dataVis.core.config.JdbcParams;
import com.taikang.dataVis.core.utils.DateUtil;
import com.taikang.dataVis.core.utils.HttpClientUtil;
import com.taikang.dataVis.domain.RDBModel;
import com.taikang.dataVis.domain.Weather;
import com.taikang.dataVis.mapper.RDBMapper;
import com.taikang.dataVis.mapper.WeatherMapper;
import com.taikang.dataVis.screen.service.WeatherService;

import net.sf.json.JSONObject;

/**
 *
 *
 * @author 孟涛
 * @description 天气信息接口实现
 * @date 2018/5/17 11:13
 * @return
 */
@Service
public class WeatherServiceImpl implements WeatherService {

	@Autowired
	private JdbcParams jdbcParams;
	@Autowired
	private WeatherMapper weatherMapper ;
	
	@Override
	@Transactional
	public JSONObject saveWeather() {
		// TODO Auto-generated method stub
		JSONObject obj = new JSONObject();
		//获取北京的天气情况（staIds=54511  北京id），   staIds=57494  武汉id
		String city;
		//不同城市的id
		String param;
		//获取的城市天气信息
		JSONObject objtmp;
		// 生成时间
        String date = DateUtil.getCurrentZonedDateTime();
		//记录获取天气信息失败的城市
		StringBuffer message= new StringBuffer();
		List<Weather> list = new ArrayList<Weather>();
		String[] city_ids= jdbcParams.getCity_ids().split("&");
		for (int i = 0; i < city_ids.length; i++) {
			city=city_ids[i].substring(0, city_ids[i].indexOf("-"));
			param="staIds="+city_ids[i].substring(city_ids[i].indexOf("-")+1,city_ids[i].length());
			String reuslt=HttpClientUtil.sendGet(jdbcParams.getWeather_url(),param);
			if (!reuslt.equals("")) {
				objtmp= JSONObject.fromObject(reuslt);
				if (objtmp.containsKey("ret_code") && objtmp.getInt("ret_code") ==0) {
					Weather weather = new Weather();
					weather.setId(UUID.randomUUID().toString().replaceAll("-",""));
					weather.setCity(city);
					weather.setWeatherJson(objtmp.containsKey("data")?objtmp.getString("data"):"");
					weather.setCreateTime(date);
					list.add(weather);
				}else {
					//记录获取天气预报信息失败的城市
					message.append(city).append("、");
				}
			}else {
				//记录获取天气预报信息失败的城市
				message.append(city).append("、");
			}
		}
		 if (list.size()>0) {
	        weatherMapper.insertBatch(list);
	    	obj.put("message", "获取天气预报信息成功！");
	    	obj.put("code", 0);
		}
		if (message.length()>0) {
			obj.put("message", "获取"+message.substring(0, message.length()-1)+"的天气信息失败！");
			obj.put("code", 3);
		}
		return obj;
	}

}
