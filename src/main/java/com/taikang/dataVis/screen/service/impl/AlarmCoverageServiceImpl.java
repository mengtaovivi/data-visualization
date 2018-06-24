package com.taikang.dataVis.screen.service.impl;

import com.taikang.dataVis.core.config.JobEnv;
import com.taikang.dataVis.core.utils.CMDBUtil;
import com.taikang.dataVis.core.utils.ClientUtil;
import com.taikang.dataVis.core.utils.DateUtil;
import com.taikang.dataVis.core.utils.JDBCUtil;
import com.taikang.dataVis.domain.TkOperationInformation;
import com.taikang.dataVis.screen.service.AlarmCoverageService;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import com.taikang.dataVis.core.utils.HttpClientUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taikang.dataVis.domain.RDBModel;
import com.taikang.dataVis.domain.TkAlarmCoverage;
import com.taikang.dataVis.mapper.AlarmCoverageMapper;
import com.taikang.dataVis.mapper.HostMapper;
import com.taikang.dataVis.mapper.OperationInformationMapper;
import com.taikang.dataVis.mapper.RDBMapper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/** 
* @ClassName: AlarmCoverageServiceImpl 
* @Description: (AlarmCoverageService 接口实现类) 
* @author 郭德才 
* @date 2018年4月11日 下午3:27:12 
*  
*/
@Service
public class AlarmCoverageServiceImpl implements AlarmCoverageService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private AlarmCoverageMapper alarmCoverageMapper ;
	@Autowired
	private JobEnv env;
	@Autowired
	private OperationInformationMapper tkOperationInformationMapper ;
	@Autowired
	private HostMapper hostMapper;
	@Autowired
	private RDBMapper rdbMapper ;
	@Autowired
	private JDBCUtil jdbcUtil;
	
	 public static final String OSSYSTEMS="windows,linux";
	 public static final String RDBENGINES="DB2,MySQL,Oracle,other";
	 public static final String ZONES="all,prod2,test,test2,tkwh";
	
	@Override
	@Transactional
	public boolean syncInstanceAlarmCoverage(String[] osSystems ,String ip) {
		// TODO Auto-generated method stub
		boolean flag=true;
		if (osSystems==null || osSystems.length==0) {
			String[] temp= OSSYSTEMS.split(",");
			osSystems=temp;
		}
		// 生成时间
        String date = DateUtil.getCurrentZonedDateTime();
		List<TkAlarmCoverage> listadd = new ArrayList<>();
		//保留历史记录，不做更新
//		List<TkAlarmCoverage> listupdate = new ArrayList<>();
//		List<String> operation_os=alarmCoverageMapper.getAlloperationOs();
		TkOperationInformation tkOperationInformation = new TkOperationInformation();
		String operationId=UUID.randomUUID().toString().replaceAll("-","");
		tkOperationInformation.setId(operationId);
		tkOperationInformation.setOperationIp(ip);
		tkOperationInformation.setOperationType("InstanceAlarmCoverage");
		tkOperationInformation.setOperationDate(date);
		tkOperationInformationMapper.save(tkOperationInformation);
		String[] zones=ZONES.split(",");
		for (int i = 0; i < osSystems.length; i++) {
			for (int j = 0; j < zones.length; j++) {
				String result=hostMapper.getInstanceAlarmCoverageresult(osSystems[i],zones[j]);
				String resultused=hostMapper.getInstanceAlarmCoveragereUsedsult(osSystems[i],zones[j]);
				if (result!=null && resultused!=null) {
					TkAlarmCoverage tkAlarmCoverage = new TkAlarmCoverage();
					tkAlarmCoverage.setOperationOs(osSystems[i]);
					tkAlarmCoverage.setOperationId(operationId);
					tkAlarmCoverage.setAllOperationOs(result);
					tkAlarmCoverage.setUsedOperationOs(resultused);
					tkAlarmCoverage.setZone(zones[j]);
					//保留历史记录，增加时间节点，只做增加处理
					tkAlarmCoverage.setCreateDate(date);
					listadd.add(tkAlarmCoverage);
					//数据更新，不做记录
//					if (operation_os.contains(osSystems[i]+zones[j])) {
//						listupdate.add(tkAlarmCoverage);
//					}else {
//						listadd.add(tkAlarmCoverage);
//					}
				}
			}
		}
		if (listadd.size()>0) {
			int saveresult=alarmCoverageMapper.insertBatch(listadd);
			if (saveresult<1) {
				flag=false;
			}
		}
//		if (listupdate.size()>0) {
//			int updateresult=alarmCoverageMapper.updateBatchByOsSystem(listupdate);
//			if (updateresult<1) {
//				flag=false;
//			}
//		}
		return flag;
	}

	
	@Override
	@Transactional
	public boolean syncRdbAlarmCoverage(String[] rdb_engines, String ip) {
		// TODO Auto-generated method stub
		boolean flag=true;
		if (rdb_engines==null || rdb_engines.length==0) {
			String[] temp= RDBENGINES.split(",");
			rdb_engines=temp;
		}
		// 生成时间
        String date = DateUtil.getCurrentZonedDateTime();
		List<TkAlarmCoverage> listadd = new ArrayList<>();
		//保留历史记录，不做更新
//		List<TkAlarmCoverage> listupdate = new ArrayList<>();
//		List<String> operation_os=alarmCoverageMapper.getAlloperationOs();
		TkOperationInformation tkOperationInformation = new TkOperationInformation();
		String operationId=UUID.randomUUID().toString().replaceAll("-","");
		tkOperationInformation.setId(operationId);
		tkOperationInformation.setOperationIp(ip);
		tkOperationInformation.setOperationType("RdbAlarmCoverage");
		tkOperationInformation.setOperationDate(date);
		tkOperationInformationMapper.save(tkOperationInformation);
		String[] zones=ZONES.split(",");
		for (int i = 0; i < rdb_engines.length; i++) {
			for (int j = 0; j < zones.length; j++) {
				String result=rdbMapper.getRdbAlarmCoverageresult(rdb_engines[i],zones[j]);
				String resultused=rdbMapper.getRdbAlarmCoveragereUsedsult(rdb_engines[i],zones[j]);
				if (result!=null && resultused!=null) {
					TkAlarmCoverage tkAlarmCoverage = new TkAlarmCoverage();
					tkAlarmCoverage.setOperationOs(rdb_engines[i]);
					tkAlarmCoverage.setOperationId(operationId);
					tkAlarmCoverage.setAllOperationOs(result);
					tkAlarmCoverage.setUsedOperationOs(resultused);
					tkAlarmCoverage.setZone(zones[j]);
					//保留历史记录，增加时间节点，只做增加处理
					tkAlarmCoverage.setCreateDate(date);
					listadd.add(tkAlarmCoverage);
//					if (operation_os.contains(rdb_engines[i]+zones[j])) {
//						listupdate.add(tkAlarmCoverage);
//					}else {
//						listadd.add(tkAlarmCoverage);
//					}
				}
			}
		}
		if (listadd.size()>0) {
			int saveresult=alarmCoverageMapper.insertBatch(listadd);
			if (saveresult<1) {
				flag=false;
			}
		}
//		if (listupdate.size()>0) {
//			int updateresult=alarmCoverageMapper.updateBatchByOsSystem(listupdate);
//			if (updateresult<1) {
//				flag=false;
//			}
//		}
		return flag;
	}

	/**
	 * 查询zabbix的监控数据
	 */
	public JSONArray getZabbixCoverage(String sql) {
		return jdbcUtil.getJSONArray(sql);
	}


	
}
