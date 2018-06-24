package com.taikang.dataVis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.taikang.dataVis.domain.HostModel;

@Mapper
public interface HostMapper {
	List<String> getAllIp();
	/**
	 * 保存host列表
	 * @MethodName: save
	 * @param hostModel
	 * @return void
	 * @throws
	 */
	void save(HostModel hostModel);
	/**
	 * 批量保存host列表
	 * @MethodName: saveBatch
	 * @param list
	 * @return void
	 * @throws
	 */
	 int saveBatch(List<HostModel> list);
	 /**
	  * 删除所有数据
	  * @MethodName: deleteAll
	  * @return void
	  * @throws
	  */
	 void deleteAll();
	 /**
	  * 查询instance的ip列表
	  * @MethodName: getAllIpInstance
	  * @return
	  * @return List<String>
	  * @throws
	  */
	List<String> getAllIpInstance();
	 /**
	  * 查询组件的ip列表
	  * @MethodName: getAllIpUnit
	  * @return
	  * @return List<String>
	  * @throws
	  */
	List<String> getAllIpUnit();
	String getInstanceAlarmCoverageresult(@Param("osSystems")String osSystems, @Param("zone")String zone);
	String getInstanceAlarmCoveragereUsedsult(@Param("osSystems")String osSystems, @Param("zone")String zone);
	//模糊匹配查询
	List<Map<String, Object>> getCoverageAllcountBytype(@Param("serch_word")String serch_word);
	List<Map<String, Object>> getAllcountBytype(@Param("serch_word")String serch_word);
}
