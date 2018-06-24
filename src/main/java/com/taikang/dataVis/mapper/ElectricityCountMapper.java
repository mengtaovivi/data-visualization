package com.taikang.dataVis.mapper;
/**
 * Created by itw_yuhao on 2018/5/8.
 */

import com.taikang.dataVis.domain.ElectricityCount;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author [作者]（必须，使用汉语）
 */
@Mapper
public interface ElectricityCountMapper {

    Integer deleteBatch(List<Integer> ids);

    Integer save(ElectricityCount electricityCount);

    Integer update(ElectricityCount electricityCount);

    List<ElectricityCount> getElectricityCountsByYear(String year);

    List<ElectricityCount> getElectricityMonthByYear(String year);

    /**
     * 获取去重所有的时间【格式：yyyy-MM】
     * @return
     */
    List<String> getYearAndMonth();

    /**
     * 根据年月获取电力信息，准备修改
     * @param params {"year":2018,"month":"05"}
     * @return
     */
    List<ElectricityCount> getElectricityCountByTime(Map params);
}
