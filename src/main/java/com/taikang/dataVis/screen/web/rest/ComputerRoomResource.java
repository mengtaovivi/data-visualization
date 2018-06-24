package com.taikang.dataVis.screen.web.rest;


import com.github.pagehelper.PageInfo;
import com.taikang.dataVis.core.base.BaseResource;
import com.taikang.dataVis.screen.service.ComputerRoomService;
import com.taikang.dataVis.screen.web.rest.vm.ComputerRoomVM;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import net.sf.json.JSONArray;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
 * @description
 *
 * @date 2018/5/8 13:53
 * @return
 */
@RestController
@RequestMapping("/api")
public class ComputerRoomResource extends BaseResource {

	@Autowired
	private ComputerRoomService computerRoomService ;

	/**
	 *
	 *
	 * @author 孟涛
	 * @description 查询最近一次9个机房的各种状态信息
	 *   参数：  {"computerRoomName":""}  默认是空字符串  当查询的时候再传相应的值信息
	 * @date 2018/5/8 14:06
	 * @return com.github.pagehelper.PageInfo<ComputerRoomVM>
	 */
	@ApiOperation(value="获取最近时间机房信息",tags={"机房相关接口信息"},notes="{\"computerRoomName\":\"\"}")
	@GetMapping(value = "/computer-room-info")
	public PageInfo<ComputerRoomVM> findAll(HttpServletRequest request,@RequestParam String json) {
		this.putParamPage(request);
		List<ComputerRoomVM> list = computerRoomService.findCurrentAll(json) ;
		//将list封装到pageinfo中返回给前台
		PageInfo<ComputerRoomVM> pages = new PageInfo<ComputerRoomVM>(list);
		return  pages;
	}
	/**
	 *  
	 * 
	 * @author 孟涛
	 * @description 获取机房的所有历史信息
	 * @date 2018/5/8 14:29
	 * @return java.util.List<ComputerRoomVM>
	 */
	@ApiOperation(value="获取机房的所有历史信息",tags={"机房相关接口信息"},notes="{\"computerRoomName\":\"\"}")
	@GetMapping(value = "/get-all-computer-room")
	public PageInfo<ComputerRoomVM> findAllInfo( HttpServletRequest request,@RequestParam String json ){
		this.putParamPage(request);
		List<ComputerRoomVM> list = computerRoomService.findAllInfo(json) ;
		//将list封装到pageinfo中返回给前台
		PageInfo<ComputerRoomVM> pages = new PageInfo<ComputerRoomVM>(list);
		return  pages;
	}

	/**
	 *  
	 * 
	 * @author 孟涛
	 * @description 获取所有日期
	 * @date 2018/5/8 17:29
	 * @return java.util.List<ComputerRoom>
	 */
	@ApiOperation(value="获取所有日期",tags={"机房相关接口信息"},notes="无参数")
	@GetMapping(value = "/find-all-date")
	public List<String> findAllDate() {
		return computerRoomService.findAllDate();
	}

	/**
	 *
	 *
	 * @author 孟涛
	 * @description 单机创建自动创建9个机房信息，并返回相关数据
	 * @date 2018/5/8 15:38
	 * @return java.util.List<ComputerRoom>
	 */
	@ApiOperation(value="单机创建返回相关数据",tags={"机房相关接口信息"},notes="无参数信息")
	@GetMapping(value = "/computer-room")
	public JSONArray returnVal() {
		return computerRoomService.returnVal();
	}

	@ApiOperation(value="批量保存多个机房值信息，并返回保存的条数",tags={"机房相关接口信息"},notes="参数：需要保存的json串信息")
	@PostMapping(value = "/save-computer-room")
	public int saveBatch(@RequestBody String json){
		return computerRoomService.saveBatch(json) ;
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
	@ApiOperation(value="单条更新机房记录信息",tags={"机房相关接口信息"},notes="参数为computerRoomVM类 {\n"
			+ "  \"computerRoom\": \"22\",\n"
			+ "  \"computerRoomName\": \"33\",\n"
			+ "  \"frame\": \"11\",\n"
			+ "  \"id\": \"2018050817094502\",\n"
			+ "  \"power\": \"33\",\n"
			+ "  \"updateTime\": \"2018-05-08 17:28:45\"\n"
			+ "}")
	@PutMapping(value = "/computer-room")
	public int update(@RequestBody ComputerRoomVM computerRoomVM) {
		return computerRoomService.update(computerRoomVM);
	}

	@ApiOperation(value="拷贝某天的值",tags={"机房相关接口信息"},notes="参数：{\"createTime\":\"2018-05-08 15:54:31\"}")
	@PutMapping(value = "/copy-computer-room")
	public int copy(@RequestBody String json) {
		return computerRoomService.copy(json);
	}

	@ApiOperation(value = "查询九大机房仪表盘（大屏接口）",tags = "大屏接口",notes = "无参数")
	@GetMapping(value = "/computer-room-screen")
	public JSONArray findAllComputerRoom(){
		return computerRoomService.findAllComputerRoom() ;
	}

}
