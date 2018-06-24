package com.taikang.dataVis.mapper;

import com.taikang.dataVis.domain.ComputerRoomAirTemperature;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;


/**
 *
 *
 * @author 孟涛
 * @description 机房温度信息接口
 * @date 2018/5/14 16:06
 * @return
 */
@Mapper
public interface ComputerRoomAirTemperatureMapper {


  int BatchInsert(List<ComputerRoomAirTemperature> list2);

  List<ComputerRoomAirTemperature> findByCreateTime(Map<String, Object> map);

  int delete(Map<String,String> map);

  int update(ComputerRoomAirTemperature airModel);

  List<Map<String, Object>> findByMaxCreateTime();

  List<ComputerRoomAirTemperature> findAllTemp();
}
