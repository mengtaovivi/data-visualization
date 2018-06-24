package com.taikang.dataVis.domain;

import java.io.Serializable;

/**
 *
 *
 * @author 孟涛
 * @description 机房送风、回风温度信息
 * @date 2018/5/14 15:27
 * @return
 */
public class ComputerRoomAirTemperature implements Serializable {

  private String id;  //id
  private String airSuply;  //送风温度
  private String returnAir; //回风温度
  private String createTime;  //创建时间
  private String updateTime;  //更新时间
  private String computerRoomName;   //九大机房名称

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAirSuply() {
    return airSuply;
  }

  public void setAirSuply(String airSuply) {
    this.airSuply = airSuply;
  }

  public String getReturnAir() {
    return returnAir;
  }

  public void setReturnAir(String returnAir) {
    this.returnAir = returnAir;
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

  public String getComputerRoomName() {
    return computerRoomName;
  }

  public void setComputerRoomName(String computerRoomName) {
    this.computerRoomName = computerRoomName;
  }
}
