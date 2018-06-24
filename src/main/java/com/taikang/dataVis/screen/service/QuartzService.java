package com.taikang.dataVis.screen.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * (定时任务方法类)
 * 项目名称:	[cmdb-core]
 * 包:		[com.taikang.dataVis.screen.service]
 * 类名称:	[QuartzService]
 * 创建人:	[itw_liangbo01]
 * 创建时间:	[2018年5月9日 上午11:04:13]
 * 修改人:	[itw_liangbo01]
 * 修改时间:	[2018年5月9日 上午11:04:13]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */
@Component
public class QuartzService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AlarmCoverageService alarmCoverageService;
	@Autowired
	private CmdbRDBService cmdbRDBService;
	@Autowired
	private CoveragePercentService coveragePercentService;

	/**
	 * (每天凌晨2点30同步监控覆盖度数据)
	 * @MethodName: alarmCoverage
	 * @return void
	 * @throws
	 */
	@Scheduled(cron = "0 30 2 * * *")
	public void alarmCoverage() {
		String ip;
		try {
			// 获取本地IP值
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ip = "";
		}
		// 虚拟主机的覆盖度采集
		alarmCoverageService.syncInstanceAlarmCoverage(null, ip);
		// 数据库覆盖度的采集
		alarmCoverageService.syncRdbAlarmCoverage(null, ip);
	}
	
	/**
	 * (每天凌晨2点15同步数据库详情数据同步)
	 * @MethodName: syncrdbDetails
	 * @return void
	 * @throws
	 */
	@Scheduled(cron = "0 15 2 * * *")
	public void syncrdbDetails() {
		String ip;
		try {
			// 获取本地IP值
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ip = "";
		}
		// 虚拟主机的覆盖度采集
		cmdbRDBService.syncrdbDetails(ip);
	}
	
	/**
	 * 为保证计算的是最新数据，此定时任务应在同步数据库详情数据后
	 * (每天凌晨4点15计算当前RDB监控已使用指标数并入库)
	 * @MethodName: calAndSaveItemsAmountByRDBIP
	 * @return void
	 * @throws
	 */
	@Scheduled(cron = "0 15 4 * * *")
	public void calAndSaveItemsAmountByRDBIP() {
		// 虚拟主机的覆盖度采集
		coveragePercentService.calAndSaveItemsAmountByRDBIP();
	}

	/**
	 * (每周周一 1:00:00 执行一次)
	 * @MethodName: weekOneTime
	 * @return void
	 * @throws
	 */
	@Scheduled(cron = "0 0 1 ? * MON")
	public void weekOneTime() {
		System.out.println("now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}

	/**
	 * (每月一号 1:00:00 执行一次)
	 * @MethodName: monthOneTime
	 * @return void
	 * @throws
	 */
	@Scheduled(cron = "0 0 1  1 * ?")
	public void monthOneTime() {
		System.out.println("now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}

	/**
	 * (每个季度的第一个月的一号的1:00:00 执行一次)
	 * @MethodName: thOneTime
	 * @return void
	 * @throws
	 */
	@Scheduled(cron = "0 0 1  1 1,4,7,10 ?")
	public void thOneTime() {
		System.out.println("now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}

	/**
	 * (一月和六月的一号的1:00:00 执行一次)
	 * @MethodName: twoMonthOneTime
	 * @return void
	 * @throws
	 */
	@Scheduled(cron = "0 0 1  1 1,6 ?")
	public void twoMonthOneTime() {
		System.out.println("now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}

	/**
	 * (每年一月的一号的1:00:00 执行一次)
	 * @MethodName: yearOneTime
	 * @return void
	 * @throws
	 */
	@Scheduled(cron = "0 0 1 1 1 ?")
	public void yearOneTime() {
		System.out.println("now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}
	//每天凌晨1点10分同步cmdb的host数据
	@Scheduled(cron = "0 10 1 * * *")
	public void syncHost() {
		try {
			System.out.println("syncHost同步cmdb的host数据，now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			//删除表数据
			coveragePercentService.deleteCoverageHostList();
			//保存表数据
			coveragePercentService.saveCoverageHostList();
		} catch (Exception e) {
			logger.error("Exception:" + e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 为保证计算的是最新数据，此定时任务应在同步cmdb的host数据后
	 * (每天凌晨4点30计算当前RDB监控已使用指标数并入库)
	 * @MethodName: calAndSaveItemsAmountByHostIP
	 * @return void
	 * @throws
	 */
	@Scheduled(cron = "0 30 4 * * *")
	public void calAndSaveItemsAmountByHostIP() {
		// 虚拟主机的覆盖度采集
		coveragePercentService.calAndSaveItemsAmountByHostIP();
	}
	//每天凌晨1点50分计算监控覆盖度百分比并入库
	@Scheduled(cron = "0 50 1 * * *")
	public void calHostCoveragePercent() {
		try {
			System.out.println("calHostCoveragePercent计算监控覆盖度并入库，now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			//算出percent并存入数据库
			coveragePercentService.calAndSaveHostCoveragePercent();
		} catch (Exception e) {
			logger.error("Exception:" + e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 每天凌晨2点50分计算IP对应指标数并入库
	 * @MethodName: SaveItemsAmountByIP
	 * @return void
	 * @throws
	 */
	@Scheduled(cron = "0 50 2 * * *")
	public void SaveItemsAmountByIP() {
		try {
			System.out.println("SaveItemsAmountByIP计算IP对应指标数并入库，now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			//算出percent并存入数据库
			coveragePercentService.SaveItemsAmountByIP();
		} catch (Exception e) {
			logger.error("Exception:" + e);
			e.printStackTrace();
		}
	}
	//每天凌晨1点30分计算监控覆盖度百分比并入库
	@Scheduled(cron = "0 30 1 * * *")
	public void calInstanceCoveragePercent() {
		try {
			System.out.println("calInstanceCoveragePercent计算监控覆盖度并入库，now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			//算出percent并存入数据库
			coveragePercentService.calAndSaveInstanceCoveragePercent();
		} catch (Exception e) {
			logger.error("Exception:" + e);
			e.printStackTrace();
		}
	}
	//每天凌晨1点40分计算监控覆盖度百分比并入库
	@Scheduled(cron = "0 40 1 * * *")
	public void calUnitCoveragePercent() {
		try {
			System.out.println("calUnitCoveragePercent计算监控覆盖度并入库，now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			//算出percent并存入数据库
			coveragePercentService.calAndSaveUnitCoveragePercent();
		} catch (Exception e) {
			logger.error("Exception:" + e);
			e.printStackTrace();
		}
	}
}
