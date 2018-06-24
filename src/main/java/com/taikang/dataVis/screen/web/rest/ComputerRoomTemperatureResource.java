package com.taikang.dataVis.screen.web.rest;


import com.taikang.dataVis.core.base.BaseResource;
import com.taikang.dataVis.screen.service.ComputerRoomTemperatureService;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 *
 * @author 孟涛
 * @description 机房温度相关接口
 * @date 2018/5/10 10:52
 * @return
 */
@RestController
@RequestMapping("/api")
public class ComputerRoomTemperatureResource extends BaseResource {

	@Autowired
	private ComputerRoomTemperatureService computerRoomTemperatureService ;

	/**
	 *
	 *
	 * @author 孟涛
	 * @description 获取所有日期--列表页面
	 * @date 2018/5/10 15:14
	 * @return java.util.List<java.lang.String>
	 */
	@ApiOperation(value="获取所有日期-列表页面",tags={"机房温度相关接口"},notes="无参数")
	@GetMapping(value = "/find-all-temperature-date")
	public List<String> findAllDate() {
		return computerRoomTemperatureService.findAllDate();
	}

	@ApiOperation(value="批量保存多个机房值信息，并返回保存的条数",tags={"机房温度相关接口"},notes="参数：需要保存的json串信息")
	@PostMapping(value = "/save-computer-temperature")
	public Map<String,String> saveBatch(@RequestBody String json){
		return computerRoomTemperatureService.saveBatch(json) ;
	}

	/**
	 *
	 *
	 * @author 孟涛
	 * @description 单条更新机房值信息  {
								"computerRoom": "22",
								"computerRoomName": "33",
								"frame": "11",
								"id": "2018050817094502",
								"power": "33",
								"updateTime": "2018-05-08 17:28:45"
								}
	 * @date 2018/5/8 16:30
	 * @return int
	 */
	@ApiOperation(value="单机创建时把对应的回显信息查询出来",tags={"机房温度相关接口"},notes="参数：{\"createTime\":\"2018-05-08 15:54:31\"}")
	@GetMapping(value = "/computer-temperature-return-val")
	public JSONArray returnVal(HttpServletRequest request,@RequestParam String json ) {
		return computerRoomTemperatureService.returnVal(json);
	}


	/**
	 *
	 *
	 * @author 孟涛
	 * @description 保存九个机房相关的信息
	 * @date 2018/5/15 14:26
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 */
	@ApiOperation(value="批量更新九个机房的相关信息",tags={"机房温度相关接口"},notes="参数：规定的json数组信息")
	@PostMapping(value = "/computer-temperature-update")
	public Map<String,String> update(@RequestBody String json ) {
		return computerRoomTemperatureService.update(json);
	}

	@ApiOperation(value="拷贝某天的值",tags={"机房温度相关接口"},notes="参数：{\"createTime\":\"2018-05-08 15:54:31\"}")
	@PostMapping(value = "/copy-computer-temperature")
	public Map<String,String> copy(@RequestBody String json) {
		Map<String,String> map = new HashMap<String,String>() ;
		int num = 0 ;
		try {
			num = computerRoomTemperatureService.copy(json) ;
			map.put("num",String.valueOf(num)) ;
			map.put("message","拷贝成功！") ;
		}catch (Exception e){
			e.printStackTrace();
			map.put("num",String.valueOf(num)) ;
			map.put("message","拷贝失败！") ;
		}
		return map;
	}

	@ApiOperation(value="根据日期删除相关信息",tags={"机房温度相关接口"},notes="参数：{\"createTime\":\"2018-05-08 15:54:31\"}")
	@PostMapping(value = "/delete-computer-temperature")
	public Map<String,String> delete(@RequestBody String json) {
		Map<String,String> map = new HashMap<String,String>() ;
		int num = 0 ;
		try {
			num = computerRoomTemperatureService.delete(json) ;
			map.put("num",String.valueOf(num)) ;
			map.put("message","删除成功！") ;
		}catch (Exception e){
			e.printStackTrace();
			map.put("num",String.valueOf(num)) ;
			map.put("message","删除失败！") ;
		}
		return map;
	}

	/**
	 *
	 *
	 * @author 孟涛
	 * @description 查询九大机房送风和回风信息（大屏项目）
	 * @date 2018/5/17 17:44
	 * @return net.sf.json.JSONArray
	 */
	@ApiOperation(value = "查询九大机房制冷与空调信息（大屏接口）",tags = "大屏接口",notes = "无参数")
	@GetMapping(value = "/computer-room-temp")
	public JSONArray findAllTemp(){
		return computerRoomTemperatureService.findAllTemp() ;
	}

	
	/**
	 *
	 *
	 * @author 郭德才
	 * @description 新查询九大机房送风和回风等信息（大屏项目）
	 * @date 2018/5/17 17:44
	 * @return net.sf.json.JSONArray
	 */
	@ApiOperation(value = "新查询九大机房送风和回风等信息（大屏接口）",tags = "大屏接口",notes = "无参数")
	@GetMapping(value = "/computer-room-temperature")
	public JSONArray findAllTemp1(){
		return computerRoomTemperatureService.findAllTemp1() ;
	}
}
