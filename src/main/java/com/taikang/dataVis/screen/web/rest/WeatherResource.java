package com.taikang.dataVis.screen.web.rest;

import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taikang.dataVis.screen.service.WeatherService;

/**
 *
 *
 * @author 孟涛
 * @description 天气信息
 * @date 2018/5/17 10:52
 * @return
 */
@RestController
@RequestMapping("/api")
public class WeatherResource {
	
	@Autowired
	private WeatherService weatherService ;
	
	@ApiOperation(value="通过url地址获取,并保存",tags={"天气预报接口"},notes="无参数")
    @GetMapping(value = "/save-weather")
    public JSONObject saveWeather() {
      return  weatherService.saveWeather();
    }
	

    @ApiOperation(value="从数据库获取最新天气",tags={"天气预报接口"},notes="无参数")
    @GetMapping(value = "/get-weather")
    public Map<String,String> getWeather() {
      return  null;
    }

}
