package com.taikang.dataVis.screen.web.rest;

import com.github.pagehelper.PageInfo;
import com.taikang.dataVis.core.base.BaseResource;
import com.taikang.dataVis.screen.service.ElectricityCountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 电力统计的Resource
 *
 * @author 余灏
 */
@RestController
@RequestMapping("/api")
@Api(value = "ElectricityCountResource", description = "提供九大机房电力操作的相关接口")
public class ElectricityCountResource extends BaseResource {

    @Autowired
    private ElectricityCountService electricityCountService;

    /**
     * 获取已保存电力信息的月份
     *
     * @return
     */
    @ApiOperation(value = "/electricity-counts", notes = "获取已保存电力信息的月份")
    @GetMapping("/electricity-counts")
    public PageInfo<String> getList() {
        List<String> list = electricityCountService.findAll();
        PageInfo<String> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     * 根据年获取本年度和上年度所有的电力数据
     *
     * @param year
     * @return
     */
    @ApiOperation(value = "/electricity-counts/{year}", notes = "根据年获取本年度和上年度所有的电力数据")
    @GetMapping("/electricity-counts/{year}")
    public Map<String, Object> getElectricityCountsByYear(@PathVariable @ApiParam(value = "年度的值，如2018") Integer year) {
        return electricityCountService.getElectricityCountsByYear(year);
    }

    /**
     * 进入新增页面获取数据结构
     *
     * @return
     */
    @ApiOperation(value = "/electricity-count/insert-data", notes = "进入新增页面获取数据结构")
    @GetMapping("/electricity-count/insert-data")
    public JSONObject insertData() {
        return electricityCountService.insertData();
    }

    /**
     * 新增电力统计信息
     *
     * @param params
     * @return 返回新增的电力统计信息影响行数
     */
    @ApiOperation(value = "/electricity-count", notes = "新增电力统计信息")
    @PostMapping("/electricity-count")
    public Integer createElectricityCount(@RequestBody @ApiParam(value = "格式: /electricity-count/insert-data接口的数据格式") String params) {
        return electricityCountService.save(params);
    }

    /**
     * 修改电力统计信息
     *
     * @param params
     * @return 返回电力统计信息影响行数
     */
    @ApiOperation(value = "/electricity-count", notes = "修改电力统计信息")
    @PutMapping("/electricity-count")
    public Integer updateElectricityCount(@RequestBody  @ApiParam(value = "格式: /electricity-count/update-data/{time}接口的数据格式") String params) {
        return electricityCountService.update(params);
    }

    /**
     * 进入修改页面获取数据结构
     *
     * @return
     */
    @ApiOperation(value = "/electricity-count/update-data/{time}", notes = "进入修改页面获取数据结构")
    @GetMapping("/electricity-count/update-data/{time}")
    public List<Object> updateData(@PathVariable @ApiParam(value = "格式: 2018-05") String time) {
        return electricityCountService.updateData(time);
    }

    @ApiOperation(value = "查询九大机房电力情况（大屏接口）", tags = "大屏接口", notes = "无参数")
    @GetMapping(value = "/find-all-electricity")
    public JSONArray findAllElectricity() {
        return electricityCountService.findAllElectricity();
    }

}
