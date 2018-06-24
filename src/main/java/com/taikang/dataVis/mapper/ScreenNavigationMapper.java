package com.taikang.dataVis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.taikang.dataVis.domain.ScreenNavigation;

@Mapper
public interface ScreenNavigationMapper {
	//查询一条
	ScreenNavigation getOne(int id);
	//查询多条
	List<ScreenNavigation> getMany();
	//添加
	boolean save(ScreenNavigation screenNavigation);
	//修改
	boolean update(ScreenNavigation screenNavigation);
	//删除
	boolean delete(int id);
}
