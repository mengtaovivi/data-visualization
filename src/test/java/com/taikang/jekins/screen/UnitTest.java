package com.taikang.jekins.screen;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.taikang.dataVis.DataVISApplication;
import com.taikang.dataVis.screen.service.AlarmCoverageService;
import com.taikang.dataVis.screen.service.CoveragePercentService;
import com.taikang.dataVis.screen.service.TemperatureAlarmService;

import net.sf.json.JSONObject;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DataVISApplication.class)// 指定spring-boot的启动类
public class UnitTest {
	@Autowired
	private AlarmCoverageService alarmCoverageService;
	
	@Autowired
	private CoveragePercentService coveragePercentService;
//	@Test
	public void test() {
//		alarmCoverageService.syncrdbDetails("1111");
	}
	
//	@Test
	public void calAndSaveRDBCoveragePercent() {
		coveragePercentService.calAndSaveRDBCoveragePercent();
	}
	
	@Autowired
	private TemperatureAlarmService temperatureAlarm ;

	@Test
	public void temperatureAlarmfindAll() {
		JSONObject objec=temperatureAlarm.findTemperatureAlarmAll();
		System.err.println(objec);
	}
}
