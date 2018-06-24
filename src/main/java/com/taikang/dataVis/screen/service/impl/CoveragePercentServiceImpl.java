package com.taikang.dataVis.screen.service.impl;

import com.taikang.dataVis.core.config.JobEnv;
import com.taikang.dataVis.core.utils.ClientUtil;
import com.taikang.dataVis.core.utils.DateUtil;
import com.taikang.dataVis.core.utils.HttpClientUtil;
import com.taikang.dataVis.core.utils.JDBCUtil;
import com.taikang.dataVis.domain.CoveragePercentModel;
import com.taikang.dataVis.domain.HostModel;
import com.taikang.dataVis.domain.IPItemsCount;
import com.taikang.dataVis.domain.IpAddressModel;
import com.taikang.dataVis.mapper.AlarmCoverageMapper;
import com.taikang.dataVis.mapper.CoveragePercentMapper;
import com.taikang.dataVis.mapper.HostMapper;
import com.taikang.dataVis.mapper.IPItemsCountMapper;
import com.taikang.dataVis.mapper.IpAddressMapper;
import com.taikang.dataVis.mapper.RDBMapper;
import com.taikang.dataVis.screen.service.CoveragePercentService;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class CoveragePercentServiceImpl implements CoveragePercentService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private JDBCUtil jdbcUtil;
	@Autowired
	private JobEnv env;
	@Autowired
	private CoveragePercentMapper coveragePercentMapper;
	@Autowired
	private AlarmCoverageMapper alarmCoverageMapper;
	@Autowired
	private IpAddressMapper ipAddressMapper;
	@Autowired
	private HostMapper hostMapper;
	@Autowired
	private RDBMapper rdbMapper;
	@Autowired
	private IPItemsCountMapper ipItemsCountMapper;
	/**
	 * 查询zabbix的监控数据ip列表
	 */
	public JSONArray getZabbixCoverage(String sql) {
		return jdbcUtil.getJSONArray(sql);
	}
	/**
	 * 计算监控覆盖度并保存监控覆盖度到数据库
	 */
	@Transactional
	public boolean calAndSaveCoveragePercent() {
		boolean result = false;
		String percent = "";
		try {
			//查询cmdb的ip列表集合
			List<String> cmdbIpList = ipAddressMapper.getAllIp();
			//从zabbix查询ip列表集合
			String sql = "select distinct ip from interface where type = '1' and ip is not null and ip <> ''";
			JSONArray array = jdbcUtil.getJSONArray(sql);
			//得到zabbix的ip列表集合
			List<String> zabbixIpList = new ArrayList<String>();
			for(int i=0;i<array.size();i++) {
				JSONObject object = array.getJSONObject(i);
				zabbixIpList.add(object.getString("ip"));
			}
			List<String> tempIpList = new ArrayList<String>();
			//循环zabbix的ip列表集合，得到两个集合的交集
			for(int i=0;i<zabbixIpList.size();i++) {
				String tempStr = zabbixIpList.get(i);
				if(cmdbIpList.contains(tempStr)) {
					tempIpList.add(tempStr);
				}
			}
			int zabbixCount = zabbixIpList.size();//zabbix数量
			int tempCount = tempIpList.size();//交集数量-分子
			int cmdbCount = cmdbIpList.size();//cmdb数量-分母
			if(zabbixCount!=0){
				percent = new DecimalFormat("0.00").format(((double) tempCount/(double) cmdbCount)*100);
			}else{
				percent = "0.00";
			}
			//保存监控覆盖度到数据库
			CoveragePercentModel coveragePercentModel = new CoveragePercentModel();
			coveragePercentModel.setId(ClientUtil.getUUID32());
			coveragePercentModel.setCompareType("zabbix");
			coveragePercentModel.setCompareCount(String.valueOf(zabbixCount));
			coveragePercentModel.setBaseType("cmdb");
			coveragePercentModel.setBaseCount(String.valueOf(cmdbCount));
			coveragePercentModel.setIntersectionCount(String.valueOf(tempCount));
			coveragePercentModel.setPercent(percent);
			coveragePercentModel.setCreateDate(DateUtil.getCurrentZonedDateTime());
			coveragePercentMapper.save(coveragePercentModel);
			result = true;
		} catch (Exception e) {
			logger.error("Exception:" + e);
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 保存cmdb的ip列表到数据库
	 */
	@Transactional
	public boolean saveCoverageIpList() {
		boolean flag = false; 
		//调用cmdb接口取到所有的ip列表
		String posturl = env.getCmdb_url() + "/object/IPADDRESS/instance/_search";
		String str = "{\"query\":{},\"page\":1,\"page_size\":2,\"fields\":{\"only_relation_view\":true}}";
		JSONObject jsonParam = JSONObject.fromObject(str);
		Map<String, String> headerMap = getCmdbHeaderMap();
		try {
			logger.info("saveCoverageIpList调用cmdb接口入参：" + jsonParam);
			String res = HttpClientUtil.httpPostWithJSON(posturl, jsonParam, headerMap);
			if (ClientUtil.isJson(res)) {
				JSONObject obj = JSONObject.fromObject(res);
				int code = obj.getInt("code");
				if(code==0) {//code为0说明成功
					JSONObject dataObj = obj.getJSONObject("data");
					int total = dataObj.containsKey("total")?dataObj.getInt("total"):0;
					if(total != 0) {
						Map<Integer,Integer> map = ClientUtil.getPage(total,200);
						System.err.println(map);
						if(!map.isEmpty()) {
							//循环页数并且调用接口
							for (Entry<Integer,Integer> entry : map.entrySet()) {
								List<IpAddressModel> list = new ArrayList<IpAddressModel>();
//								str = "{\"query\":{},\"page\":"+(entry.getKey())+",\"page_size\":"+entry.getValue()+"}";
//								jsonParam = JSONObject.fromObject(str);
								jsonParam = new JSONObject();
								jsonParam.put("page", entry.getKey());
								jsonParam.put("page_size", entry.getValue());
								JSONObject tempFields = new JSONObject();
								tempFields.put("only_relation_view", true);
								tempFields.put("name", true);
								tempFields.put("private_host.qingcloud_id", true);
								tempFields.put("address", true);
								tempFields.put("type", true);
								tempFields.put("RESOURCEPOOL.id_in_cloud", true);
								tempFields.put("private_host.cloud_type", true);
								tempFields.put("instanceId", true);
								tempFields.put("private_host._object_id", true);
								tempFields.put("private_host._object_version", true);
								tempFields.put("private_host.creator", true);
								tempFields.put("private_host.ctime", true);
								tempFields.put("private_host.osSystem", true);
								tempFields.put("private_host.status", true);
								jsonParam.put("fields", tempFields);
								logger.info("saveCoverageIpList调用cmdb接口入参：" + jsonParam);
								res = HttpClientUtil.httpPostWithJSON(posturl, jsonParam, headerMap);
								if (ClientUtil.isJson(res)) {
									obj = JSONObject.fromObject(res);
									code = obj.getInt("code");
									if(code==0) {//code为0说明成功
										JSONObject tempObj = obj.getJSONObject("data");
										JSONArray tempArray = tempObj.getJSONArray("list");
										System.err.println("page: "+(entry.getKey())+"  "+entry.getValue()+"   条数："+tempArray.size());
										for(int i=0;i<tempArray.size();i++) {
											JSONObject object = tempArray.getJSONObject(i);
											JSONArray poolArray = object.containsKey("RESOURCEPOOL")?object.getJSONArray("RESOURCEPOOL"):new JSONArray();
											String id_in_cloud = "";
											if(!poolArray.isEmpty() && poolArray.size()>=1) {
												String temStr = poolArray.get(0).toString();
												if(!"[]".equals(temStr)) {
													JSONObject temp = poolArray.getJSONObject(0);
													id_in_cloud = temp.containsKey("id_in_cloud")?temp.getString("id_in_cloud"):"";
												}
											}
											JSONArray hostArray = object.containsKey("private_host")?object.getJSONArray("private_host"):new JSONArray();
											JSONObject hostObject = new JSONObject();
											if(!hostArray.isEmpty()  && hostArray.size()>=1) {
												String temStr = hostArray.get(0).toString();
												if(!"[]".equals(temStr)) {
													hostObject = hostArray.getJSONObject(0);
												}
											}
											IpAddressModel ipAddressModel = new IpAddressModel();
											ipAddressModel.setId(ClientUtil.getUUID32());
											ipAddressModel.setName(object.containsKey("name")?object.getString("name"):"");
											ipAddressModel.setQingcloud_id(object.containsKey("qingcloud_id")?object.getString("qingcloud_id"):"");
											ipAddressModel.setAddress(object.containsKey("address")?object.getString("address"):"");
											ipAddressModel.setType(object.containsKey("type")?object.getString("type"):"");
											ipAddressModel.setId_in_cloud(id_in_cloud);
											ipAddressModel.setCloud_type(hostObject.containsKey("cloud_type")?hostObject.getString("cloud_type"):"");
											ipAddressModel.setInstance_id(object.containsKey("instanceId")?object.getString("instanceId"):"");
											ipAddressModel.setObject_id(hostObject.containsKey("_object_id")?hostObject.getString("_object_id"):"");
											ipAddressModel.setObject_version(hostObject.containsKey("_object_version")?hostObject.getString("_object_version"):"");
											ipAddressModel.setCreator(hostObject.containsKey("creator")?hostObject.getString("creator"):"");
											ipAddressModel.setCtime(hostObject.containsKey("ctime")?hostObject.getString("ctime"):"");
											ipAddressModel.setCreate_time(DateUtil.getCurrentDate());
											ipAddressModel.setOs_system(hostObject.containsKey("osSystem")?hostObject.getString("osSystem"):"");
											ipAddressModel.setStatus(hostObject.containsKey("status")?hostObject.getString("status"):"");
											list.add(ipAddressModel);
										}
										//批量保存IpAddressModel
										ipAddressMapper.saveBatch(list);
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
	/**
	 * 删除所有数据
	 */
	@Transactional
	public void deleteCoverageIpList() {
		ipAddressMapper.deleteAll();
	}
	/**
	 * 得到所有cmdb的ip列表
	 */
	public List<String> getAllIp() {
		return ipAddressMapper.getAllIp();
	}
	/**
	 * 构造headerMap cmdb
	 * @MethodName: getCmdbHeaderMap
	 * @return
	 * @return Map<String,String>
	 * @throws
	 */
	public Map<String, String> getCmdbHeaderMap() {
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("host", env.getCmdb_host_cmdb());
		headerMap.put("org", env.getCmdb_org());
		headerMap.put("user", env.getCmdb_user());
		return headerMap;
	}
	/**
	 * 根据平台类型取得覆盖度数据
	 */
	public String getCoverageByType(String type) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list = coveragePercentMapper.getCoverageByType(type);
		return JSONArray.fromObject(list).toString();
	}
	/**
	 * 根据zone取得覆盖度数据
	 */
	public String getCoverageByZone(String zone) {
		if("test".equals(zone)) {
			zone = "('test')";
		}else if("dev".equals(zone)) {
			zone = "('test2')";
		}else if("pro".equals(zone)) {
			zone = "('prod2','tkwh')";
		}
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list = coveragePercentMapper.getCoverageByZone(zone);
		return JSONArray.fromObject(list).toString();
	}
	
	/**
	 * 取得监控覆盖数量
	 */
	public String getCoverageAmount() {
		Map<String,Object> map = coveragePercentMapper.getCoverageAmount();
		return JSONObject.fromObject(map).toString();
	}
	/**
	 * 保存host列表
	 */
	@Transactional
	public boolean saveCoverageHostList() {
		boolean flag = false; 
		//调用cmdb接口取到所有的ip列表
		String posturl = env.getCmdb_url() + "/object/HOST/instance/_search";
		String str = "{\"query\":{},\"page\":1,\"page_size\":2,\"fields\":{\"only_relation_view\":true}}";
		JSONObject jsonParam = JSONObject.fromObject(str);
		Map<String, String> headerMap = getCmdbHeaderMap();
		try {
			logger.info("saveCoverageHostList调用cmdb接口入参：" + jsonParam);
			System.err.println(posturl);
			System.err.println(jsonParam);
			System.err.println(headerMap);
			String res = HttpClientUtil.httpPostWithJSON(posturl, jsonParam, headerMap);
			System.err.println(res);
			if (ClientUtil.isJson(res)) {
				JSONObject obj = JSONObject.fromObject(res);
				int code = obj.getInt("code");
				if(code==0) {//code为0说明成功
					JSONObject dataObj = obj.getJSONObject("data");
					int total = dataObj.containsKey("total")?dataObj.getInt("total"):0;
					if(total != 0) {
						Map<Integer,Integer> map = ClientUtil.getPage(total,200);
						System.err.println(map);
						if(!map.isEmpty()) {
							//循环页数并且调用接口
							for (Entry<Integer,Integer> entry : map.entrySet()) {
								List<HostModel> list = new ArrayList<HostModel>();
								jsonParam = new JSONObject();
								jsonParam.put("page", entry.getKey());
								jsonParam.put("page_size", entry.getValue());
								JSONObject tempFields = new JSONObject();
								tempFields.put("only_relation_view", true);
								tempFields.put("hostname", true);
								tempFields.put("display_name", true);
								tempFields.put("ip", true);
								tempFields.put("status", true);
								tempFields.put("deviceId", true);
								tempFields.put("memSize", true);
								tempFields.put("diskSize", true);
								tempFields.put("cpus", true);
								tempFields.put("osRelease", true);
								tempFields.put("osSystem", true);
								tempFields.put("id_in_cloud", true);
								tempFields.put("cloud_type", true);
								tempFields.put("RESOURCEPOOL.zone_name", true);
								tempFields.put("_object_version", true);
								tempFields.put("creator", true);
								tempFields.put("ctime", true);
								jsonParam.put("fields", tempFields);
								logger.info("saveCoverageHostList调用cmdb接口入参：" + jsonParam);
								res = HttpClientUtil.httpPostWithJSON(posturl, jsonParam, headerMap);
								if (ClientUtil.isJson(res)) {
									obj = JSONObject.fromObject(res);
									code = obj.getInt("code");
									if(code==0) {//code为0说明成功
										JSONObject tempObj = obj.getJSONObject("data");
										JSONArray tempArray = tempObj.getJSONArray("list");
										System.err.println("page: "+(entry.getKey())+"  "+entry.getValue()+"   条数："+tempArray.size());
										for(int i=0;i<tempArray.size();i++) {
											JSONObject object = tempArray.getJSONObject(i);
											HostModel hostModel = new HostModel();
											hostModel.setId(ClientUtil.getUUID32());
											hostModel.setHostname(object.containsKey("hostname")?object.getString("hostname"):"");
											hostModel.setDisplay_name(object.containsKey("display_name")?object.getString("display_name"):"");
											hostModel.setIp(object.containsKey("ip")?object.getString("ip"):"");
											hostModel.setStatus(object.containsKey("status")?object.getString("status"):"");
											hostModel.setDevice_id(object.containsKey("deviceId")?object.getString("deviceId"):"");
											hostModel.setMem_size(object.containsKey("memSize")?object.getString("memSize"):"");
											hostModel.setDisk_size(object.containsKey("diskSize")?object.getString("diskSize"):"");
											hostModel.setCpus(object.containsKey("cpus")?object.getString("cpus"):"");
											hostModel.setOs_release(object.containsKey("osRelease")?object.getString("osRelease"):"");
											hostModel.setOs_system(object.containsKey("osSystem")?object.getString("osSystem"):"");
											hostModel.setId_in_cloud(object.containsKey("id_in_cloud")?object.getString("id_in_cloud"):"");
											hostModel.setCloud_type(object.containsKey("cloud_type")?object.getString("cloud_type"):"");
											
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
											
											hostModel.setZone_name(zone_name);
											hostModel.setObject_version(object.containsKey("_object_version")?object.getString("_object_version"):"");
											hostModel.setCreator(object.containsKey("creator")?object.getString("creator"):"");
											hostModel.setCtime(object.containsKey("ctime")?object.getString("ctime"):"");
											hostModel.setCreate_time(DateUtil.getCurrentDate());
											list.add(hostModel);
										}
										//批量保存HostModel
										hostMapper.saveBatch(list);
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
	/**
	 * 删除所有数据
	 */
	@Transactional
	public void deleteCoverageHostList() {
		hostMapper.deleteAll();
	}
	/**
	 * 计算host的覆盖度
	 */
	@Transactional
	public boolean calAndSaveHostCoveragePercent() {
		boolean result = false;
		String percent = "";
		try {
			//查询cmdb的ip列表集合
			List<String> cmdbIpList = hostMapper.getAllIp();
			//从zabbix查询ip列表集合
			String sql = "select distinct ip from interface where type = '1' and ip is not null and ip <> ''";
			JSONArray array = jdbcUtil.getJSONArray(sql);
			//得到zabbix的ip列表集合
			List<String> zabbixIpList = new ArrayList<String>();
			for(int i=0;i<array.size();i++) {
				JSONObject object = array.getJSONObject(i);
				zabbixIpList.add(object.getString("ip"));
			}
			int zabbixCount = zabbixIpList.size();//zabbix数量
			// 1.总体的覆盖度
			List<String> tempIpList = new ArrayList<String>();
			//循环zabbix的ip列表集合，得到两个集合的交集
			for(int i=0;i<zabbixIpList.size();i++) {
				String tempStr = zabbixIpList.get(i);
				if(cmdbIpList.contains(tempStr)) {
					tempIpList.add(tempStr);
				}
			}
			int tempCount = tempIpList.size();//交集数量-分子
			int cmdbCount = cmdbIpList.size();//cmdb数量-分母
			if(zabbixCount!=0){
				if(cmdbCount != 0) {
					percent = new DecimalFormat("0.00").format(((double) tempCount/(double) cmdbCount)*100);
				}else {
					percent = "0.00";
				}
			}else{
				percent = "0.00";
			}
			//保存监控覆盖度到数据库
			CoveragePercentModel coveragePercentModel = new CoveragePercentModel();
			coveragePercentModel.setId(ClientUtil.getUUID32());
			coveragePercentModel.setCompareType("zabbix");
			coveragePercentModel.setCompareCount(String.valueOf(zabbixCount));
			coveragePercentModel.setBaseType("cmdb_host");
			coveragePercentModel.setBaseCount(String.valueOf(cmdbCount));
			coveragePercentModel.setIntersectionCount(String.valueOf(tempCount));
			coveragePercentModel.setPercent(percent);
			coveragePercentModel.setCreateDate(DateUtil.getCurrentZonedDateTime());
			coveragePercentMapper.save(coveragePercentModel);
			// 2.test类型
			String zone_test = "('test')";
			List<String> ipListTest = new ArrayList<String>();
			ipListTest = coveragePercentMapper.getHostIpListByZone(zone_test);
			// test类型的交集
			List<String> tempListTest = new ArrayList<String>();
			for(int i=0;i<zabbixIpList.size();i++) {
				String tempStr = zabbixIpList.get(i);
				if(ipListTest.contains(tempStr)) {
					tempListTest.add(tempStr);
				}
			}
			String percentTest = "";
			int tempCountTest = tempListTest.size();//test类型的交集数量-分子
			int cmdbCountTest = ipListTest.size();//test类型的cmdb数量-分母
			if(zabbixCount!=0){
				if(cmdbCountTest != 0) {
					percentTest = new DecimalFormat("0.00").format(((double) tempCountTest/(double) cmdbCountTest)*100);
				}else {
					percentTest = "0.00";
				}
			}else{
				percentTest = "0.00";
			}
			// 保存test类型监控覆盖度到数据库
			CoveragePercentModel coveragePercentModelTest = new CoveragePercentModel();
			coveragePercentModelTest.setId(ClientUtil.getUUID32());
			coveragePercentModelTest.setCompareType("zabbix");
			coveragePercentModelTest.setCompareCount(String.valueOf(zabbixCount));
			coveragePercentModelTest.setBaseType("cmdb_host_test");
			coveragePercentModelTest.setBaseCount(String.valueOf(cmdbCountTest));
			coveragePercentModelTest.setIntersectionCount(String.valueOf(tempCountTest));
			coveragePercentModelTest.setPercent(percentTest);
			coveragePercentModelTest.setCreateDate(DateUtil.getCurrentZonedDateTime());
			coveragePercentMapper.save(coveragePercentModelTest);
			// 3.dev类型
			String zone_dev = "('test2')";
			List<String> ipListDev = new ArrayList<String>();
			ipListDev = coveragePercentMapper.getHostIpListByZone(zone_dev);
			// dev类型的交集
			List<String> tempListDev = new ArrayList<String>();
			for(int i=0;i<zabbixIpList.size();i++) {
				String tempStr = zabbixIpList.get(i);
				if(ipListDev.contains(tempStr)) {
					tempListDev.add(tempStr);
				}
			}
			String percentDev = "";
			int tempCountDev = tempListDev.size();//dev类型的交集数量-分子
			int cmdbCountDev = ipListDev.size();//dev类型的cmdb数量-分母
			if(zabbixCount!=0){
				if(cmdbCountDev != 0) {
					percentDev = new DecimalFormat("0.00").format(((double) tempCountDev/(double) cmdbCountDev)*100);
				}else {
					percentDev = "0.00";
				}
			}else{
				percentDev = "0.00";
			}
			// 保存dev类型监控覆盖度到数据库
			CoveragePercentModel coveragePercentModelDev = new CoveragePercentModel();
			coveragePercentModelDev.setId(ClientUtil.getUUID32());
			coveragePercentModelDev.setCompareType("zabbix");
			coveragePercentModelDev.setCompareCount(String.valueOf(zabbixCount));
			coveragePercentModelDev.setBaseType("cmdb_host_dev");
			coveragePercentModelDev.setBaseCount(String.valueOf(cmdbCountDev));
			coveragePercentModelDev.setIntersectionCount(String.valueOf(tempCountDev));
			coveragePercentModelDev.setPercent(percentDev);
			coveragePercentModelDev.setCreateDate(DateUtil.getCurrentZonedDateTime());
			coveragePercentMapper.save(coveragePercentModelDev);
			// 4.pro类型
			String zone_pro = "('prod2','tkwh')";
			List<String> ipListPro = new ArrayList<String>();
			ipListPro = coveragePercentMapper.getHostIpListByZone(zone_pro);
			// pro类型的交集
			List<String> tempListPro = new ArrayList<String>();
			for(int i=0;i<zabbixIpList.size();i++) {
				String tempStr = zabbixIpList.get(i);
				if(ipListPro.contains(tempStr)) {
					tempListPro.add(tempStr);
				}
			}
			String percentPro = "";
			int tempCountPro = tempListPro.size();//pro类型的交集数量-分子
			int cmdbCountPro = ipListPro.size();//pro类型的cmdb数量-分母
			if(zabbixCount!=0){
				if(cmdbCountPro != 0) {
					percentPro = new DecimalFormat("0.00").format(((double) tempCountPro/(double) cmdbCountPro)*100);
				}else {
					percentPro = "0.00";
				}
			}else{
				percentPro = "0.00";
			}
			// 保存pro类型监控覆盖度到数据库
			CoveragePercentModel coveragePercentModelPro = new CoveragePercentModel();
			coveragePercentModelPro.setId(ClientUtil.getUUID32());
			coveragePercentModelPro.setCompareType("zabbix");
			coveragePercentModelPro.setCompareCount(String.valueOf(zabbixCount));
			coveragePercentModelPro.setBaseType("cmdb_host_pro");
			coveragePercentModelPro.setBaseCount(String.valueOf(cmdbCountPro));
			coveragePercentModelPro.setIntersectionCount(String.valueOf(tempCountPro));
			coveragePercentModelPro.setPercent(percentPro);
			coveragePercentModelPro.setCreateDate(DateUtil.getCurrentZonedDateTime());
			coveragePercentMapper.save(coveragePercentModelPro);
			result = true;
		} catch (Exception e) {
			logger.error("Exception:" + e);
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 计算rdb的覆盖度
	 */
	@Transactional
	@Override
	public boolean calAndSaveRDBCoveragePercent() {
		// TODO Auto-generated method stub
		boolean result = false;
		try {
			Long compareCount =ipItemsCountMapper.getAllcount();
			// 1.总体的覆盖度
			Long BaseCount=rdbMapper.getBaseCount(null);
			Long intersectionCount=rdbMapper.getIntersectionCount(null);
			//保存监控覆盖度到数据库
			CoveragePercentModel coveragePercentModel = new CoveragePercentModel();
			coveragePercentModel.setId(ClientUtil.getUUID32());
			coveragePercentModel.setCompareType("zabbix");
			coveragePercentModel.setCompareCount(String.valueOf(compareCount));
			coveragePercentModel.setBaseType("cmdb_rdb");
			coveragePercentModel.setBaseCount(String.valueOf(BaseCount));
			coveragePercentModel.setIntersectionCount(String.valueOf(intersectionCount));
			if (BaseCount==0) {
				coveragePercentModel.setPercent("0.00");
			}else {
				coveragePercentModel.setPercent(new DecimalFormat("0.00").format(((double) intersectionCount/(double) BaseCount)*100));
			}
			coveragePercentModel.setCreateDate(DateUtil.getCurrentZonedDateTime());
			coveragePercentMapper.save(coveragePercentModel);
			String[] list ="dev,pro,test".split(",");
			for (int i = 0; i < list.length; i++) {
				Long BaseCountzone=rdbMapper.getBaseCount(list[i]);
				Long intersectionCountzone=rdbMapper.getIntersectionCount(list[i]);
				//保存监控覆盖度到数据库
				CoveragePercentModel coveragePercentModelzone = new CoveragePercentModel();
				coveragePercentModelzone.setId(ClientUtil.getUUID32());
				coveragePercentModelzone.setCompareType("zabbix");
				coveragePercentModelzone.setCompareCount(String.valueOf(compareCount));
				coveragePercentModelzone.setBaseType("cmdb_rdb_"+list[i]);
				coveragePercentModelzone.setBaseCount(String.valueOf(BaseCountzone));
				coveragePercentModelzone.setIntersectionCount(String.valueOf(intersectionCountzone));
				if (BaseCountzone==0) {
					coveragePercentModelzone.setPercent("0.00");
				}else {
					coveragePercentModelzone.setPercent(new DecimalFormat("0.00").format(((double) intersectionCountzone/(double) BaseCountzone)*100));
				}
				
				coveragePercentModelzone.setCreateDate(DateUtil.getCurrentZonedDateTime());
				coveragePercentMapper.save(coveragePercentModelzone);
			}
			//
			result = true;
		} catch (Exception e) {
			logger.error("Exception:" + e);
			e.printStackTrace();
		}
		return result;
	
	}
	
	@Override
	public Long getItemsAmountByHostIP() {
		// TODO Auto-generated method stub
		return ipItemsCountMapper.getItemsAmountByHostIP();
		
	}
	
	@Override
	public Long getItemsAmountByIP(String[] ips) {
		// TODO Auto-generated method stub
		
		return ipItemsCountMapper.getItemsAmountByIP(ips);
	}
	@Override
	@Transactional
	public boolean SaveItemsAmountByIP() {
		// TODO Auto-generated method stub
		boolean flag=true;
		String total_countsql="SELECT count(*) count FROM interface";
		int total_count=jdbcUtil.getCount(total_countsql);
		// 生成时间
        String date = DateUtil.getCurrentZonedDateTime();
        //采集前删除旧数据
        int deleteresult= ipItemsCountMapper.deleteAll();
        if (deleteresult<0) {
			flag=false;
		}else {
			 //查询cmdb的ip列表集合
	        List<IPItemsCount> listadd = new ArrayList<>();
			for (int limit = 0; limit < total_count; limit+=800) {
				String sql = "SELECT DISTINCT i.ip,i.hostid,(SELECT count(*) FROM items m WHERE m.`status`=0 AND m.hostid=i.hostid) count FROM interface i "
						+ "ORDER BY i.ip DESC LIMIT "+limit+", 800";
				JSONArray array=jdbcUtil.getJSONArray(sql);
				for (int i = 0; i < array.size(); i++) {
					IPItemsCount ipItemsCount = new IPItemsCount();
					ipItemsCount.setIp(array.getJSONObject(i).getString("ip"));
					ipItemsCount.setHostid(array.getJSONObject(i).getString("hostid"));
					ipItemsCount.setItemsCount(array.getJSONObject(i).getLong("count"));
					ipItemsCount.setCreateDate(date);
					listadd.add(ipItemsCount);
				}
				if (listadd.size()>0) {
					int saveresult=ipItemsCountMapper.insertBatch(listadd);
					if (saveresult<1) {
						flag=false;
						logger.error("ipItemsCountMapper 批量插入 limit:"+limit+"~"+(limit+800)+"  失败！" );
					}
					listadd=new ArrayList<>();
				}
			}
		}
		return flag;
	}
	/**
	 * 取得host监控覆盖度百分比
	 */
	public String getCoverageHost() {
		JSONObject resObj = new JSONObject();
		try {
			//监控覆盖度覆盖率
			JSONObject object = new JSONObject();
			List<Map<String,String>> listPercent = coveragePercentMapper.selectCoverageHost();
			JSONObject objectPercent = new JSONObject();
			Long count = ipItemsCountMapper.getItemsAmountByHostIP();
			if(count == null) {
				count = (long) 0;
			}
			objectPercent.put("count", String.valueOf(count));
			if(!listPercent.isEmpty() && listPercent.size() > 0) {
				for(int i=0;i<listPercent.size();i++) {
					Map<String,String> map = listPercent.get(i);
					String base_type = map.get("base_type");
					String percent = map.get("percent");
					if("cmdb_host".equals(base_type)) {
						objectPercent.put("cmdb_host", percent);
					}else if("cmdb_host_pro".equals(base_type)) {
						objectPercent.put("cmdb_host_pro", percent);
					}else if("cmdb_host_test".equals(base_type)) {
						objectPercent.put("cmdb_host_test", percent);
					}else if("cmdb_host_dev".equals(base_type)) {
						objectPercent.put("cmdb_host_dev", percent);
					}
				}
			}else {
				objectPercent.put("cmdb_host", "0.00");
				objectPercent.put("cmdb_host_pro", "0.00");
				objectPercent.put("cmdb_host_test", "0.00");
				objectPercent.put("cmdb_host_dev", "0.00");
			}
			object.put("percent", objectPercent);
			//监控覆盖度数量
			List<Map<String,String>> listAmount = alarmCoverageMapper.getCoverageHostCount();
			JSONObject objectAmount = new JSONObject();
			JSONArray arrayTempAll = new JSONArray();
			JSONArray arrayTempTest = new JSONArray();
			JSONArray arrayTempDev = new JSONArray();
			JSONArray arrayTempPro = new JSONArray();
			if(!listAmount.isEmpty() && listAmount.size() > 0) {
				for(int i=0;i<listAmount.size();i++) {
					Map<String,String> map = listAmount.get(i);
					String zone = map.get("zone");
					String operation_os = map.get("operation_os");
					String used_operation_os = map.get("used_operation_os");
					String all_operation_os = map.get("all_operation_os");
					if("all".equals(zone)) {
						JSONObject obj = new JSONObject();
						obj.put("operation_os", operation_os);
						obj.put("used_operation_os", used_operation_os);
						obj.put("all_operation_os", all_operation_os);
						arrayTempAll.add(obj);
					}else if("test".equals(zone)) {
						JSONObject obj = new JSONObject();
						obj.put("operation_os", operation_os);
						obj.put("used_operation_os", used_operation_os);
						obj.put("all_operation_os", all_operation_os);
						arrayTempTest.add(obj);
					}else if("dev".equals(zone)) {
						JSONObject obj = new JSONObject();
						obj.put("operation_os", operation_os);
						obj.put("used_operation_os", used_operation_os);
						obj.put("all_operation_os", all_operation_os);
						arrayTempDev.add(obj);
					}else if("pro".equals(zone)) {
						JSONObject obj = new JSONObject();
						obj.put("operation_os", operation_os);
						obj.put("used_operation_os", used_operation_os);
						obj.put("all_operation_os", all_operation_os);
						arrayTempPro.add(obj);
					}
				}
				objectAmount.put("all", arrayTempAll);
				objectAmount.put("test", arrayTempTest);
				objectAmount.put("dev", arrayTempDev);
				objectAmount.put("pro", arrayTempPro);
			}else {
				arrayTempAll.add(getLinuxObject());
				arrayTempAll.add(getWindowsObject());
				
				arrayTempTest.add(getLinuxObject());
				arrayTempTest.add(getWindowsObject());
				
				arrayTempDev.add(getLinuxObject());
				arrayTempDev.add(getWindowsObject());
				
				arrayTempPro.add(getLinuxObject());
				arrayTempPro.add(getWindowsObject());
				
				objectAmount.put("all", arrayTempAll);
				objectAmount.put("test", arrayTempTest);
				objectAmount.put("dev", arrayTempDev);
				objectAmount.put("pro", arrayTempPro);
			}
			object.put("count", objectAmount);
			resObj.put("ret_code", 0);
			resObj.put("result", object);
		} catch (Exception e) {
			resObj.put("ret_code", 1200);
			resObj.put("message", "程序异常");
			logger.error("Exception:" + e);
			e.printStackTrace();
		}
		return resObj.toString();
	}
	/**
	 * 组装LinuxObject
	 * @MethodName: getLinuxObject
	 * @return
	 * @return JSONObject
	 * @throws
	 */
	public JSONObject getLinuxObject() {
		JSONObject object = new JSONObject();
		object.put("operation_os", "linux");
		object.put("used_operation_os", "0");
		object.put("all_operation_os", "0");
		return object;
	}
	/**
	 * 组装WindowsObject
	 * @MethodName: getWindowsObject
	 * @return
	 * @return JSONObject
	 * @throws
	 */
	public JSONObject getWindowsObject() {
		JSONObject object = new JSONObject();
		object.put("operation_os", "windows");
		object.put("used_operation_os", "0");
		object.put("all_operation_os", "0");
		return object;
	}
	@Override
	public String getCoveragePercentByTime(String paramStr) {
		JSONObject resObj = new JSONObject();
		try {
			JSONObject param = new JSONObject();
			if(ClientUtil.isJson(paramStr)) {
				param = JSONObject.fromObject(paramStr);
				String type = param.containsKey("type")?param.getString("type"):"";
				String start_time = param.containsKey("start_time")?param.getString("start_time"):"";
				String end_time = param.containsKey("end_time")?param.getString("end_time"):"";
				JSONObject tempObj = new JSONObject();
				if("host".equals(type)) {
					List<Map<String,String>> listPro = coveragePercentMapper.getCoveragePercentByTime("items_count_host_pro",start_time,end_time);
					List<Map<String,String>> listTest = coveragePercentMapper.getCoveragePercentByTime("items_count_host_test",start_time,end_time);
					List<Map<String,String>> listDev = coveragePercentMapper.getCoveragePercentByTime("items_count_host_dev",start_time,end_time);
					tempObj.put("pro", JSONArray.fromObject(listPro));
					tempObj.put("test", JSONArray.fromObject(listTest));
					tempObj.put("dev", JSONArray.fromObject(listDev));
				}else if("rdb".equals(type)){
					List<Map<String,String>> listPro = coveragePercentMapper.getCoveragePercentByTime("items_count_rdb_pro",start_time,end_time);
					List<Map<String,String>> listTest = coveragePercentMapper.getCoveragePercentByTime("items_count_rdb_test",start_time,end_time);
					List<Map<String,String>> listDev = coveragePercentMapper.getCoveragePercentByTime("items_count_rdb_dev",start_time,end_time);
					tempObj.put("pro", JSONArray.fromObject(listPro));
					tempObj.put("test", JSONArray.fromObject(listTest));
					tempObj.put("dev", JSONArray.fromObject(listDev));
				}else if("instance".equals(type)){
					List<Map<String,String>> listPro = coveragePercentMapper.getCoveragePercentByTime("items_count_instance_pro",start_time,end_time);
					List<Map<String,String>> listTest = coveragePercentMapper.getCoveragePercentByTime("items_count_instance_test",start_time,end_time);
					List<Map<String,String>> listDev = coveragePercentMapper.getCoveragePercentByTime("items_count_instance_dev",start_time,end_time);
					tempObj.put("pro", JSONArray.fromObject(listPro));
					tempObj.put("test", JSONArray.fromObject(listTest));
					tempObj.put("dev", JSONArray.fromObject(listDev));
				}else if("unit".equals(type)){
					List<Map<String,String>> listPro = coveragePercentMapper.getCoveragePercentByTime("items_count_unit_pro",start_time,end_time);
					List<Map<String,String>> listTest = coveragePercentMapper.getCoveragePercentByTime("items_count_unit_test",start_time,end_time);
					List<Map<String,String>> listDev = coveragePercentMapper.getCoveragePercentByTime("items_count_unit_dev",start_time,end_time);
					tempObj.put("pro", JSONArray.fromObject(listPro));
					tempObj.put("test", JSONArray.fromObject(listTest));
					tempObj.put("dev", JSONArray.fromObject(listDev));
				}
				resObj.put("ret_code", 0);
				resObj.put("result", tempObj);
			}else {
				resObj.put("ret_code", 1200);
				resObj.put("message", "参数不是json格式");
			}
		} catch (Exception e) {
			resObj.put("ret_code", 1200);
			resObj.put("message", "程序异常");
			logger.error("Exception:" + e);
			e.printStackTrace();
		}
		return resObj.toString();
	}
	/**
	 * 取得rdb监控覆盖度百分比
	 */
	public String getCoverageRdb() {
		JSONObject resObj = new JSONObject();
		try {
			//监控覆盖度覆盖率
			JSONObject object = new JSONObject();
			List<Map<String,String>> listPercent = coveragePercentMapper.selectCoverageRdb();
			JSONObject objectPercent = new JSONObject();
			Long count = ipItemsCountMapper.getItemsAmountByRdbIP();
			if(count == null) {
				count = (long) 0;
			}
			objectPercent.put("count", String.valueOf(count));
			if(!listPercent.isEmpty() && listPercent.size() > 0) {
				for(int i=0;i<listPercent.size();i++) {
					Map<String,String> map = listPercent.get(i);
					String base_type = map.get("base_type");
					String percent = map.get("percent");
					if("cmdb_rdb".equals(base_type)) {
						objectPercent.put("cmdb_rdb", percent);
					}else if("cmdb_rdb_pro".equals(base_type)) {
						objectPercent.put("cmdb_rdb_pro", percent);
					}else if("cmdb_rdb_test".equals(base_type)) {
						objectPercent.put("cmdb_rdb_test", percent);
					}else if("cmdb_rdb_dev".equals(base_type)) {
						objectPercent.put("cmdb_rdb_dev", percent);
					}
				}
			}else {
				objectPercent.put("cmdb_rdb", "0.00");
				objectPercent.put("cmdb_rdb_pro", "0.00");
				objectPercent.put("cmdb_rdb_test", "0.00");
				objectPercent.put("cmdb_rdb_dev", "0.00");
			}
			object.put("percent", objectPercent);
			//监控覆盖度数量
			List<Map<String,String>> listAmount = alarmCoverageMapper.getCoverageRdbCount();
			JSONObject objectAmount = new JSONObject();
			JSONArray arrayTempAll = new JSONArray();
			JSONArray arrayTempTest = new JSONArray();
			JSONArray arrayTempDev = new JSONArray();
			JSONArray arrayTempPro = new JSONArray();
			if(!listAmount.isEmpty() && listAmount.size() > 0) {
				for(int i=0;i<listAmount.size();i++) {
					Map<String,String> map = listAmount.get(i);
					String zone = map.get("zone");
					String operation_os = map.get("operation_os");
					String used_operation_os = map.get("used_operation_os");
					String all_operation_os = map.get("all_operation_os");
					if("all".equals(zone)) {
						JSONObject obj = new JSONObject();
						obj.put("operation_os", operation_os);
						obj.put("used_operation_os", used_operation_os);
						obj.put("all_operation_os", all_operation_os);
						arrayTempAll.add(obj);
					}else if("test".equals(zone)) {
						JSONObject obj = new JSONObject();
						obj.put("operation_os", operation_os);
						obj.put("used_operation_os", used_operation_os);
						obj.put("all_operation_os", all_operation_os);
						arrayTempTest.add(obj);
					}else if("dev".equals(zone)) {
						JSONObject obj = new JSONObject();
						obj.put("operation_os", operation_os);
						obj.put("used_operation_os", used_operation_os);
						obj.put("all_operation_os", all_operation_os);
						arrayTempDev.add(obj);
					}else if("pro".equals(zone)) {
						JSONObject obj = new JSONObject();
						obj.put("operation_os", operation_os);
						obj.put("used_operation_os", used_operation_os);
						obj.put("all_operation_os", all_operation_os);
						arrayTempPro.add(obj);
					}
				}
				objectAmount.put("all", arrayTempAll);
				objectAmount.put("test", arrayTempTest);
				objectAmount.put("dev", arrayTempDev);
				objectAmount.put("pro", arrayTempPro);
			}else {
				arrayTempAll.add(getLinuxObject());
				arrayTempAll.add(getWindowsObject());
				
				arrayTempTest.add(getLinuxObject());
				arrayTempTest.add(getWindowsObject());
				
				arrayTempDev.add(getLinuxObject());
				arrayTempDev.add(getWindowsObject());
				
				arrayTempPro.add(getLinuxObject());
				arrayTempPro.add(getWindowsObject());
				
				objectAmount.put("all", arrayTempAll);
				objectAmount.put("test", arrayTempTest);
				objectAmount.put("dev", arrayTempDev);
				objectAmount.put("pro", arrayTempPro);
			}
			object.put("count", objectAmount);
			resObj.put("ret_code", 0);
			resObj.put("result", object);
		} catch (Exception e) {
			resObj.put("ret_code", 1200);
			resObj.put("message", "程序异常");
			logger.error("Exception:" + e);
			e.printStackTrace();
		}
		return resObj.toString();
	}
	@Override
	public boolean calAndSaveItemsAmountByHostIP() {
		// TODO Auto-generated method stub
		boolean result = false;
		try {
			Long count=ipItemsCountMapper.getItemsAmountByHostIP();
			//增加排除组件的虚机指标数统计
			Long countinstance=ipItemsCountMapper.getItemsAmountByInstancetIP();
			
			if (count==null) {
				count=(long)0;
			}
			//保存监控覆盖度到数据库
			CoveragePercentModel coveragePercentModel = new CoveragePercentModel();
			coveragePercentModel.setId(ClientUtil.getUUID32());
			coveragePercentModel.setCompareType("zabbix");
			coveragePercentModel.setCompareCount("");
			coveragePercentModel.setBaseType("items_count_host");
			coveragePercentModel.setBaseCount("");
			coveragePercentModel.setIntersectionCount(String.valueOf(count));
			coveragePercentModel.setPercent("");
			coveragePercentModel.setCreateDate(DateUtil.getCurrentZonedDateTime());
			coveragePercentMapper.save(coveragePercentModel);
			//保存排除组件的虚机指标数统计
			if (countinstance==null) {
				countinstance=(long)0;
			}
			coveragePercentModel.setId(ClientUtil.getUUID32());
			coveragePercentModel.setBaseType("items_count_instance");
			coveragePercentModel.setIntersectionCount(String.valueOf(countinstance));
			coveragePercentMapper.save(coveragePercentModel);
			//保存组件的指标数统计
			coveragePercentModel.setId(ClientUtil.getUUID32());
			coveragePercentModel.setBaseType("items_count_unit");
			coveragePercentModel.setIntersectionCount(String.valueOf(count-countinstance));
			coveragePercentMapper.save(coveragePercentModel);
			
			//第二部分
			//按开发，测试，生产统计，并保存
			String[] list ="dev,pro,test,other".split(",");
			for (int i = 0; i < list.length; i++) {
				Long countzone=ipItemsCountMapper.getItemsAmountByHostIPZone(list[i]);
				//增加排除组件的虚机指标数统计
				Long countzoneinstance=ipItemsCountMapper.getItemsAmountByInstancetIPZone(list[i]);
				if (countzone==null) {
					countzone=(long)0;
				}
				//保存监控覆盖度到数据库
				CoveragePercentModel coveragePercentModelzone = new CoveragePercentModel();
				coveragePercentModelzone.setId(ClientUtil.getUUID32());
				coveragePercentModelzone.setCompareType("zabbix");
				coveragePercentModelzone.setCompareCount("");
				coveragePercentModelzone.setBaseType("items_count_host_"+list[i]);
				coveragePercentModelzone.setBaseCount("");
				coveragePercentModelzone.setIntersectionCount(String.valueOf(countzone));
				coveragePercentModelzone.setPercent("");
				coveragePercentModelzone.setCreateDate(DateUtil.getCurrentZonedDateTime());
				coveragePercentMapper.save(coveragePercentModelzone);
				//保存排除组件的虚机指标数统计
				if (countzoneinstance==null) {
					countzoneinstance=(long)0;
				}
				coveragePercentModel.setId(ClientUtil.getUUID32());
				coveragePercentModel.setBaseType("items_count_instance_"+list[i]);
				coveragePercentModel.setIntersectionCount(String.valueOf(countzoneinstance));
				coveragePercentMapper.save(coveragePercentModel);
				//保存组件的指标数统计
				coveragePercentModel.setId(ClientUtil.getUUID32());
				coveragePercentModel.setBaseType("items_count_unit_"+list[i]);
				coveragePercentModel.setIntersectionCount(String.valueOf(countzone-countzoneinstance));
				coveragePercentMapper.save(coveragePercentModel);
			}
			result = true;
		} catch (Exception e) {
			logger.error("Exception:" + e);
			e.printStackTrace();
		}
		return result;
		
	}
	@Override
	public boolean calAndSaveItemsAmountByRDBIP() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				boolean result = false;
				try {
					Long count=ipItemsCountMapper.getItemsAmountByRdbIP();
					if (count==null) {
						count=(long)0;
					}
					//保存监控覆盖度到数据库
					CoveragePercentModel coveragePercentModel = new CoveragePercentModel();
					coveragePercentModel.setId(ClientUtil.getUUID32());
					coveragePercentModel.setCompareType("zabbix");
					coveragePercentModel.setCompareCount("");
					coveragePercentModel.setBaseType("items_count_rdb");
					coveragePercentModel.setBaseCount("");
					coveragePercentModel.setIntersectionCount(String.valueOf(count));
					coveragePercentModel.setPercent("");
					coveragePercentModel.setCreateDate(DateUtil.getCurrentZonedDateTime());
					coveragePercentMapper.save(coveragePercentModel);
					String[] list ="dev,pro,test,other".split(",");
					for (int i = 0; i < list.length; i++) {
						Long countzone=ipItemsCountMapper.getItemsAmountByRdbIPZone(list[i]);
						if (countzone==null) {
							countzone=(long)0;
						}
						//保存监控覆盖度到数据库
						CoveragePercentModel coveragePercentModelzone = new CoveragePercentModel();
						coveragePercentModelzone.setId(ClientUtil.getUUID32());
						coveragePercentModelzone.setCompareType("zabbix");
						coveragePercentModelzone.setCompareCount("");
						coveragePercentModelzone.setBaseType("items_count_rdb_"+list[i]);
						coveragePercentModelzone.setBaseCount("");
						coveragePercentModelzone.setIntersectionCount(String.valueOf(countzone));
						coveragePercentModelzone.setPercent("");
						coveragePercentModelzone.setCreateDate(DateUtil.getCurrentZonedDateTime());
						coveragePercentMapper.save(coveragePercentModelzone);
					}
					result = true;
				} catch (Exception e) {
					logger.error("Exception:" + e);
					e.printStackTrace();
				}
				return result;
	}
	/**
	 * 计算Instance监控覆盖度并入库
	 */
	public boolean calAndSaveInstanceCoveragePercent() {
		boolean result = false;
		String percent = "";
		try {
			//查询cmdb的ip列表集合
			List<String> cmdbIpList = hostMapper.getAllIpInstance();
			//从zabbix查询ip列表集合
			String sql = "select distinct ip from interface where type = '1' and ip is not null and ip <> ''";
			JSONArray array = jdbcUtil.getJSONArray(sql);
			//得到zabbix的ip列表集合
			List<String> zabbixIpList = new ArrayList<String>();
			for(int i=0;i<array.size();i++) {
				JSONObject object = array.getJSONObject(i);
				zabbixIpList.add(object.getString("ip"));
			}
			int zabbixCount = zabbixIpList.size();//zabbix数量
			// 1.总体的覆盖度
			List<String> tempIpList = new ArrayList<String>();
			//循环zabbix的ip列表集合，得到两个集合的交集
			for(int i=0;i<zabbixIpList.size();i++) {
				String tempStr = zabbixIpList.get(i);
				if(cmdbIpList.contains(tempStr)) {
					tempIpList.add(tempStr);
				}
			}
			int tempCount = tempIpList.size();//交集数量-分子
			int cmdbCount = cmdbIpList.size();//cmdb数量-分母
			if(zabbixCount!=0){
				if(cmdbCount != 0) {
					percent = new DecimalFormat("0.00").format(((double) tempCount/(double) cmdbCount)*100);
				}else {
					percent = "0.00";
				}
			}else{
				percent = "0.00";
			}
			//保存监控覆盖度到数据库
			CoveragePercentModel coveragePercentModel = new CoveragePercentModel();
			coveragePercentModel.setId(ClientUtil.getUUID32());
			coveragePercentModel.setCompareType("zabbix");
			coveragePercentModel.setCompareCount(String.valueOf(zabbixCount));
			coveragePercentModel.setBaseType("cmdb_instance");
			coveragePercentModel.setBaseCount(String.valueOf(cmdbCount));
			coveragePercentModel.setIntersectionCount(String.valueOf(tempCount));
			coveragePercentModel.setPercent(percent);
			coveragePercentModel.setCreateDate(DateUtil.getCurrentZonedDateTime());
			coveragePercentMapper.save(coveragePercentModel);
			// 2.test类型
			String zone_test = "('test')";
			List<String> ipListTest = new ArrayList<String>();
			ipListTest = coveragePercentMapper.getInstanceIpListByZone(zone_test);
			// test类型的交集
			List<String> tempListTest = new ArrayList<String>();
			for(int i=0;i<zabbixIpList.size();i++) {
				String tempStr = zabbixIpList.get(i);
				if(ipListTest.contains(tempStr)) {
					tempListTest.add(tempStr);
				}
			}
			String percentTest = "";
			int tempCountTest = tempListTest.size();//test类型的交集数量-分子
			int cmdbCountTest = ipListTest.size();//test类型的cmdb数量-分母
			if(zabbixCount!=0){
				if(cmdbCountTest != 0) {
					percentTest = new DecimalFormat("0.00").format(((double) tempCountTest/(double) cmdbCountTest)*100);
				}else {
					percentTest = "0.00";
				}
			}else{
				percentTest = "0.00";
			}
			// 保存test类型监控覆盖度到数据库
			CoveragePercentModel coveragePercentModelTest = new CoveragePercentModel();
			coveragePercentModelTest.setId(ClientUtil.getUUID32());
			coveragePercentModelTest.setCompareType("zabbix");
			coveragePercentModelTest.setCompareCount(String.valueOf(zabbixCount));
			coveragePercentModelTest.setBaseType("cmdb_instance_test");
			coveragePercentModelTest.setBaseCount(String.valueOf(cmdbCountTest));
			coveragePercentModelTest.setIntersectionCount(String.valueOf(tempCountTest));
			coveragePercentModelTest.setPercent(percentTest);
			coveragePercentModelTest.setCreateDate(DateUtil.getCurrentZonedDateTime());
			coveragePercentMapper.save(coveragePercentModelTest);
			// 3.dev类型
			String zone_dev = "('test2')";
			List<String> ipListDev = new ArrayList<String>();
			ipListDev = coveragePercentMapper.getInstanceIpListByZone(zone_dev);
			// dev类型的交集
			List<String> tempListDev = new ArrayList<String>();
			for(int i=0;i<zabbixIpList.size();i++) {
				String tempStr = zabbixIpList.get(i);
				if(ipListDev.contains(tempStr)) {
					tempListDev.add(tempStr);
				}
			}
			String percentDev = "";
			int tempCountDev = tempListDev.size();//dev类型的交集数量-分子
			int cmdbCountDev = ipListDev.size();//dev类型的cmdb数量-分母
			if(zabbixCount!=0){
				if(cmdbCountDev != 0) {
					percentDev = new DecimalFormat("0.00").format(((double) tempCountDev/(double) cmdbCountDev)*100);
				}else {
					percentDev = "0.00";
				}
			}else{
				percentDev = "0.00";
			}
			// 保存dev类型监控覆盖度到数据库
			CoveragePercentModel coveragePercentModelDev = new CoveragePercentModel();
			coveragePercentModelDev.setId(ClientUtil.getUUID32());
			coveragePercentModelDev.setCompareType("zabbix");
			coveragePercentModelDev.setCompareCount(String.valueOf(zabbixCount));
			coveragePercentModelDev.setBaseType("cmdb_instance_dev");
			coveragePercentModelDev.setBaseCount(String.valueOf(cmdbCountDev));
			coveragePercentModelDev.setIntersectionCount(String.valueOf(tempCountDev));
			coveragePercentModelDev.setPercent(percentDev);
			coveragePercentModelDev.setCreateDate(DateUtil.getCurrentZonedDateTime());
			coveragePercentMapper.save(coveragePercentModelDev);
			// 4.pro类型
			String zone_pro = "('prod2','tkwh')";
			List<String> ipListPro = new ArrayList<String>();
			ipListPro = coveragePercentMapper.getInstanceIpListByZone(zone_pro);
			// pro类型的交集
			List<String> tempListPro = new ArrayList<String>();
			for(int i=0;i<zabbixIpList.size();i++) {
				String tempStr = zabbixIpList.get(i);
				if(ipListPro.contains(tempStr)) {
					tempListPro.add(tempStr);
				}
			}
			String percentPro = "";
			int tempCountPro = tempListPro.size();//pro类型的交集数量-分子
			int cmdbCountPro = ipListPro.size();//pro类型的cmdb数量-分母
			if(zabbixCount!=0){
				if(cmdbCountPro != 0) {
					percentPro = new DecimalFormat("0.00").format(((double) tempCountPro/(double) cmdbCountPro)*100);
				}else {
					percentPro = "0.00";
				}
			}else{
				percentPro = "0.00";
			}
			// 保存pro类型监控覆盖度到数据库
			CoveragePercentModel coveragePercentModelPro = new CoveragePercentModel();
			coveragePercentModelPro.setId(ClientUtil.getUUID32());
			coveragePercentModelPro.setCompareType("zabbix");
			coveragePercentModelPro.setCompareCount(String.valueOf(zabbixCount));
			coveragePercentModelPro.setBaseType("cmdb_instance_pro");
			coveragePercentModelPro.setBaseCount(String.valueOf(cmdbCountPro));
			coveragePercentModelPro.setIntersectionCount(String.valueOf(tempCountPro));
			coveragePercentModelPro.setPercent(percentPro);
			coveragePercentModelPro.setCreateDate(DateUtil.getCurrentZonedDateTime());
			coveragePercentMapper.save(coveragePercentModelPro);
			result = true;
		} catch (Exception e) {
			logger.error("Exception:" + e);
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 计算unit监控覆盖度并入库
	 */
	public boolean calAndSaveUnitCoveragePercent() {
		boolean result = false;
		String percent = "";
		try {
			//查询cmdb的ip列表集合
			List<String> cmdbIpList = hostMapper.getAllIpUnit();
			//从zabbix查询ip列表集合
			String sql = "select distinct ip from interface where type = '1' and ip is not null and ip <> ''";
			JSONArray array = jdbcUtil.getJSONArray(sql);
			//得到zabbix的ip列表集合
			List<String> zabbixIpList = new ArrayList<String>();
			for(int i=0;i<array.size();i++) {
				JSONObject object = array.getJSONObject(i);
				zabbixIpList.add(object.getString("ip"));
			}
			int zabbixCount = zabbixIpList.size();//zabbix数量
			// 1.总体的覆盖度
			List<String> tempIpList = new ArrayList<String>();
			//循环zabbix的ip列表集合，得到两个集合的交集
			for(int i=0;i<zabbixIpList.size();i++) {
				String tempStr = zabbixIpList.get(i);
				if(cmdbIpList.contains(tempStr)) {
					tempIpList.add(tempStr);
				}
			}
			int tempCount = tempIpList.size();//交集数量-分子
			int cmdbCount = cmdbIpList.size();//cmdb数量-分母
			if(zabbixCount!=0){
				if(cmdbCount != 0) {
					percent = new DecimalFormat("0.00").format(((double) tempCount/(double) cmdbCount)*100);
				}else {
					percent = "0.00";
				}
			}else{
				percent = "0.00";
			}
			//保存监控覆盖度到数据库
			CoveragePercentModel coveragePercentModel = new CoveragePercentModel();
			coveragePercentModel.setId(ClientUtil.getUUID32());
			coveragePercentModel.setCompareType("zabbix");
			coveragePercentModel.setCompareCount(String.valueOf(zabbixCount));
			coveragePercentModel.setBaseType("cmdb_unit");
			coveragePercentModel.setBaseCount(String.valueOf(cmdbCount));
			coveragePercentModel.setIntersectionCount(String.valueOf(tempCount));
			coveragePercentModel.setPercent(percent);
			coveragePercentModel.setCreateDate(DateUtil.getCurrentZonedDateTime());
			coveragePercentMapper.save(coveragePercentModel);
			// 2.test类型
			String zone_test = "('test')";
			List<String> ipListTest = new ArrayList<String>();
			ipListTest = coveragePercentMapper.getUnitIpListByZone(zone_test);
			// test类型的交集
			List<String> tempListTest = new ArrayList<String>();
			for(int i=0;i<zabbixIpList.size();i++) {
				String tempStr = zabbixIpList.get(i);
				if(ipListTest.contains(tempStr)) {
					tempListTest.add(tempStr);
				}
			}
			String percentTest = "";
			int tempCountTest = tempListTest.size();//test类型的交集数量-分子
			int cmdbCountTest = ipListTest.size();//test类型的cmdb数量-分母
			if(zabbixCount!=0){
				if(cmdbCountTest != 0) {
					percentTest = new DecimalFormat("0.00").format(((double) tempCountTest/(double) cmdbCountTest)*100);
				}else {
					percentTest = "0.00";
				}
			}else{
				percentTest = "0.00";
			}
			// 保存test类型监控覆盖度到数据库
			CoveragePercentModel coveragePercentModelTest = new CoveragePercentModel();
			coveragePercentModelTest.setId(ClientUtil.getUUID32());
			coveragePercentModelTest.setCompareType("zabbix");
			coveragePercentModelTest.setCompareCount(String.valueOf(zabbixCount));
			coveragePercentModelTest.setBaseType("cmdb_unit_test");
			coveragePercentModelTest.setBaseCount(String.valueOf(cmdbCountTest));
			coveragePercentModelTest.setIntersectionCount(String.valueOf(tempCountTest));
			coveragePercentModelTest.setPercent(percentTest);
			coveragePercentModelTest.setCreateDate(DateUtil.getCurrentZonedDateTime());
			coveragePercentMapper.save(coveragePercentModelTest);
			// 3.dev类型
			String zone_dev = "('test2')";
			List<String> ipListDev = new ArrayList<String>();
			ipListDev = coveragePercentMapper.getUnitIpListByZone(zone_dev);
			// dev类型的交集
			List<String> tempListDev = new ArrayList<String>();
			for(int i=0;i<zabbixIpList.size();i++) {
				String tempStr = zabbixIpList.get(i);
				if(ipListDev.contains(tempStr)) {
					tempListDev.add(tempStr);
				}
			}
			String percentDev = "";
			int tempCountDev = tempListDev.size();//dev类型的交集数量-分子
			int cmdbCountDev = ipListDev.size();//dev类型的cmdb数量-分母
			if(zabbixCount!=0){
				if(cmdbCountDev != 0) {
					percentDev = new DecimalFormat("0.00").format(((double) tempCountDev/(double) cmdbCountDev)*100);
				}else {
					percentDev = "0.00";
				}
			}else{
				percentDev = "0.00";
			}
			// 保存dev类型监控覆盖度到数据库
			CoveragePercentModel coveragePercentModelDev = new CoveragePercentModel();
			coveragePercentModelDev.setId(ClientUtil.getUUID32());
			coveragePercentModelDev.setCompareType("zabbix");
			coveragePercentModelDev.setCompareCount(String.valueOf(zabbixCount));
			coveragePercentModelDev.setBaseType("cmdb_unit_dev");
			coveragePercentModelDev.setBaseCount(String.valueOf(cmdbCountDev));
			coveragePercentModelDev.setIntersectionCount(String.valueOf(tempCountDev));
			coveragePercentModelDev.setPercent(percentDev);
			coveragePercentModelDev.setCreateDate(DateUtil.getCurrentZonedDateTime());
			coveragePercentMapper.save(coveragePercentModelDev);
			// 4.pro类型
			String zone_pro = "('prod2','tkwh')";
			List<String> ipListPro = new ArrayList<String>();
			ipListPro = coveragePercentMapper.getUnitIpListByZone(zone_pro);
			// pro类型的交集
			List<String> tempListPro = new ArrayList<String>();
			for(int i=0;i<zabbixIpList.size();i++) {
				String tempStr = zabbixIpList.get(i);
				if(ipListPro.contains(tempStr)) {
					tempListPro.add(tempStr);
				}
			}
			String percentPro = "";
			int tempCountPro = tempListPro.size();//pro类型的交集数量-分子
			int cmdbCountPro = ipListPro.size();//pro类型的cmdb数量-分母
			if(zabbixCount!=0){
				if(cmdbCountPro != 0) {
					percentPro = new DecimalFormat("0.00").format(((double) tempCountPro/(double) cmdbCountPro)*100);
				}else {
					percentPro = "0.00";
				}
			}else{
				percentPro = "0.00";
			}
			// 保存pro类型监控覆盖度到数据库
			CoveragePercentModel coveragePercentModelPro = new CoveragePercentModel();
			coveragePercentModelPro.setId(ClientUtil.getUUID32());
			coveragePercentModelPro.setCompareType("zabbix");
			coveragePercentModelPro.setCompareCount(String.valueOf(zabbixCount));
			coveragePercentModelPro.setBaseType("cmdb_unit_pro");
			coveragePercentModelPro.setBaseCount(String.valueOf(cmdbCountPro));
			coveragePercentModelPro.setIntersectionCount(String.valueOf(tempCountPro));
			coveragePercentModelPro.setPercent(percentPro);
			coveragePercentModelPro.setCreateDate(DateUtil.getCurrentZonedDateTime());
			coveragePercentMapper.save(coveragePercentModelPro);
			result = true;
		} catch (Exception e) {
			logger.error("Exception:" + e);
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * instance覆盖度
	 */
	public String getCoverageInstance() {
		JSONObject resObj = new JSONObject();
		try {
			//监控覆盖度覆盖率
			JSONObject object = new JSONObject();
			List<Map<String,String>> listPercent = coveragePercentMapper.selectCoverageInstance();
			JSONObject objectPercent = new JSONObject();
			Long count = ipItemsCountMapper.getItemsAmountByInstancetIP();
			if(count == null) {
				count = (long) 0;
			}
			objectPercent.put("count", String.valueOf(count));
			objectPercent.put("cmdb_instance", "0.00");
			objectPercent.put("cmdb_instance_pro", "0.00");
			objectPercent.put("cmdb_instance_test", "0.00");
			objectPercent.put("cmdb_instance_dev", "0.00");
			if(!listPercent.isEmpty() && listPercent.size() > 0) {
				for(int i=0;i<listPercent.size();i++) {
					Map<String,String> map = listPercent.get(i);
					String base_type = map.get("base_type");
					String percent = map.get("percent");
					if("cmdb_instance".equals(base_type)) {
						objectPercent.put("cmdb_instance", percent);
					}else if("cmdb_instance_pro".equals(base_type)) {
						objectPercent.put("cmdb_instance_pro", percent);
					}else if("cmdb_instance_test".equals(base_type)) {
						objectPercent.put("cmdb_instance_test", percent);
					}else if("cmdb_instance_dev".equals(base_type)) {
						objectPercent.put("cmdb_instance_dev", percent);
					}
				}
			}
			object.put("percent", objectPercent);
			//监控覆盖度数量
			List<Map<String,String>> listAmountUsedTest = coveragePercentMapper.getCoverageInstanceCountUsed("('test')");
			List<Map<String,String>> listAmountAllTest = coveragePercentMapper.getCoverageInstanceCountAll("('test')");
			List<Map<String,String>> listAmountUsedDev = coveragePercentMapper.getCoverageInstanceCountUsed("('test2')");
			List<Map<String,String>> listAmountAllDev = coveragePercentMapper.getCoverageInstanceCountAll("('test2')");
			List<Map<String,String>> listAmountUsedPro = coveragePercentMapper.getCoverageInstanceCountUsed("('prod2','tkwh')");
			List<Map<String,String>> listAmountAllPro = coveragePercentMapper.getCoverageInstanceCountAll("('prod2','tkwh')");
			//test
			JSONObject obj_test_linux = new JSONObject();
			JSONObject obj_test_windows = new JSONObject();
			JSONObject obj_test_unknown = new JSONObject();
			obj_test_linux.put("operation_os", "linux");
			obj_test_windows.put("operation_os", "windows");
			obj_test_unknown.put("operation_os", "unknown");
			
			obj_test_linux.put("used_operation_os", 0);
			obj_test_windows.put("used_operation_os", 0);
			obj_test_unknown.put("used_operation_os", 0);
			
			obj_test_linux.put("all_operation_os", 0);
			obj_test_windows.put("all_operation_os", 0);
			obj_test_unknown.put("all_operation_os", 0);
			
			if(!listAmountUsedTest.isEmpty()&&listAmountUsedTest.size()>0) {
				for(int i=0;i<listAmountUsedTest.size();i++) {
					Map<String,String> map = listAmountUsedTest.get(i);
					if(map.containsValue("linux")) {
						obj_test_linux.put("used_operation_os", map.get("count"));
					}
					if(map.containsValue("windows")) {
						obj_test_windows.put("used_operation_os", map.get("count"));
					}
					if(map.containsValue("unknown")) {
						obj_test_unknown.put("used_operation_os", map.get("count"));
						 continue;
					}
				}
			}
			if(!listAmountAllTest.isEmpty()&&listAmountAllTest.size()>0) {
				for(int i=0;i<listAmountAllTest.size();i++) {
					Map<String,String> map = listAmountAllTest.get(i);
					if(map.containsValue("linux")) {
						obj_test_linux.put("all_operation_os", map.get("count"));
					}
					if(map.containsValue("windows")) {
						obj_test_windows.put("all_operation_os", map.get("count"));
					}
					if(map.containsValue("unknown")) {
						obj_test_unknown.put("all_operation_os", map.get("count"));
					}
				}
			}
			//dev
			JSONObject obj_dev_linux = new JSONObject();
			JSONObject obj_dev_windows = new JSONObject();
			JSONObject obj_dev_unknown = new JSONObject();
			obj_dev_linux.put("operation_os", "linux");
			obj_dev_windows.put("operation_os", "windows");
			obj_dev_unknown.put("operation_os", "unknown");
			
			obj_dev_linux.put("used_operation_os", 0);
			obj_dev_windows.put("used_operation_os", 0);
			obj_dev_unknown.put("used_operation_os", 0);
			
			obj_dev_linux.put("all_operation_os", 0);
			obj_dev_windows.put("all_operation_os", 0);
			obj_dev_unknown.put("all_operation_os", 0);
			if(!listAmountUsedDev.isEmpty()&&listAmountUsedDev.size()>0) {
				for(int i=0;i<listAmountUsedDev.size();i++) {
					Map<String,String> map = listAmountUsedDev.get(i);
					if(map.containsValue("linux")) {
						obj_dev_linux.put("used_operation_os", map.get("count"));
					}
					if(map.containsValue("windows")) {
						obj_dev_windows.put("used_operation_os", map.get("count"));
					}
					if(map.containsValue("unknown")) {
						obj_dev_unknown.put("used_operation_os", map.get("count"));
					}
				}
			}
			if(!listAmountAllDev.isEmpty()&&listAmountAllDev.size()>0) {
				for(int i=0;i<listAmountAllDev.size();i++) {
					Map<String,String> map = listAmountAllDev.get(i);
					if(map.containsValue("linux")) {
						obj_dev_linux.put("all_operation_os", map.get("count"));
					}
					if(map.containsValue("windows")) {
						obj_dev_windows.put("all_operation_os", map.get("count"));
					}
					if(map.containsValue("unknown")) {
						obj_dev_unknown.put("all_operation_os", map.get("count"));
					}
				}
			}
			//pro
			JSONObject obj_pro_linux = new JSONObject();
			JSONObject obj_pro_windows = new JSONObject();
			JSONObject obj_pro_unknown = new JSONObject();
			obj_pro_linux.put("operation_os", "linux");
			obj_pro_windows.put("operation_os", "windows");
			obj_pro_unknown.put("operation_os", "unknown");
			
			obj_pro_linux.put("used_operation_os", 0);
			obj_pro_windows.put("used_operation_os", 0);
			obj_pro_unknown.put("used_operation_os", 0);
			
			obj_pro_linux.put("all_operation_os", 0);
			obj_pro_windows.put("all_operation_os", 0);
			obj_pro_unknown.put("all_operation_os", 0);
			if(!listAmountUsedPro.isEmpty()&&listAmountUsedPro.size()>0) {
				for(int i=0;i<listAmountUsedPro.size();i++) {
					Map<String,String> map = listAmountUsedPro.get(i);
					if(map.containsValue("linux")) {
						obj_pro_linux.put("used_operation_os", map.get("count"));
					}
					if(map.containsValue("windows")) {
						obj_pro_windows.put("used_operation_os", map.get("count"));
					}
					if(map.containsValue("unknown")) {
						obj_pro_unknown.put("used_operation_os", map.get("count"));
					}
				}
			}
			if(!listAmountAllPro.isEmpty()&&listAmountAllPro.size()>0) {
				for(int i=0;i<listAmountAllPro.size();i++) {
					Map<String,String> map = listAmountAllPro.get(i);
					if(map.containsValue("linux")) {
						obj_pro_linux.put("all_operation_os", map.get("count"));
					}
					if(map.containsValue("windows")) {
						obj_pro_windows.put("all_operation_os", map.get("count"));
					}
					if(map.containsValue("unknown")) {
						obj_pro_unknown.put("all_operation_os", map.get("count"));
					}
				}
			}
			JSONArray array_test = new JSONArray();
			array_test.add(obj_test_linux);
			array_test.add(obj_test_windows);
			array_test.add(obj_test_unknown);
			
			JSONArray array_dev = new JSONArray();
			array_dev.add(obj_dev_linux);
			array_dev.add(obj_dev_windows);
			array_dev.add(obj_dev_unknown);
			
			JSONArray array_pro = new JSONArray();
			array_pro.add(obj_pro_linux);
			array_pro.add(obj_pro_windows);
			array_pro.add(obj_pro_unknown);
			
			JSONObject obj = new JSONObject();
			obj.put("test", array_test);
			obj.put("dev", array_dev);
			obj.put("pro", array_pro);
			object.put("count", obj);
			
			resObj.put("ret_code", 0);
			resObj.put("result", object);
		} catch (Exception e) {
			resObj.put("ret_code", 1200);
			resObj.put("message", "程序异常");
			logger.error("Exception:" + e);
			e.printStackTrace();
		}
		return resObj.toString();
	}
	/**
	 * unit覆盖度
	 */
	public String getCoverageUnit() {
		JSONObject resObj = new JSONObject();
		try {
			//监控覆盖度覆盖率
			JSONObject object = new JSONObject();
			List<Map<String,String>> listPercent = coveragePercentMapper.selectCoverageUnit();
			JSONObject objectPercent = new JSONObject();
			Long count_host = ipItemsCountMapper.getItemsAmountByHostIP();
			Long count_instance = ipItemsCountMapper.getItemsAmountByInstancetIP();
			if(count_host == null) {
				count_host = (long) 0;
			}
			if(count_instance == null) {
				count_instance = (long) 0;
			}
			Long count = count_host - count_instance;
			
			objectPercent.put("count", String.valueOf(count));
			objectPercent.put("cmdb_unit", "0.00");
			objectPercent.put("cmdb_unit_pro", "0.00");
			objectPercent.put("cmdb_unit_test", "0.00");
			objectPercent.put("cmdb_unit_dev", "0.00");
			if(!listPercent.isEmpty() && listPercent.size() > 0) {
				for(int i=0;i<listPercent.size();i++) {
					Map<String,String> map = listPercent.get(i);
					String base_type = map.get("base_type");
					String percent = map.get("percent");
					if("cmdb_unit".equals(base_type)) {
						objectPercent.put("cmdb_unit", percent);
					}else if("cmdb_unit_pro".equals(base_type)) {
						objectPercent.put("cmdb_unit_pro", percent);
					}else if("cmdb_unit_test".equals(base_type)) {
						objectPercent.put("cmdb_unit_test", percent);
					}else if("cmdb_unit_dev".equals(base_type)) {
						objectPercent.put("cmdb_unit_dev", percent);
					}
				}
			}
			object.put("percent", objectPercent);
			//监控覆盖度数量
			List<Map<String,String>> listAmountUsedTest = coveragePercentMapper.getCoverageUnitCountUsed("('test')");
			List<Map<String,String>> listAmountAllTest = coveragePercentMapper.getCoverageUnitCountAll("('test')");
			List<Map<String,String>> listAmountUsedDev = coveragePercentMapper.getCoverageUnitCountUsed("('test2')");
			List<Map<String,String>> listAmountAllDev = coveragePercentMapper.getCoverageUnitCountAll("('test2')");
			List<Map<String,String>> listAmountUsedPro = coveragePercentMapper.getCoverageUnitCountUsed("('prod2','tkwh')");
			List<Map<String,String>> listAmountAllPro = coveragePercentMapper.getCoverageUnitCountAll("('prod2','tkwh')");
			//test
			JSONObject obj_test_linux = new JSONObject();
			JSONObject obj_test_windows = new JSONObject();
			JSONObject obj_test_unknown = new JSONObject();
			obj_test_linux.put("operation_os", "linux");
			obj_test_windows.put("operation_os", "windows");
			obj_test_unknown.put("operation_os", "unknown");
			
			obj_test_linux.put("used_operation_os", 0);
			obj_test_windows.put("used_operation_os", 0);
			obj_test_unknown.put("used_operation_os", 0);
			
			obj_test_linux.put("all_operation_os", 0);
			obj_test_windows.put("all_operation_os", 0);
			obj_test_unknown.put("all_operation_os", 0);
			
			if(!listAmountUsedTest.isEmpty()&&listAmountUsedTest.size()>0) {
				for(int i=0;i<listAmountUsedTest.size();i++) {
					Map<String,String> map = listAmountUsedTest.get(i);
					if(map.containsValue("linux")) {
						obj_test_linux.put("used_operation_os", map.get("count"));
					}
					if(map.containsValue("windows")) {
						obj_test_windows.put("used_operation_os", map.get("count"));
					}
					if(map.containsValue("unknown")) {
						obj_test_unknown.put("used_operation_os", map.get("count"));
						 continue;
					}
				}
			}
			if(!listAmountAllTest.isEmpty()&&listAmountAllTest.size()>0) {
				for(int i=0;i<listAmountAllTest.size();i++) {
					Map<String,String> map = listAmountAllTest.get(i);
					if(map.containsValue("linux")) {
						obj_test_linux.put("all_operation_os", map.get("count"));
					}
					if(map.containsValue("windows")) {
						obj_test_windows.put("all_operation_os", map.get("count"));
					}
					if(map.containsValue("unknown")) {
						obj_test_unknown.put("all_operation_os", map.get("count"));
					}
				}
			}
			//dev
			JSONObject obj_dev_linux = new JSONObject();
			JSONObject obj_dev_windows = new JSONObject();
			JSONObject obj_dev_unknown = new JSONObject();
			obj_dev_linux.put("operation_os", "linux");
			obj_dev_windows.put("operation_os", "windows");
			obj_dev_unknown.put("operation_os", "unknown");
			
			obj_dev_linux.put("used_operation_os", 0);
			obj_dev_windows.put("used_operation_os", 0);
			obj_dev_unknown.put("used_operation_os", 0);
			
			obj_dev_linux.put("all_operation_os", 0);
			obj_dev_windows.put("all_operation_os", 0);
			obj_dev_unknown.put("all_operation_os", 0);
			if(!listAmountUsedDev.isEmpty()&&listAmountUsedDev.size()>0) {
				for(int i=0;i<listAmountUsedDev.size();i++) {
					Map<String,String> map = listAmountUsedDev.get(i);
					if(map.containsValue("linux")) {
						obj_dev_linux.put("used_operation_os", map.get("count"));
					}
					if(map.containsValue("windows")) {
						obj_dev_windows.put("used_operation_os", map.get("count"));
					}
					if(map.containsValue("unknown")) {
						obj_dev_unknown.put("used_operation_os", map.get("count"));
					}
				}
			}
			if(!listAmountAllDev.isEmpty()&&listAmountAllDev.size()>0) {
				for(int i=0;i<listAmountAllDev.size();i++) {
					Map<String,String> map = listAmountAllDev.get(i);
					if(map.containsValue("linux")) {
						obj_dev_linux.put("all_operation_os", map.get("count"));
					}
					if(map.containsValue("windows")) {
						obj_dev_windows.put("all_operation_os", map.get("count"));
					}
					if(map.containsValue("unknown")) {
						obj_dev_unknown.put("all_operation_os", map.get("count"));
					}
				}
			}
			//pro
			JSONObject obj_pro_linux = new JSONObject();
			JSONObject obj_pro_windows = new JSONObject();
			JSONObject obj_pro_unknown = new JSONObject();
			obj_pro_linux.put("operation_os", "linux");
			obj_pro_windows.put("operation_os", "windows");
			obj_pro_unknown.put("operation_os", "unknown");
			
			obj_pro_linux.put("used_operation_os", 0);
			obj_pro_windows.put("used_operation_os", 0);
			obj_pro_unknown.put("used_operation_os", 0);
			
			obj_pro_linux.put("all_operation_os", 0);
			obj_pro_windows.put("all_operation_os", 0);
			obj_pro_unknown.put("all_operation_os", 0);
			if(!listAmountUsedPro.isEmpty()&&listAmountUsedPro.size()>0) {
				for(int i=0;i<listAmountUsedPro.size();i++) {
					Map<String,String> map = listAmountUsedPro.get(i);
					if(map.containsValue("linux")) {
						obj_pro_linux.put("used_operation_os", map.get("count"));
					}
					if(map.containsValue("windows")) {
						obj_pro_windows.put("used_operation_os", map.get("count"));
					}
					if(map.containsValue("unknown")) {
						obj_pro_unknown.put("used_operation_os", map.get("count"));
					}
				}
			}
			if(!listAmountAllPro.isEmpty()&&listAmountAllPro.size()>0) {
				for(int i=0;i<listAmountAllPro.size();i++) {
					Map<String,String> map = listAmountAllPro.get(i);
					if(map.containsValue("linux")) {
						obj_pro_linux.put("all_operation_os", map.get("count"));
					}
					if(map.containsValue("windows")) {
						obj_pro_windows.put("all_operation_os", map.get("count"));
					}
					if(map.containsValue("unknown")) {
						obj_pro_unknown.put("all_operation_os", map.get("count"));
					}
				}
			}
			JSONArray array_test = new JSONArray();
			array_test.add(obj_test_linux);
			array_test.add(obj_test_windows);
			array_test.add(obj_test_unknown);
			
			JSONArray array_dev = new JSONArray();
			array_dev.add(obj_dev_linux);
			array_dev.add(obj_dev_windows);
			array_dev.add(obj_dev_unknown);
			
			JSONArray array_pro = new JSONArray();
			array_pro.add(obj_pro_linux);
			array_pro.add(obj_pro_windows);
			array_pro.add(obj_pro_unknown);
			
			JSONObject obj = new JSONObject();
			obj.put("test", array_test);
			obj.put("dev", array_dev);
			obj.put("pro", array_pro);
			object.put("count", obj);
			
			resObj.put("ret_code", 0);
			resObj.put("result", object);
		} catch (Exception e) {
			resObj.put("ret_code", 1200);
			resObj.put("message", "程序异常");
			logger.error("Exception:" + e);
			e.printStackTrace();
		}
		return resObj.toString();
	}
}
