package com.taikang.dataVis.screen.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taikang.dataVis.screen.service.CoverageAllService;
import com.taikang.dataVis.screen.service.CoveragePercentService;

import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

@RestController
@RequestMapping("/api")
public class CoverageAllResource {

	@Autowired
	private CoveragePercentService coveragePercentService;
	@Autowired
	private CoverageAllService coverageAllService;
	
	/**
	 * 接口取数据不带时间参数
	 * @MethodName: getCoveragePercent
	 * @return
	 * @return String
	 * @throws
	 */
	@ApiOperation(tags={"监控覆盖度总接口"}, value="覆盖度不带时间区间参数", notes="host,rdb等")
	@GetMapping(value = "/coverage/get-coverage-percent/{type}")
	public String getCoveragePercent(@PathVariable String type) {
		String result = "";
		if("host".equals(type) || "虚拟机".equals(type)) {
			result = coveragePercentService.getCoverageHost();
		}else if("rdb".equals(type) || "数据库".equals(type)) {
			result = coveragePercentService.getCoverageRdb();
		}else if("instance".equals(type) ) {
			result = coveragePercentService.getCoverageInstance();
		}else if("unit".equals(type) || "组件".equals(type)) {
			result = coveragePercentService.getCoverageUnit();
		}else {
			result = coverageAllService.getCoverageAll(type);
		}
		return result;
	}
}
