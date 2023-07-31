package com.cuit.jz.view;

import com.cuit.jz.domain.ZhangWu;
import com.cuit.jz.service.ZhangWuService;
import com.cuit.jz.service.userService;
import com.cuit.jz.utils.Print;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainView {
	private static final userService us = new userService();
	private static final Scanner sc = new Scanner(System.in);
	private static final ZhangWuService zws=new ZhangWuService();
	public static String user;
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public void login() throws Exception {
		boolean flag = false;
		Scanner sc = new Scanner(System.in);
		while (!flag){
			System.out.println("-----------记账app------------");
			System.out.println("1.Login,2.register,3.quit");
			System.out.println("enter the num which you wanna operate: 1-3");
			String op = sc.next();
			switch (op) {
				case "1":
					Login();
					break;
				case "2":
					Register();
					break;
				case "3":
					System.out.println("bye");
					flag=true;
					break;
				default:
					System.out.println("格式输入错误，重新输入");
					break;
			}
		}
	}

	public void run() throws Exception {
		boolean flag=true;
		Scanner in=new Scanner(System.in);
		while(flag){
			System.out.println("-----------记账软件-------------");
			System.out.println("1.查询账务，2.多条件查询，3.添加账务，4.编辑账务，5.删除账务，6.搜索账务，7.导出账务，8.上传或下载，9.退出" );
			System.out.println("请输入你要操作的功能的序号1-9");
			Pattern p = Pattern.compile("^[1-9]$");
			String op = in.next();
			switch (op) {
				case "1":
					findZhangWu();
					break;
				case "2":
					queryWithCondition();
					break;
				case "3":
					chooseAddMethod();
					break;
				case "4":
					editZhangWu();
					break;
				case "5":
					chooseDeleteMethod();
					break;
				case "6":
					searchZhangWu();
					break;
				case "7":
				chooseExportMethod();
					break;
				case "8":
				connectAcc();
					break;
				case "9":
					System.out.println("欢迎下次使用");
					flag=false;
					break;
				default:
					System.out.println("格式输入错误，重新输入");
					break;
			}
		}
	}

	private void connectAcc() throws Exception {
		zws.connect();
	}


	private void chooseDeleteMethod() {
		System.out.println("请选择删除的方式");
		System.out.println("1.删除单条，2.删除多条");
		String  order = sc.next();
		switch (order) {
			case "1":
				deleteZhangWu();
				break;
			case "2":
				deleteMultipleAcc();
				break;
			default:
				System.out.println("格式输入错误，重新输入");
				break;
		}

	}

	private void deleteMultipleAcc() {
		List<ZhangWu> all = zws.findAll();
		int num = all.size();
		Print.printZhangWu(all);
		List<Integer> list = new ArrayList<>();
		if(num!=0){
			while(true) {
				int index;
				while (true) {
					System.out.println("请输入你要删除的序号，输入-1结束");
					index = sc.nextInt() - 1;
					if(index == -2 || (index >=0 && index < num))break;
					else System.out.println("越界 重新输入");
				}
				if(Objects.equals(index,-2)){
					System.out.println("输入完毕，你要删除的序号为："+list);
					break;
				}
				list.add(index);
			}
			System.out.println("确认删除请输入1");
			String affirm = sc.next();
			if(Objects.equals(affirm ,"1"))zws.deleteBatch(all ,list);
			else System.out.println("取消删除，返回上一级");
		}

	}

	private void searchZhangWu() {
		System.out.println("请选择搜索的方式：");
		Print.printOptions();
		String op = sc.next();
		switch (op) {
			case "1":
				searchNameKeyWords();
				break;
			case "2":
				switchAccKinds();
				break;
			case "3":
				chooseMoneyMethod();
				break;
			case "4":
				chooseTimeMethod();
				break;
			case "5":
				searchDescKeyWords();
				break;
			default:
				System.out.println("格式输入错误，重新输入");
				break;
		}
	}

	private void chooseTimeMethod() {
		System.out.println("请选择搜索方式：");
		Print.showTimeKinds();
		String order = sc.next();
		switch (order) {
			case "1":
				queryBySpecificDate();
				break;
			case "2":
				searchTime(0);
				break;
			case "3":
				searchTime(1);
				break;
			case "4":
				queryByRangeDate();
				break;
			default:
				System.out.println("格式输入错误，重新输入");
				break;
		}
	}

	private void searchTime(int order) {
		System.out.println("请输入日期:（格式yyyy-MM-dd）");
		String s = sc.next();
		try {
			Date date = sdf.parse(s);
			List<ZhangWu> zhangWus = zws.searchDate(order ,date);
			Print.printZhangWu(zhangWus);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	private void searchNameKeyWords() {
		System.out.println("请输入要搜索的类别的关键字：");
		String key = sc.next();
		List<ZhangWu> zhangWus = zws.searchKeyword(key);
		Print.printZhangWu(zhangWus);
	}

	private void switchAccKinds() {
		System.out.println("请输出要搜索的账户类型：");
		String key = sc.next();
		List<ZhangWu> zhangWus = zws.searchAccKind(key);
		Print.printZhangWu(zhangWus);
	}

	private void chooseMoneyMethod() {
		System.out.println("请选择搜索方式：");
		Print.showMoneyKinds();
		String order = sc.next();
		switch (order) {
			case "1":
				searchMoney(0);
				break;
			case "2":
				searchMoney(1);
				break;
			case "3":
				searchMoney();
				break;
			default:
				System.out.println("格式输入错误，重新输入");
				break;
		}
	}
	//范围搜索
	private void searchMoney() {
		System.out.println("查询金额范围，请输入金额1：");
		double moneyL = sc.nextDouble();
		System.out.println("请输入金额2：");
		double moneyH = sc.nextDouble();
		List<ZhangWu> zhangWus = zws.searchMoney(moneyL ,moneyH);
		Print.printZhangWu(zhangWus);
	}
	//根据order选择搜索方式
	private void searchMoney(int order) {
		System.out.println("请输入金额：");
		Double money = sc.nextDouble();
		List<ZhangWu> zhangWus = zws.searchMoney(order,money);
		Print.printZhangWu(zhangWus);
	}


	private void searchDescKeyWords() {
		System.out.println("请输入要搜索的说明的关键字：");
		String key = sc.next();
		List<ZhangWu> zhangWus = zws.searchKeyword1(key);
		Print.printZhangWu(zhangWus);
	}

	private void chooseAddMethod() {
		System.out.println("请输入你要添加的方式");
		System.out.println("1.添加一项，2.添加多项");
		String op = sc.next();
		if(Objects.equals(op,"1")){
			addSingleZhangWu();
		}else if (Objects.equals(op,"2")){
			addMultipleAccount();
		}
	}

	private void addMultipleAccount() {
		List<ZhangWu> list = new ArrayList<>();
		int count = 0;
		while(true){
			ZhangWu zhangWu = new ZhangWu();
			System.out.println("请输入你要添加的第"+(++count)+"项账务,添加完毕请输入-1");
			System.out.println("请输入类别：");
			String temp = sc.next();
			if(Objects.equals(temp,"-1")) break;
			zhangWu.setFlname(temp);
			System.out.println("请输入金额：");
			zhangWu.setMoney(sc.nextDouble());
			System.out.println("请输入账户：");
			zhangWu.setZhanghu(sc.next());
			System.out.println("请输入时间，输入-1默认为今天");
			String s = sc.next();
			String time = Objects.equals(s ,"-1")? String.valueOf(LocalDate.now()) :s;
			zhangWu.setCreatetime(time);
			System.out.println("请输入说明：");
			zhangWu.setDescription(sc.next());
			list.add(zhangWu);
		}
		zws.addMultiple(list);

	}

	private void chooseExportMethod() {
		//选择导出的方式
		Print.showExportKinds();
		String order = sc.next();
		switch (order) {
			case "1":
				exportIncome();
				break;
			case "2":
				exportExpense();
				break;
			case "3":
				exportSpecificDate();
				break;
			case "4":
				exportRangeDate();
				break;
			case "5":
				exportAll();
                break;
			default:
				System.out.println("格式输入错误，重新输入");
				break;
		}

	}

	private void exportAll() {
		List<ZhangWu> zhangWus = zws.findAll();
        zws.export(zhangWus);
	}

	private void exportRangeDate() {
		List<ZhangWu> zhangWus = queryByRangeDate();
		zws.export(zhangWus);
	}

	private void exportSpecificDate() {
		List<ZhangWu> zhangWus = queryBySpecificDate();
		zws.export(zhangWus);
	}

	private void exportExpense() {
		List<ZhangWu> zhangWus = queryExpense();
		zws.export(zhangWus);
	}

	private void exportIncome() {
		List<ZhangWu> zhangWus = queryIncome();
		zws.export(zhangWus);
	}

	private void queryWithCondition() {
		System.out.println("----请选择你要查询的方式----");
		System.out.println("1.按指定日期查询，2.按日期范围查询，3.查询收入，4.查询支出");
		String order = sc.next();
		switch (order) {
			case "1":
				queryBySpecificDate();
				break;
			case "2":
				queryByRangeDate();
				break;
			case "3":
				queryIncome();
				break;
			case "4":
				queryExpense();
				break;
			default:
				System.out.println("格式输入错误，重新输入");
				break;
		}
	}

	private List<ZhangWu> queryExpense() {
		List<ZhangWu> zhangWus = zws.queryExpense();
		Print.printZhangWu(zhangWus);
		return  zhangWus;
	}

	private List<ZhangWu> queryIncome() {
		List<ZhangWu> zhangWus = zws.queryIncome();
		Print.printZhangWu(zhangWus);
		return  zhangWus;
	}

	public List<ZhangWu> queryByRangeDate() {
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
				return zhangWus;
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}

	}

	public List<ZhangWu> queryBySpecificDate()  {
		System.out.println("请输入要查询的日期：（格式yyyy-MM-dd）");
		String s = sc.next();
		try {
			Date date = sdf.parse(s);
			List<ZhangWu> zhangWus = zws.checkDate(date);
			Print.printZhangWu(zhangWus);
			return zhangWus;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

	}

	//查询该用户下所有账目
	private static void findZhangWu() {
		List<ZhangWu> findAll = zws.findAll();
		Print.printZhangWu(findAll);
	}
	//删除账户
	private static void deleteZhangWu() {
		List<ZhangWu> all = zws.findAll();
		int num = all.size();
		Print.printZhangWu(all);
		if(num!=0){
			int index;
			//维护：判断
			while(true){
				System.out.println("请输入你要删除的账户的序号");
				index = sc.nextInt() - 1;
				if(index >= 0&& index < num)break;
				else System.out.println("越界 重新输入");
			}

			System.out.println("确定删除吗，确定请输入1");
			String affirm = sc.next();
			if(Objects.equals(affirm,"1")){
				zws.deleteAcc(all ,index);
			}
		}
	}
	//编辑账户
	private static void editZhangWu() {
		List<ZhangWu> all = zws.findAll();
		int num = all.size();
		Print.printZhangWu(all);
		if(num!=0) {
			int index;
			while (true) {
				System.out.println("请输入你要编辑的账目左边的序号");//维护：判断
				index = sc.nextInt() - 1;
				if(index >= 0 && index < num) break;
				else System.out.println("越界 重新输入");;
			}
//			ZhangWu zhangWu = all.get(index);
			System.out.println("请选择你要编辑的栏位：");
			Print.printOptions();
			int op = sc.nextInt();
			System.out.println("请输入修改后的值：");
			String str = sc.next();
			zws.editAccount(all ,index , op , str);
		}
	}
	//添加单条账目
	private static void addSingleZhangWu() {
		System.out.println("请输入你要添加的类别：");
		String flname = sc.next();
		System.out.println("请输入金额：");
		double money = 0;
		try {
			 money = sc.nextDouble();
		}catch (Exception e){
			System.out.println("参数错误");
		}

		System.out.println("请输入账户：");
		String account = sc.next();
		System.out.println("请输入时间，输入-1默认为今天");
		String s = sc.next();
		String time = Objects.equals(s,"-1") ? String.valueOf(LocalDate.now()) : s;
		System.out.println("请输入说明：");
		String description = sc.next();
		zws.addAccount(flname,money,account,time,description,user);
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
	private void Login() throws Exception {
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
}
