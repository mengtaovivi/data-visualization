package com.taikang.dataVis.core.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taikang.dataVis.core.page.Page;
import com.taikang.dataVis.core.utils.StringUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * @param
 * @author itw_mengtao
 * @Description basecontroller
 * @date 2018/3/14 13:57
 * @return
 */
public class BaseResource {

    /**
     * 获取请求参数
     *
     * @param request
     * @return
     */
    protected Map<String, String> getRequestParams(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();

        for (String key : request.getParameterMap().keySet()) {
            params.put(key, StringUtil.join(request.getParameterValues(key), ","));
        }

        return params;
    }

    /**
     * @return void
     * @author itw_mengtao
     * @Description 默认页码
     * @date 2018/3/14 14:32
     */
    public void putParamPage(HttpServletRequest request) {
//        String orderColumn = request.getParameter("orderColumn");
//        String orderSort = request.getParameter("orderSort");
        Integer pageNum =
                StringUtil.isBlank(request.getParameter("pageNum"))
                        ? Page.DEFAULT_PAGE_NUM
                        : Integer.valueOf(request.getParameter("pageNum"));
        Integer pageSize =
                StringUtil.isBlank(request.getParameter("pageSize"))
                        ? Page.DEFAULT_PAGE_SIZE
                        : Integer.valueOf(request.getParameter("pageSize"));
        //第一个参数是第几页；第二个参数是每页显示条数。
        PageHelper.startPage(Integer.valueOf(pageNum), Integer.valueOf(pageSize));
//        PageHelper.orderBy(orderColumn + " " + orderSort);
    }

    /**
     * 设置总记录数
     * @param list
     * @return
     */
    public PageInfo newInstance(List list) {
        com.github.pagehelper.Page page = (com.github.pagehelper.Page) list;
        long total = list.size();
        page.setTotal(total);
        PageInfo pageInfo = new PageInfo(page);
        return pageInfo;
    }

    public void putParamPageByPage(Map<String, Object> map) {
        Integer pageNum = (map==null?Page.DEFAULT_PAGE_NUM:(StringUtil.isBlank(map.get("pageNum").toString())
                        ? Page.DEFAULT_PAGE_NUM
                        : Integer.valueOf(map.get("pageNum").toString())));
        Integer pageSize =( map == null ?Page.DEFAULT_PAGE_SIZE:(StringUtil.isBlank(map.get("pageSize").toString())
                        ? Page.DEFAULT_PAGE_SIZE
                        : Integer.valueOf(map.get("pageSize").toString())));
        //第一个参数是第几页；第二个参数是每页显示条数。
        PageHelper.startPage(Integer.valueOf(pageNum), Integer.valueOf(pageSize));
    }
}
