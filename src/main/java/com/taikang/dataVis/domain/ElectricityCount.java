package com.taikang.dataVis.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author 余灏
 */
@ApiModel(description= "机房电力的返回类型")
public class ElectricityCount implements Serializable {

    @ApiModelProperty(value = "主机ID")
    private Integer id;
    @ApiModelProperty(value = "月")
    private String year;
    @ApiModelProperty(value = "月份")
    private String month;
    @ApiModelProperty(value = "电力的类型,类型：IT用电、空调用电、其他")
    private String type;
    @ApiModelProperty(value = "电力值")
    private String value;
    @ApiModelProperty(value = "创建日期")
    private String createdDate;
    @ApiModelProperty(value = "机房名称")
    private String computerRoomName ;

    public String getComputerRoomName() {
        return computerRoomName;
    }

    public void setComputerRoomName(String computerRoomName) {
        this.computerRoomName = computerRoomName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
