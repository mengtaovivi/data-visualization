package com.taikang.dataVis.screen.service.impl;

import com.taikang.dataVis.core.utils.ClientUtil;
import com.taikang.dataVis.core.utils.DateUtil;
import com.taikang.dataVis.core.utils.PinyinUtils;
import com.taikang.dataVis.core.utils.StringUtil;
import com.taikang.dataVis.domain.ComputerRoomAirTemperature;
import com.taikang.dataVis.domain.ComputerRoomTemperature;
import com.taikang.dataVis.domain.Dict;
import com.taikang.dataVis.mapper.ComputerRoomAirTemperatureMapper;
import com.taikang.dataVis.mapper.ComputerRoomTemperatureMapper;
import com.taikang.dataVis.mapper.DictMapper;
import com.taikang.dataVis.screen.service.ComputerRoomTemperatureService;
import com.taikang.dataVis.screen.web.rest.vm.ComputerRoomTemperatureVM;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 *
 * @author 孟涛
 * @description 机房温度信息
 * @date 2018/5/9 17:23
 * @return
 */
@Service
public class ComputerRoomTemperatureServiceImpl implements ComputerRoomTemperatureService {

  @Autowired
  private ComputerRoomTemperatureMapper computerRoomTemperatureMapper ;
  @Autowired
  private ComputerRoomAirTemperatureMapper computerRoomAirTemperatureMapper ;
  @Autowired
  private DictMapper dictMapper ;

  /**
   *
   *
   * @author 孟涛
   * @description 查询当前9个机房的状态信息
   * @date 2018/5/8 14:42
   * @return java.util.List<ComputerRoomVM>
   */
  @Override
  public List<ComputerRoomTemperatureVM> findAll(String json) {
    Map<String,Object> map = new HashMap<String,Object>() ;
    List<ComputerRoomTemperatureVM> list = new ArrayList<ComputerRoomTemperatureVM>() ;
    // 校验是否是json格式
    if (ClientUtil.isJson(json)) {
      JSONObject object = JSONObject.fromObject(json);
      String computerRoomName = object.get("computerRoomName").toString();
      map.put("computerRoomName",computerRoomName) ;
      list =  computerRoomTemperatureMapper.findAll(map);
    }
    return list;
  }

  /**
   *
   *
   * @author 孟涛
   * @description 查询所有的信息
   * @date 2018/5/8 14:43
   * @return java.util.List<ComputerRoomVM>
   */
  @Override
  public List<ComputerRoomTemperatureVM> findAllInfo(String json) {
    Map<String,Object> map = new HashMap<String,Object>() ;
    List<ComputerRoomTemperatureVM> list = new ArrayList<ComputerRoomTemperatureVM>() ;
    // 校验是否是json格式
    if (ClientUtil.isJson(json)) {
      JSONObject object = JSONObject.fromObject(json);
      String computerRoomName = object.get("computerRoomName").toString();
      map.put("computerRoomName",computerRoomName) ;
      list =  computerRoomTemperatureMapper.findAll(map);
    }
    return list;
  }

  /**
   *
   *
   * @author 孟涛
   * @description 单机创建时默认创建9个机房的状态信息
   * @date 2018/5/8 14:56
   * @return java.util.List<ComputerRoomVM>
   */
  @Override
  public JSONArray insert() {
    List<ComputerRoomTemperature> list = new ArrayList<ComputerRoomTemperature>() ;
    String date = DateUtil.getCurrentDate("yyyyMMddHHmmssSSS") ;
    String currentDate = DateUtil.getCurrentDate() ;

    //从字典表中拿到机房类型，分割，保存对象中，然后保存到数据库，并把list对象返回前台显示
    Dict dict = dictMapper.getDictByCode("computer_room_type") ;
    String computerRooms = dict.getDictValue() ;
    JSONArray jsonArray = JSONArray.fromObject(computerRooms) ;
    JSONArray jsonArray1 = new JSONArray() ;
    for( int i =0;i<jsonArray.size();i++ ){
      JSONObject obj = jsonArray.getJSONObject(i) ;
      String key = obj.get("key").toString() ;
      String label = obj.get("label").toString() ;
      System.out.println(key);
      System.out.println(label);
      ComputerRoomTemperature computerRoom = new ComputerRoomTemperature() ;
      String id =  date+(i<10?("0"+(i+1)):(i+1)) ;
      computerRoom.setId(id);
      computerRoom.setComputerRoomName(label);
      computerRoom.setCreateTime(currentDate);
      Map<String,Object> map1 = new HashMap<String,Object>() ;
      Map<String,String> map2 = new HashMap<>() ;
      map1.put("key",key) ;
      map1.put("label",label);
      map2.put("id",id) ;
      map2.put("frameAll","") ;
      map2.put("frameUsed","") ;
      map2.put("computerRoom","") ;
      map2.put("powerAll","") ;
      map2.put("powerUsed","") ;
      map2.put("computerRoomName",label) ;
      map1.put(key,map2) ;
      jsonArray1.add(map1) ;
      list.add(computerRoom) ;
    }
    if( list.size() > 0 ){
      computerRoomTemperatureMapper.BatchInsert(list);
    }
    return jsonArray1;
  }

  @Override
  public JSONArray returnVal(String json) {
    JSONArray jsonArray1 = new JSONArray() ;
    // 校验是否是json格式
    if (ClientUtil.isJson(json)) {
      Map<String,Object> map = new HashMap<String,Object>() ;
      JSONObject object = JSONObject.fromObject(json);
      String createTime = object.get("createTime").toString();
      map.put("createTime",createTime) ;
      List<ComputerRoomTemperature> lists1 = computerRoomTemperatureMapper.findByCreateTime(map);
      List<ComputerRoomAirTemperature> lists2 = computerRoomAirTemperatureMapper.findByCreateTime(map);
      //从字典表中拿到机房类型，分割，保存对象中，然后保存到数据库，并把list对象返回前台显示
      Dict dict = dictMapper.getDictByCode("computer_room_type") ;
      String computerRooms = dict.getDictValue() ;
      JSONArray jsonArray = JSONArray.fromObject(computerRooms) ;
      for( int i =0;i<jsonArray.size();i++ ){
        JSONArray jsonArray2 = new JSONArray() ;
        Map<String,Object> map2 = new HashMap<String,Object>() ;
        Map<String,Object> map3 = new HashMap<String,Object>() ;
        Map<String,Object> map4 = new HashMap<String,Object>() ;
        Map<String,Object> map5 = new HashMap<String,Object>() ;
        Map<String,Object> map6 = new HashMap<String,Object>() ;
        JSONObject obj = jsonArray.getJSONObject(i) ;
        String label = obj.getString("label");
        String eName = PinyinUtils.getAlpha(label).toUpperCase().toString() ;
        map2.put("name",eName) ;
        map2.put("cName",label) ;
        for( int j=0;j<lists2.size();j++ ){

          ComputerRoomAirTemperature air = lists2.get(j) ;
          String airId = air.getId() ;
          String airSuply = air.getAirSuply() ;
          String returnAir = air.getReturnAir() ;
          String name = air.getComputerRoomName() ;
          if( label.equals(name) ){
            map3.put("name","windTemperature") ;
            map4.put("airSupply",airSuply) ;
            map4.put("returnAir",returnAir) ;
            map3.put("value",map4) ;
            map3.put("id",airId) ;
          }
        }
        //如果为空插入空值
        if (map3.isEmpty()) {
        	map3.put("name","windTemperature") ;
            map4.put("airSupply","") ;
            map4.put("returnAir","") ;
            map3.put("value",map4) ;
            map3.put("id","") ;
		}
        //
        jsonArray2.add(map3) ;
        for( int j=0;j<lists1.size();j++ ){
          ComputerRoomTemperature room = lists1.get(j) ;
          String id = room.getId() ;
          String refrigeration = room.getCold() ;
          String precis = room.getPrecise() ;
          String cName = room.getComputerRoomName() ;
          String types = room.getTypes() ;
          map5.put("cName",types) ;
          if( label.equals(cName) ){
            if( "进水温度".equals(types) ){
              map5.put("name","waterInletTemperature") ;
            }else if( "回水温度".equals(types) ){
              map5.put("name","backwaterTemperature") ;
            }else if( "工作状态".equals(types) ){
              map5.put("name","workingCondition") ;
            }else if( "制冷状态".equals(types) ){
              map5.put("name","refrigeratingState") ;
            }else if( "漏水".equals(types) ){
              map5.put("name","waterLeakage") ;
            }else if( "风机".equals(types) ){
              map5.put("name","fan") ;
            }else if( "压缩机".equals(types) ){
              map5.put("name","compressor") ;
            }
            map6.put("refrigerationUnit",refrigeration) ;
            map6.put("precisionAirConditioning",precis) ;
            map5.put("value",map6) ;
            map5.put("id",id) ;
            jsonArray2.add(map5) ;
          }
        }
        //如果是空值，赋予空值
        if (lists1.size()==0) {
        	//先赋予一个值
        	map5.put("cName","进水温度");
        	map5.put("name","waterInletTemperature") ;
        	map6.put("refrigerationUnit","") ;
            map6.put("precisionAirConditioning","") ;
            map5.put("value",map6) ;
            map5.put("id","") ;
            jsonArray2.add(map5) ;
            map5.put("cName","回水温度");
            map5.put("name","backwaterTemperature") ;
            jsonArray2.add(map5) ;
            map5.put("cName","工作状态");
            map5.put("name","workingCondition") ;
            jsonArray2.add(map5) ;
            map5.put("cName","制冷状态");
            map5.put("name","refrigeratingState") ;
            jsonArray2.add(map5) ;
            map5.put("cName","漏水");
            map5.put("name","waterLeakage") ;
            jsonArray2.add(map5) ;
            map5.put("cName","风机");
            map5.put("name","fan") ;
            jsonArray2.add(map5) ;
            map5.put("cName","压缩机");
            map5.put("name","compressor") ;
            jsonArray2.add(map5) ;
		}
        //
        map2.put("value",jsonArray2) ;
        jsonArray1.add(map2) ;
      }
    }
    return jsonArray1;
  }

  @Override
  public int copy(String json) {
    String id = DateUtil.getCurrentDate("yyyyMMddHHmmssSSS") ;
    String date = DateUtil.getCurrentDate() ;
    int num = 0;
    Map<String,Object> map = new HashMap<String,Object>() ;
    List<ComputerRoomTemperature> list1 = new ArrayList<ComputerRoomTemperature>() ;
    List<ComputerRoomAirTemperature> list2 = new ArrayList<ComputerRoomAirTemperature>() ;
    // 校验是否是json格式
    if (ClientUtil.isJson(json)) {
      JSONObject object = JSONObject.fromObject(json);
      String createTime = object.get("createTime").toString();
      map.put("createTime",createTime) ;
      List<ComputerRoomTemperature> lists1 = computerRoomTemperatureMapper.findByCreateTime(map);
      List<ComputerRoomAirTemperature> lists2 = computerRoomAirTemperatureMapper.findByCreateTime(map);

      for( int i =0;i<lists1.size();i++ ){
        ComputerRoomTemperature room = lists1.get(i) ;
        room.setId(id+(i<10?("0"+(i+1)):(i+1)));
        room.setCreateTime(date);
        list1.add(room) ;
      }

      for( int i =0;i<lists2.size();i++ ){
        ComputerRoomAirTemperature air = lists2.get(i) ;
        air.setId(id+(i<10?("0"+(i+1)):(i+1)));
        air.setCreateTime(date);
        list2.add(air) ;
      }

      if( list1.size() > 0 ){
        num += computerRoomTemperatureMapper.BatchInsert(list1) ;
      }

      if( list2.size() > 0 ){
        num += computerRoomAirTemperatureMapper.BatchInsert(list2) ;
      }
    }
    return num;
  }

  @Override
  public List<ComputerRoomTemperatureVM> findCurrentAll(String json) {
    Map<String,String> map = new HashMap<String,String>() ;
    List<ComputerRoomTemperatureVM> list = new ArrayList<ComputerRoomTemperatureVM>() ;
    if (ClientUtil.isJson(json)) {
      JSONObject object = JSONObject.fromObject(json);
      String createTime = object.get("createTime").toString();
      map.put("createTime", createTime);
      list = computerRoomTemperatureMapper.findCurrentAll(map) ;
    }
    return list;
  }

  @Override
  public List<String> findAllDate() {
    return computerRoomTemperatureMapper.findAllDate();
  }

  @Override
  public Map<String,String> saveBatch(String json) {
    Map<String,String> map = new HashMap<String,String>() ;
    int num = 0;
    if (ClientUtil.isJson(json)) {
      JSONArray jsonArray = JSONArray.fromObject(json);
      try {
        String createTime = DateUtil.getCurrentDate() ;
        List<ComputerRoomTemperature> list1 = new ArrayList<ComputerRoomTemperature>() ;
        List<ComputerRoomAirTemperature> list2 = new ArrayList<ComputerRoomAirTemperature>() ;
        for( int i =0;i<jsonArray.size();i++ ){
          JSONObject obj = jsonArray.getJSONObject(i) ;
//          String comName = obj.get("name") ==null ?"":obj.get("name").toString() ;
          String cName = obj.get("cName") == null ?"":obj.get("cName").toString() ;
          String val = obj.get("value") == null ?"":obj.get("value").toString() ;
          if(!StringUtil.isEmpty(val)){
            JSONArray json1 = JSONArray.fromObject(val) ;
            for( int j=0;j < json1.size();j++ ){
              ComputerRoomTemperature room = new ComputerRoomTemperature() ;
              ComputerRoomAirTemperature airModel = new ComputerRoomAirTemperature() ;
              room.setComputerRoomName(cName);
              room.setCreateTime(createTime);
              airModel.setComputerRoomName(cName);
              airModel.setCreateTime(createTime);
              JSONObject object = json1.getJSONObject(j) ;
              String name = object.getString("name") ;
              if( "windTemperature".equals(name) ){
                //保存温度信息
                String val1 = object.getString("value") ;
                System.out.println(val1);
                JSONObject obj1 = JSONObject.fromObject(val1) ;
                String air = obj1.get("airSupply").toString() ;
                String returnAir = obj1.get("returnAir").toString() ;
                airModel.setAirSuply(air);
                airModel.setReturnAir(returnAir);
                airModel.setId(UUID.randomUUID().toString());
                list2.add(airModel) ;
              }else if( "waterInletTemperature".equals(name) || "backwaterTemperature".equals(name) || "workingCondition".equals(name) ||"refrigeratingState".equals(name)
                || "waterLeakage".equals(name) || "fan".equals(name) || "compressor".equals(name) ){
                String val1 = object.getString("value") ;
                String cName_ = object.getString("cName") ;
                System.out.println(val1);
                JSONObject obj1 = JSONObject.fromObject(val1) ;
                String refrigerationUnit = obj1.get("refrigerationUnit").toString() ;
                String precisionAirConditioning = obj1.get("precisionAirConditioning").toString() ;
                room.setCold(refrigerationUnit);
                room.setPrecise(precisionAirConditioning);
                room.setTypes(cName_);
                room.setId(UUID.randomUUID().toString());
                list1.add(room) ;
              }
            }
          }
        }
        num = computerRoomTemperatureMapper.BatchInsert(list1) ;
        num += computerRoomAirTemperatureMapper.BatchInsert(list2);
        map.put("num",String.valueOf(num)) ;
        map.put("message","保存成功") ;
      } catch (Exception e) {
        map.put("num",String.valueOf(num)) ;
        map.put("message","保存失败") ;
        e.printStackTrace();
      }
    }
    return map;
  }

  /**
   *
   *
   * @author 孟涛
   * @description 保存九个机房相关信息
   * @date 2018/5/15 14:29
   * @return java.util.Map<java.lang.String,java.lang.String>
   */
  @Override
  public Map<String, String> update(String json) {
    Map<String,String> map = new HashMap<String,String>() ;
    int num = 0;
    if (ClientUtil.isJson(json)) {
      JSONArray jsonArray = JSONArray.fromObject(json);
      try {
        String createTime = DateUtil.getCurrentDate() ;
        for( int i =0;i<jsonArray.size();i++ ){
          JSONObject obj = jsonArray.getJSONObject(i) ;
          String cName = obj.get("cName") == null ?"":obj.get("cName").toString() ;
          String val = obj.get("value") == null ?"":obj.get("value").toString() ;
          if(!StringUtil.isEmpty(val)){
            JSONArray json1 = JSONArray.fromObject(val) ;
            for( int j=0;j < json1.size();j++ ){
              ComputerRoomTemperature room = new ComputerRoomTemperature() ;
              ComputerRoomAirTemperature airModel = new ComputerRoomAirTemperature() ;
              room.setComputerRoomName(cName);
              room.setCreateTime(createTime);
              airModel.setComputerRoomName(cName);
              airModel.setCreateTime(createTime);
              JSONObject object = json1.getJSONObject(j) ;
              if( object.size() > 0 ){
                String name = object.getString("name") ;
                String id = object.getString("id") ;
                if( "windTemperature".equals(name) ){
                  //保存温度信息
                  String val1 = object.getString("value") ;
                  System.out.println(val1);
                  JSONObject obj1 = JSONObject.fromObject(val1) ;
                  String air = obj1.get("airSupply").toString() ;
                  String returnAir = obj1.get("returnAir").toString() ;
                  airModel.setAirSuply(air);
                  airModel.setReturnAir(returnAir);
                  airModel.setId(id);
                  airModel.setUpdateTime(DateUtil.getCurrentDate());
                  num += computerRoomAirTemperatureMapper.update(airModel);
                }else if( "waterInletTemperature".equals(name) || "backwaterTemperature".equals(name) || "workingCondition".equals(name) ||"refrigeratingState".equals(name)
                    || "waterLeakage".equals(name) || "fan".equals(name) || "compressor".equals(name) ){
                  String val1 = object.getString("value") ;
                  String cName_ = object.getString("cName") ;
                  System.out.println(val1);
                  JSONObject obj1 = JSONObject.fromObject(val1) ;
                  String refrigerationUnit = obj1.get("refrigerationUnit").toString() ;
                  String precisionAirConditioning = obj1.get("precisionAirConditioning").toString() ;
                  room.setCold(refrigerationUnit);
                  room.setPrecise(precisionAirConditioning);
                  room.setTypes(cName_);
                  room.setId(id);
                  room.setUpdateTime(DateUtil.getCurrentDate());
                  num += computerRoomTemperatureMapper.update(room) ;
                }
              }
            }
          }
        }
        map.put("num",String.valueOf(num)) ;
        map.put("message","保存成功") ;
      } catch (Exception e) {
        map.put("num",String.valueOf(num)) ;
        map.put("message","保存失败") ;
        e.printStackTrace();
      }
    }
    return map;
  }

  @Override
  public int delete(String json) {
    int num = 0;
    Map<String,String> map = new HashMap<String,String>() ;
    List<ComputerRoomTemperatureVM> list = new ArrayList<ComputerRoomTemperatureVM>() ;
    if (ClientUtil.isJson(json)) {
      JSONObject object = JSONObject.fromObject(json);
      String createTime = object.get("createTime").toString();
      map.put("createTime", createTime);
      num += computerRoomTemperatureMapper.delete(map);
      num += computerRoomAirTemperatureMapper.delete(map);
    }
    return num;
  }

  @Override
  public JSONArray findAllTemp() {

    //从字典表中拿到机房类型，分割，保存对象中，然后保存到数据库，并把list对象返回前台显示
    Dict dict = dictMapper.getDictByCode("computer_room_type") ;
    String computerRooms = dict.getDictValue() ;
    JSONArray jsonArrays = JSONArray.fromObject(computerRooms) ;
    JSONArray jsonArray = new JSONArray() ;
    Map<String,Object> map = new HashMap<String,Object>() ;
    List<ComputerRoomTemperature> list = computerRoomTemperatureMapper.findAllTemp() ;

    for( int i =0;i<jsonArrays.size();i++ ) {
      JSONObject obj = jsonArrays.getJSONObject(i);
//      String key = obj.get("key").toString();
      Map<String,Object> map1 = new HashMap<String,Object>() ;
      JSONArray json = new JSONArray() ;
      String label = obj.get("label").toString();
      map.put("key",label) ;
      for( int j =0;j<list.size();j++ ){
        ComputerRoomTemperature vm = list.get(j) ;
        String types = vm.getTypes() ;
        String cold = vm.getCold() ;
        String precise = vm.getPrecise() ;
        String computerRoomName = vm.getComputerRoomName() ;
        if( label.equals(computerRoomName) ){
          map1.put("types",types) ;
          map1.put("precise",precise) ;
          map1.put("cold",cold) ;
          json.add(map1) ;
        }
      }
      map.put("value",json) ;
      jsonArray.add(map) ;
    }
    return jsonArray;
  }
  
  @Override
  public JSONArray findAllTemp1() {

	JSONArray jsonArray1 = new JSONArray() ;
    //从字典表中拿到机房类型，分割，保存对象中，然后保存到数据库，并把list对象返回前台显示
    Dict dict = dictMapper.getDictByCode("computer_room_type") ;
    String computerRooms = dict.getDictValue() ;
    JSONArray jsonArrays = JSONArray.fromObject(computerRooms) ;
    Map<String,Object> map = new HashMap<String,Object>() ;
    List<ComputerRoomTemperature> lists1 = computerRoomTemperatureMapper.findAllTemp() ;
    List<ComputerRoomAirTemperature> lists2 = computerRoomAirTemperatureMapper.findAllTemp();
    for( int i =0;i<jsonArrays.size();i++ ) {
        JSONArray jsonArray2 = new JSONArray() ;
        Map<String,Object> map2 = new HashMap<String,Object>() ;
        Map<String,Object> map3 = new HashMap<String,Object>() ;
        Map<String,Object> map4 = new HashMap<String,Object>() ;
        Map<String,Object> map5 = new HashMap<String,Object>() ;
        Map<String,Object> map6 = new HashMap<String,Object>() ;
        JSONObject obj = jsonArrays.getJSONObject(i) ;
        String label = obj.getString("label");
        String eName = PinyinUtils.getAlpha(label).toUpperCase().toString() ;
        map2.put("name",eName) ;
        map2.put("cName",label) ;
        for( int j=0;j<lists2.size();j++ ){

          ComputerRoomAirTemperature air = lists2.get(j) ;
          String airId = air.getId() ;
          String airSuply = air.getAirSuply() ;
          String returnAir = air.getReturnAir() ;
          String name = air.getComputerRoomName() ;
          if( label.equals(name) ){
            map3.put("name","windTemperature") ;
            map4.put("airSupply",airSuply) ;
            map4.put("returnAir",returnAir) ;
            map3.put("value",map4) ;
            map3.put("id",airId) ;
          }
        }
        //如果为空插入空值
        if (map3.isEmpty()) {
        	map3.put("name","windTemperature") ;
            map4.put("airSupply","") ;
            map4.put("returnAir","") ;
            map3.put("value",map4) ;
            map3.put("id","") ;
		}
        //
        jsonArray2.add(map3) ;
        for( int j=0;j<lists1.size();j++ ){
          ComputerRoomTemperature room = lists1.get(j) ;
          String id = room.getId() ;
          String refrigeration = room.getCold() ;
          String precis = room.getPrecise() ;
          String cName = room.getComputerRoomName() ;
          String types = room.getTypes() ;
          map5.put("cName",types) ;
          if( label.equals(cName) ){
            if( "进水温度".equals(types) ){
              map5.put("name","waterInletTemperature") ;
            }else if( "回水温度".equals(types) ){
              map5.put("name","backwaterTemperature") ;
            }else if( "工作状态".equals(types) ){
              map5.put("name","workingCondition") ;
            }else if( "制冷状态".equals(types) ){
              map5.put("name","refrigeratingState") ;
            }else if( "漏水".equals(types) ){
              map5.put("name","waterLeakage") ;
            }else if( "风机".equals(types) ){
              map5.put("name","fan") ;
            }else if( "压缩机".equals(types) ){
              map5.put("name","compressor") ;
            }
            map6.put("refrigerationUnit",refrigeration) ;
            map6.put("precisionAirConditioning",precis) ;
            map5.put("value",map6) ;
            map5.put("id",id) ;
            jsonArray2.add(map5) ;
          }
        }
        //如果是空值，赋予空值
        if (lists1.size()==0) {
        	//先赋予一个值
        	map5.put("cName","进水温度");
        	map5.put("name","waterInletTemperature") ;
        	map6.put("refrigerationUnit","") ;
            map6.put("precisionAirConditioning","") ;
            map5.put("value",map6) ;
            map5.put("id","") ;
            jsonArray2.add(map5) ;
            map5.put("cName","回水温度");
            map5.put("name","backwaterTemperature") ;
            jsonArray2.add(map5) ;
            map5.put("cName","工作状态");
            map5.put("name","workingCondition") ;
            jsonArray2.add(map5) ;
            map5.put("cName","制冷状态");
            map5.put("name","refrigeratingState") ;
            jsonArray2.add(map5) ;
            map5.put("cName","漏水");
            map5.put("name","waterLeakage") ;
            jsonArray2.add(map5) ;
            map5.put("cName","风机");
            map5.put("name","fan") ;
            jsonArray2.add(map5) ;
            map5.put("cName","压缩机");
            map5.put("name","compressor") ;
            jsonArray2.add(map5) ;
		}
        //
        map2.put("value",jsonArray2) ;
        jsonArray1.add(map2) ;
      }
    return jsonArray1;
  }

}
