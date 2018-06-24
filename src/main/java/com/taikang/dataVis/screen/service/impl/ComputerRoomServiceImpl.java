package com.taikang.dataVis.screen.service.impl;

import com.taikang.dataVis.core.utils.ClientUtil;
import com.taikang.dataVis.core.utils.DateUtil;
import com.taikang.dataVis.domain.ComputerRoom;
import com.taikang.dataVis.domain.Dict;
import com.taikang.dataVis.mapper.ComputerRoomMapper;
import com.taikang.dataVis.mapper.DictMapper;
import com.taikang.dataVis.screen.service.ComputerRoomService;
import com.taikang.dataVis.screen.web.rest.vm.ComputerRoomVM;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 孟涛
 * @description 机房信息
 * @date 2018/5/8 13:47
 * @return
 */
@Service
public class ComputerRoomServiceImpl implements ComputerRoomService {

  @Autowired
  private ComputerRoomMapper computerRoomMapper;
  @Autowired
  private DictMapper dictMapper;

  /**
   * @return java.util.List<ComputerRoomVM>
   * @author 孟涛
   * @description 查询当前9个机房的状态信息
   * @date 2018/5/8 14:42
   */
  @Override
  public List<ComputerRoomVM> findAll(String json) {
    Map<String, Object> map = new HashMap<String, Object>();
    List<ComputerRoomVM> list = new ArrayList<ComputerRoomVM>();
    // 校验是否是json格式
    if (ClientUtil.isJson(json)) {
      JSONObject object = JSONObject.fromObject(json);
      String computerRoomName = object.get("computerRoomName").toString();
      map.put("computerRoomName", computerRoomName);
      list = computerRoomMapper.findAll(map);
    }
    return list;
  }

  /**
   * @return java.util.List<ComputerRoomVM>
   * @author 孟涛
   * @description 查询所有的信息
   * @date 2018/5/8 14:43
   */
  @Override
  public List<ComputerRoomVM> findAllInfo(String json) {
    Map<String, Object> map = new HashMap<String, Object>();
    List<ComputerRoomVM> list = new ArrayList<ComputerRoomVM>();
    // 校验是否是json格式
    if (ClientUtil.isJson(json)) {
      JSONObject object = JSONObject.fromObject(json);
      String computerRoomName = object.get("computerRoomName").toString();
      map.put("computerRoomName", computerRoomName);
      list = computerRoomMapper.findAll(map);
    }
    return list;
  }

  /**
   * @return java.util.List<ComputerRoomVM>
   * @author 孟涛
   * @description 单机创建时默认创建9个机房的状态信息
   * @date 2018/5/8 14:56
   */
  @Override
  public JSONArray returnVal() {
//    List<ComputerRoom> list = new ArrayList<ComputerRoom>() ;
    String date = DateUtil.getCurrentDate("yyyyMMddHHmmssSSS");

    //从字典表中拿到机房类型，分割，保存对象中，然后保存到数据库，并把list对象返回前台显示
    Dict dict = dictMapper.getDictByCode("computer_room_type");
    String computerRooms = dict.getDictValue();
    JSONArray jsonArray = JSONArray.fromObject(computerRooms);
    JSONArray jsonArray1 = new JSONArray();
    for (int i = 0; i < jsonArray.size(); i++) {
      JSONObject obj = jsonArray.getJSONObject(i);
      String key = obj.get("key").toString();
      String label = obj.get("label").toString();
      System.out.println(key);
      System.out.println(label);
      ComputerRoom computerRoom = new ComputerRoom();
      String id = date + (i < 10 ? ("0" + (i + 1)) : (i + 1));
      computerRoom.setId(id);
      computerRoom.setComputerRoomName(label);
      Map<String, Object> map1 = new HashMap<String, Object>();
      Map<String, String> map2 = new HashMap<>();
      map1.put("key", key);
      map1.put("label", label);
      map2.put("id", id);
      map2.put("frameAll", "");
      map2.put("frameUsed", "");
      map2.put("computerRoom", "");
      map2.put("powerAll", "");
      map2.put("powerUsed", "");
      map2.put("computerRoomName", label);
      map1.put(key, map2);
      jsonArray1.add(map1);
//      list.add(computerRoom) ;
    }
    /*if( list.size() > 0 ){
      computerRoomMapper.BatchInsert(list);
    }*/
    return jsonArray1;
  }

  @Override
  public int update(ComputerRoomVM computerRoomVM) {
    ComputerRoom room = new ComputerRoom();
    BeanUtils.copyProperties(computerRoomVM, room);
    room.setUpdateTime(DateUtil.getCurrentDate());
    return computerRoomMapper.update(room);
  }

  @Override
  public int copy(String json) {
    int num = 0;
    Map<String, Object> map = new HashMap<String, Object>();
    List<ComputerRoom> list = new ArrayList<ComputerRoom>();
    // 校验是否是json格式
    if (ClientUtil.isJson(json)) {
      JSONObject object = JSONObject.fromObject(json);
      String createTime = object.get("createTime").toString();
      map.put("createTime", createTime);
      List<ComputerRoom> lists = computerRoomMapper.findByCreateTime(map);
      String date = DateUtil.getCurrentDate("yyyyMMddHHmmssSSS");
      String currentDate = DateUtil.getCurrentDate();
      for (int i = 0; i < lists.size(); i++) {
        ComputerRoom room = lists.get(i);
        room.setId(date + (i < 10 ? ("0" + (i + 1)) : (i + 1)));
        room.setCreateTime(currentDate);
        room.setUpdateTime("");
        list.add(room);
      }

      if (list.size() > 0) {
        num = computerRoomMapper.BatchInsert(list);
      }

    }
    return num;
  }

  @Override
  public List<ComputerRoomVM> findCurrentAll(String json) {
    Map<String, String> map = new HashMap<String, String>();
    List<ComputerRoomVM> list = new ArrayList<ComputerRoomVM>();
    if (ClientUtil.isJson(json)) {
      JSONObject object = JSONObject.fromObject(json);
      String createTime =
          object.get("computerRoomName") == null ? "" : object.get("computerRoomName").toString();
      map.put("computerRoomName", createTime);
      list = computerRoomMapper.findCurrentAll(map);
    }
    return list;
  }

  @Override
  public List<String> findAllDate() {
    return computerRoomMapper.findAllDate();
  }

  @Override
  public int saveBatch(String json) {
//    json = "[{\"key\": \"cp\",\"label\": \"昌平\",\"cp\": {\"id\":\"2018050919460001\",\"frameAll\":\"11\",\"frameUsed\":\"22\",\"computerRoom\":\"33\",\"powerUsed\":\"\",\"computerRoomName\":\"昌平\"}},{\"key\": \"gm\",\"label\": \"公墓\",\"gm\": {\"id\":\"2018050919460002\",\"frameAll\":\"\",\"frameUsed\":\"\",\"computerRoom\":\"\",\"powerUsed\":\"\",\"computerRoomName\":\"公墓\"}},{\"key\": \"zc\",\"label\": \"资产\",\"zc\": {\"id\":\"2018050919460003\",\"frameAll\":\"\",\"frameUsed\":\"\",\"computerRoom\":\"\",\"powerUsed\":\"\",\"computerRoomName\":\"资产\"}},{\"key\": \"cbm\",\"label\": \"CBD\",\"cbm\": {\"id\":\"2018050919460004\",\"frameAll\":\"\",\"frameUsed\":\"\",\"computerRoom\":\"\",\"powerUsed\":\"\",\"computerRoomName\":\"CBD\"}},{\"key\": \"txy\",\"label\": \"腾讯云\",\"txy\": {\"id\":\"2018050919460005\",\"frameAll\":\"\",\"frameUsed\":\"\",\"computerRoom\":\"\",\"powerUsed\":\"\",\"computerRoomName\":\"腾讯云\"}},{\"key\": \"fxm\",\"label\": \"复兴门\",\"fxm\": {\"id\":\"2018050919460006\",\"frameAll\":\"\",\"frameUsed\":\"\",\"computerRoom\":\"\",\"powerUsed\":\"\",\"computerRoomName\":\"复兴门\"}},{\"key\": \"wh\",\"label\": \"武汉\",\"wh\": {\"id\":\"2018050919460007\",\"frameAll\":\"\",\"frameUsed\":\"\",\"computerRoom\":\"\",\"powerUsed\":\"\",\"computerRoomName\":\"武汉\"}},{\"key\": \"xg\",\"label\": \"香港\",\"xg\": {\"id\":\"2018050919460008\",\"frameAll\":\"\",\"frameUsed\":\"\",\"computerRoom\":\"\",\"powerUsed\":\"\",\"computerRoomName\":\"香港\"}},{\"key\": \"xl\",\"label\": \"仙林鼓楼医院\",\"xl\": {\"id\":\"2018050919460009\",\"frameAll\":\"\",\"frameUsed\":\"\",\"computerRoom\":\"\",\"powerUsed\":\"\",\"computerRoomName\":\"仙林鼓楼医院\"}}]" ;
    int num = 0;
    // 校验是否是json格式
    if (ClientUtil.isJson(json)) {
      try {
        String currentDate = DateUtil.getCurrentDate();
        JSONArray jsonArray = JSONArray.fromObject(json);
        List<ComputerRoom> list = new ArrayList<ComputerRoom>();
        for (int i = 0; i < jsonArray.size(); i++) {
          JSONObject obj = jsonArray.getJSONObject(i);
          String key = obj.get("key").toString();
          String object = obj.get(key + "").toString();
          JSONObject o = JSONObject.fromObject(object);
          ComputerRoom room = (ComputerRoom) JSONObject
              .toBean(o, ComputerRoom.class);  //通过JSONObject.toBean()方法进行对象间的转换
          room.setCreateTime(currentDate);
          list.add(room);
//          num += computerRoomMapper.update(room) ;
        }
        if (list.size() > 0) {
          num = computerRoomMapper.BatchInsert(list);
        }

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return num;
  }

  @Override
  public JSONArray findAllComputerRoom() {
    JSONArray jsonArray = new JSONArray();
    Map<String, Object> map = new HashMap<String, Object>();
    List<ComputerRoom> list = computerRoomMapper.findAllComputerRoom();
    for (int i = 0; i < list.size(); i++) {
      Map<String, Object> map1 = new HashMap<String, Object>();
      ComputerRoom vm = list.get(i);
      String name = vm.getComputerRoomName();
      String frameAll = vm.getFrameAll();
      String frameUsed = vm.getFrameUsed();
      String powerAll = vm.getPowerAll();
      String powerUsed = vm.getPowerUsed();
      String computerRoom = vm.getComputerRoom();
      map.put("key", name);
      map1.put("frameAll", frameAll);
      map1.put("frameUsed", frameUsed);
      map1.put("powerAll", powerAll);
      map1.put("powerUsed", powerUsed);
      map1.put("computerRoom", computerRoom);

      map.put("value", map1);
      jsonArray.add(map);
    }
    return jsonArray;
  }

}
