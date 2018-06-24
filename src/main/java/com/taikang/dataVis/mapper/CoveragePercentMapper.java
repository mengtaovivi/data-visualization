package com.taikang.dataVis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.taikang.dataVis.domain.CoveragePercentModel;
/**
 * 监控覆盖度
 * 项目名称:	[cmdb-core]
 * 包:		[com.taikang.dataVis.screen.mapper]
 * 类名称:	[CoveragePercentMapper]
 * 创建人:	[itw_liangbo01]
 * 创建时间:	[2018年5月9日 上午11:07:35]
 * 修改人:	[itw_liangbo01]
 * 修改时间:	[2018年5月9日 上午11:07:35]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */
@Mapper
public interface CoveragePercentMapper {
	/**
	 * 保存监控覆盖度百分比
	 * @MethodName: save
	 * @param coveragePercentModel
	 * @return void
	 * @throws
	 */
	void save(CoveragePercentModel coveragePercentModel);
	/**
	 * 取得host监控覆盖度百分比
	 * @MethodName: selectCoverageHost
	 * @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	List<Map<String,String>> selectCoverageHost();
	/**
	 * 根据zone取得覆盖度数据
	 * @MethodName: getCoverageByZone
	 * @param zone
	 * @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	List<Map<String,Object>> getCoverageByZone(@Param("zone") String zone);
	/**
	 * 根据平台类型取得覆盖度数据
	 * @MethodName: getCoverageByType
	 * @param type
	 * @return
	 * @return List<Map<String,Object>>
	 * @throws
	 */
	List<Map<String,Object>> getCoverageByType(String type);
	/**
	 * 取得监控覆盖数量
	 * @MethodName: getCoverageAmount
	 * @return
	 * @return Map<String,Object>
	 * @throws
	 */
	Map<String, Object> getCoverageAmount();
	/**
	 * 根据zone查询host的ip列表
	 * @MethodName: getHostListByZone
	 * @param zone_test
	 * @return
	 * @return List<String>
	 * @throws
	 */
	List<String> getHostIpListByZone(@Param("zone") String zone);
	/**
	 * 取得rdb监控覆盖度百分比
	 * @MethodName: selectCoverageRdb
	 * @return
	 * @return List<Map<String,String>>
	 * @throws
	 */
	List<Map<String, String>> selectCoverageRdb();
	/**
	 * 根据zone分类计算host的操作系统数量
	 * @MethodName: getCoverageCount
	 * @return
	 * @return List<Map<String,String>>
	 * @throws
	 */
	List<Map<String, String>> getCoverageCount();
	/**
	 * 根据类型和时间区间查询历史数据
	 * @MethodName: getCoveragePercentByTime
	 * @param type
	 * @param start_time
	 * @param end_time
	 * @return
	 * @return List<Map<String,String>>
	 * @throws
	 */
	List<Map<String, String>> getCoveragePercentByTime(@Param("type") String type,
			@Param("start_time") String start_time,@Param("end_time") String end_time);
	/**
	 * 根据zone查询instance的ip列表
	 * @MethodName: getInstanceListByZone
	 * @param zone_test
	 * @return
	 * @return List<String>
	 * @throws
	 */
	List<String> getInstanceIpListByZone(@Param("zone") String zone_test);
	/**
	 * 根据zone查询unit的ip列表
	 * @MethodName: getUnitListByZone
	 * @param zone_test
	 * @return
	 * @return List<String>
	 * @throws
	 */
	List<String> getUnitIpListByZone(@Param("zone") String zone_test);
	/**
	 * 取得instance监控覆盖度百分比
	 * @MethodName: selectCoverageInstance
	 * @return
	 * @return List<Map<String,String>>
	 * @throws
	 */
	List<Map<String, String>> selectCoverageInstance();
	/**
	 * 取得unit监控覆盖度百分比
	 * @MethodName: selectCoverageUnit
	 * @return
	 * @return List<Map<String,String>>
	 * @throws
	 */
	List<Map<String, String>> selectCoverageUnit();

	List<Map<String, String>> getCoverageUnitCountUsed(@Param("zone") String zone);
	List<Map<String, String>> getCoverageUnitCountAll(@Param("zone") String zone);
	List<Map<String, String>> getCoverageInstanceCountUsed(@Param("zone") String zone);
	List<Map<String, String>> getCoverageInstanceCountAll(@Param("zone") String zone);
}
