package com.cuit.jz.app;

import com.cuit.jz.view.MainView;

import java.sql.SQLException;

public class App {
	public static void main(String[] args) throws SQLException {
		new MainView().login();
	}
}
