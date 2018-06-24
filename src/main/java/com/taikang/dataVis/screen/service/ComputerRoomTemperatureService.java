package com.taikang.dataVis.screen.service;

import com.taikang.dataVis.screen.web.rest.vm.ComputerRoomTemperatureVM;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;

/**
 *
 *
 * @author 孟涛
 * @description 机房温度信息 接口
 * @date 2018/5/9 17:22
 * @return
 */
public interface ComputerRoomTemperatureService {

  List<ComputerRoomTemperatureVM> findAll(String json);

  List<ComputerRoomTemperatureVM> findAllInfo(String json);

  JSONArray insert();

  JSONArray returnVal(String json);

  int copy(String json);

  List<ComputerRoomTemperatureVM> findCurrentAll(String json);

  List<String> findAllDate();

  Map<String,String> saveBatch(String json);

  Map<String,String> update(String json);

  int delete(String json);

  JSONArray findAllTemp();
  
  JSONArray findAllTemp1();

}
