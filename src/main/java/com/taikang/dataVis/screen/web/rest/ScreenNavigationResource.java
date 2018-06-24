package com.taikang.dataVis.screen.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.taikang.dataVis.core.base.BaseResource;
import com.taikang.dataVis.domain.ScreenNavigation;
import com.taikang.dataVis.screen.service.ScreenNavigationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
/**
 * 大屏导航控制类
 * 项目名称:	[data-visualization]
 * 包:		[com.taikang.dataVis.screen.web.rest]
 * 类名称:	[ScreenNavigationResource]
 * 创建人:	[itw_liangbo01]
 * 创建时间:	[2018年5月25日 上午10:00:57]
 * 修改人:	[itw_liangbo01]
 * 修改时间:	[2018年5月25日 上午10:00:57]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */
@RestController
@RequestMapping("/api")
@Api(value="ScreenNavigationResource")
public class ScreenNavigationResource extends BaseResource {
	@Autowired
	private ScreenNavigationService screenNavigationService;
	/**
	 * 查询一条
	 * @MethodName: getOne
	 * @param id
	 * @return
	 * @return ScreenNavigation
	 * @throws
	 */
	@ApiOperation(tags={"大屏导航"}, value="根据id查询一条数据", notes="")
	@GetMapping(value = "/screen-navigation/get-one/{id}")
	public ScreenNavigation getOne(@PathVariable @ApiParam(value = "id") int id) {
		return screenNavigationService.getOne(id);
	}
	/**
	 * 查询多条
	 * @MethodName: getMany
	 * @return
	 * @return PageInfo<ScreenNavigation>
	 * @throws
	 */
	@ApiOperation(tags={"大屏导航"}, value="查询多条数据", notes="")
	@GetMapping(value = "/screen-navigation/get-many")
	public PageInfo<ScreenNavigation> getMany() {
		List<ScreenNavigation> list = screenNavigationService.getMany();
		PageInfo<ScreenNavigation> pages = new PageInfo<ScreenNavigation>(list);
		return pages;
	}
	/**
	 * 保存
	 * @MethodName: save
	 * @param screenNavigation
	 * @return
	 * @return boolean
	 * @throws
	 */
	@ApiOperation(tags={"大屏导航"}, value="保存", notes="")
	@PutMapping(value = "/screen-navigation/save")
	public boolean save(@RequestBody ScreenNavigation screenNavigation) {
		return screenNavigationService.save(screenNavigation);
	}
	/**
	 * 修改
	 * @MethodName: update
	 * @param screenNavigation
	 * @return
	 * @return boolean
	 * @throws
	 */
	@ApiOperation(tags={"大屏导航"}, value="修改", notes="")
	@PostMapping(value = "/screen-navigation/update")
	public boolean update(@RequestBody ScreenNavigation screenNavigation) {
		return screenNavigationService.update(screenNavigation);
	}
	/**
	 * 删除
	 * @MethodName: delete
	 * @param id
	 * @return
	 * @return boolean
	 * @throws
	 */
	@ApiOperation(tags={"大屏导航"}, value="删除", notes="")
	@DeleteMapping(value = "/screen-navigation/delete/{id}")
	public boolean delete(@PathVariable @ApiParam(value = "id") int id) {
		return screenNavigationService.delete(id);
	}
}
