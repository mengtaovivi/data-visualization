package com.taikang.dataVis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.taikang.dataVis.domain.LoadbalancerModel;

@Mapper
public interface LoadbalancerMapper {

	void deleteAll();

	void saveBatch(List<LoadbalancerModel> list);

	List<Map<String, Object>> getCoverageAllcountBytype(@Param("serch_word")String serch_word);

	List<Map<String, Object>> getAllcountBytype(@Param("serch_word")String serch_word);

}
