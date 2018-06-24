package com.taikang.dataVis.domain;

import com.taikang.dataVis.core.enums.ProtocolEnum;

/**
 * 文件源
 *
 * @author 余灏
 */
public class FileSource {

    /**
     * instanceId 主键
     */
    private String instanceId;
    /**
     * 名称
     */
    private String name;
    /**
     * 文件源地址
     */
    private String location;
    /**
     * 文件源管理协议  FTP; SVN; GIT
     */
    private ProtocolEnum protocol;
    /**
     * 用户名
     */
    private String user;
    /**
     * 密码
     */
    private String password;
    /**
     * token
     */
    private String token;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ProtocolEnum getProtocol() {
        return protocol;
    }

    public void setProtocol(ProtocolEnum protocol) {
        this.protocol = protocol;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }
}
