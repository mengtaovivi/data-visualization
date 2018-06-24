package com.taikang.dataVis.screen.service;

import java.util.List;

import com.taikang.dataVis.domain.ScreenNavigation;

/**
 * 大屏导航
 * 项目名称:	[data-visualization]
 * 包:		[com.taikang.dataVis.screen.service]
 * 类名称:	[ScreenNavigationService]
 * 创建人:	[itw_liangbo01]
 * 创建时间:	[2018年5月25日 上午10:03:12]
 * 修改人:	[itw_liangbo01]
 * 修改时间:	[2018年5月25日 上午10:03:12]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */
public interface ScreenNavigationService {
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
