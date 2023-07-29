package com.cuit.jz.utils;


import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
public class JDBCUtils3 {
	
	//创建连接池
	public static DataSource dataSource;
	
	static{
		try {
			InputStream in =new FileInputStream("dbcp.properties");
			Properties p=new Properties();
			p.load(in);
			dataSource=BasicDataSourceFactory.createDataSource(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//返回连接池对象
	
	public static DataSource getDataSource(){
		return dataSource;
	}

	public static Connection getConnection()  {

		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
