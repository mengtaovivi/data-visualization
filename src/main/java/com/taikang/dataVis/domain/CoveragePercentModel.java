package com.taikang.dataVis.domain;

import java.io.Serializable;
/**
 * 监控覆盖度的模型类
 * 项目名称:	[cmdb-core]
 * 包:		[com.taikang.dataVis.screen.domain]
 * 类名称:	[CoveragePercentModel]
 * 创建人:	[itw_liangbo01]
 * 创建时间:	[2018年5月8日 上午11:35:43]
 * 修改人:	[itw_liangbo01]
 * 修改时间:	[2018年5月8日 上午11:35:43]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */
public class CoveragePercentModel implements Serializable {

	/**
	 * @Fields: serialVersionUID
	 * @Type: long
	 * @Description: TODO(用一句话描述这个变量表示什么)
	 */ 
	private static final long serialVersionUID = 1L;
	private String id;					//	uuid
	private String compareType;			//	比较的集合来源类型，比如从cmdb来的就是cmdb
	private String compareCount;		//	比较的集合数据总数量
	private String baseType;			//	被比较的集合来源类型，比如从zabbix来的就是zabbix
	private String baseCount;			//	被比较的集合数据总数量
	private String intersectionCount;	//	交集的数量
	private String percent;				//	交集的数量/被比较的集合数据总数量
	private String createDate;			//	数据计算时间
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCompareType() {
		return compareType;
	}
	public void setCompareType(String compareType) {
		this.compareType = compareType;
	}
	public String getCompareCount() {
		return compareCount;
	}
	public void setCompareCount(String compareCount) {
		this.compareCount = compareCount;
	}
	public String getBaseType() {
		return baseType;
	}
	public void setBaseType(String baseType) {
		this.baseType = baseType;
	}
	public String getBaseCount() {
		return baseCount;
	}
	public void setBaseCount(String baseCount) {
		this.baseCount = baseCount;
	}
	public String getIntersectionCount() {
		return intersectionCount;
	}
	public void setIntersectionCount(String intersectionCount) {
		this.intersectionCount = intersectionCount;
	}
	public String getPercent() {
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
