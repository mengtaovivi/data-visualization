package com.taikang.dataVis.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JobEnv {
	
	@Value("${cmdb.host_cmdb}")
	private String cmdb_host_cmdb;
	
	@Value("${cmdb.host_tool}")
	private String cmdb_host_tool;
	
	@Value("${cmdb.user}")
	private String cmdb_user;
	
	@Value("${cmdb.org}")
	private String cmdb_org;
	
	@Value("${cmdb.url}")
	private String cmdb_url;
	
	@Value("${jenkins.url}")
	private String jenkins_url;
	
	@Value("${jenkins.user}")
	private String jenkins_user;
	
	@Value("${jenkins.password}")
	private String jenkins_password;
	
	@Value("${cmdb.skey}")
	private String cmdb_skey;
	
	@Value("${cmdb.ivparameter}")
	private String cmdb_ivparameter;

	@Value("${gitlab.url}")
	private String gitlab_url ;

	public String getCmdb_host_cmdb() {
		return cmdb_host_cmdb;
	}

	public void setCmdb_host_cmdb(String cmdb_host_cmdb) {
		this.cmdb_host_cmdb = cmdb_host_cmdb;
	}

	public String getCmdb_host_tool() {
		return cmdb_host_tool;
	}

	public void setCmdb_host_tool(String cmdb_host_tool) {
		this.cmdb_host_tool = cmdb_host_tool;
	}

	public String getCmdb_user() {
		return cmdb_user;
	}

	public void setCmdb_user(String cmdb_user) {
		this.cmdb_user = cmdb_user;
	}

	public String getCmdb_org() {
		return cmdb_org;
	}

	public void setCmdb_org(String cmdb_org) {
		this.cmdb_org = cmdb_org;
	}

	public String getCmdb_url() {
		return cmdb_url;
	}

	public void setCmdb_url(String cmdb_url) {
		this.cmdb_url = cmdb_url;
	}

	public String getJenkins_url() {
		return jenkins_url;
	}

	public void setJenkins_url(String jenkins_url) {
		this.jenkins_url = jenkins_url;
	}

	public String getJenkins_user() {
		return jenkins_user;
	}

	public void setJenkins_user(String jenkins_user) {
		this.jenkins_user = jenkins_user;
	}

	public String getJenkins_password() {
		return jenkins_password;
	}

	public void setJenkins_password(String jenkins_password) {
		this.jenkins_password = jenkins_password;
	}

	public String getCmdb_skey() {
		return cmdb_skey;
	}

	public void setCmdb_skey(String cmdb_skey) {
		this.cmdb_skey = cmdb_skey;
	}

	public String getCmdb_ivparameter() {
		return cmdb_ivparameter;
	}

	public void setCmdb_ivparameter(String cmdb_ivparameter) {
		this.cmdb_ivparameter = cmdb_ivparameter;
	}

	public String getGitlab_url() {
		return gitlab_url;
	}

	public void setGitlab_url(String gitlab_url) {
		this.gitlab_url = gitlab_url;
	}
}
