package com.taikang.dataVis.domain;

import java.io.Serializable;

public class LoadbalancerModel implements Serializable{

	private String id;
	private String loadbalancerId;
	private String name;
	private String loadbalancerName;
	private String status;
	private String privateIps;
	private String address;
	private String cloudType;
	private String zoneName;
	private String createTime;
	private String statusTime;
	private String appName;
	private String user;
	private String refreshTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoadbalancerId() {
		return loadbalancerId;
	}
	public void setLoadbalancerId(String loadbalancerId) {
		this.loadbalancerId = loadbalancerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLoadbalancerName() {
		return loadbalancerName;
	}
	public void setLoadbalancerName(String loadbalancerName) {
		this.loadbalancerName = loadbalancerName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPrivateIps() {
		return privateIps;
	}
	public void setPrivateIps(String privateIps) {
		this.privateIps = privateIps;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCloudType() {
		return cloudType;
	}
	public void setCloudType(String cloudType) {
		this.cloudType = cloudType;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getStatusTime() {
		return statusTime;
	}
	public void setStatusTime(String statusTime) {
		this.statusTime = statusTime;
	}
	
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appTame) {
		this.appName = appTame;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getRefreshTime() {
		return refreshTime;
	}
	public void setRefreshTime(String refreshTime) {
		this.refreshTime = refreshTime;
	}

	
}
