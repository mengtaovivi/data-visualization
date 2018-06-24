package com.taikang.dataVis.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * zabbix的配置信息
 * 项目名称:	[cmdb-core]
 * 包:		[com.taikang.dataVis.screen.config]
 * 类名称:	[JdbcParams]
 * 创建人:	[itw_liangbo01]
 * 创建时间:	[2018年4月28日 下午4:10:03]
 * 修改人:	[itw_liangbo01]
 * 修改时间:	[2018年4月28日 下午4:10:03]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */
@Component
public class JdbcParams {
	// zabbix数据库的url
	@Value("${jdbc_zabbix.url}")
	private String url;
	// zabbix数据库的用户名
	@Value("${jdbc_zabbix.username}")
	private String username;
	// zabbix数据库的密码
	@Value("${jdbc_zabbix.password}")
	private String password;
	// zabbix数据库的驱动名
	@Value("${jdbc_zabbix.driver_class_name}")
	private String driver_class_name;
	//天气预报数据抓取url
	@Value("${weather_url}")
	private String weather_url;
	
	//天气预报数据抓取城市id
	@Value("${city_ids}")
	private String city_ids;
	
	
	public String getCity_ids() {
		return city_ids;
	}

	public void setCity_ids(String city_ids) {
		this.city_ids = city_ids;
	}

	public String getWeather_url() {
		return weather_url;
	}

	public void setWeather_url(String weather_url) {
		this.weather_url = weather_url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriver_class_name() {
		return driver_class_name;
	}

	public void setDriver_class_name(String driver_class_name) {
		this.driver_class_name = driver_class_name;
	}

}
