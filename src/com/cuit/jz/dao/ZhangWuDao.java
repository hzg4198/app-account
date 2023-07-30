package com.cuit.jz.dao;

import com.cuit.jz.domain.ZhangWu;
import com.cuit.jz.utils.JDBCUtils3;
import com.cuit.jz.utils.Print;
import com.cuit.jz.view.MainView;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
			return qr.query(JDBCUtils3.getConnection(),sql, new BeanListHandler<>(ZhangWu.class), params);
		} catch (SQLException e) {
			throw  new RuntimeException(e);
		}
	}

	public List<ZhangWu> queryExpense() {
		String sql = "select * from cuit_zhangwu where username=? and flname like '%支出%'";
		Object[] params = {MainView.user};
		try {
			return qr.query(JDBCUtils3.getConnection(),sql, new BeanListHandler<>(ZhangWu.class), params);
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
		Export.checkId(id);
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
		String sql = "select * from cuit_zhangwu where username=? and flname like ?";
		Object[] params = {MainView.user,"%"+key+"%"};
		try {
			return qr.query(JDBCUtils3.getConnection(), sql, new BeanListHandler<>(ZhangWu.class), params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<ZhangWu> searchKeyWord1(String key) {
		String sql = "select * from cuit_zhangwu where username=? and description like ?";
		Object[] params = {MainView.user,"%"+key+"%"};
		try {
			return qr.query(JDBCUtils3.getConnection(), sql, new BeanListHandler<>(ZhangWu.class), params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<ZhangWu> searchAccKind(String key) {
		String sql = "select * from cuit_zhangwu where username=? and zhanghu like ?";
		Object[] params = {MainView.user,"%"+key+"%"};
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
				sql = "select * from cuit_zhangwu where username=? and money <=?";
				break;
			case 1:
				sql = "select * from cuit_zhangwu where username=? and money >?";
				break;
		}
		Object[] params = {MainView.user,money};
		try {
			return qr.query(JDBCUtils3.getConnection() ,sql ,new BeanListHandler<>(ZhangWu.class),params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<ZhangWu> searchMoney(double moneyL, double moneyH) {
		String sql = "select * from cuit_zhangwu where username=? and money between ? and ?";
		Object[] params = moneyL>moneyH? new Object[]{MainView.user,moneyH, moneyL} : new Object[]{MainView.user,moneyL, moneyH};
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
				sql ="select * from cuit_zhangwu where username=? and createtime < ?";
				break;
			case 1:
				sql ="select * from cuit_zhangwu where username=? and createtime > ?";
		}
		Object[] params = {MainView.user,date};
		try {
			return qr.query(JDBCUtils3.getConnection() ,sql ,new BeanListHandler<>(ZhangWu.class) ,params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public static class Export{
		static String dir = "accountData/" + MainView.user;//账户数据文件夹
		static String fileName = dir+"/" + MainView.user + ".txt";//该用户的导出数据
		static File file = new File(dir);
		static File acc = new File(fileName);
		static int id;
		public Export(){}
		public Export(int id){
			Export.id = id;
		}
		//导出方法
		public static void export(List<ZhangWu> zhangWus){
			if (!file.exists()) {//文件夹不存在
				file.mkdirs();
				try {
					FileWriter fileWriter = new FileWriter(fileName);
					fileWriter.write(Print.exportZhangWu(zhangWus));
					fileWriter.close();
				} catch (IOException e) {throw new RuntimeException(e);}
			}else {//该用户文件存在
				if(acc.exists()){
					acc.delete();
					try {
						FileWriter fileWriter = new FileWriter(fileName);
						fileWriter.write(Print.exportZhangWu(zhangWus));
						fileWriter.close();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}else {
					try {
						FileWriter fileWriter = new FileWriter(fileName);
						fileWriter.write(Print.exportZhangWu(zhangWus));
						fileWriter.close();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
			System.out.println("export success!");
		}

		public static void checkId(int id){
			if(acc.exists()){
				try {
					BufferedReader bufferedReader = new BufferedReader(new FileReader(acc));
					String line;
					while ((line=bufferedReader.readLine()) !=null){
						boolean id1 = line.startsWith(String.valueOf(id));
						if(id1){
							editExport(id);
						}
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}

		public static void  editExport(int id){
			try {
				BufferedReader bufferedReader = new BufferedReader(new FileReader(acc));
				String line;
				StringBuilder sb = new StringBuilder();
				while ((line=bufferedReader.readLine()) !=null){
					boolean id1 = line.startsWith(String.valueOf(id));
                    if(id1){
						String sql = "select * from cuit_zhangwu where zwid=?";
	                    List<ZhangWu> query = qr.query(JDBCUtils3.getConnection(), sql, new BeanListHandler<>(ZhangWu.class), id);
	                    String s = Print.exportZhangWu(query);
						sb.append(s);
                    }else {
						sb.append(line).append(System.getProperty("line.separator"));
                    }
				}
				BufferedWriter writer = new BufferedWriter(new FileWriter(acc));
				writer.write(String.valueOf(sb));
				writer.close();
				bufferedReader.close();
			} catch (IOException | SQLException e) {
				throw new RuntimeException(e);
			}
			System.out.println("成功更新导出文件");
		}
	}


}
