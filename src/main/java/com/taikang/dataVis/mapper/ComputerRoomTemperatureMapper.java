package com.taikang.dataVis.mapper;

import com.taikang.dataVis.domain.ComputerRoomTemperature;
import com.taikang.dataVis.screen.web.rest.vm.ComputerRoomTemperatureVM;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;


/**
 *
 *
 * @author 孟涛
 * @description 机房温度信息接口
 * @date 2018/5/9 17:21
 * @return
 */
@Mapper
public interface ComputerRoomTemperatureMapper {


  List<ComputerRoomTemperatureVM> findAll(Map<String, Object> map);

  int BatchInsert(List<ComputerRoomTemperature> list);

  int update(ComputerRoomTemperature computerRoom);

  List<ComputerRoomTemperature> findByCreateTime(Map<String, Object> map);

  List<ComputerRoomTemperatureVM> findCurrentAll(Map<String, String> map);

  List<String> findAllDate();

  int delete(Map<String,String> map);

  List<ComputerRoomTemperature> findAllTemp();
}
