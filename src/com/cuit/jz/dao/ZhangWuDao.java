package com.cuit.jz.dao;

import com.cuit.jz.domain.ZhangWu;
import com.cuit.jz.utils.JDBCUtils3;
import com.cuit.jz.utils.Print;
import com.cuit.jz.view.MainView;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
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
			System.out.println("参数错误");
			return null;
		}
	}

	public static List<ZhangWu> queryIncome(){
		String sql = "select * from cuit_zhangwu where username=? and flname like '%收入%'";
		Object[] params = {MainView.user};
		try {
			return qr.query(JDBCUtils3.getConnection(),sql, new BeanListHandler<>(ZhangWu.class), params);
		} catch (SQLException e) {
			throw  new RuntimeException(e);
		}
	}

	public static List<ZhangWu> queryExpense() {
		String sql = "select * from cuit_zhangwu where username=? and flname like '%支出%'";
		Object[] params = {MainView.user};
		try {
			return qr.query(JDBCUtils3.getConnection(),sql, new BeanListHandler<>(ZhangWu.class), params);
		} catch (SQLException e) {
			System.out.println("参数错误");
			return null;
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
			System.out.println("参数错误");
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
				System.out.println("参数错误");
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
			System.out.println("参数错误");;
		}
		Export.checkId(id);
//		Client client = new Client();
//		client.check(id);
	}

	public void deleteAcc(List<ZhangWu> list, int index) {
		int id = list.get(index).getZwid();
		String sql = "delete from cuit_zhangwu where zwid=?";
		Object[] params = {id};
		try {
			int delete = qr.update(JDBCUtils3.getConnection(), sql ,params);
			System.out.println("成功删除数目："+delete);
		} catch (SQLException e) {
			System.out.println("删除错误");
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
				System.out.println("参数异常");
			}
			System.out.println("成功更新导出文件");
		}

	}


	public static class Client{
		int order;
		public Client(){
			this.order = order;
		}
		public void connect() throws Exception {
			Socket sock = new Socket("192.168.1.106", 10087); // 连接指定服务器和端口
			try (InputStream input = sock.getInputStream()) {
				try (OutputStream output = sock.getOutputStream()) {
					handle(input, output);
				}
			}
			sock.close();
			System.out.println("disconnected.");
		}

		private  void handle(InputStream input, OutputStream output) throws IOException {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output,StandardCharsets.UTF_8));
			BufferedReader reader = new BufferedReader(new InputStreamReader(input,StandardCharsets.UTF_8));
			Scanner sc = new Scanner(System.in);
			System.out.println("[server] " + reader.readLine());
			writer.write(MainView.user+"\n");
			writer.flush();
			label:
			while (true) {
				System.out.print(">>> 输入1上传账目，输入2下载账目，" +
						"输入3上传文件，输入4下载文件,输入bye断开连接"); // 打印提示
				String s = sc.nextLine(); // 读取一行输入
				switch (s) {
					case "1": //上传账目
						Print.showUploadOptions();
						String op = sc.next();
						switch (op) {
							case "1"://上传收入
								writer.write("1\n");
								writer.flush();
								List<ZhangWu> zhangWus = queryIncome();
								String s1 = Print.exportZhangWu(zhangWus);
								System.out.println(s1);
								writer.write(Print.exportZhangWu(zhangWus));
								writer.newLine();
								writer.flush();
								System.out.println(reader.readLine());
								break ;
							case "2"://上传支出
								writer.write("1\n");
								writer.flush();
								List<ZhangWu> zhangWus1 = queryExpense();
								writer.write(Print.exportZhangWu(zhangWus1));
								writer.newLine();
								writer.flush();
								System.out.println(reader.readLine());
								break ;
							case "3":
								writer.write("1\n");
								writer.flush();
								MainView mainView = new MainView();
								List<ZhangWu> zhangWus2 = mainView.queryBySpecificDate();
								writer.write(Print.exportZhangWu(zhangWus2));
								writer.newLine();
								writer.flush();
								System.out.println(reader.readLine());
								break ;
							case "4":
								writer.write("1\n");
								writer.flush();
								MainView mainView1 = new MainView();
								List<ZhangWu> zhangWus3 = mainView1.queryByRangeDate();
								writer.write(Print.exportZhangWu(zhangWus3));
								writer.newLine();
								writer.flush();
								System.out.println(reader.readLine());
								break ;
							case "5":
								writer.write("1\n");
								writer.flush();
								ZhangWuDao zwd = new ZhangWuDao();
								List<ZhangWu> zhangWus4 = zwd.findAll();
								writer.write(Print.exportZhangWu(zhangWus4));
								writer.newLine();
								writer.flush();
								System.out.println(reader.readLine());
								break ;


						}
						break;
					case "2": //下载账目
						System.out.println("下载账目");
						writer.write("2\n");
						writer.flush();
						String s2 = reader.readLine();
						if(s2.equals("-1")){
							System.out.println("没有上传的账目");
							break;
						}
						System.out.println(s2);
						StringBuilder sb = new StringBuilder();
						String s1;
						while ((s1 = reader.readLine()) != null) {
							sb.append(s1).append(System.getProperty("line.separator"));
							if (s1.equals("")) break;
						}
						BufferedWriter writer1 = new BufferedWriter(new FileWriter(MainView.user + "下载.txt"));
						writer1.write(String.valueOf(sb));
						writer1.close();
						System.out.println("下载成功");

						break;
					case "3": {//上传文件
						writer.write("3\n");
						writer.flush();
						System.out.println("请输出要上传文件的路径");
						String next = sc.next();
						File file = new File(next);
						String name = file.getName();
						long length = file.length();
						writer.write(name + "\n");
						writer.flush();
						writer.write(length + "\n");
						writer.flush();
						FileInputStream fileInputStream = null;
						try {
							fileInputStream = new FileInputStream(file);
						} catch (FileNotFoundException e) {
							System.out.println("找不到指定的文件");
							writer.write("refresh\n");
							writer.flush();
							break ;
						}
						writer.write("begin\n");
						writer.flush();
						BufferedInputStream bis = new BufferedInputStream(fileInputStream);

						byte[] bytes = new byte[1024];
						int len;
						while ((len = bis.read(bytes)) != -1) {
							output.write(bytes, 0, len);
							output.flush();
						}
						System.out.println("读取完毕");
						break;
					}
					case "4": {
						writer.write("4\n");
						writer.flush();
						System.out.println("test");
						System.out.println("请输入你要下载的文件的序号");
						String len = reader.readLine();//文件目录下文件的个数
						System.out.println(len);
						if(Objects.equals(len,"0")){
							System.out.println("没有文件可以下载");
							break ;
						}

						List<String> fileList = new ArrayList<>();
						for (int i = 0; i < Integer.parseInt(len); i++) {
							String readLine = reader.readLine();
							fileList.add(readLine.substring(2));
							System.out.println(readLine);
						}
						String index = sc.next();
						writer.write(index + "\n");
						writer.flush();
//					System.out.println("文件个数为："+len);
						String filename = fileList.get(Integer.parseInt(index) - 1);
						System.out.println("文件名：" + filename);
						long fileLength = Long.parseLong(reader.readLine());
						System.out.println("文件大小为：" + fileLength);
						BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filename));
						byte[] bytes = new byte[1024];
						int length;
						long temp = 0;
						while ((length = input.read(bytes)) != -1) {
							bos.write(bytes, 0, length);
							temp += length;
							if (temp >= fileLength) break;
						}
						bos.close();
						System.out.println("下载完成：" + filename);

						break;
					}
					default:
						writer.write(s + "\n");
						writer.flush();
						if (s.equals("bye")) break label;
						break;
				}

			}
		}

//		public void check(int id) throws IOException {
//			Socket sock = new Socket("localhost",10087);
//			try(InputStream input = sock.getInputStream()){
//				try (OutputStream output = sock.getOutputStream()){
////					String sql = "select * from cuit_zhangwu where zwid=?";
////					List<ZhangWu> query = qr.query(JDBCUtils3.getConnection(), sql, new BeanListHandler<>(ZhangWu.class), id);
//					update(input ,output ,id);
//				}
//			}
//			sock.close();
//
//		}

	}
}
