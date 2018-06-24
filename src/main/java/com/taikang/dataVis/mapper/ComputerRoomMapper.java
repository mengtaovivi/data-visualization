package com.taikang.dataVis.mapper;

import com.taikang.dataVis.domain.ComputerRoom;
import com.taikang.dataVis.screen.web.rest.vm.ComputerRoomVM;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;


/**
 *
 *
 * @author 孟涛
 * @description 机房信息crud信息
 * @date 2018/5/8 13:48
 * @return
 */
@Mapper
public interface ComputerRoomMapper {


  List<ComputerRoomVM> findAll(Map<String, Object> map);

  int BatchInsert(List<ComputerRoom> list);

  int update(ComputerRoom computerRoom);

  List<ComputerRoom> findByCreateTime(Map<String, Object> map);

  List<ComputerRoomVM> findCurrentAll(Map<String, String> map);

  List<String> findAllDate();

  List<ComputerRoom> findAllComputerRoom();

}
