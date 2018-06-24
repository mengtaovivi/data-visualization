package com.taikang.dataVis.screen.service;

import com.taikang.dataVis.domain.TemperatureAlarm;
import com.taikang.dataVis.screen.web.rest.vm.TemperatureAlarmVM;

import net.sf.json.JSONObject;

import java.util.List;

/**
 *
 *
 * @author 孟涛
 * @description 温度湿度接口信息
 * @date 2018/5/18 17:04
 * @return
 */
public interface TemperatureAlarmService {

  List<TemperatureAlarmVM> findAll(String json);

  JSONObject findTemperatureAlarmAll();

  Integer save(TemperatureAlarm temperatureAlarm);

  Integer update(TemperatureAlarm temperatureAlarm);

  Integer deleteBatch(List<Integer> id);
}
