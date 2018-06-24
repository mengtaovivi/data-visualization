package com.taikang.dataVis.screen.service;

import com.taikang.dataVis.screen.web.rest.vm.ComputerRoomVM;
import java.util.List;
import net.sf.json.JSONArray;

/**
 *
 *
 * @author 孟涛
 * @description 机房信息
 * @date 2018/5/8 13:47
 * @return
 */
public interface ComputerRoomService {

  List<ComputerRoomVM> findAll(String json);

  List<ComputerRoomVM> findAllInfo(String json);

  JSONArray returnVal();

  int update(ComputerRoomVM computerRoomVM);

  int copy(String json);

  List<ComputerRoomVM> findCurrentAll(String json);

  List<String> findAllDate();

  int saveBatch(String json);

  JSONArray findAllComputerRoom();

}
