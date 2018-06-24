package com.taikang.dataVis.core.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * jenkins模板
 * 项目名称:	[cus]
 * 包:		[com.taikang.cus.core.utils]
 * 类名称:	[JenkinsModel]
 * 创建人:	[itw_liangbo01]
 * 创建时间:	[2018年3月26日 下午5:30:40]
 * 修改人:	[itw_liangbo01]
 * 修改时间:	[2018年3月26日 下午5:30:40]
 * 修改备注:	[说明本次修改内容]
 * 版本:		[v1.0]
 */
public class JenkinsModel {
	/**
	 * 得到tomcat模板的xml
	 * @MethodName: getSingleParamsData
	 * @param extraMap,singleList,multipleMap
	 * @return
	 * @return String
	 * @throws
	 */
	public static String getTomcatModel(Map<String,String> extraMap,List<JSONObject> singleList,Map<String,String> multipleMap){
		String serviceName = extraMap.get("serviceName");
		String appDescribe = extraMap.get("appDescribe");
		String repositoryName = extraMap.get("repositoryName");
		String midwareType = extraMap.get("midwareType");
		String shellUrl = "";
		if ("Tomcat".equals(midwareType)) {
			shellUrl = "http://gitlab.it.taikang.com/devops/jenkins-tomcat.git";
		} else if ("SpringBoot".equals(midwareType)) {
			shellUrl = "http://gitlab.it.taikang.com/devops/jenkins-springboot.git";
		} else {
			shellUrl = "http://gitlab.it.taikang.com/devops/jenkins-tomcat.git";
		}
		//根据不同的中间件类型选择不同的url
//		String sql = "select cfg_name from configuration where cfg_from = '中间件类型' and cfg_type = '"+midwareType+"'";
//		System.out.println("midwareType sql="+sql);
//		JSONArray arr = JDBCConn.getJSONArray(sql);
//		System.out.println("midwareType arr="+arr.toString());
//		if(!arr.isEmpty()){
//			JSONObject object = arr.getJSONObject(0);
//			shellUrl = object.getString("cfg_name");
//		}
		String result = "<?xml version='1.0' encoding='UTF-8'?> "
				+ " <flow-definition plugin=\"workflow-screen@2.15\"> "
				+ "   <actions> "
				+ "     <io.jenkins.blueocean.service.embedded.BlueOceanUrlAction plugin=\"blueocean-rest-impl@1.3.3\"> "
				+ "       <blueOceanUrlObject class=\"io.jenkins.blueocean.service.embedded.BlueOceanUrlObjectImpl\"> "
				+ "         <mappedUrl>blue/organizations/jenkins/"+serviceName+"</mappedUrl> "
				+ "       </blueOceanUrlObject> "
				+ "     </io.jenkins.blueocean.service.embedded.BlueOceanUrlAction> "
				+ "   </actions> "
				+ "   <description>"+appDescribe+"</description> "
				+ "   <keepDependencies>false</keepDependencies> "
				+ "   <properties> "
				+ "     <io.fabric8.jenkins.openshiftsync.BuildConfigProjectProperty plugin=\"openshift-sync@0.1.32\"> "
				+ "       <uid></uid> "
				+ "       <namespace></namespace> "
				+ "       <name></name> "
				+ "       <resourceVersion></resourceVersion> "
				+ "     </io.fabric8.jenkins.openshiftsync.BuildConfigProjectProperty> "
				+ "     <com.dabsquared.gitlabjenkins.connection.GitLabConnectionProperty plugin=\"gitlab-plugin@1.5.2\"> "
				+ "       <gitLabConnection>gitlab</gitLabConnection> "
				+ "     </com.dabsquared.gitlabjenkins.connection.GitLabConnectionProperty> "
				+ "     <org.jenkinsci.plugins.gitlablogo.GitlabLogoProperty plugin=\"gitlab-logo@1.0.3\"> "
				+ "       <repositoryName>"+repositoryName+"</repositoryName> "
				+ "     </org.jenkinsci.plugins.gitlablogo.GitlabLogoProperty> "
				+ "     <jenkins.model.BuildDiscarderProperty> "
				+ "       <strategy class=\"hudson.tasks.LogRotator\"> "
				+ "         <daysToKeep>30</daysToKeep> "
				+ "         <numToKeep>5</numToKeep> "
				+ "         <artifactDaysToKeep>-1</artifactDaysToKeep> "
				+ "         <artifactNumToKeep>-1</artifactNumToKeep> "
				+ "       </strategy> "
				+ "     </jenkins.model.BuildDiscarderProperty> "
				+ "     <hudson.model.ParametersDefinitionProperty> "
				+ "       <parameterDefinitions> "
				+ getStringPart()
				+ getMultipleParam(multipleMap)
				+ getSingleParam(singleList)
				+ "         <hudson.model.ChoiceParameterDefinition> "
				+ "           <name>opsService</name> "
				+ "           <description></description> "
				+ "           <choices class=\"java.util.Arrays$ArrayList\"> "
				+ "             <a class=\"string-array\"> "
				+ "               <string>noOperation</string> "
				+ "               <string>status</string> "
				+ "               <string>start</string> "
				+ "               <string>stop</string> "
				+ "               <string>restart</string> "
				+ "             </a> "
				+ "           </choices> "
				+ "         </hudson.model.ChoiceParameterDefinition> "
				+ "         <com.cloudbees.plugins.credentials.CredentialsParameterDefinition plugin=\"credentials@2.1.16\"> "
				+ "           <name>credentialsId</name> "
				+ "           <description></description> "
				+ "           <defaultValue>c016027e-0573-4246-93cf-f4a55b08a86a</defaultValue> "
				+ "           <credentialType>com.cloudbees.plugins.credentials.common.StandardCredentials</credentialType> "
				+ "           <required>false</required> "
				+ "         </com.cloudbees.plugins.credentials.CredentialsParameterDefinition> "
				+ "       </parameterDefinitions> "
				+ "     </hudson.model.ParametersDefinitionProperty> "
				+ "     <org.jenkinsci.plugins.workflow.screen.properties.PipelineTriggersJobProperty> "
				+ "       <triggers/> "
				+ "     </org.jenkinsci.plugins.workflow.screen.properties.PipelineTriggersJobProperty> "
				+ "   </properties> "
				+ "   <definition class=\"org.jenkinsci.plugins.workflow.cps.CpsScmFlowDefinition\" plugin=\"workflow-cps@2.41\"> "
				+ "     <scm class=\"hudson.plugins.git.GitSCM\" plugin=\"git@3.6.4\"> "
				+ "       <configVersion>2</configVersion> "
				+ "       <userRemoteConfigs> "
				+ "         <hudson.plugins.git.UserRemoteConfig> "
				+ "           <url>"+shellUrl+"</url> "
				+ "           <credentialsId>c016027e-0573-4246-93cf-f4a55b08a86a</credentialsId> "
				+ "         </hudson.plugins.git.UserRemoteConfig> "
				+ "       </userRemoteConfigs> "
				+ "       <branches> "
				+ "         <hudson.plugins.git.BranchSpec> "
				+ "           <name>*/master</name> "
				+ "         </hudson.plugins.git.BranchSpec> "
				+ "       </branches> "
				+ "       <doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations> "
				+ "       <submoduleCfg class=\"list\"/> "
				+ "       <extensions/> "
				+ "     </scm> "
				+ "     <scriptPath>Jenkinsfile</scriptPath> "
				+ "     <lightweight>true</lightweight> "
				+ "   </definition> "
				+ "   <triggers/> "
				+ "   <disabled>false</disabled> "
				+ " </flow-definition> ";
		return result.replaceAll("\\\\","");
	}
	/**
	 * 准备下拉框参数的数据集合
	 * @MethodName: getSingleParamsData
	 * @param singleMap
	 * @return
	 * @return List<JsonObject>
	 * @throws
	 */
	public static List<JSONObject> getSingleParamsData(Map<String,String> singleMap){
		List<JSONObject> list = new ArrayList<JSONObject>();
		for (Entry<String, String> entry : singleMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			JSONObject obj = new JSONObject();
			obj.put("key", key);
			obj.put("value", value);
			list.add(obj);
		}
		return list;
	}
	/**
	 * 组装下拉框参数的xml
	 * @MethodName: getSingleParam
	 * @param singleList
	 * @return
	 * @return String
	 * @throws
	 */
	public static String getSingleParam(List<JSONObject> singleList){
		String params = "";
		for(int i=0;i<singleList.size();i++){
			JSONObject object = singleList.get(i);
			String key = object.containsKey("key")?object.getString("key"):"";
			String description = object.containsKey("description")?object.getString("description"):"";
			String value = object.containsKey("value")?object.getString("value"):"";
			if(!"".equals(key)){
				String temp = "         <hudson.model.ChoiceParameterDefinition> "
							+ "           <name>"+key+"</name> "
							+ "           <description>"+description+"</description> "
							+ "           <choices class=\"java.util.Arrays$ArrayList\"> "
							+ "             <a class=\"string-array\"> "
							+ "               <string>"+value+"</string> "
							+ "             </a> "
							+ "           </choices> "
							+ "         </hudson.model.ChoiceParameterDefinition> ";
				params += temp;
			}
		}
		return params;
	}
	/**
	 * 组装多选按钮参数的xml
	 * @MethodName: getMultipleParam
	 * @param multipleMap
	 * @return
	 * @return String
	 * @throws
	 */
	public static String getMultipleParam(Map<String,String> multipleMap){
		String targetHosts = multipleMap.get("targetHosts");
		String params = "";
		if("".equals(targetHosts)){
			return params;
		}else{
			if(ClientUtil.isJson(targetHosts)){
				JSONArray array = JSONArray.fromObject(targetHosts);
				for(int i=0;i<array.size();i++){
					JSONObject object = array.getJSONObject(i);
					String key = "targetHosts";
					String description = object.containsKey("description")?object.getString("description"):"";
					String value = object.containsKey("hostname")?object.getString("hostname"):"";
					if(!"".equals(key)){
						String temp = "         <com.cwctravel.hudson.plugins.extended__choice__parameter.ExtendedChoiceParameterDefinition plugin=\"extended-choice-parameter@0.76\"> "
									+ "           <name>"+key+"</name> "
									+ "           <description>"+description+"</description> "
									+ "           <quoteValue>false</quoteValue> "
									+ "           <saveJSONParameterToFile>false</saveJSONParameterToFile> "
									+ "           <visibleItemCount>5</visibleItemCount> "
									+ "           <type>PT_CHECKBOX</type> "
									+ "           <value>"+value+"</value> "
									+ "           <defaultValue>"+value+"</defaultValue> "
									+ "           <multiSelectDelimiter>,</multiSelectDelimiter> "
									+ "         </com.cwctravel.hudson.plugins.extended__choice__parameter.ExtendedChoiceParameterDefinition> ";
						params += temp;
					}
				}
			}else{
				return params;
			}
		}
		return params;
	}
	/**
	 * 组装tagName
	 * @MethodName: getStringPart
	 * @return
	 * @return String
	 * @throws
	 */
	public static String getStringPart(){
		String temp = "			<hudson.model.StringParameterDefinition> "
					+ "			  <name>tagName</name> "
					+ "				<description>Git Tag</description> "
					+ "				<defaultValue></defaultValue> "
					+ "			</hudson.model.StringParameterDefinition> ";
		return temp;
	}
}
