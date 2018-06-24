package com.taikang.dataVis.mapper;

import com.taikang.dataVis.domain.Dict;
import com.taikang.dataVis.screen.web.rest.vm.DictVM;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/** 
* @ClassName: DictMapper 
* @Description: (DictMapper类，可以自定义接口 ) 
* @author iwt_gaoliang 
* @date 2018年3月12日 下午2:05:02 
*  
*/
@Mapper
public interface DictMapper {

    /**
     * 根据dict_code查询dict
     * @param dictCode 字典表的code
     * @return
     */
	Dict getDictByCode(String dictCode);

    Dict findOne(DictVM dictVM);

    List<Dict> findAll(DictVM dictVM);

    Dict save(Dict dict);

    Dict update(Dict dict);

    int deleteDicts(List<Long> ids);
}
