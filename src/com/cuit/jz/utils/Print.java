package com.cuit.jz.utils;

import com.cuit.jz.domain.ZhangWu;

import java.util.List;


public class Print {

	public static void printZhangWu(List<ZhangWu> list){
		System.out.println("id\t���\t\t�˻�\t\t���\t\tʱ��\t\t˵��");
		for (ZhangWu zhangWu : list) {
			System.out.println(zhangWu.getZwid()+"\t"+zhangWu.getFlname()+"\t\t"+zhangWu.getZhanghu()+
					"\t\t"+zhangWu.getMoney()+"\t\t"+zhangWu.getCreatetime()+"\t\t"+zhangWu.getDescription());
		}
	}
}
