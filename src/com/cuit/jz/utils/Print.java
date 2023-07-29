package com.cuit.jz.utils;

import com.cuit.jz.domain.ZhangWu;

import java.util.List;


public class Print {

	public static void printZhangWu(List<ZhangWu> list){
		int id = 0;
		System.out.println("id\t类别\t\t账户\t\t金额\t\t时间\t\t说明");
		if(list.isEmpty()) System.out.println("没有符合该查询条件下的记录");
		for (ZhangWu zhangWu : list) {
			System.out.println((++id)+"\t"+zhangWu.getFlname()+"\t\t"+zhangWu.getZhanghu()+
					"\t\t"+zhangWu.getMoney()+"\t\t"+zhangWu.getCreatetime()+"\t\t"+zhangWu.getDescription());
		}
	}

	public static void printOptions() {
		System.out.println(
				"1.类别，2.账户，3.金额，4.时间，5.说明"
		);
	}

	public static void showMoneyKinds() {
		System.out.println(
				"1.搜索低于某个金额，2.搜索高于某个金额，3.搜索金额范围"
		);
	}

	public static void showTimeKinds(){
		System.out.println(
				"1.搜索指定日期，2.搜索指定日期之前，3.搜索指定日期之后，4.搜索日期范围"
		);
	}
}
