package com.taikang.dataVis.domain;

/**
 * 项目名称:	[data-visualization]
 * 包:		[com.taikang.dataVis.domain]
 * 类名称:	[CloudEquipment]
 * 创建人:	[itw_guodc]
 * 创建时间:	[2018年5月28日 上午11:16:56]
 * 修改人:	[itw_guodc]
 * 修改时间:	[2018年5月28日 上午11:16:56]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */ 
public class CloudEquipment {
	
	private String id;
	private String department;
	private String inVal;
	private String outVal;
	private String replaceVal;
	private String createTime;
	private String updateTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getInVal() {
		return inVal;
	}
	public void setInVal(String inVal) {
		this.inVal = inVal;
	}
	public String getOutVal() {
		return outVal;
	}
	public void setOutVal(String outVal) {
		this.outVal = outVal;
	}
	public String getReplaceVal() {
		return replaceVal;
	}
	public void setReplaceVal(String replaceVal) {
		this.replaceVal = replaceVal;
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
