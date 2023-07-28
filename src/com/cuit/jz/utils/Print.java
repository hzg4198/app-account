package com.cuit.jz.utils;

import com.cuit.jz.domain.ZhangWu;

import java.util.List;


public class Print {

	public static void printZhangWu(List<ZhangWu> list){
		System.out.println("id\t类别\t\t账户\t\t金额\t\t时间\t\t说明");
		for (ZhangWu zhangWu : list) {
			System.out.println(zhangWu.getZwid()+"\t"+zhangWu.getFlname()+"\t\t"+zhangWu.getZhanghu()+
					"\t\t"+zhangWu.getMoney()+"\t\t"+zhangWu.getCreatetime()+"\t\t"+zhangWu.getDescription());
		}
	}
}
