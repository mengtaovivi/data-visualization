package com.taikang.dataVis.screen.service.impl;

import com.taikang.dataVis.core.utils.DateUtil;
import com.taikang.dataVis.domain.Dict;
import com.taikang.dataVis.domain.ElectricityCount;
import com.taikang.dataVis.mapper.DictMapper;
import com.taikang.dataVis.mapper.ElectricityCountMapper;

import java.util.*;

import com.taikang.dataVis.screen.service.ElectricityCountService;
import com.taikang.dataVis.screen.web.rest.vm.ElectricityCountVM;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 电力的service
 *
 * @author 余灏
 * @see [相关类/方法]（可选）
 */
@Service
public class ElectricityCountServiceImpl implements ElectricityCountService {

    @Autowired
    private ElectricityCountMapper electricityCountMapper;

    @Autowired
    private DictMapper dictMapper;

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Integer update(String paramsStr) {
        int row = 0;

        JSONArray jsonArray = JSONArray.fromObject(paramsStr);

        ElectricityCount electricityCount = null;
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONArray valueArray  = jsonArray.getJSONObject(i).getJSONObject("list").getJSONArray("valueArray");
            for (int j = 0; j < valueArray.size(); j++) {
                JSONObject valueJson = valueArray.getJSONObject(j);
                electricityCount = new ElectricityCount();
                electricityCount.setId(valueJson.getInt("id"));
                electricityCount.setValue(valueJson.get("value") == null ? "" : valueJson.getString("value"));
                row += electricityCountMapper.update(electricityCount);
            }
        }
        return row;
    }

    /**
     * 根据年月获取电力信息，准备修改
     * @param time {"year":2018,"month":"05"}
     * @return
     */
    @Override
    public List<Object> updateData(String time) {

        String[] timeArray = time.split("-");
        Map<Object, Object> params = new HashMap<>();
        params.put("year", timeArray[0]);
        params.put("month", timeArray[1]);

        List<ElectricityCount> list= electricityCountMapper.getElectricityCountByTime(params);

        Dict dict = dictMapper.getDictByCode("computer_room_type");
        String computerRooms = dict.getDictValue();
        JSONArray computerRoomsArray = JSONArray.fromObject(computerRooms);

        //从字典表中拿到电力类型，分割，保存对象中，然后保存到数据库，并把list对象返回前台显示
        String electricityType = dictMapper.getDictByCode("electricity_type").getDictValue();
        JSONArray electricityTypeValueArray = JSONArray.fromObject(electricityType);

        ElectricityCount electricityCount;
        ElectricityCountVM electricityCountVM;
        List<Object> electricityCounts = new ArrayList<>();
        Map<Object, Object> map = null;

        // 组装的单个value对象
        JSONObject value ;
        // 组装某个机房的value数组
        JSONArray valueArray ;
        // 循环机房的集合
        for (int i = 0; i < computerRoomsArray.size(); i++) {
            JSONObject obj = computerRoomsArray.getJSONObject(i);
            String key = obj.get("key").toString();
            String labelRoomName = obj.get("label").toString();

            map = new HashMap<>();
            valueArray = new JSONArray();
            // 循环要修改的所有值
            for (int j = 0; j < list.size(); j++) {
                electricityCount = list.get(j);
                String roomName = electricityCount.getComputerRoomName();
                String type = electricityCount.getType();

                if(labelRoomName.equals(roomName)){

                    // 组装数据
                    for (int k = 0; k < electricityTypeValueArray.size(); k++) {
                        JSONObject electricityTypeValueJson = electricityTypeValueArray.getJSONObject(k);
                        String typeName = electricityTypeValueJson.getString("label");
                        if(typeName.equals(type)){
                            value = new JSONObject();
                            value.put("value",electricityCount.getValue());
                            value.put("id",electricityCount.getId());
                            value.put("key",electricityTypeValueJson.getString("key"));
                            value.put("label",electricityTypeValueJson.getString("label"));
                            valueArray.add(value);
                        }
                    }
                }
            }
            electricityCountVM = new ElectricityCountVM();
            electricityCountVM.setValueArray(valueArray);
            electricityCountVM.setComputerRoomName(labelRoomName);
            map.put("key",key);
            map.put("label",labelRoomName);
            map.put("list",electricityCountVM);
            electricityCounts.add(map);
        }
        return electricityCounts;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class, Exception.class})
    public Integer save(String paramsStr) {
        int row = 0;
        JSONObject params = JSONObject.fromObject(paramsStr);

        String time = params.getString("time");
        String[] timeArray = time.split("-");

        JSONArray jsonArray = params.getJSONArray("list");

        ElectricityCount electricityCount = null;
        String currentDate = DateUtil.getCurrentDate();
        for (int i = 0; i < jsonArray.size(); i++) {
            String key = jsonArray.getJSONObject(i).getString("key");
            String roomName = jsonArray.getJSONObject(i).getString("label");

            JSONObject json = jsonArray.getJSONObject(i).getJSONObject(key);
            JSONArray valueArray = json.getJSONArray("valueArray");
            for (int j = 0; j < valueArray.size(); j++) {
                JSONObject valueJson = valueArray.getJSONObject(j);
                electricityCount = new ElectricityCount();
                electricityCount.setMonth(timeArray[1]);
                electricityCount.setYear(timeArray[0]);
                electricityCount.setCreatedDate(currentDate);
                electricityCount.setComputerRoomName(roomName);
                electricityCount.setType(valueJson.getString("label"));
                electricityCount.setValue(valueJson.get("value") == null ? "" : valueJson.getString("value"));
                row += electricityCountMapper.save(electricityCount);
            }
        }
        return row;
    }

    /**
     * 新增的时候，获取新增的数据结构
     * @return
     */
    @Override
    public JSONObject insertData() {
        JSONObject result = new JSONObject();

        //从字典表中拿到机房类型，分割，保存对象中，然后保存到数据库，并把list对象返回前台显示
        Dict roomType = dictMapper.getDictByCode("computer_room_type");
        String computerRooms = roomType.getDictValue();
        JSONArray computerRoomsArray = JSONArray.fromObject(computerRooms);

        //从字典表中拿到电力类型，分割，保存对象中，然后保存到数据库，并把list对象返回前台显示
        String electricityType = dictMapper.getDictByCode("electricity_type").getDictValue();
        JSONArray electricityTypeValueArray = JSONArray.fromObject(electricityType);
        for (int i = 0; i < electricityTypeValueArray.size(); i++) {
            JSONObject json = (JSONObject) electricityTypeValueArray.get(i);
            json .put("value", "");
        }

        ElectricityCountVM electricityCount;
        List<Object> electricityCounts = new ArrayList<>();
        Map<Object, Object> map = null;
        for (int i = 0; i < computerRoomsArray.size(); i++) {
            JSONObject obj = computerRoomsArray.getJSONObject(i);
            String key = obj.get("key").toString();
            String label = obj.get("label").toString();
            map = new HashMap<>();
            electricityCount = new ElectricityCountVM();
            electricityCount.setComputerRoomName(label);
            electricityCount.setValueArray(electricityTypeValueArray);
            map.put(key,electricityCount);
            map.put("label",label);
            map.put("key",key);
            electricityCounts.add(map);
        }

        List<String> hasTimes = electricityCountMapper.getYearAndMonth();
        List<String> times = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        calendar.set(Calendar.YEAR, 2017);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        int size = (currentYear - 2017) * 12 + (currentMonth - Calendar.JANUARY + 1);

        String time = null;
        for (int i = 0; i < size; i++) {
            int month = calendar.get(Calendar.MONTH) + 1;
            time = calendar.get(Calendar.YEAR) + "-" + (month < 10 ? "0" + month : month);
            if(!hasTimes.contains(time)){
                times.add(time);
            }
            calendar.add(Calendar.MONTH, 1);
        }

        result.put("list", electricityCounts);
        result.put("times", times);
        return result;
    }

    @Override
    public List<String> findAll() {
        return electricityCountMapper.getYearAndMonth();
    }

    @Override
    public Map<String, Object> getElectricityCountsByYear(Integer year) {
        Map<String, Object> result = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.add(Calendar.YEAR, -1);
        Integer preYear = calendar.get(Calendar.YEAR);
        List<ElectricityCount> preList = electricityCountMapper.getElectricityCountsByYear(preYear.toString());
        result.put(preYear.toString(), preList);
        List<ElectricityCount> list = electricityCountMapper.getElectricityCountsByYear(year.toString());
        result.put(year.toString(), list);
        result.put(preYear.toString(), preList);
        return result;
    }

    @Override
    public JSONArray findAllElectricity() {
        //当前年
        Integer year = Integer.valueOf(DateUtil.getCurrentYear(0));//今年
        //获取去年
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.add(Calendar.YEAR, -1);
        Integer preYear = calendar.get(Calendar.YEAR);

        Dict dict = dictMapper.getDictByCode("computer_room_type");
        String computerRooms = dict.getDictValue();
        JSONArray jsonArrays = JSONArray.fromObject(computerRooms);

        //解析出来今年和去年信息
        List<ElectricityCount> list1 = electricityCountMapper.getElectricityCountsByYear(preYear.toString());//去年
        List<ElectricityCount> list2 = electricityCountMapper.getElectricityCountsByYear(year.toString());//今年

        List<ElectricityCount> list3 = electricityCountMapper.getElectricityMonthByYear(preYear.toString());//去年月份信息
        List<ElectricityCount> list4 = electricityCountMapper.getElectricityMonthByYear(year.toString());//今年月份信息

        JSONArray js_ = new JSONArray();
        //循环九个机房信息，开始装入map，封装json
        for (int i = 0; i < jsonArrays.size(); i++) {
            //封装外层
            Map<String, Object> map = new HashMap<String, Object>();
            JSONArray js = new JSONArray();
            JSONObject obj = jsonArrays.getJSONObject(i);
            String name = obj.get("label").toString();
            map.put("key", name);
            //去年年度封装
            Map<String, Object> map1 = new HashMap<String, Object>();
            //今年年度封装
            Map<String, Object> map2 = new HashMap<String, Object>();
            //今年年度封装
            Map<String, Object> map3 = new HashMap<String, Object>();

            JSONArray jss = new JSONArray();
            JSONArray jss1 = new JSONArray();

            map1.put("year", preYear);
            map2.put("year", year);

            for (int j = 0; j < list3.size(); j++) {
                JSONArray json1 = new JSONArray();
                String month_ = list3.get(j).getMonth();
                map3.put("name", month_);
                //循环今年信息
                for (int k = 0; k < list1.size(); k++) {
                    ElectricityCount el = list1.get(k);
                    String month = el.getMonth();
                    String name_ = el.getComputerRoomName();
                    if (name.equals(name_)) {
                        if (month_.equals(month)) {
                            Map<String, Object> map4 = new HashMap<String, Object>();
                            String type = el.getType();
                            //改为key，value的形式放入
                            map4.put("key", type);
                            String val = el.getValue();
                            map4.put("value", val);
                            json1.add(map4);
                        }
                    }
                }
                map3.put("value", json1);
                jss.add(map3);
            }
            map1.put("value", jss);
            js.add(map1);
            //循环去年信息
            for (int j = 0; j < list4.size(); j++) {
                JSONArray json1 = new JSONArray();
                String month_ = list4.get(j).getMonth();
                map3.put("name", month_);
                //循环今年信息
                for (int k = 0; k < list2.size(); k++) {
                    ElectricityCount el = list2.get(k);
                    String month = el.getMonth();
                    String name_ = el.getComputerRoomName();
                    if (name.equals(name_)) {
                        if (month_.equals(month)) {
                            Map<String, Object> map4 = new HashMap<String, Object>();
                            String type = el.getType();
                            //改为key，value的形式放入
                            map4.put("key", type);
                            String val = el.getValue();
                            map4.put("value", val);
                            json1.add(map4);
                        }
                    }
                }
                map3.put("value", json1);
                jss1.add(map3);
            }
            map2.put("value", jss1);
            js.add(map2);
            map.put("value", js);
            js_.add(map);
        }
        return js_;
    }

}
