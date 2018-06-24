package com.taikang.dataVis.domain;

/**
 *  
 * 
 * @author 孟涛
 * @description gitlab 类
 * @date 2018/4/16 14:46
 * @return 
 */
public class RequestBodyGitLabModel {
    private Boolean simple = null ;
    private String search = "" ;
    private String private_token = "" ;

//    @Override
//    public String toString() {
//        return "simple=" + simple +"&search='" + search+"'" ;
//    }


    @Override
    public String toString() {
        return "RequestBodyGitLabModel{" +
            "simple=" + simple +
            ", search='" + search + '\'' +
            ", private_token='" + private_token + '\'' +
            '}';
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Boolean getSimple() {
        return simple;
    }

    public void setSimple(Boolean simple) {
        this.simple = simple;
    }

    public String getPrivate_token() {
        return private_token;
    }

    public void setPrivate_token(String private_token) {
        this.private_token = private_token;
    }
}
