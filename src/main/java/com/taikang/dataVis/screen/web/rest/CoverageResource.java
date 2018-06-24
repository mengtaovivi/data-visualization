package com.taikang.dataVis.screen.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taikang.dataVis.screen.service.CoveragePercentService;

import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
/**
 * 监控覆盖度的手动同步
 * 项目名称:	[cmdb-core]
 * 包:		[com.taikang.dataVis.screen.web.rest]
 * 类名称:	[CoverageResource]
 * 创建人:	[itw_liangbo01]
 * 创建时间:	[2018年5月9日 上午11:23:51]
 * 修改人:	[itw_liangbo01]
 * 修改时间:	[2018年5月9日 上午11:23:51]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */
@RestController
@RequestMapping("/api")
public class CoverageResource {
	@Autowired
	private CoveragePercentService coveragePercentService;
	/**
	 * 保存cmdb的ip列表到数据库
	 * 先删除原表数据，后保存新数据
	 * @MethodName: saveCoverageIpList
	 * @return
	 * @return String
	 * @throws
	 */
	@ApiOperation(tags={"监控覆盖度"}, value="保存IPADDRESS的ip列表", notes="")
	@GetMapping(value = "/coverage/save-coverage-ip-list")
	public String saveCoverageIpList() {
		//先删除
		coveragePercentService.deleteCoverageIpList();
		//再保存
		boolean res = coveragePercentService.saveCoverageIpList();
		return String.valueOf(res);
	}
	/**
	 * 计算监控覆盖度并入库
	 * @MethodName: calAndSaveCoveragePercent
	 * @return
	 * @return String
	 * @throws
	 */
	@ApiOperation(tags={"监控覆盖度"}, value="计算IPADDRESS的覆盖度", notes="")
	@GetMapping(value = "/coverage/cal-and-save-coverage-percent")
	public String calAndSaveCoveragePercent() {
		boolean res = coveragePercentService.calAndSaveCoveragePercent();
		return String.valueOf(res);
	}
	/**
	 * 保存cmdb的host列表到数据库
	 * @MethodName: saveCoverageHostList
	 * @return
	 * @return String
	 * @throws
	 */
	@ApiOperation(tags={"监控覆盖度"}, value="保存host的ip列表", notes="")
	@GetMapping(value = "/coverage/save-coverage-host-list")
	public String saveCoverageHostList() {
		//先删除
		coveragePercentService.deleteCoverageHostList();
		//再保存
		boolean res = coveragePercentService.saveCoverageHostList();
		return String.valueOf(res);
	}	
	/**
	 * 计算host监控覆盖度并入库
	 * @MethodName: calAndSaveHostCoveragePercent
	 * @return
	 * @return String
	 * @throws
	 */
	@ApiOperation(tags={"监控覆盖度"}, value="计算host的覆盖度", notes="")
	@GetMapping(value = "/coverage/cal-and-save-host-coverage-percent")
	public String calAndSaveHostCoveragePercent() {
		boolean res = coveragePercentService.calAndSaveHostCoveragePercent();
		return String.valueOf(res);
	}	
	
	/**
	 * 计算rdb监控覆盖度并入库
	 * @MethodName: calAndSaveRDBCoveragePercent
	 * @return
	 * @return String
	 * @throws
	 */
	@ApiOperation(tags={"监控覆盖度"}, value="计算rdb的覆盖度", notes="")
	@GetMapping(value = "/coverage/cal-and-save-rdb-coverage-percent")
	public String calAndSaveRDBCoveragePercent() {
		boolean res = coveragePercentService.calAndSaveRDBCoveragePercent();
		return String.valueOf(res);
	}	
	/**
	 * 根据平台类型取得覆盖度数据
	 * @MethodName: getCoverageByType
	 * @param type
	 * @return
	 * @return String
	 * @throws
	 */
	@ApiOperation(tags={"监控覆盖度"}, value="根据类型取得覆盖度", notes="")
	@GetMapping(value = "/coverage/get-coverage-by-type/{type}")
	public String getCoverageByType(@PathVariable String type) {
		return coveragePercentService.getCoverageByType(type);
	}
	/**
	 * 根据zone取得覆盖度数据
	 * @MethodName: getCoverageByZone
	 * @param zone
	 * @return
	 * @return String
	 * @throws
	 */
	@ApiOperation(tags={"监控覆盖度"}, value="根据zone取得覆盖度", notes="")
	@GetMapping(value = "/coverage/get-coverage-by-zone/{zone}")
	public String getCoverageByZone(@PathVariable String zone) {
		return coveragePercentService.getCoverageByZone(zone);
	}
	/**
	 * 取得监控覆盖数量
	 * @MethodName: getCoverageAmount
	 * @return
	 * @return String
	 * @throws
	 */
	@ApiOperation(tags={"监控覆盖度"}, value="取得监控覆盖数量", notes="")
	@GetMapping(value = "/coverage/get-coverage-amount")
	public String getCoverageAmount() {
		return coveragePercentService.getCoverageAmount();
	}
	
	/**
	 * 当前监控已使用指标数
	 * @MethodName: getCoverageAmount
	 * @return
	 * @return String
	 * @throws
	 */
	@ApiOperation(tags={"监控覆盖度"}, value="当前监控已使用指标数", notes="")
	@GetMapping(value = "/coverage/get-items-amount-host-ip")
	public Long getItemsAmountByHostIP() {
		return coveragePercentService.getItemsAmountByHostIP();
	}
	
	
	/**
	 * 计算当前HOST监控已使用指标数并入库
	 * @MethodName: calAndSaveItemsAmountByHostIP
	 * @return
	 * @return String
	 * @throws
	 */
	@ApiOperation(tags={"监控覆盖度"}, value="计算HOST指标数并入库", notes="")
	@GetMapping(value = "/coverage/cal-save-items-amount-host-ip")
	public String calAndSaveItemsAmountByHostIP() {
		boolean res = coveragePercentService.calAndSaveItemsAmountByHostIP();
		return String.valueOf(res);
	}
	
	/**
	 * 计算当前RDB监控已使用指标数并入库
	 * @MethodName: calAndSaveItemsAmountByRDBIP
	 * @return
	 * @return String
	 * @throws
	 */
	@ApiOperation(tags={"监控覆盖度"}, value="计算RDB指标数并入库", notes="")
	@GetMapping(value = "/coverage/cal-save-items-amount-rdb-ip")
	public String calAndSaveItemsAmountByRDBIP() {
		boolean res = coveragePercentService.calAndSaveItemsAmountByRDBIP();
		return String.valueOf(res);
	}
	
	/**
	 * 当前监控已使用指标数byIP
	 * @MethodName: getItemsAmountByIP
	 * @return
	 * @return String
	 * @throws
	 */
	@ApiOperation(tags={"监控覆盖度"}, value="当前监控已使用指标数byIP", notes="")
	@PostMapping(value = "/coverage/get-ip-items-amount")
	public Long getItemsAmountByIP(@RequestParam(required =false) String[] ips) {
		return coveragePercentService.getItemsAmountByIP(ips);
	}
	
	/**
	 * 计算IP对应指标数并入库
	 * @MethodName: SaveItemsAmountByIP
	 * @return
	 * @return String
	 * @throws
	 */
	@ApiOperation(tags={"监控覆盖度"}, value="计算IP对应指标数并入库", notes="")
	@GetMapping(value = "/coverage/save-ip-items-amount")
	public String SaveItemsAmountByIP() {
		boolean res = coveragePercentService.SaveItemsAmountByIP();
		return String.valueOf(res);
	}
	
	/**
	 * 接口取数据带时间区间参数
	 * @MethodName: getCoveragePercentByTime
	 * @param paramStr
	 * @return
	 * @return String
	 * @throws
	 */
	@ApiOperation(tags={"监控覆盖度"},value="覆盖度带时间区间参数",
				  notes="{\"type\":\"host\",\"start_time\":\"2018-05-14 16:00:00\",\"end_time\":\"2018-05-18 16:00:00\"}")
	@PostMapping(value = "/coverage/get-coverage-percent-by-time")
	public String getCoveragePercentByTime(@RequestBody String paramStr) {
		return coveragePercentService.getCoveragePercentByTime(paramStr);
	}
	/**
	 * 计算Instance监控覆盖度并入库
	 * @MethodName: calAndSaveInstanceCoveragePercent
	 * @return
	 * @return String
	 * @throws
	 */
	@ApiOperation(tags={"监控覆盖度"}, value="计算Instance的覆盖度", notes="")
	@GetMapping(value = "/coverage/cal-and-save-instance-coverage-percent")
	public String calAndSaveInstanceCoveragePercent() {
		boolean res = coveragePercentService.calAndSaveInstanceCoveragePercent();
		return String.valueOf(res);
	}	
	/**
	 * 计算unit监控覆盖度并入库
	 * @MethodName: calAndSaveInstanceCoveragePercent
	 * @return
	 * @return String
	 * @throws
	 */
	@ApiOperation(tags={"监控覆盖度"}, value="计算unit的覆盖度", notes="")
	@GetMapping(value = "/coverage/cal-and-save-unit-coverage-percent")
	public String calAndSaveUnitCoveragePercent() {
		boolean res = coveragePercentService.calAndSaveUnitCoveragePercent();
		return String.valueOf(res);
	}	
}
