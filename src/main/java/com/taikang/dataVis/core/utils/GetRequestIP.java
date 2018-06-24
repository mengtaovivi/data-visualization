package com.taikang.dataVis.core.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 项目名称:	[cus]
 * 包:		[com.taikang.cus.core.utils]
 * 类名称:	[GetRequestIP]
 * 类描述:	[获取客户端IP值]
 * 创建人:	[itw_guodc]
 * 创建时间:	[2018年4月12日 下午1:13:07]
 * 修改人:	[itw_guodc]
 * 修改时间:	[2018年4月12日 下午1:13:07]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 *
 */
public class GetRequestIP {
	
	
	public static String getLocalIp(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String forwarded = request.getHeader("X-Forwarded-For");
        String realIp = request.getHeader("X-Real-IP");

        String ip = null;
        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;
            } else {
                ip = remoteAddr + "/" + forwarded.split(",")[0];
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                if(forwarded != null){
                    forwarded = forwarded.split(",")[0];
                }
                ip = realIp + "/" + forwarded;
            }
        }
        return ip;
    }

}
