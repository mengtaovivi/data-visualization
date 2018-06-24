package com.taikang.dataVis.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 大屏导航实体类
 * 项目名称:	[data-visualization]
 * 包:		[com.taikang.dataVis.domain]
 * 类名称:	[ScreenNavigation]
 * 创建人:	[itw_liangbo01]
 * 创建时间:	[2018年5月25日 上午10:00:34]
 * 修改人:	[itw_liangbo01]
 * 修改时间:	[2018年5月25日 上午10:00:34]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */
@ApiModel(description= "返回响应数据")
public class ScreenNavigation {
	@ApiModelProperty(value = "主键id")
	private Integer id;
	@ApiModelProperty(value = "标题")
	private String title;
	@ApiModelProperty(value = "图片地址")
	private String url;
	@ApiModelProperty(value = "图片信息")
	private String image;
	@ApiModelProperty(value = "操作者")
	private String operator;
	@ApiModelProperty(value = "操作时间")
	private String operate_time;
	@ApiModelProperty(value = "备注")
	private String remark;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOperate_time() {
		return operate_time;
	}
	public void setOperate_time(String operate_time) {
		this.operate_time = operate_time;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
