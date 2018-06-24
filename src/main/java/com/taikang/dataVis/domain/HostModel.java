package com.taikang.dataVis.domain;

import java.io.Serializable;
/**
 * host信息实体类
 * 项目名称:	[cmdb-core]
 * 包:		[com.taikang.dataVis.domain]
 * 类名称:	[HostModel]
 * 创建人:	[itw_liangbo01]
 * 创建时间:	[2018年5月10日 下午4:03:19]
 * 修改人:	[itw_liangbo01]
 * 修改时间:	[2018年5月10日 下午4:03:19]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */
public class HostModel implements Serializable {
	/**
	 * @Fields: serialVersionUID
	 * @Type: long
	 * @Description: TODO(用一句话描述这个变量表示什么)
	 */ 
	private static final long serialVersionUID = 1L;
	private String id;				//	uuid
	private String hostname;		//	主机名称
	private String display_name;	//	显示名称
	private String ip;				//	ip
	private String status;			//	状态
	private String device_id;		//	设备ID
	private String mem_size;		//	内存大小（KB）
	private String disk_size;		//	磁盘大小（KB）
	private String cpus;			//	CPU总数
	private String os_release;		//	操作系统内核发行版本
	private String os_system;		//	操作系统类型
	private String id_in_cloud;		//	云平台 id
	private String cloud_type;		//	云平台类型
	private String zone_name;		//	zone-name
	private String object_version;	//	对象版本
	private String creator;			//	创建人
	private String ctime;			//	创建时间
	private String create_time;		//	本条数据插入时间
	
	public String getZone_name() {
		return zone_name;
	}
	public void setZone_name(String zone_name) {
		this.zone_name = zone_name;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getMem_size() {
		return mem_size;
	}
	public void setMem_size(String mem_size) {
		this.mem_size = mem_size;
	}
	public String getDisk_size() {
		return disk_size;
	}
	public void setDisk_size(String disk_size) {
		this.disk_size = disk_size;
	}
	public String getCpus() {
		return cpus;
	}
	public void setCpus(String cpus) {
		this.cpus = cpus;
	}
	public String getOs_release() {
		return os_release;
	}
	public void setOs_release(String os_release) {
		this.os_release = os_release;
	}
	public String getOs_system() {
		return os_system;
	}
	public void setOs_system(String os_system) {
		this.os_system = os_system;
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
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
}
