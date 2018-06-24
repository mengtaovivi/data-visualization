package com.taikang.dataVis.screen.service.impl;

import com.taikang.dataVis.core.utils.DictUtil;
import com.taikang.dataVis.mapper.DictMapper;
import com.taikang.dataVis.domain.Dict;
import com.taikang.dataVis.screen.service.DictService;
import com.taikang.dataVis.screen.web.rest.vm.DictVM;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

/**
* @ClassName: DictServiceImpl
* @Description: (DictService实现类)
* @author iwt_gaoliang
* @date 2018年3月12日 下午2:06:48
*
*/
@Service
@CacheConfig(cacheNames = "cus_redis")
public class DictServiceImpl implements DictService {

	/**
	* @Fields dictMapper : (注入的mapper对象)
	*/
	@Autowired
	private DictMapper dictMapper;



    /**
     * 根据dict_code查询dict_value
     * @param dictCode dict字典code
     * @return Dict dict对象
     */
    @Override
    public Dict getDictByCode(String dictCode) {
        return dictMapper.getDictByCode(dictCode);
    }

    /**
     * 〈根据dictCode查询dictValue〉
     * @return  java.util.List<java.lang.Object>
     * @exception/throws [异常类型] [异常说明]
     * @since [起始版本]（可选）
     */
    @Override
    public List<Object> getDictValueByCode(String dictCode) {
        List<Object> result = null;
        Dict dict = dictMapper.getDictByCode(dictCode);
        if(null != dict && null != dict.getDictValue()){
            result = DictUtil.parseDictValue(dict.getDictValue());
        }
        return result;
    }

    @Override
    public Dict findOne(DictVM dictVM) {
        return dictMapper.findOne(dictVM);
    }

    @Override
    public List<Dict> findAll(DictVM dictVM) {
        return dictMapper.findAll(dictVM);
    }

    @Override
    public Dict save(Dict dict) {
        return dictMapper.save(dict);
    }

    @Override
    public Dict update(Dict dict) {
        return dictMapper.update(dict);
    }

    @Override
    public int deleteDicts(List<Long> ids) {
        return dictMapper.deleteDicts(ids);
    }

}
