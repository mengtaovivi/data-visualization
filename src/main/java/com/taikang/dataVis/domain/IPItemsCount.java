package com.taikang.dataVis.domain;

/**
 * 项目名称:	[cmdb-core]
 * 包:		[com.taikang.dataVis.domain]
 * 类名称:	[IPItemsCount]
 * 创建人:	[itw_guodc]
 * 创建时间:	[2018年5月14日 上午11:44:35]
 * 修改人:	[itw_guodc]
 * 修改时间:	[2018年5月14日 上午11:44:35]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */ 
public class IPItemsCount {

	private Long id;
	private String ip;
	private Long itemsCount;
	private String hostid;
	private String createDate;
	private String enableFlag;
	public Long getItemsCount() {
		return itemsCount;
	}
	public void setItemsCount(Long itemsCount) {
		this.itemsCount = itemsCount;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getEnableFlag() {
		return enableFlag;
	}
	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getHostid() {
		return hostid;
	}
	public void setHostid(String hostid) {
		this.hostid = hostid;
	}
	

	
}
