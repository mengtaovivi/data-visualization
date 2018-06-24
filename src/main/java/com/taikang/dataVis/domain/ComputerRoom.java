package com.taikang.dataVis.domain;

import java.io.Serializable;

/**
 * @author 孟涛
 * @description 九大机房信息展示
 * @date 2018/5/8 11:33
 * @return
 */
public class ComputerRoom implements Serializable {

  private String id;
  private String frameAll;
  private String frameUsed;
  private String computerRoom;
  private String powerUsed;
  private String powerAll;
  private String computerRoomName;
  private String createTime;
  private String updateTime;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFrameAll() {
    return frameAll;
  }

  public void setFrameAll(String frameAll) {
    this.frameAll = frameAll;
  }

  public String getFrameUsed() {
    return frameUsed;
  }

  public void setFrameUsed(String frameUsed) {
    this.frameUsed = frameUsed;
  }

  public String getComputerRoom() {
    return computerRoom;
  }

  public void setComputerRoom(String computerRoom) {
    this.computerRoom = computerRoom;
  }

  public String getPowerUsed() {
    return powerUsed;
  }

  public void setPowerUsed(String powerUsed) {
    this.powerUsed = powerUsed;
  }

  public String getPowerAll() {
    return powerAll;
  }

  public void setPowerAll(String powerAll) {
    this.powerAll = powerAll;
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
