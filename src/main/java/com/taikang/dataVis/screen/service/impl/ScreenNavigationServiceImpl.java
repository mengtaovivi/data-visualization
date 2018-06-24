package com.taikang.dataVis.screen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taikang.dataVis.domain.ScreenNavigation;
import com.taikang.dataVis.mapper.ScreenNavigationMapper;
import com.taikang.dataVis.screen.service.ScreenNavigationService;
/**
 * 大屏导航
 * 项目名称:	[data-visualization]
 * 包:		[com.taikang.dataVis.screen.service.impl]
 * 类名称:	[ScreenNavigationServiceImpl]
 * 创建人:	[itw_liangbo01]
 * 创建时间:	[2018年5月25日 上午10:14:26]
 * 修改人:	[itw_liangbo01]
 * 修改时间:	[2018年5月25日 上午10:14:26]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */
@Service
public class ScreenNavigationServiceImpl implements ScreenNavigationService {
	@Autowired
	private ScreenNavigationMapper screenNavigationMapper;
	//查询一条
	public ScreenNavigation getOne(int id) {
		return screenNavigationMapper.getOne(id);
	}

	//查询多条
	public List<ScreenNavigation> getMany() {
		return screenNavigationMapper.getMany();
	}

	//添加
	@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
	public boolean save(ScreenNavigation screenNavigation) {
		return screenNavigationMapper.save(screenNavigation);
	}

	//修改
	@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
	public boolean update(ScreenNavigation screenNavigation) {
		return screenNavigationMapper.update(screenNavigation);
	}

	//删除
	@Transactional(rollbackFor = { RuntimeException.class, Exception.class })
	public boolean delete(int id) {
		return screenNavigationMapper.delete(id);
	}

}
