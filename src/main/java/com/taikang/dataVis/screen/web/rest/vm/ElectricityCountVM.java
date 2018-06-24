package com.taikang.dataVis.screen.web.rest.vm;
/**
 * Created by itw_yuhao on 2018/5/25.
 */

import com.taikang.dataVis.domain.ElectricityCount;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.sf.json.JSONArray;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author [作者]（必须，使用汉语）
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@ApiModel(description = "机房电力的返回类型VM")
public class ElectricityCountVM extends ElectricityCount {

    @ApiModelProperty(value = "值的JSON对象，例如：[{\"type\":\"空调用电\",\"value\":\"1000\"},{\"type\":\"IT用电\",\"value\":\"1000\"},{\"type\":\"其他\",\"value\":\"1000\"}]")
    private JSONArray valueArray;

    public JSONArray getValueArray() {
        return valueArray;
    }

    public void setValueArray(JSONArray valueArray) {
        this.valueArray = valueArray;
    }
}
