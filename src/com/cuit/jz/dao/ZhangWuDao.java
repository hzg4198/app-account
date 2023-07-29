package com.cuit.jz.dao;

import com.cuit.jz.domain.ZhangWu;
import com.cuit.jz.utils.JDBCUtils3;
import com.cuit.jz.view.MainView;
import jdk.nashorn.internal.scripts.JD;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.*;

public class ZhangWuDao {
	static QueryRunner qr=new QueryRunner(JDBCUtils3.getDataSource());
	static  Map<Integer ,String> editMap = new HashMap<>();
	static {
		editMap.put(1,"update cuit_zhangwu set flname=? where zwid=?");
		editMap.put(2,"update cuit_zhangwu set zhanghu=? where zwid=?");
		editMap.put(3,"update cuit_zhangwu set money=? where zwid=?");
		editMap.put(4,"update cuit_zhangwu set createtime=? where zwid=?");
		editMap.put(5,"update cuit_zhangwu set description=? where zwid=?");
	}

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
			return qr.query(sql, new BeanListHandler<>(ZhangWu.class), params);
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
			System.out.println(query);
			return query;
		} catch (SQLException e) {
			throw  new RuntimeException(e);
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

	public void addMuti(List<ZhangWu> list) {
		int i = 0,len = list.size();//记录项目数量(没用到)
		while (i < len){
			String sql = "insert into cuit_zhangwu (flname,money,zhanghu,createtime,description,username)" +
					"values(?,?,?,?,?,?)";
			ZhangWu zhangWu = list.get(i++);
			Object[] params = {zhangWu.getFlname() ,zhangWu.getMoney() ,zhangWu.getZhanghu() ,zhangWu.getCreatetime() ,
			zhangWu.getDescription() , MainView.user};
			try {
				qr.insert(JDBCUtils3.getConnection() ,sql ,new ArrayHandler() ,params);
				System.out.println("第"+i+"条账目添加成功！");
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void editAcc(List<ZhangWu> list, int index, int op, String str) {
		int id = list.get(index).getZwid();//记录原表中的id
		String sql = editMap.get(op);
		Object[] params = {str,id};
		try {
			int update = qr.update(JDBCUtils3.getConnection(), sql ,params);
			System.out.println("成功更新数目："+update);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void deleteAcc(List<ZhangWu> list, int index) {
		int id = list.get(index).getZwid();
		String sql = "delete from cuit_zhangwu where zwid=?";
		Object[] params = {id};
		try {
			int delete = qr.update(JDBCUtils3.getConnection(), sql ,params);
			System.out.println("成功删除数目："+delete);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<ZhangWu> searchKeyWord(String key) {
		String sql = "select * from cuit_zhangwu where flname like ?";
		Object[] params = {"%"+key+"%"};
		try {
			return qr.query(JDBCUtils3.getConnection(), sql, new BeanListHandler<>(ZhangWu.class), params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<ZhangWu> searchKeyWord1(String key) {
		String sql = "select * from cuit_zhangwu where description like ?";
		Object[] params = {"%"+key+"%"};
		try {
			return qr.query(JDBCUtils3.getConnection(), sql, new BeanListHandler<>(ZhangWu.class), params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<ZhangWu> searchAccKind(String key) {
		String sql = "select * from cuit_zhangwu where zhanghu like ?";
		Object[] params = {"%"+key+"%"};
		try {
			return qr.query(JDBCUtils3.getConnection() ,sql ,new BeanListHandler<>(ZhangWu.class) ,params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<ZhangWu> searchMoney(int order, Double money) {
		//order:0：低于某个金额
		String sql = "";
		switch (order) {
			case 0:
			sql = "select * from cuit_zhangwu where money <=?";
				break;
			case 1:
			sql = "select * from cuit_zhangwu where money >?";
				break;
		}
		Object[] params = {money};
		try {
			return qr.query(JDBCUtils3.getConnection() ,sql ,new BeanListHandler<>(ZhangWu.class),params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<ZhangWu> searchMoney(double moneyL, double moneyH) {
		String sql = "select * from cuit_zhangwu where money between ? and ?";
		Object[] params = moneyL>moneyH? new Object[]{moneyH, moneyL} : new Object[]{moneyL, moneyH};
		try {
			return qr.query(JDBCUtils3.getConnection() ,sql ,new BeanListHandler<>(ZhangWu.class) ,params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<ZhangWu> searchDate(int order, Date date) {
		String sql = "";
		switch (order) {
			case 0:
				sql ="select * from cuit_zhangwu where createtime < ?";
				break;
			case 1:
				sql ="select * from cuit_zhangwu where createtime > ?";
		}
		Object[] params = {date};
		try {
			return qr.query(JDBCUtils3.getConnection() ,sql ,new BeanListHandler<>(ZhangWu.class) ,params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
