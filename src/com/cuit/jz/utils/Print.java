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
}
