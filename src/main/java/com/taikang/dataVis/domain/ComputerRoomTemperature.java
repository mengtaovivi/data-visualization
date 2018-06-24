package com.taikang.dataVis.domain;

import java.io.Serializable;

/**
 *
 *
 * @author 孟涛
 * @description 机房温度信息
 * @date 2018/5/9 17:20
 * @return
 */
public class ComputerRoomTemperature implements Serializable {

  private String id;  //id
  private String types;  //指标类型
  private String cold; //制冷机柜
  private String precise;  //精密空调
  private String createTime;  //创建时间
  private String updateTime;  //更新时间
  private String computerRoomName;   //九大机房名称

  public String getPrecise() {
    return precise;
  }

  public void setPrecise(String precise) {
    this.precise = precise;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTypes() {
    return types;
  }

  public void setTypes(String types) {
    this.types = types;
  }

  public String getCold() {
    return cold;
  }

  public void setCold(String cold) {
    this.cold = cold;
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
