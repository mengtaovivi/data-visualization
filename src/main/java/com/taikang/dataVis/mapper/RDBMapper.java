package com.taikang.dataVis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.taikang.dataVis.domain.RDBModel;

@Mapper
public interface RDBMapper {

	/**
	 * 批量保存RDB列表
	 * @MethodName: saveBatch
	 * @param list
	 * @return void
	 * @throws
	 */
	void saveBatch(List<RDBModel> list);
	
	/**
	  * 删除所有数据
	  * @MethodName: deleteAll
	  * @return void
	  * @throws
	  */
	void deleteAll();

	Long getBaseCount(String zoneName);

	Long getIntersectionCount(String zoneName);

	List<String> getZoneAndOs();

	List<String> getZones();

	String getRdbAlarmCoverageresult(@Param("rdbEngines")String rdbEngines, @Param("zone")String zone);

	String getRdbAlarmCoveragereUsedsult(@Param("rdbEngines")String rdbEngines, @Param("zone")String zone);

	List<Map<String, Object>> getCoverageAllcountBytype(@Param("serch_word")String serch_word);

	List<Map<String, Object>> getAllcountBytype(@Param("serch_word")String serch_word);

	List<String> getAllRDBEngine(@Param("serch_word")String serch_word);

}
