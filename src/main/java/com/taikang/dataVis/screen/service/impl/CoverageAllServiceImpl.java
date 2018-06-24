package com.taikang.dataVis.screen.service.impl;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taikang.dataVis.mapper.HostMapper;
import com.taikang.dataVis.mapper.IPItemsCountMapper;
import com.taikang.dataVis.mapper.LoadbalancerMapper;
import com.taikang.dataVis.mapper.RDBMapper;
import com.taikang.dataVis.screen.service.CoverageAllService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 项目名称:	[data-visualization]
 * 包:		[com.taikang.dataVis.screen.service.impl]
 * 类名称:	[CoverageAllServiceImpl]
 * 创建人:	[itw_guodc]
 * 创建时间:	[2018年6月4日 下午4:42:20]
 * 修改人:	[itw_guodc]
 * 修改时间:	[2018年6月4日 下午4:42:20]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */ 
@Service
public class CoverageAllServiceImpl implements CoverageAllService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private RDBMapper rdbMapper;
	@Autowired
	private LoadbalancerMapper loadbalancerMapper;
	@Autowired
	private HostMapper hostMapper;
	@Autowired
	private IPItemsCountMapper ipItemsCountMapper;
	
	@Override
	public String getCoverageAll(String type) {
		// TODO Auto-generated method stub
		JSONObject resObj = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject nameobj= new JSONObject();
		JSONObject valueobj= new JSONObject();
		//与参数相关数据库资源被监控数量统计
		List<Map<String,Object>> listrdbcg = rdbMapper.getCoverageAllcountBytype(type);
		//与参数相关数据库资源数量统计
		List<Map<String,Object>> listrdb = rdbMapper.getAllcountBytype(type);
		
		List<Map<String,Object>> listhostcg = hostMapper.getCoverageAllcountBytype(type);
		List<Map<String,Object>> listhost = hostMapper.getAllcountBytype(type);
		List<Map<String,Object>> listlbcg = loadbalancerMapper.getCoverageAllcountBytype(type);
		List<Map<String,Object>> listlb = loadbalancerMapper.getAllcountBytype(type);
		List<String> rdb_engines  = rdbMapper.getAllRDBEngine(type);
		System.err.println(rdb_engines);
		//与参数相关所有资源合计监控指标数
		Long typeCount =ipItemsCountMapper.getItemsAmountBySerchWord(type);
		resObj.put("total_items_count", typeCount);
		//与参数相关所有资源总体覆盖度计算（v.1,v.2）
		//v.1 被监控数量总和（分子）
		int total_nt=0;
		//v.1按资源类型
		int rdb_nt=0;
		int host_nt=0;
		int lb_nt=0;
		//v.1按生产、开发、测试
		int pro_nt=0;
		int test_nt=0;
		int dev_nt=0;
		int linux_nt=0;
		int windows_nt=0;
		if (listrdbcg.size()>0) {
			for (int i = 0; i < listrdbcg.size(); i++) {
				total_nt+=Integer.parseInt(listrdbcg.get(i).get("zcount").toString());
				rdb_nt+=Integer.parseInt(listrdbcg.get(i).get("zcount").toString());
				if (listrdbcg.get(i).get("type").toString().contains("prod2") || listrdbcg.get(i).get("type").toString().contains("tkwh")) {
					pro_nt+=Integer.parseInt(listrdbcg.get(i).get("zcount").toString());
				}else if (listrdbcg.get(i).get("type").toString().contains("test") || listrdbcg.get(i).get("type").toString().contains("test")) {
					test_nt+=Integer.parseInt(listrdbcg.get(i).get("zcount").toString());
				}else if (listrdbcg.get(i).get("type").toString().contains("test2") || listrdbcg.get(i).get("type").toString().contains("test2")) {
					dev_nt+=Integer.parseInt(listrdbcg.get(i).get("zcount").toString());
				}else if (!listrdbcg.get(i).get("type").toString().contains("_") ) {
					valueobj.put("_nt", listrdbcg.get(i).get("zcount"));
				}
			}
		}
		if (listhostcg.size()>0) {
			for (int i = 0; i < listhostcg.size(); i++) {
				total_nt+=Integer.parseInt(listhostcg.get(i).get("zcount").toString());
				host_nt+=Integer.parseInt(listhostcg.get(i).get("zcount").toString());
				//按生产、测试、开发 统计
				if (listhostcg.get(i).get("type").toString().contains("prod2") || listhostcg.get(i).get("type").toString().contains("tkwh")) {
					pro_nt+=Integer.parseInt(listhostcg.get(i).get("zcount").toString());
				}else if (listhostcg.get(i).get("type").toString().contains("test") || listhostcg.get(i).get("type").toString().contains("test")) {
					test_nt+=Integer.parseInt(listhostcg.get(i).get("zcount").toString());
				}else if (listhostcg.get(i).get("type").toString().contains("test2") || listhostcg.get(i).get("type").toString().contains("test2")) {
					dev_nt+=Integer.parseInt(listhostcg.get(i).get("zcount").toString());
				}
				//按系统统计
				if (listhostcg.get(i).get("type").toString().contains("linux") || listhostcg.get(i).get("type").toString().contains("Linux")) {
					linux_nt+=Integer.parseInt(listhostcg.get(i).get("zcount").toString());
				}else if (listhostcg.get(i).get("type").toString().contains("windows") || listhostcg.get(i).get("type").toString().contains("Windows")) {
					windows_nt+=Integer.parseInt(listhostcg.get(i).get("zcount").toString());
				}
			}
		}
		if (listlbcg.size()>0) {
			for (int i = 0; i < listlbcg.size(); i++) {
				total_nt+=Integer.parseInt(listlbcg.get(i).get("zcount").toString());
				lb_nt+=Integer.parseInt(listlbcg.get(i).get("zcount").toString());
				if (listlbcg.get(i).get("type").toString().contains("prod2") || listlbcg.get(i).get("type").toString().contains("tkwh")) {
					pro_nt+=Integer.parseInt(listlbcg.get(i).get("zcount").toString());
				}else if (listlbcg.get(i).get("type").toString().contains("test") || listlbcg.get(i).get("type").toString().contains("test")) {
					test_nt+=Integer.parseInt(listlbcg.get(i).get("zcount").toString());
				}else if (listlbcg.get(i).get("type").toString().contains("test2") || listlbcg.get(i).get("type").toString().contains("test2")) {
					dev_nt+=Integer.parseInt(listlbcg.get(i).get("zcount").toString());
				}
			}
		}
		//v.2 被监控数量总和（分母）
		int total_dt=0;
		//v.2按资源类型
		int rdb_dt=0;
		int host_dt=0;
		int lb_dt=0;
		//v.2按生产、开发、测试
		int pro_dt=0;
		int test_dt=0;
		int dev_dt=0;	
		int linux_dt=0;
		int windows_dt=0;
		if (listrdb.size()>0) {
			for (int i = 0; i < listrdb.size(); i++) {
				total_dt+=Integer.parseInt(listrdb.get(i).get("zcount").toString());
				rdb_dt+=Integer.parseInt(listrdb.get(i).get("zcount").toString());
				if (listrdb.get(i).get("type").toString().contains("prod2") || listrdb.get(i).get("type").toString().contains("tkwh")) {
					pro_dt+=Integer.parseInt(listrdb.get(i).get("zcount").toString());
				}else if (listrdb.get(i).get("type").toString().contains("test")) {
					test_dt+=Integer.parseInt(listrdb.get(i).get("zcount").toString());
				}else if (listrdb.get(i).get("type").toString().contains("test2")) {
					dev_dt+=Integer.parseInt(listrdb.get(i).get("zcount").toString());
				}else if (!listrdb.get(i).get("type").toString().contains("_") ) {
					valueobj.put("_dt", listrdb.get(i).get("zcount"));
					if (valueobj.containsKey(listrdb.get(i).get("type").toString()+"_nt") && Integer.parseInt(valueobj.get("_nt").toString())>0) {
						int tmp_nt=Integer.parseInt(valueobj.get("_nt").toString());
						int tmp_dt=Integer.parseInt(valueobj.get("_dt").toString());
						valueobj.put("_coverage", new DecimalFormat("0.00").format(((double) tmp_nt/(double) tmp_dt)*100));
					}else {
						valueobj.put("_coverage", "0.00");
					}
					nameobj.put("name", listrdb.get(i).get("type").toString());
					nameobj.put("value", valueobj);
					array.add(nameobj);
				}
			}
		}
		if (listhost.size()>0) {
			for (int i = 0; i < listhost.size(); i++) {
				total_dt+=Integer.parseInt(listhost.get(i).get("zcount").toString());
				host_dt+=Integer.parseInt(listhost.get(i).get("zcount").toString());
				if (listhost.get(i).get("type").toString().contains("prod2") || listhost.get(i).get("type").toString().contains("tkwh")) {
					pro_dt+=Integer.parseInt(listhost.get(i).get("zcount").toString());
				}else if (listhost.get(i).get("type").toString().contains("test")) {
					test_dt+=Integer.parseInt(listhost.get(i).get("zcount").toString());
				}else if (listhost.get(i).get("type").toString().contains("test2")) {
					dev_dt+=Integer.parseInt(listhost.get(i).get("zcount").toString());
				}
				//按系统统计
				if (listhost.get(i).get("type").toString().contains("linux") || listhost.get(i).get("type").toString().contains("Linux")) {
					linux_dt+=Integer.parseInt(listhost.get(i).get("zcount").toString());
				}else if (listhost.get(i).get("type").toString().contains("windows") || listhost.get(i).get("type").toString().contains("Windows")) {
					windows_dt+=Integer.parseInt(listhost.get(i).get("zcount").toString());
				}
			}
		}
		if (listlb.size()>0) {
			for (int i = 0; i < listlb.size(); i++) {
				total_dt+=Integer.parseInt(listlb.get(i).get("zcount").toString());
				lb_dt+=Integer.parseInt(listlb.get(i).get("zcount").toString());
				if (listlb.get(i).get("type").toString().contains("prod2") || listlb.get(i).get("type").toString().contains("tkwh")) {
					pro_dt+=Integer.parseInt(listlb.get(i).get("zcount").toString());
				}else if (listlb.get(i).get("type").toString().contains("test") ) {
					test_dt+=Integer.parseInt(listlb.get(i).get("zcount").toString());
				}else if (listlb.get(i).get("type").toString().contains("test2") ) {
					dev_dt+=Integer.parseInt(listlb.get(i).get("zcount").toString());
				}
			}
		}
		
        //计算覆盖度
		if (total_nt>0 && total_dt>0) {
			valueobj.put("_coverage",  new DecimalFormat("0.00").format(((double) total_nt/(double) total_dt)*100));
		}else {
			valueobj.put("_coverage", "0.00");
		}
		valueobj.put("_nt", total_nt);
		valueobj.put("_dt", total_dt);
		nameobj.put("name", "total");
		nameobj.put("value", valueobj);
		array.add(nameobj);
		//rdb
		if (rdb_nt>0 && rdb_dt>0) {
			valueobj.put("_coverage",  new DecimalFormat("0.00").format(((double) rdb_nt/(double) rdb_dt)*100));
		}else {
			valueobj.put("_coverage", "0.00");
		}
		valueobj.put("_nt", rdb_nt);
		valueobj.put("_dt", rdb_dt);
		nameobj.put("name", "rdb");
		nameobj.put("value", valueobj);
		array.add(nameobj);
		//host
		if (host_nt>0 && host_dt>0) {
			valueobj.put("_coverage",  new DecimalFormat("0.00").format(((double) host_nt/(double) host_dt)*100));
		}else {
			valueobj.put("_coverage", "0.00");
		}
		valueobj.put("_nt", host_nt);
		valueobj.put("_dt", host_dt);
		nameobj.put("name", "host");
		nameobj.put("value", valueobj);
		array.add(nameobj);
		//lb
		if (lb_nt>0 && lb_dt>0) {
			valueobj.put("_coverage",  new DecimalFormat("0.00").format(((double) lb_nt/(double) lb_dt)*100));
		}else {
			valueobj.put("_coverage", "0.00");
		}	
		valueobj.put("_nt", lb_nt);
		valueobj.put("_dt", lb_dt);
		nameobj.put("name", "lb");
		nameobj.put("value", valueobj);
		array.add(nameobj);
		//pro 生产环境
		if (pro_nt>0 && pro_dt>0) {
			valueobj.put("_coverage",  new DecimalFormat("0.00").format(((double) pro_nt/(double) pro_dt)*100));
		}else {
			valueobj.put("_coverage", "0.00");
		}
		valueobj.put("_nt", pro_nt);
		valueobj.put("_dt", pro_dt);
		nameobj.put("name", "pro");
		nameobj.put("value", valueobj);
		array.add(nameobj);
		//test 测试环境
		if (test_nt>0 && test_dt>0) {
			valueobj.put("_coverage",  new DecimalFormat("0.00").format(((double) test_nt/(double) test_dt)*100));
		}else {
			valueobj.put("_coverage", "0.00");
		}
		valueobj.put("_nt", test_nt);
		valueobj.put("_dt", test_dt);
		nameobj.put("name", "test");
		nameobj.put("value", valueobj);
		array.add(nameobj);
		//dev 测试环境
		if (dev_nt>0 && dev_dt>0) {
			valueobj.put("_coverage",  new DecimalFormat("0.00").format(((double) dev_nt/(double) dev_dt)*100));
		}else {
			valueobj.put("_coverage", "0.00");
		}
		valueobj.put("_nt", dev_nt);
		valueobj.put("_dt", dev_dt);
		nameobj.put("name", "dev");
		nameobj.put("value", valueobj);
		array.add(nameobj);
		//linux 
		if (linux_nt>0 && linux_dt>0) {
			valueobj.put("_coverage",  new DecimalFormat("0.00").format(((double) linux_nt/(double) linux_dt)*100));
		}else {
			valueobj.put("_coverage", "0.00");
		}
		valueobj.put("_nt", linux_nt);
		valueobj.put("_dt", linux_dt);	
		nameobj.put("name", "linux");
		nameobj.put("value", valueobj);
		array.add(nameobj);
		//windows 
		if (windows_nt>0 && windows_dt>0) {
			valueobj.put("_coverage",  new DecimalFormat("0.00").format(((double) windows_nt/(double) windows_dt)*100));
		}else {
			valueobj.put("_coverage", "0.00");
		}
		valueobj.put("_nt", windows_nt);
		valueobj.put("_dt", windows_dt);		
		nameobj.put("name", "windows");
		nameobj.put("value", valueobj);
		array.add(nameobj);
		resObj.put("ret_code", 0);
		resObj.put("result", array);
		return resObj.toString();
	}

}
