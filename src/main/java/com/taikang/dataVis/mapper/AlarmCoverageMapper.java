package com.taikang.dataVis.mapper;

import com.taikang.dataVis.domain.TkAlarmCoverage;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/** 
* @ClassName: AlarmCoverageMapper 
* @Description: (AlarmCoverage 增删改查接口) 
* @author 郭德才 
* @date 2018年4月11日 下午3:22:07 
*  
*/

@Mapper
public interface AlarmCoverageMapper {

	void save(TkAlarmCoverage tkAlarmCoverage);

	List<String> getAlloperationOs();

	void update(TkAlarmCoverage tkAlarmCoverage);

	int insertBatch(List<TkAlarmCoverage> listadd);

	int updateBatchByOsSystem(List<TkAlarmCoverage> listupdate);

	List<Map<String,String>> getCoverageHostCount();
	
	List<Map<String,String>> getCoverageRdbCount();

	List<Map<String, String>> getCoverageUnitCountUsed();

	List<Map<String, String>> getCoverageUnitCountAll();
}
