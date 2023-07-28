package com.cuit.jz.dao;

import com.cuit.jz.domain.ZhangWu;
import com.cuit.jz.utils.JDBCUtils3;
import com.cuit.jz.view.MainView;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ZhangWuDao {
	static QueryRunner qr=new QueryRunner(JDBCUtils3.getDataSource());
//	private static final String user;
//	static {
//		user = MainView.user;
//	}
	
	public List<ZhangWu> findAll(){

		System.out.println();
		String sql="select * from cuit_zhangwu where username=?";
		Object[] params = {MainView.user};
		try {
			List<ZhangWu> list = qr.query(sql, new BeanListHandler<>(ZhangWu.class),params);
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	public List<ZhangWu> checkDate(Date date) {
		String sql = "select * from cuit_zhangwu where username=? and createtime=?";
		Object[] params = {MainView.user ,date};
		try {
			List<ZhangWu> query = qr.query(sql, new BeanListHandler<>(ZhangWu.class), params);
			return query;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public List<ZhangWu> checkDate(Date sdate, Date eDate) {
		String sql = "select * from cuit_zhangwu where username=? and createtime between ?and?";
		Object[] params = {MainView.user ,sdate ,eDate};
		try {
			List<ZhangWu> query = qr.query(sql, new BeanListHandler<>(ZhangWu.class), params);
			return query;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<ZhangWu> queryIncome(){
		String sql = "select * from cuit_zhangwu where username=? and flname like '%收入%'";
		Object[] params = {MainView.user};
		try {
			List<ZhangWu> query = qr.query(JDBCUtils3.getConnection(),sql, new BeanListHandler<>(ZhangWu.class), params);
			return query;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<ZhangWu> queryExpense() {
		String sql = "select * from cuit_zhangwu where username=? and flname like '%支出%'";
		Object[] params = {MainView.user};
		try {
			List<ZhangWu> query = qr.query(JDBCUtils3.getConnection(),sql, new BeanListHandler<>(ZhangWu.class), params);
			return query;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void addAccount(String flname, Double money, String account, String time, String description, String user) {
		String sql = "insert into cuit_zhangwu (flname,money,zhanghu,createtime,description,username)" +
				"values(?,?,?,?,?,?)";
		Object[] params = {flname,money,account,time,description,user};
		try {
			qr.insert(JDBCUtils3.getConnection() ,sql ,new ArrayHandler() ,params);
			System.out.println("插入成功");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
