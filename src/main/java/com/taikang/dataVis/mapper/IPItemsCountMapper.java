package com.taikang.dataVis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.taikang.dataVis.domain.IPItemsCount;

/**
 * 项目名称:	[cmdb-core]
 * 包:		[com.taikang.dataVis.mapper]
 * 类名称:	[IPItemsCountMapper]
 * 创建人:	[itw_guodc]
 * 创建时间:	[2018年5月14日 下午2:22:13]
 * 修改人:	[itw_guodc]
 * 修改时间:	[2018年5月14日 下午2:22:13]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */ 
@Mapper
public interface IPItemsCountMapper {

	int insertBatch(List<IPItemsCount> listadd);

	int deleteAll();

	Long getItemsAmountByHostIP();
	
	Long getItemsAmountByRdbIP();

	Long getItemsAmountByIP(String[] ips);

	Long getAllcount();

	Long getItemsAmountByHostIPZone(String string);

	Long getItemsAmountByRdbIPZone(String string);

	Long getItemsAmountByInstancetIP();

	Long getItemsAmountByInstancetIPZone(String string);

	Long getItemsAmountBySerchWord(@Param("serch_word")String serch_word);


}
