package com.bcnx.web.app.service.dao;

import java.sql.SQLException;
import java.util.List;

import com.bcnx.web.app.service.entity.User;

public interface UserDao {
	public void save(User user) throws SQLException;
	public void update(User user) throws SQLException;
	public void updatePasswd(User user) throws SQLException;
	public User getUser(User user) throws SQLException;
	public List<User> getUsers(int first, int max) throws SQLException;
	public List<User> getUsers(User user, int first, int max) throws SQLException;
}
