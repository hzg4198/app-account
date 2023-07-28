package com.cuit.jz.service;

import com.cuit.jz.dao.userDao;

import java.sql.SQLException;
import java.util.Date;

public class userService {
	static userDao userDao = new userDao();

//	public int Login(String username, String password) throws SQLException {
//		return userDao.Login(username, password);
//	}

	public void Register(String username, String password) throws SQLException {
		userDao.Register(username,password);
	}

	public boolean checkName(String username) throws SQLException {
		return userDao.checkName(username);
	}

	public boolean checkPassword(String username ,String password) throws SQLException {
		return userDao.checkPassword(username ,password);
	}


}
