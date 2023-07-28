package com.cuit.jz.dao;

import com.cuit.jz.service.userService;
import com.cuit.jz.utils.JDBCUtils3;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;

import java.io.StringReader;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;


public class userDao {
    static userService us = new userService();
    private static final QueryRunner qr = new QueryRunner(JDBCUtils3.getDataSource());
		/*登陆功能，到user表中去匹配用户密码信息
			都没有匹配到则用户名或密码错误
			用户名正确则提示密码错误
			都正确则登陆成功，调用MainView.run（）方法*/
public int Login(String username ,String password) throws SQLException {
    String sql = "select * from user where username=?";
    Object[] params = {username};
    Object[] query = qr.query(JDBCUtils3.getConnection(), sql, new ArrayHandler(), params);
    if(query.length==0)return 0;
    return Objects.equals(query[1],password) ? 1 : 2;//密码正确则返回1 错误则返回2
}

    public void Register(String username ,String password) throws SQLException {
        boolean checkName = us.checkName(username);
        if(checkName){
            System.out.println("用户名已存在，请直接登录");
        }else {
            String sql = "INSERT INTO user (username,password) values(?,?)";
            Object[] params = {username ,password};
            qr.insert(JDBCUtils3.getConnection(), sql, new ArrayHandler(), params);
            System.out.println("注册成功，请登录");
        }
    }

    public boolean checkName(String username) throws SQLException {
        String sql = "select * from user where username=?";
        Object[] params = {username};
        Object[] query = qr.query(JDBCUtils3.getConnection(), sql, new ArrayHandler(), params);
        return query.length != 0;
    }

    public boolean checkPassword(String username, String password) throws SQLException {
        String sql = "select password from user where username=?";
        Object[] params = {username};
        Object[] query = qr.query(JDBCUtils3.getConnection(), sql, new ArrayHandler(), params);
        System.out.println(Arrays.toString(query));
        return Objects.equals(query[0],password);
    }


}
