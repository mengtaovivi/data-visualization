package com.taikang.dataVis.screen.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author [作者]（必须，使用汉语）
 * @see [相关类/方法]（可选）
 */
public interface ElectricityCountService {
    Integer update(String params);

    Integer save(String params);

    List<String> findAll();

    Map<String, Object> getElectricityCountsByYear(Integer year);

    JSONArray findAllElectricity();

    JSONObject insertData();

    List<Object> updateData(String time);
}
