package com.taikang.dataVis.core.utils;

public class Constants {


    /**
     *
     *
     * @author 孟涛
     * @description jenkins信息相关信息
     * @date 2018/4/25 17:28
     * @return
     */
    public enum JenkinsInfo {
        /**
         * 创建代码库：/object/FILESOURCE/instance/
         * 构建job：/tools/execution
         * 根据name获取job信息：/object/APP/instance/_search
         *
         */
        CREATE_FILER_ESOURCE("/object/FILESOURCE/instance/"), BUILD_JENKINS_JOB("/tools/execution"),GET_JOB_BY_NAME("/object/APP/instance/_search"),
        GET_ID_BY_NAME("/tools");
        private String name;

        private JenkinsInfo(String name) {
            this.name = name;
        }

        public String getValue() {
            return name;
        }
    }

    /**
     *
     *
     * @author 孟涛
     * @description 通过api获取vid和toolid
     * @date 2018/4/25 18:12
     * @return
     */
    public enum CmdbIp {
        /**
         * 构建job：build_jenkins_job
         * 加密：EasyOps加密
         * 创建job：create_jenkins_job
         *
         */
        BUILD_JENKINS_JOB("build_jenkins_job"),ENCRYPT("EasyOps加密"),CREATE_JENKINS_JOB("create_jenkins_job");
        private String name;

        private CmdbIp(String name) {
            this.name = name;
        }

        public String getValue() {
            return name;
        }
    }


}
