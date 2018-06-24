package com.taikang.dataVis.domain;

/**
 *
 *
 * @author 孟涛
 * @description 天气信息
 * @date 2018/5/17 10:50
 * @return
 */
public class Weather {

  private String id ;
  private String city ;
  private String weatherJson ;
  private String createTime ;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCity() {
		return city;
  }

  public void setCity(String city) {
		this.city = city;
  }
  public String getWeatherJson() {
    return weatherJson;
  }

  public void setWeatherJson(String weatherJson) {
    this.weatherJson = weatherJson;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }
}
