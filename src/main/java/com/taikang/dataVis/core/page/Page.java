package com.taikang.dataVis.core.page;

import java.io.Serializable;

/**
 * @param
 * @author itw_mengtao
 * @Description Page 描述
 * @date 2018/3/14 14:01
 * @return
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 默认查询页
     */
    public static final Integer DEFAULT_PAGE_NUM = 1;
    /**
     * 默认每页记录数
     */
    public static final Integer DEFAULT_PAGE_SIZE = 15;

    public static Integer getDefaultPageNum() {
        return DEFAULT_PAGE_NUM;
    }

    public static Integer getDefaultPageSize() {
        return DEFAULT_PAGE_SIZE;
    }
}
