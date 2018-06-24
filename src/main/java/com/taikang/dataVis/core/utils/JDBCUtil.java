package com.taikang.dataVis.core.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taikang.dataVis.core.config.JdbcParams;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Service
public class JDBCUtil {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private JdbcParams jdbcParams;

	// 从zabbix查询数据
	public JSONArray getJSONArray(String sql) {
		JSONArray jsonArray = new JSONArray();
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName(jdbcParams.getDriver_class_name());
			con = DriverManager.getConnection(jdbcParams.getUrl(), jdbcParams.getUsername(), jdbcParams.getPassword());
//			Class.forName("com.mysql.jdbc.Driver");
//			con = DriverManager.getConnection("jdbc:mysql://10.130.211.240:3306/zabbix?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true", "zabbix", "77SHIbian01");
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData metaDate = rs.getMetaData();
			int number = metaDate.getColumnCount();
			String[] column = new String[number];
			for (int j = 0; j < column.length; j++) {
				column[j] = metaDate.getColumnName(j + 1);
			}
			while (rs.next()) {
				JSONObject o = new JSONObject();
				for (int j = 1; j <= column.length; j++) {
					o.put(column[j - 1], rs.getString(j) == null ? "" : rs.getString(j).trim());
				}
				jsonArray.add(o);
			}
		} catch (SQLException e) {
			logger.error(LogUtil.PrintStack(e));
		} catch (ClassNotFoundException e) {
			logger.error(LogUtil.PrintStack(e));
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error(LogUtil.PrintStack(e));
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					logger.error(LogUtil.PrintStack(e));
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					logger.error(LogUtil.PrintStack(e));
				}
			}
		}
		return jsonArray;
	}
	
	// 从zabbix查询数据
	public int getCount(String sql) {
		int result = 0;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName(jdbcParams.getDriver_class_name());
			con = DriverManager.getConnection(jdbcParams.getUrl(), jdbcParams.getUsername(), jdbcParams.getPassword());
//			Class.forName("com.mysql.jdbc.Driver");
//			con = DriverManager.getConnection("jdbc:mysql://10.130.211.240:3306/zabbix?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true", "zabbix", "77SHIbian01");
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()){
				result =rs.getInt("count");
	           }
		} catch (SQLException e) {
			logger.error(LogUtil.PrintStack(e));
		} catch (ClassNotFoundException e) {
			logger.error(LogUtil.PrintStack(e));
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error(LogUtil.PrintStack(e));
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					logger.error(LogUtil.PrintStack(e));
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					logger.error(LogUtil.PrintStack(e));
				}
			}
		}
		return result;
	}	
	
	public static void main(String[] args) {
		String sql = "select count(*) from interface";
		System.out.println(new JDBCUtil().getJSONArray(sql));
	}
}
