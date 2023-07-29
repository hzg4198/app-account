package com.cuit.jz.service;

import com.cuit.jz.dao.ZhangWuDao;
import com.cuit.jz.domain.ZhangWu;

import java.util.Date;
import java.util.List;

public class ZhangWuService {
	ZhangWuDao zwd=new ZhangWuDao();


	public List<ZhangWu> findAll(){
		return zwd.findAll();
	}


	public List<ZhangWu> checkDate(Date date){
		return zwd.checkDate(date);
	}

	public List<ZhangWu> checkDate(Date sdate ,Date eDate){
		return zwd.checkDate(sdate ,eDate);
	}
	//优化：合并收入和支出
	public List<ZhangWu> queryIncome(){
		return zwd.queryIncome();
	}

	public List<ZhangWu> queryExpense() {
		return zwd.queryExpense();
	}

	public void addAccount(String flname, Double money, String account, String time, String description, String user) {
		zwd.addAccount(flname ,money ,account ,time ,description ,user);
	}

	public void addMutiple(List<ZhangWu> list) {
		zwd.addMuti(list);
	}

	public void editAccount(List<ZhangWu> list, int index ,int op ,String str) {
		zwd.editAcc(list ,index ,op ,str);
	}


	public void deleteAcc(List<ZhangWu> all, int index) {
		zwd.deleteAcc(all ,index);
	}
}
