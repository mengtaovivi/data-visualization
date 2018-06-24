package com.taikang.dataVis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.taikang.dataVis.domain.Weather;

/**
 *
 *
 * @author 孟涛
 * @description 天气信息接口
 * @date 2018/5/17 11:13
 * @return
 */
@Mapper
public interface WeatherMapper {

	int insertBatch(List<Weather> list);


}
