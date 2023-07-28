package com.cuit.jz.dao;

import com.cuit.jz.domain.User;
import com.cuit.jz.utils.JDBCUtils3;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;

import java.sql.SQLException;
import java.util.Arrays;


public class userDao {

    public boolean Login(String username ,String password) throws SQLException {
		/*登陆功能，到user表中去匹配用户密码信息
			都没有匹配到则用户名或密码错误
			用户名正确则提示密码错误
			都正确则登陆成功，调用MainView.run（）方法
		 */
        QueryRunner qr = new QueryRunner(JDBCUtils3.getDataSource());
        String sql = "select * from user where username=?";
        Object[] params = {username};
        Object[] query = qr.query(JDBCUtils3.getConnection(), sql, new ArrayHandler(), params);
        System.out.println(Arrays.toString(query));
        return query.length != 0;
    }
}
