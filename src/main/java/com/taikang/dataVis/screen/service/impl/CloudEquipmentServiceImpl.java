package com.taikang.dataVis.screen.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taikang.dataVis.core.utils.ClientUtil;
import com.taikang.dataVis.core.utils.DateUtil;
import com.taikang.dataVis.domain.CloudEquipment;
import com.taikang.dataVis.domain.Dict;
import com.taikang.dataVis.mapper.CloudEquipmentMapper;
import com.taikang.dataVis.mapper.DictMapper;
import com.taikang.dataVis.screen.service.CloudEquipmentService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 项目名称:	[data-visualization]
 * 包:		[com.taikang.dataVis.screen.service.impl]
 * 类名称:	[CloudEquipmentServiceImpl]
 * 创建人:	[itw_guodc]
 * 创建时间:	[2018年5月28日 上午11:19:42]
 * 修改人:	[itw_guodc]
 * 修改时间:	[2018年5月28日 上午11:19:42]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */ 
@Service
public class CloudEquipmentServiceImpl implements CloudEquipmentService {

	@Autowired
	private CloudEquipmentMapper cloudEquipmentMapper;
	@Autowired
	private DictMapper dictMapper ;
	
	@Override
	public List<String> findAllDate() {
		// TODO Auto-generated method stub
		return cloudEquipmentMapper.findAllDate();
	}

	@Override
	public List<CloudEquipment> findNewestAll() {
		// TODO Auto-generated method stub
		return cloudEquipmentMapper.findNewestAll();
	}

	@Override
	public JSONArray findAllByCreateTime(String json) {
		JSONArray returarry = new JSONArray();
		// 校验是否是json格式
	    if (ClientUtil.isJson(json)) {
	    	  Map<String,Object> map = new HashMap<String,Object>() ;
	          JSONObject object = JSONObject.fromObject(json);
	          String createTime = object.get("createTime").toString();
	          map.put("createTime",createTime) ;
	          List<CloudEquipment> list = cloudEquipmentMapper.findByCreateTime(map);
	        //从字典表中拿到机房类型，分割，保存对象中，然后保存到数据库，并把list对象返回前台显示
	          Dict dict = dictMapper.getDictByCode("cloud_equipment_type") ;
	          String computerRooms = dict.getDictValue() ;
	          JSONArray jsonArray = JSONArray.fromObject(computerRooms) ;
	          for( int i =0;i<jsonArray.size();i++ ){
	              String label = jsonArray.getJSONObject(i).get("label").toString() ;
	              JSONObject objtemp=null ;
	              //进行循环比对，对不存在的处室，设置默认值
	              for (int j = 0; j < list.size(); j++) {
	            	  //如果相等，存入返回值,并跳出循环
					if (label.equals(list.get(j).getDepartment())) {
						objtemp=JSONObject.fromObject(list.get(j));
						break;
					}
				  }
	              //如果为null，存入默认值
	              if (objtemp==null) {
	            	  objtemp=new JSONObject();
	            	  objtemp.put("id", "");
	            	  objtemp.put("department", label);
	            	  objtemp.put("inVal", "");
	            	  objtemp.put("outVal", "");
	            	  objtemp.put("replaceVal", "");
	            	  objtemp.put("createTime", "");
	            	  objtemp.put("updateTime", "");
				  }
	              returarry.add(objtemp);
	          }
	    }
		// TODO Auto-generated method stub
		return returarry;
	}

	@Override
	public Map<String, String> saveBatch(String json) {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String,String>();
	    int num = 0;
	    if (ClientUtil.isJson(json)) {
	    	 JSONArray jsonArray = JSONArray.fromObject(json);
	    	 try {
	    	        String createTime = DateUtil.getCurrentDate();
	    	        List<CloudEquipment> list = new ArrayList<CloudEquipment>();
	    	        for( int i =0;i<jsonArray.size();i++ ){
	    	        	 JSONObject objtemp = jsonArray.getJSONObject(i);
	    	        	 CloudEquipment cloudEquipment = new CloudEquipment();
	    	        	 cloudEquipment.setId(ClientUtil.getUUID32());
	    	        	 cloudEquipment.setDepartment(objtemp.getString("department"));
	    	        	 cloudEquipment.setInVal(objtemp.containsKey("inVal")?objtemp.getString("inVal"):"");
	    	        	 cloudEquipment.setOutVal(objtemp.containsKey("outVal")?objtemp.getString("outVal"):"");
	    	        	 cloudEquipment.setReplaceVal(objtemp.containsKey("replaceVal")?objtemp.getString("replaceVal"):"");
	    	        	 cloudEquipment.setCreateTime(createTime);
	    	        	 list.add(cloudEquipment);
	    	        }
	    	        num = cloudEquipmentMapper.insertBatch(list);
	    	        map.put("num",String.valueOf(num)) ;
	    	        map.put("message","保存成功！") ;
	    	 } catch (Exception e) {
	    	        map.put("num",String.valueOf(num)) ;
	    	        map.put("message","保存失败") ;
	    	        e.printStackTrace();
	    	      }       
	    }else {
	    	map.put("num",String.valueOf(num)) ;
	        map.put("message","传参不正确！") ;
		}
		return map;
	}

	@Override
	public Map<String, String> updateBatch(String json) {
		// TODO Auto-generated method stub
		 Map<String,String> map = new HashMap<String,String>() ;
		 int num = 0;
		 if (ClientUtil.isJson(json)) {
			 JSONArray jsonArray = JSONArray.fromObject(json);
	    	 try {
	    	        String updateTime = DateUtil.getCurrentDate();
	    	        for( int i =0;i<jsonArray.size();i++ ){
	    	        	 JSONObject objtemp = jsonArray.getJSONObject(i);
	    	        	 CloudEquipment cloudEquipment = new CloudEquipment();
	    	        	 cloudEquipment.setId(objtemp.getString("id"));
	    	        	 cloudEquipment.setDepartment(objtemp.getString("department"));
	    	        	 cloudEquipment.setInVal(objtemp.containsKey("inVal")?objtemp.getString("inVal"):"");
	    	        	 cloudEquipment.setOutVal(objtemp.containsKey("outVal")?objtemp.getString("outVal"):"");
	    	        	 cloudEquipment.setReplaceVal(objtemp.containsKey("replaceVal")?objtemp.getString("replaceVal"):"");
	    	        	 cloudEquipment.setUpdateTime(updateTime);
	    	        	 num += cloudEquipmentMapper.update(cloudEquipment);
	    	        }
	    	        map.put("num",String.valueOf(num)) ;
	    	        map.put("message","修改成功！") ;
	    	 } catch (Exception e) {
	    	        map.put("num",String.valueOf(num)) ;
	    	        map.put("message","修改失败！") ;
	    	        e.printStackTrace();
	    	 }	
		 }else {
		    	map.put("num",String.valueOf(num)) ;
		        map.put("message","传参不正确！") ;
		 }
		return map;
	}

}
