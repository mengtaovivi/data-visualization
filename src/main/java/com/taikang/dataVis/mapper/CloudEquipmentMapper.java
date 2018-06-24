package com.taikang.dataVis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.taikang.dataVis.domain.CloudEquipment;

@Mapper
public interface CloudEquipmentMapper {

	List<String> findAllDate();

	List<CloudEquipment> findNewestAll();

	List<CloudEquipment> findByCreateTime(Map<String, Object> map);

	int insertBatch(List<CloudEquipment> list);

	int update(CloudEquipment cloudEquipment);

}
