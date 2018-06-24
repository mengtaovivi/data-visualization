package com.taikang.dataVis.mapper;

import com.taikang.dataVis.domain.TkOperationInformation;
import org.apache.ibatis.annotations.Mapper;


/** 
* @ClassName: OperationInformationMapper 
* @Description: (OperationInformation 增删改查接口) 
* @author 郭德才 
* @date 2018年4月11日 下午5:22:07 
*  
*/

@Mapper
public interface OperationInformationMapper {

	void save(TkOperationInformation tkOperationInformation);

}
