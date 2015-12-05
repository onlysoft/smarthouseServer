package cn.onlysoft.smarthouseweb.db.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.onlysoft.smarthouseweb.domain.User;
import cn.onlysoft.smarthouseweb.utils.JdbcUtils;



public class UserDao {
	public User findUserByName(String name)
	{
		User user = null;
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from users where name=?";
			user = (User) runner.query(sql, new BeanHandler(User.class), new Object[] { name });
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return user;
	}
	public int add(User user)
	{
		int num = 0;
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "insert into users (id,name,pwd,email) values (?,?,?,?)";
			num = runner.update(sql, new Object[] { user.getId(), user.getName(), user.getPwd(),user.getEmail()});
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return num;
	}
	public User findUser(String name,String pwd)
	{
		User user = null;
		try {
			QueryRunner runner = new QueryRunner(JdbcUtils.getDataSource());
			String sql = "select * from users where (name=? or email=?) and pwd=? limit 1";
			user = (User) runner.query(sql, new BeanHandler(User.class), new Object[] { name,name,pwd});
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return user;
	}
}
