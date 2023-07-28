package com.cuit.jz.view;

import com.cuit.jz.domain.ZhangWu;
import com.cuit.jz.service.ZhangWuService;
import com.cuit.jz.service.userService;
import com.cuit.jz.utils.Print;

import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class MainView {
	private static final userService us = new userService();
	private static final Scanner sc = new Scanner(System.in);
	private static final ZhangWuService zws=new ZhangWuService();
	public static String user;
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public void login() throws SQLException {
		boolean flag = false;
		Scanner sc = new Scanner(System.in);
		while (!flag){
			System.out.println("-----------记账app------------");
			System.out.println("1.Login,2.register,3.quit");
			System.out.println("enter the num which you wanna operate: 1-3");
			int op = sc.nextInt();
			switch (op) {
				case 1:
					Login();
					break;
				case 2:
					Register();
					break;
				case 3:
					System.out.println("bye");
					flag=true;
					break;
			}
		}
	}
	//注册
	private void Register() throws SQLException {
		System.out.println("请输入你要注册的用户名");
		String username = sc.next();
		System.out.println("请输入你的密码：");
		String password = sc.next();
		us.Register(username ,password);
	}
	//登陆
	private void Login() throws SQLException {
		boolean flag = false;
		while (!flag) {
			System.out.println("请输入用户名：");
			String username = sc.next();
			boolean nameF = us.checkName(username);
			if(nameF){
				System.out.println("请输入密码：");
				String password = sc.next();
				boolean wordF = us.checkPassword(username, password);
				if(wordF){
					flag = true;
					user = username;
					System.out.println("Login Success!");
					run();
				}else System.out.println("密码错误，请重新输入");
			}else {
				System.out.println("用户名不存在，请重新输入:");
			}
		}
	}

	public void run(){
		boolean flag=true;
		Scanner in=new Scanner(System.in);
		while(flag){
			System.out.println("-----------记账软件-------------");
			System.out.println("1.查询账务，2.多条件查询，3.添加账务，4.编辑账务，5.删除账务，6.搜索账务，7.导出账务,8.退出");
			System.out.println("请输入你要操作的功能的序号1-5");
			int op=in.nextInt();
			switch (op) {
				case 1:
					findZhangWu();
					break;
				case 2:
					queryWithCondition();
					break;
				case 3:
					addZhangWu();
					break;
				case 4:
					editZhangWu();
					break;
				case 5:
					deleteZhangWu();
					break;
				case 6:
					queryZhangWu();
					break;
				case 7:
					exportZhangWu();
					break;
				case 8:
					System.out.println("欢迎下次使用");
					flag=false;
					break;
				default:
					break;
			}
		}
	}

	private void exportZhangWu() {
	}

	private void queryWithCondition() {
		System.out.println("----请选择你要查询的方式----");
		System.out.println("1.按指定日期查询，2.按日期范围查询，3.查询收入，4.查询支出");
		int order = sc.nextInt();
		switch (order) {
			case 1:
				queryBySpecificDate();
				break;
			case 2:
				queryByRangeDate();
				break;
			case 3:
				queryIncome();
				break;
			case 4:
				queryExpense();
				break;
		}
	}

	private void queryExpense() {
		List<ZhangWu> zhangWus = zws.queryExpense();
		Print.printZhangWu(zhangWus);
	}

	private void queryIncome() {
		List<ZhangWu> zhangWus = zws.queryIncome();
		Print.printZhangWu(zhangWus);
	}

	private void queryByRangeDate() {
		while (true) {
			System.out.println("请输入要查询的起始日期：（格式yyyy-MM-dd）");
			String startDate = sc.next();
			System.out.println("请输入要查询的截至日期：（格式yyyy-MM-dd）");
			String endDate = sc.next();
			try {
				Date sDate = sdf.parse(startDate);
				Date eDate = sdf.parse(endDate);
				if(eDate.before(sDate)){
					System.out.println("日期范围有误，请重新输入");
					continue;
				}
				List<ZhangWu> zhangWus = zws.checkDate(sDate, eDate);
				Print.printZhangWu(zhangWus);
				break;
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}

	}

	private void queryBySpecificDate()  {
		System.out.println("请输入要查询的日期：（格式yyyy-MM-dd）");
		String s = sc.next();
		try {
			Date date = sdf.parse(s);
			List<ZhangWu> zhangWus = zws.checkDate(date);
			Print.printZhangWu(zhangWus);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	private void queryZhangWu() {

	}

	private static void findZhangWu() {
		List<ZhangWu> findAll = zws.findAll();
		Print.printZhangWu(findAll);
	}

	private static void deleteZhangWu() {

	}

	private static void editZhangWu() {

	}
	//自增自减问题
	private static void addZhangWu() {
		System.out.println("请输入你要添加的类别：");
		String flname = sc.next();
		System.out.println("请输入金额：");
		Double money = sc.nextDouble();
		System.out.println("请输入账户：");
		String account = sc.next();
		System.out.println("请输入时间，输入-1默认为今天");
		String s = sc.next();
		String time = Objects.equals(s,"-1") ? String.valueOf(LocalDate.now()) : s;
		System.out.println("请输入说明：");
		String description = sc.next();
		zws.addAccount(flname,money,account,time,description,user);
	}
}
