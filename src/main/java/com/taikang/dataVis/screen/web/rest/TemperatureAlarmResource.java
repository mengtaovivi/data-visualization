package com.taikang.dataVis.screen.web.rest;


import com.github.pagehelper.PageInfo;
import com.taikang.dataVis.core.base.BaseResource;
import com.taikang.dataVis.domain.TemperatureAlarm;
import com.taikang.dataVis.screen.service.TemperatureAlarmService;
import com.taikang.dataVis.screen.web.rest.vm.TemperatureAlarmVM;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 *
 * @author 孟涛
 * @description 温度湿度相关接口信息
 * @date 2018/5/18 17:07
 * @return
 */
@RestController
@RequestMapping("/api")
public class TemperatureAlarmResource extends BaseResource {
	
	@Autowired
	private TemperatureAlarmService temperatureAlarmService ;


	/**
	 *  
	 * 
	 * @author 孟涛
	 * @description
	 * @date 2018/5/21 9:40
	 * @return com.github.pagehelper.PageInfo<com.taikang.dataVis.screen.web.rest.vm.TemperatureAlarmVM>
	 */
	@ApiOperation(value="获取温湿度告警监控列表信息",tags={"温湿度告警监控"},notes="{\"computerRoomName\":\"\"}")
	@GetMapping(value = "/temperature-alarm")
	public PageInfo<TemperatureAlarmVM> findAll(HttpServletRequest request,@RequestParam String json) {
		this.putParamPage(request);
		List<TemperatureAlarmVM> list = temperatureAlarmService.findAll(json) ;
		//将list封装到pageinfo中返回给前台
		PageInfo<TemperatureAlarmVM> pages = new PageInfo<TemperatureAlarmVM>(list);
		return  pages;
	}

	/**
	 * 
	 * @author 郭德才
	 * @description
	 * @date 2018/5/21 14:40
	 * @return JSONArray
	 */
	@ApiOperation(value="获取温湿度告警监控大屏显示信息",tags = "大屏接口",notes = "无参数")
	@GetMapping(value = "/temperature-alarm-all")
	public JSONObject findTemperatureAlarmAll(HttpServletRequest request) {
		this.putParamPage(request);
		return  temperatureAlarmService.findTemperatureAlarmAll();
	}
	
	 /**
     * 新增温湿度告警监控信息
     *
     * @param TemperatureAlarm
     * @return 返回新增温湿度告警监控影响行数
     */
    @PostMapping("/temperature-alarm")
    public Integer createElectricityCount(@RequestBody TemperatureAlarm temperatureAlarm) {
        return temperatureAlarmService.save(temperatureAlarm);
    }

    /**
     * 修改温湿度告警监控信息
     *
     * @param TemperatureAlarm
     * @return 返回温湿度告警监控影响行数
     */
    @PutMapping("/temperature-alarm")
    public Integer updateElectricityCount(@RequestBody TemperatureAlarm temperatureAlarm) {
        return temperatureAlarmService.update(temperatureAlarm);
    }

    /**
     * 批量温湿度告警监控信息
     *
     * @param id
     * @return 删除的行数
     */
    @DeleteMapping("/temperature-alarm/{id}") 
    public Integer deleteElectricityCount(@PathVariable List<Integer> id) {
        return temperatureAlarmService.deleteBatch(id);
    }
	
}
