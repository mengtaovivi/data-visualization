package com.taikang.dataVis.domain;

import java.io.Serializable;

/**
 * 项目名称:	[cmdb-core]
 * 包:		[com.taikang.dataVis.domain]
 * 类名称:	[RDBModel]
 * 创建人:	[itw_guodc]
 * 创建时间:	[2018年5月16日 上午10:55:43]
 * 修改人:	[itw_guodc]
 * 修改时间:	[2018年5月16日 上午10:55:43]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */ 
public class RDBModel implements Serializable{
	
	private String id;
	private String rdbId;
	private String name;
	private String rdbName;
	private String status;
	private String masterIp;
	private String rvip;
	private String wvip;
	private String cloudType;
	private String rdbEngine;
	private String zoneName;
	private String refreshTime;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRdbId() {
		return rdbId;
	}
	public void setRdbId(String rdbId) {
		this.rdbId = rdbId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRdbName() {
		return rdbName;
	}
	public void setRdbName(String rdbName) {
		this.rdbName = rdbName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMasterIp() {
		return masterIp;
	}
	public void setMasterIp(String masterIp) {
		this.masterIp = masterIp;
	}
	public String getRvip() {
		return rvip;
	}
	public void setRvip(String rvip) {
		this.rvip = rvip;
	}
	public String getWvip() {
		return wvip;
	}
	public void setWvip(String wvip) {
		this.wvip = wvip;
	}
	public String getCloudType() {
		return cloudType;
	}
	public void setCloudType(String cloudType) {
		this.cloudType = cloudType;
	}
	public String getRdbEngine() {
		return rdbEngine;
	}
	public void setRdbEngine(String rdbEngine) {
		this.rdbEngine = rdbEngine;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getRefreshTime() {
		return refreshTime;
	}
	public void setRefreshTime(String refreshTime) {
		this.refreshTime = refreshTime;
	}
	


}
