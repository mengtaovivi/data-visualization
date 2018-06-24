package com.taikang.dataVis.domain;

import java.io.Serializable;
/**
 * cmdb的ip列表
 * 项目名称:	[cmdb-core]
 * 包:		[com.taikang.dataVis.screen.domain]
 * 类名称:	[IpAddressModel]
 * 创建人:	[itw_liangbo01]
 * 创建时间:	[2018年5月9日 上午11:06:59]
 * 修改人:	[itw_liangbo01]
 * 修改时间:	[2018年5月9日 上午11:06:59]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */
public class IpAddressModel implements Serializable {

	/**
	 * @Fields: serialVersionUID
	 * @Type: long
	 * @Description: TODO(用一句话描述这个变量表示什么)
	 */ 
	private static final long serialVersionUID = 1L;
	private String id;				//	uuid
	private String name;			//	名称	
	private String qingcloud_id;	//	青云ID
	private String address;			//	IP地址
	private String type;			//	IP类型
	private String id_in_cloud;		//	云平台 id
	private String cloud_type;		//	云平台类型
	private String instance_id;		//	主机id
	private String object_id;		//	对象id
	private String object_version;	//	对象版本
	private String creator;			//	创建人
	private String ctime;			//	创建时间
	private String create_time;		//	本条数据插入时间
	private String os_system;		//	操作系统类型
	private String status;			//	状态
	public String getOs_system() {
		return os_system;
	}
	public void setOs_system(String os_system) {
		this.os_system = os_system;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQingcloud_id() {
		return qingcloud_id;
	}
	public void setQingcloud_id(String qingcloud_id) {
		this.qingcloud_id = qingcloud_id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId_in_cloud() {
		return id_in_cloud;
	}
	public void setId_in_cloud(String id_in_cloud) {
		this.id_in_cloud = id_in_cloud;
	}
	public String getCloud_type() {
		return cloud_type;
	}
	public void setCloud_type(String cloud_type) {
		this.cloud_type = cloud_type;
	}
	public String getInstance_id() {
		return instance_id;
	}
	public void setInstance_id(String instance_id) {
		this.instance_id = instance_id;
	}
	public String getObject_id() {
		return object_id;
	}
	public void setObject_id(String object_id) {
		this.object_id = object_id;
	}
	public String getObject_version() {
		return object_version;
	}
	public void setObject_version(String object_version) {
		this.object_version = object_version;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
}
