package com.taikang.dataVis.domain;

/**
 *
 *
 * @author 孟涛
 * @description cmdb标准请求参数
 * @date 2018/4/25 14:11
 * @return
 */
public class RequestCmdbHeaderModel {
    private String host = "" ;
    private String org = "" ;
    private String user = "" ;

    @Override
    public String toString() {
        return "{" +
                "host:'" + host + '\'' +
                ", org:'" + org + '\'' +
                ", user:'" + user + '\'' +
                '}';
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
