package com.taikang.dataVis.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 孟涛
 * @description  ClusterModel
 * @date 2018/4/26 15:36
 * @return
 */
public class ClusterModel {

  private String rid = ""; //-> #38:17
  private String instanceId = ""; //-> 5ab0a8cb2fc98
  private String name = ""; //-> hip-plc-domain_DEV
  private String clusterId = ""; //-> b03422d3c68d6ae170724fe852814310
  private String type = ""; //-> 0
  private List<String> host_names = new ArrayList<String>(); //->  size = 1
  private String _object_id = ""; //-> CLUSTER
  private String _object_version = ""; //-> 16
  private String creator = ""; //-> easyops
  private String ctime = ""; //-> 2018-03-20 14:23:07
  private String org = ""; //-> 1045
  private String _ts = ""; //-> 1524153964
  private String _version = ""; //-> 2
  private String modifier = ""; //-> easyops
  private String mtime = ""; //-> 2018-04-20 00:06:04
  private List<AppIdModel> appId = new ArrayList<AppIdModel>(); //->  size = 1
  private List<String> deviceList = new ArrayList<String>(); //->  size = 0

  public String getRid() {
    return rid;
  }

  public void setRid(String rid) {
    this.rid = rid;
  }

  public String getInstanceId() {
    return instanceId;
  }

  public void setInstanceId(String instanceId) {
    this.instanceId = instanceId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getClusterId() {
    return clusterId;
  }

  public void setClusterId(String clusterId) {
    this.clusterId = clusterId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String get_object_id() {
    return _object_id;
  }

  public void set_object_id(String _object_id) {
    this._object_id = _object_id;
  }

  public String get_object_version() {
    return _object_version;
  }

  public void set_object_version(String _object_version) {
    this._object_version = _object_version;
  }

  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public String getCtime() {
    return ctime;
  }

  public void setCtime(String ctime) {
    this.ctime = ctime;
  }

  public String getOrg() {
    return org;
  }

  public void setOrg(String org) {
    this.org = org;
  }

  public String get_ts() {
    return _ts;
  }

  public void set_ts(String _ts) {
    this._ts = _ts;
  }

  public String get_version() {
    return _version;
  }

  public void set_version(String _version) {
    this._version = _version;
  }

  public String getModifier() {
    return modifier;
  }

  public void setModifier(String modifier) {
    this.modifier = modifier;
  }

  public String getMtime() {
    return mtime;
  }

  public void setMtime(String mtime) {
    this.mtime = mtime;
  }

  public List<String> getHost_names() {
    return host_names;
  }

  public void setHost_names(List<String> host_names) {
    this.host_names = host_names;
  }

  public List<AppIdModel> getAppId() {
    return appId;
  }

  public void setAppId(List<AppIdModel> appId) {
    this.appId = appId;
  }

  public List<String> getDeviceList() {
    return deviceList;
  }

  public void setDeviceList(List<String> deviceList) {
    this.deviceList = deviceList;
  }
}
