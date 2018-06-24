package com.taikang.dataVis.screen.service;

import com.taikang.dataVis.domain.Dict;
import com.taikang.dataVis.screen.web.rest.vm.DictVM;
import java.util.List;

/** 
* @ClassName: DictService 
* @Description: (DictService接口 ) 
* @author iwt_gaoliang 
* @date 2018年3月12日 下午2:05:33 
*  
*/
public interface DictService {

    /**
     * 根据dict_code查询dict字典表
     * @return  Dict对象
     */
    Dict getDictByCode(String dictCode);

    /**
     * 〈根据dictCode查询dictValue〉
     * @return  java.util.List<java.lang.Object>
     * @exception/throws [异常类型] [异常说明]
     * @since [起始版本]（可选）
     */
	List<Object> getDictValueByCode(String dictCode);

	Dict findOne(DictVM dictVM);

	List<Dict> findAll(DictVM dictVM);

	Dict save(Dict dict);

	Dict update(Dict dict);

	int deleteDicts(List<Long> ids);
}
