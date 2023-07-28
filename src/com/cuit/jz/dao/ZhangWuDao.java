package com.cuit.jz.dao;

import com.cuit.jz.domain.ZhangWu;
import com.cuit.jz.utils.JDBCUtils3;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class ZhangWuDao {

	
	public List<ZhangWu> findAll(){
		QueryRunner qr=new QueryRunner(JDBCUtils3.getDataSource());
		String sql="select * from cuit_zhangwu";
		try {
			List<ZhangWu> list = qr.query(sql, new BeanListHandler<>(ZhangWu.class));
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


}
