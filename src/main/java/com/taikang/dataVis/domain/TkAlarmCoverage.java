package com.taikang.dataVis.domain;

import java.io.Serializable;

/**
 * 项目名称:	[cmdb-core]
 * 包:		[com.taikang.dataVis.screen.domain]
 * 类名称:	[TkAlarmCoverage]
 * 创建人:	[itw_guodc]
 * 创建时间:	[2018年5月3日 下午2:32:58]
 * 修改人:	[itw_guodc]
 * 修改时间:	[2018年5月3日 下午2:32:58]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */ 
public class TkAlarmCoverage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//主键id
	private Long id;
	//操作外键
	private String operationId;
	//操作系统
	private String operationOs;
	//操作系统数量
	private String allOperationOs;
	//已用数量
	private String usedOperationOs;
	//所属区域
	private String zone;
	//创建日期
	private String createDate;    
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOperationId() {
		return operationId;
	}
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}
	public String getOperationOs() {
		return operationOs;
	}
	public void setOperationOs(String operationOs) {
		this.operationOs = operationOs;
	}
	public String getAllOperationOs() {
		return allOperationOs;
	}
	public void setAllOperationOs(String allOperationOs) {
		this.allOperationOs = allOperationOs;
	}
	public String getUsedOperationOs() {
		return usedOperationOs;
	}
	public void setUsedOperationOs(String usedOperationOs) {
		this.usedOperationOs = usedOperationOs;
	}
	
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}


}
