package com.cuit.jz.utils;


import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
public class JDBCUtils3 {
	
	//�������ӳ�
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
	//�������ӳض���
	
	public static DataSource getDataSource(){
		return dataSource;
	}
	public static Connection getConnection() throws SQLException {

		return dataSource.getConnection();
	}
}
