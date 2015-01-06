package com.bcnx.web.app.service;

import java.util.List;

import com.bcnx.web.app.service.entity.User;

public interface UserService {
	public void save(User user);
	public void update(User user);
	public void updatePasswd(User user);
	public User getUser(User user);
	public List<User> getUsers(int first, int max);
	public List<User> getUsers(User user, int first, int max);
}
