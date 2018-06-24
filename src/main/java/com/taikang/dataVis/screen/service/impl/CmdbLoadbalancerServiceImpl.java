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

import com.taikang.dataVis.core.config.JobEnv;
import com.taikang.dataVis.core.utils.CMDBUtil;
import com.taikang.dataVis.core.utils.ClientUtil;
import com.taikang.dataVis.core.utils.DateUtil;
import com.taikang.dataVis.core.utils.HttpClientUtil;
import com.taikang.dataVis.domain.LoadbalancerModel;
import com.taikang.dataVis.domain.RDBModel;
import com.taikang.dataVis.domain.TkOperationInformation;
import com.taikang.dataVis.mapper.LoadbalancerMapper;
import com.taikang.dataVis.mapper.OperationInformationMapper;
import com.taikang.dataVis.screen.service.CmdbLoadbalancerService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 项目名称:	[data-visualization]
 * 包:		[com.taikang.dataVis.screen.service.impl]
 * 类名称:	[CmdbLoadbalancerServiceImpl]
 * 创建人:	[itw_guodc]
 * 创建时间:	[2018年5月31日 下午1:50:59]
 * 修改人:	[itw_guodc]
 * 修改时间:	[2018年5月31日 下午1:50:59]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */ 
@Service
public class CmdbLoadbalancerServiceImpl implements CmdbLoadbalancerService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private LoadbalancerMapper loadbalancerMapper;
	@Autowired
	private JobEnv env;
	@Autowired
	private OperationInformationMapper tkOperationInformationMapper ;
	
	
	@Override
	public boolean syncLoadbalancerDetails(String ip) {
		// TODO Auto-generated method stub
		boolean flag = false; 
		// 生成时间
        String date = DateUtil.getCurrentZonedDateTime();
		TkOperationInformation tkOperationInformation = new TkOperationInformation();
		String operationId=UUID.randomUUID().toString().replaceAll("-","");
		tkOperationInformation.setId(operationId);
		tkOperationInformation.setOperationIp(ip);
		tkOperationInformation.setOperationType("syncLoadbalancerDetails");
		tkOperationInformation.setOperationDate(date);
		//保存采集记录（IP和时间）
		tkOperationInformationMapper.save(tkOperationInformation);
		//调用cmdb接口取到所有的ip列表
		String posturl = env.getCmdb_url() + "/object/LB/instance/_search";
		String str = "{\"query\":{},\"page\":1,\"page_size\":2}";
		JSONObject jsonParam = JSONObject.fromObject(str);
		Map<String, String> headerMap = CMDBUtil.getCmdbHeaderMap();
		try {
			logger.info("syncLoadbalancerDetails调用cmdb接口入参：" + jsonParam);
			String res = HttpClientUtil.httpPostWithJSON(posturl, jsonParam, headerMap);
			if (ClientUtil.isJson(res)) {
				JSONObject obj = JSONObject.fromObject(res);
				int code = obj.getInt("code");
				if(code==0) {//code为0说明成功
					JSONObject dataObj = obj.getJSONObject("data");
					int total = dataObj.containsKey("total")?dataObj.getInt("total"):0;
					if(total != 0) {
						//删除旧数据，为更新做准备
						loadbalancerMapper.deleteAll();
						Map<Integer,Integer> map = ClientUtil.getPage(total,200);
						if(!map.isEmpty()) {
							//循环页数并且调用接口
							for (Entry<Integer,Integer> entry : map.entrySet()) {
								List<LoadbalancerModel> list = new ArrayList<LoadbalancerModel>();
								str = "{\"query\":{},\"page\":"+(entry.getKey())+",\"page_size\":"+entry.getValue()+"}";
								jsonParam = JSONObject.fromObject(str);
								logger.info("syncLoadbalancerDetails调用cmdb接口入参：" + jsonParam);
								res = HttpClientUtil.httpPostWithJSON(posturl, jsonParam, headerMap);
								if (ClientUtil.isJson(res)) {
									obj = JSONObject.fromObject(res);
									code = obj.getInt("code");
									if(code==0) {//code为0说明成功
										JSONObject tempObj = obj.getJSONObject("data");
										JSONArray tempArray = tempObj.getJSONArray("list");
										for(int i=0;i<tempArray.size();i++) {
											JSONObject object = tempArray.getJSONObject(i);
											System.err.println(object);
											LoadbalancerModel loadbalancerModel = new LoadbalancerModel();
											loadbalancerModel.setId(object.getString("instanceId"));
											loadbalancerModel.setLoadbalancerId(object.containsKey("loadbalancer_id")?object.getString("loadbalancer_id"):"");
											loadbalancerModel.setName(object.containsKey("name")?object.getString("name"):"");
											loadbalancerModel.setLoadbalancerName(object.containsKey("Loadbalancer_name")?object.getString("Loadbalancer_name"):"");
											loadbalancerModel.setStatus(object.containsKey("status")?object.getString("status"):"");
											StringBuffer private_ips=new StringBuffer();
											if (object.containsKey("private_ips")) {
												JSONArray array =object.getJSONArray("private_ips");
												for (int j = 0; j < array.size(); j++) {
													private_ips.append(array.get(j)).append(",");
												}
											}
											if (private_ips.length()>1) {
												loadbalancerModel.setPrivateIps(private_ips.substring(0,private_ips.length()-1).toString());
											}else {
												loadbalancerModel.setPrivateIps("");
											}
											StringBuffer address=new StringBuffer();
											if (object.containsKey("IPADDRESS")) {
												JSONArray array = object.getJSONArray("IPADDRESS");
												for (int j = 0; j < array.size(); j++) {
													if (array.getJSONObject(j).containsKey("address")) {
														address.append(array.getJSONObject(j).getString("address")).append(",");
													}
												}
											}
											if (address.length()>1) {
												loadbalancerModel.setAddress(address.substring(0,address.length()-1).toString());
											}else {
												loadbalancerModel.setAddress("");
											}
											loadbalancerModel.setCloudType(object.containsKey("cloud_type")?object.getString("cloud_type"):"");
											loadbalancerModel.setStatusTime(object.containsKey("status_time")?object.getString("status_time"):"");
											loadbalancerModel.setCreateTime(object.containsKey("create_time")?object.getString("create_time"):"");
											JSONArray poolArray = object.containsKey("RESOURCEPOOL")?object.getJSONArray("RESOURCEPOOL"):new JSONArray();
											String zone_name = "";
											if(!poolArray.isEmpty() && poolArray.size()>=1) {
												String temStr = poolArray.get(0).toString();
												if(!"[]".equals(temStr)) {
													JSONObject temp = poolArray.getJSONObject(0);
													zone_name = temp.containsKey("zone_name")?temp.getString("zone_name"):"";
												}
											}
											loadbalancerModel.setZoneName(zone_name);
											loadbalancerModel.setRefreshTime(date);
											list.add(loadbalancerModel);
										}
//										批量保存loadbalancerModel
										loadbalancerMapper.saveBatch(list);
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
