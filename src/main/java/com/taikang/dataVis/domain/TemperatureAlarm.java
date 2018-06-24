package com.taikang.dataVis.domain;

import java.io.Serializable;

/**
 * @author 孟涛
 * @description 温湿度监控
 * @date 2018/5/18 16:59
 * @return
 */
public class TemperatureAlarm implements Serializable {

  private Integer id; // 唯一标识
  private String temperatureVal; //  温度
  private String humidity; // 湿度
  private String alarmAll; // 告警总数
  private String alarmAlert; // 警报
  private String alarmNormal; // 正常
  private String alarmEarlyWarning; // 预警
  private String computerRoomName; // 机房信息
  private String createTime; // 创建时间
  private String updateTime; // 更新时间

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTemperatureVal() {
    return temperatureVal;
  }

  public void setTemperatureVal(String temperatureVal) {
    this.temperatureVal = temperatureVal;
  }

  public String getHumidity() {
    return humidity;
  }

  public void setHumidity(String humidity) {
    this.humidity = humidity;
  }

  public String getAlarmAll() {
    return alarmAll;
  }

  public void setAlarmAll(String alarmAll) {
    this.alarmAll = alarmAll;
  }

  public String getAlarmAlert() {
    return alarmAlert;
  }

  public void setAlarmAlert(String alarmAlert) {
    this.alarmAlert = alarmAlert;
  }

  public String getAlarmNormal() {
    return alarmNormal;
  }

  public void setAlarmNormal(String alarmNormal) {
    this.alarmNormal = alarmNormal;
  }

  public String getAlarmEarlyWarning() {
    return alarmEarlyWarning;
  }

  public void setAlarmEarlyWarning(String alarmEarlyWarning) {
    this.alarmEarlyWarning = alarmEarlyWarning;
  }

  public String getComputerRoomName() {
    return computerRoomName;
  }

  public void setComputerRoomName(String computerRoomName) {
    this.computerRoomName = computerRoomName;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  public String getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }
}
