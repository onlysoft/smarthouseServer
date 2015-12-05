package cn.onlysoft.smarthouseweb.db.service;

import cn.onlysoft.smarthouseweb.db.dao.UserDao;
import cn.onlysoft.smarthouseweb.domain.User;

public class UserService {
	private UserDao userDao;
	
	public UserService() {
		this.userDao = new UserDao();
	}

	public boolean findUser(String name)
	{
		return userDao.findUserByName(name)==null?false:true;
	}
	public boolean register(User user)
	{
		int add = userDao.add(user);
		return add>0?true:false;
	}

	public User login(String name, String pwd) {
		 return userDao.findUser(name, pwd);
	}
}
