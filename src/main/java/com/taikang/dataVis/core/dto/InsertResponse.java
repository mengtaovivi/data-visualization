package com.taikang.dataVis.core.dto;

/**
 * Created by itw_jifc on 2018/3/21.
 */
public class InsertResponse {

//    private String code;//130500   0    |返回码|备注| |---|---| | 130300 | 数据库错误 | 130600 | 权限错误 | 130313 | 数据重复
    private String error;//未知参数错误   成功
//    private String message;//创建实例失败   Success
    private String instanceId;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "InsertResponse{" +
                "error='" + error + '\'' +
                ", instanceId='" + instanceId + '\'' +
                '}';
    }
}
