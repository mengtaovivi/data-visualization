package com.taikang.dataVis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.taikang.dataVis.domain.IpAddressModel;
/**
 * cmdb的ip列表
 * 项目名称:	[cmdb-core]
 * 包:		[com.taikang.dataVis.screen.mapper]
 * 类名称:	[IpAddressMapper]
 * 创建人:	[itw_liangbo01]
 * 创建时间:	[2018年5月9日 上午11:07:23]
 * 修改人:	[itw_liangbo01]
 * 修改时间:	[2018年5月9日 上午11:07:23]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */
@Mapper
public interface IpAddressMapper {
	List<String> getAllIp();
	/**
	 * 保存ip地址列表
	 * @MethodName: save
	 * @param ipAddressModel
	 * @return void
	 * @throws
	 */
	void save(IpAddressModel ipAddressModel);
	/**
	 * 批量保存ip地址列表
	 * @MethodName: saveBatch
	 * @param list
	 * @return void
	 * @throws
	 */
	 int saveBatch(List<IpAddressModel> list);
	 /**
	  * 删除所有数据
	  * @MethodName: deleteAll
	  * @return void
	  * @throws
	  */
	 void deleteAll();
}
