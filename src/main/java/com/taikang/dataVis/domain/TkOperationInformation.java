package com.taikang.dataVis.domain;

public class TkOperationInformation {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//主键id
	private String id;
	//操作ip
	private String operationIp;
	//采集资源分类
	private String operationType;
	//操作日期
	private String operationDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOperationIp() {
		return operationIp;
	}
	public void setOperationIp(String operationIp) {
		this.operationIp = operationIp;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}


	
}
