package com.bcnx.web.app.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.bcnx.web.app.service.dao.UserDao;
import com.bcnx.web.app.service.entity.User;

public class UserServiceImp implements UserService {
	private static final Logger logger = Logger.getLogger(UserServiceImp.class);
	private UserDao userDao;
	public void setUserDao(UserDao userDao){
		this.userDao = userDao;
	}
	@Override
	public void save(User user) {
		try {
			userDao.save(user);
		} catch (SQLException e) {
			logger.debug("Exception occured while try to save user data",e);
		}
	}
	@Override
	public void updatePasswd(User user) {
		try {
			userDao.updatePasswd(user);
		} catch (SQLException e) {
			logger.debug("Exception occured while try to updat user",e);
		}
	}
	@Override
	public User getUser(User user) {
		try {
			return userDao.getUser(user);
		} catch (SQLException e) {
			logger.debug("Exception occured while try to user", e);
			return null;
		}
	}
	@Override
	public List<User> getUsers(int first, int max) {
		try {
			return userDao.getUsers(first, max);
		} catch (SQLException e) {
			logger.debug("Exception occured while try to user", e);
			return null;
		}
	}
	@Override
	public List<User> getUsers(User user, int first, int max) {
		try {
			return userDao.getUsers(user, first, max);
		} catch (SQLException e) {
			logger.debug("Exception occured while try to user", e);
			return null;
		}
	}
}
