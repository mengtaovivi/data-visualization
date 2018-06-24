package com.taikang.dataVis.screen.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.taikang.dataVis.core.config.JobEnv;
import com.taikang.dataVis.core.utils.CMDBUtil;
import com.taikang.dataVis.core.utils.ClientUtil;
import com.taikang.dataVis.core.utils.DateUtil;
import com.taikang.dataVis.core.utils.HttpClientUtil;
import com.taikang.dataVis.domain.RDBModel;
import com.taikang.dataVis.domain.TkOperationInformation;
import com.taikang.dataVis.mapper.OperationInformationMapper;
import com.taikang.dataVis.mapper.RDBMapper;
import com.taikang.dataVis.screen.service.CmdbRDBService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 项目名称:	[data-visualization]
 * 包:		[com.taikang.dataVis.screen.service.impl]
 * 类名称:	[CmdbRDBServiceImpl]
 * 创建人:	[itw_guodc]
 * 创建时间:	[2018年6月4日 下午4:42:11]
 * 修改人:	[itw_guodc]
 * 修改时间:	[2018年6月4日 下午4:42:11]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */ 
@Service
public class CmdbRDBServiceImpl implements CmdbRDBService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private RDBMapper rdbMapper;
	@Autowired
	private JobEnv env;
	@Autowired
	private OperationInformationMapper tkOperationInformationMapper ;
	
	@Override
	@Transactional
	public boolean syncrdbDetails(String ip) {
		// TODO Auto-generated method stub
		boolean flag = false; 
		// 生成时间
        String date = DateUtil.getCurrentZonedDateTime();
		TkOperationInformation tkOperationInformation = new TkOperationInformation();
		String operationId=UUID.randomUUID().toString().replaceAll("-","");
		tkOperationInformation.setId(operationId);
		tkOperationInformation.setOperationIp(ip);
		tkOperationInformation.setOperationType("syncRdbDetails");
		tkOperationInformation.setOperationDate(date);
		//保存采集记录（IP和时间）
		tkOperationInformationMapper.save(tkOperationInformation);
		//调用cmdb接口取到所有的ip列表
		String posturl = env.getCmdb_url() + "/object/RDB/instance/_search";
		String str = "{\"query\":{},\"page\":1,\"page_size\":2}";
		JSONObject jsonParam = JSONObject.fromObject(str);
		Map<String, String> headerMap = CMDBUtil.getCmdbHeaderMap();
		try {
			logger.info("syncrdbDetails调用cmdb接口入参：" + jsonParam);
			String res = HttpClientUtil.httpPostWithJSON(posturl, jsonParam, headerMap);
			if (ClientUtil.isJson(res)) {
				JSONObject obj = JSONObject.fromObject(res);
				int code = obj.getInt("code");
				if(code==0) {//code为0说明成功
					JSONObject dataObj = obj.getJSONObject("data");
					int total = dataObj.containsKey("total")?dataObj.getInt("total"):0;
					if(total != 0) {
						//删除旧数据，为更新做准备
						rdbMapper.deleteAll();
						Map<Integer,Integer> map = ClientUtil.getPage(total,200);
						if(!map.isEmpty()) {
							//循环页数并且调用接口
							for (Entry<Integer,Integer> entry : map.entrySet()) {
								List<RDBModel> list = new ArrayList<RDBModel>();
								str = "{\"query\":{},\"page\":"+(entry.getKey())+",\"page_size\":"+entry.getValue()+"}";
								jsonParam = JSONObject.fromObject(str);
								logger.info("syncrdbDetails调用cmdb接口入参：" + jsonParam);
								res = HttpClientUtil.httpPostWithJSON(posturl, jsonParam, headerMap);
								if (ClientUtil.isJson(res)) {
									obj = JSONObject.fromObject(res);
									code = obj.getInt("code");
									if(code==0) {//code为0说明成功
										JSONObject tempObj = obj.getJSONObject("data");
										JSONArray tempArray = tempObj.getJSONArray("list");
										for(int i=0;i<tempArray.size();i++) {
											JSONObject object = tempArray.getJSONObject(i);
											RDBModel rdbModel = new RDBModel();
											rdbModel.setId(object.getString("instanceId"));
											rdbModel.setRdbId(object.containsKey("rdb_id")?object.getString("rdb_id"):"");
											rdbModel.setName(object.containsKey("name")?object.getString("name"):"");
											rdbModel.setRdbName(object.containsKey("rdb_name")?object.getString("rdb_name"):"");
											rdbModel.setStatus(object.containsKey("status")?object.getString("status"):"");
											rdbModel.setMasterIp(object.containsKey("master_ip")?object.getString("master_ip"):"");
											rdbModel.setRvip(object.containsKey("rvip")?object.getString("rvip"):"");
											rdbModel.setWvip(object.containsKey("wvip")?object.getString("wvip"):"");
											rdbModel.setCloudType(object.containsKey("cloud_type")?object.getString("cloud_type"):"");
											rdbModel.setRdbEngine(object.containsKey("rdb_engine")?object.getString("rdb_engine"):"");
											JSONArray poolArray = object.containsKey("RESOURCEPOOL")?object.getJSONArray("RESOURCEPOOL"):new JSONArray();
											String zone_name = "";
											if(!poolArray.isEmpty() && poolArray.size()>=1) {
//												System.err.println("---    "+poolArray);
												String temStr = poolArray.get(0).toString();
												if(!"[]".equals(temStr)) {
													JSONObject temp = poolArray.getJSONObject(0);
													zone_name = temp.containsKey("zone_name")?temp.getString("zone_name"):"";
												}
											}
											rdbModel.setZoneName(zone_name);
											rdbModel.setRefreshTime(date);
											list.add(rdbModel);
										}
//										批量保存rdbModel
										rdbMapper.saveBatch(list);
									}
								}
							}
							flag = true;//如果保存完还不报错则所有都是成功保存
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("Exception:" + e);
			e.printStackTrace();
		}
		return flag;
	}
}
