package com.taikang.dataVis.mapper;

import com.taikang.dataVis.domain.TemperatureAlarm;
import com.taikang.dataVis.screen.web.rest.vm.TemperatureAlarmVM;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;


/**
 *
 *
 * @author 孟涛
 * @description 温度湿度接口
 * @date 2018/5/18 17:04
 * @return
 */
@Mapper
public interface TemperatureAlarmMapper {


  List<TemperatureAlarmVM> findAll();

  List<Map<String, Object>> findMaxCreateTime();

  List<Map<String, Object>> findMonthMaxAndMin(HashMap<String, String> map);

  Integer save(TemperatureAlarm temperatureAlarm);

  Integer update(TemperatureAlarm temperatureAlarm);

  Integer deleteBatch(List<Integer> id);
}
