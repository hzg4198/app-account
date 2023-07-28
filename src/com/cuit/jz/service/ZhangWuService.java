package com.cuit.jz.service;

import com.cuit.jz.dao.ZhangWuDao;
import com.cuit.jz.dao.userDao;
import com.cuit.jz.domain.ZhangWu;

import java.sql.SQLException;
import java.util.List;

public class ZhangWuService {
	ZhangWuDao zwd=new ZhangWuDao();
	userDao userDao = new userDao();

	public List<ZhangWu> findAll(){
		return zwd.findAll();
	}

	public boolean Login(String username, String password) throws SQLException {
		return userDao.Login(username, password);
	}
}
