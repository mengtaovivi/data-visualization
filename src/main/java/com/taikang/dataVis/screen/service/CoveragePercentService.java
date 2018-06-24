package com.taikang.dataVis.screen.service;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface CoveragePercentService {
	/**
	 * 查询zabbix的监控数据ip列表
	 * @MethodName: getZabbixCoverage
	 * @return
	 * @return JSONArray
	 * @throws
	 */
	JSONArray getZabbixCoverage(String sql);
	/**
	 * 计算监控覆盖度并保存监控覆盖度到数据库
	 * @MethodName: calCoveragePercent
	 * @return
	 * @return String
	 * @throws
	 */
	boolean calAndSaveCoveragePercent();
	/**
	 * 保存cmdb的ip列表到数据库
	 * @MethodName: saveCoverageIpList
	 * @return
	 * @return boolean
	 * @throws
	 */
	boolean saveCoverageIpList();
	/**
	 * 删除所有数据
	 * @MethodName: deleteCoverageIpList
	 * @return void
	 * @throws
	 */
	void deleteCoverageIpList();
	/**
	 * 得到所有cmdb的ip列表
	 * @MethodName: getAllIp
	 * @return
	 * @return List<String>
	 * @throws
	 */
	List<String> getAllIp();
	/**
	 * 根据平台类型取得覆盖度数据
	 * @MethodName: getCoverageByType
	 * @param type
	 * @return
	 * @return String
	 * @throws
	 */
	String getCoverageByType(String type);
	/**
	 * 根据zone取得覆盖度数据
	 * @MethodName: getCoverageByZone
	 * @param zone
	 * @return
	 * @return String
	 * @throws
	 */
	String getCoverageByZone(String zone);
	/**
	 * 取得host监控覆盖度百分比
	 * @MethodName: getCoverageHost
	 * @return
	 * @return String
	 * @throws
	 */
	String getCoverageHost();
	/**
	 * 取得监控覆盖数量
	 * @MethodName: getCoverageAmount
	 * @return
	 * @return String
	 * @throws
	 */
	String getCoverageAmount();
	/**
	 * 保存host列表
	 * @MethodName: saveCoverageHostList
	 * @return
	 * @return boolean
	 * @throws
	 */
	boolean saveCoverageHostList();
	/**
	 * 删除所有数据
	 * @MethodName: deleteCoverageHostList
	 * @return void
	 * @throws
	 */
	void deleteCoverageHostList();
	/**
	 * 计算host的覆盖度
	 * @MethodName: calAndSaveHostCoveragePercent
	 * @return
	 * @return boolean
	 * @throws
	 */
	boolean calAndSaveHostCoveragePercent();
	
	
	/**
	 * 计算rdb的覆盖度
	 * @MethodName: calAndSaveHostCoveragePercent
	 * @return
	 * @return boolean
	 * @throws
	 */
	boolean calAndSaveRDBCoveragePercent();
	
	/**
	 * 当前监控已使用指标数
	 * @MethodName: getItemsAmount
	 * @return
	 * @return String
	 * @throws
	 */
	Long getItemsAmountByHostIP();
	
	/**
	 * 当前ip对应监控已使用指标数
	 * @MethodName: calAndSaveHostCoveragePercent
	 * @return
	 * @return boolean
	 * @throws
	 */
	Long getItemsAmountByIP(String[] ips);
	
	/**
	 * 计算IP对应指标数并入库
	 * @MethodName: calAndSaveHostCoveragePercent
	 * @return
	 * @return boolean
	 * @throws
	 */
	boolean SaveItemsAmountByIP();
	/**
	 * 取得rdb监控覆盖度百分比
	 * @MethodName: getCoverageRdb
	 * @return
	 * @return String
	 * @throws
	 */
	String getCoverageRdb();
	String getCoveragePercentByTime(String paramStr);
	
	/**
	 * 计算当前HOST监控已使用指标数
	 * @MethodName: calAndSaveItemsAmountByHostIP
	 * @return boolean
	 * @throws
	 */
	boolean calAndSaveItemsAmountByHostIP();
	
	/**
	 * 计算当前RDB监控已使用指标数
	 * @MethodName: calAndSaveItemsAmountByRDBIP
	 * @return boolean
	 * @throws
	 */
	boolean calAndSaveItemsAmountByRDBIP();
	/**
	 * 计算Instance监控覆盖度并入库
	 * @MethodName: calAndSaveInstanceCoveragePercent
	 * @return
	 * @return boolean
	 * @throws
	 */
	boolean calAndSaveInstanceCoveragePercent();
	/**
	 * 计算unit监控覆盖度并入库
	 * @MethodName: calAndSaveUnitCoveragePercent
	 * @return
	 * @return boolean
	 * @throws
	 */
	boolean calAndSaveUnitCoveragePercent();
	String getCoverageInstance();
	String getCoverageUnit();
}
