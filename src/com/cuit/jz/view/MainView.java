package com.cuit.jz.view;

import com.cuit.jz.domain.ZhangWu;
import com.cuit.jz.service.ZhangWuService;
import com.cuit.jz.utils.Print;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MainView {


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
	private void Register() {
		System.out.println();
	}
	//登陆
	private void Login() throws SQLException {
		ZhangWuService zhangWuService = new ZhangWuService();
		Scanner sc = new Scanner(System.in);
		boolean flag = false;
		while (!flag) {
			System.out.println("请输入用户名：");
			String username = sc.next();
			System.out.println("请输入密码：");
			String password = sc.next();
			boolean login = zhangWuService.Login(username, password);
			if (login) {
				flag = true;
				run();
			} else {
				System.out.println("用户名或密码错误，重新输入：");
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
					queryZhangwu();
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
				case 7:
					findZhangWu();
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

	private void queryWithCondition() {
	}

	private void queryZhangwu() {
	}

	private static void findZhangWu() {
		ZhangWuService zws=new ZhangWuService();
		List<ZhangWu> findAll = zws.findAll();
		Print.printZhangWu(findAll);
	}

	private static void deleteZhangWu() {

	}

	private static void editZhangWu() {

	}

	private static void addZhangWu() {

	}
}
