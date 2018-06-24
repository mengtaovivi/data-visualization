package com.taikang.dataVis.screen.web.rest;

import java.util.List;
import java.util.Map;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.taikang.dataVis.core.base.BaseResource;
import com.taikang.dataVis.domain.CloudEquipment;
import com.taikang.dataVis.screen.service.CloudEquipmentService;

import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;

/**
 * 项目名称:	[data-visualization]
 * 包:		[com.taikang.dataVis.screen.web.rest]
 * 类名称:	[CloudEquipmentResource]
 * 创建人:	[itw_guodc]
 * 创建时间:	[2018年5月28日 上午9:54:21]
 * 修改人:	[itw_guodc]
 * 修改时间:	[2018年5月28日 上午9:54:21]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */ 

@RestController
@RequestMapping("/api")
public class CloudEquipmentResource extends BaseResource{

	@Autowired
	private CloudEquipmentService cloudEquipmentService ;
	
	/**
	 * @MethodName: findAllDate
	 * @return
	 * @return List<String>
	 * @throws
	 */ 
	@ApiOperation(value="获取所有日期-列表页面",tags={"云中心设备上下架"},notes="无参数")
	@GetMapping(value = "/find-all-cloud-equipment-date")
	public List<String> findAllDate() {
		return cloudEquipmentService.findAllDate();
	}
	
	
	/**
	 *
	 *
	 * @description 查询最近一次各处云中心设备上下架情况
	 * @date 2018/5/28 14:06
	 * @return com.github.pagehelper.PageInfo<CloudEquipment>
	 */
	@ApiOperation(value="获取最新时间各处云中心设备上下架情况",tags={"云中心设备上下架"},notes="无参数")
	@GetMapping(value = "/cloud-equipment-newest")
	public PageInfo<CloudEquipment> findAll(HttpServletRequest request) {
		this.putParamPage(request);
		List<CloudEquipment> list = cloudEquipmentService.findNewestAll() ;
		//将list封装到pageinfo中返回给前台
		PageInfo<CloudEquipment> pages = new PageInfo<CloudEquipment>(list);
		return  pages;
	}
	
	
	/**
	 *
	 *
	 * @description 单条设备上下架信息  {
      "id": "",
      "department": "",
      "inVal": "",
      "outVal": "",
      "replaceVal": "",
      "createTime": "",
      "updateTime": ""
    }
	 * @date 2018/5/28 16:30
	 * @return JSONArray
	 */
	@ApiOperation(value="传时间参数查出对应的信息（创建回显也是此接口）",tags={"云中心设备上下架"},notes="参数：{\"createTime\":\"2018-05-08 15:54:31\"}")
	@GetMapping(value = "/cloud-equipment-time")
	public JSONArray findAllByCreateTime(HttpServletRequest request,@RequestParam String json ) {
		return cloudEquipmentService.findAllByCreateTime(json);
	}
	
	
	@ApiOperation(value="批量保存多个云中心设备上下架信息，并返回保存的条数",tags={"云中心设备上下架"},notes="参数：需要保存的json串信息")
	@PostMapping(value = "/save-cloud-equipment")
	public Map<String,String> saveBatch(@RequestBody String json){
		return cloudEquipmentService.saveBatch(json) ;
	}
	
	
	@ApiOperation(value="批量更新多个云中心设备上下架信息，并返回保存的条数",tags={"云中心设备上下架"},notes="参数：规定的json数组信息")
	@PostMapping(value = "/cloud-equipment-update")
	public Map<String,String> update(@RequestBody String json ) {
		return cloudEquipmentService.updateBatch(json);
	}
}
