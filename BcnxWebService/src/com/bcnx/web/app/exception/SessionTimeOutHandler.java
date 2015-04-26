package com.bcnx.web.app.exception;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.bcnx.web.app.service.UserService;
import com.bcnx.web.app.service.entity.User;

public class SessionTimeOutHandler implements HttpSessionListener {
	private static Logger logger = Logger.getLogger(SessionTimeOutHandler.class);
	private UserService userService;
	public void setUserService(UserService userService){
		this.userService = userService;
	}
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		logger.debug("session is created");
		return;
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		logger.debug("session is destroyed");
		HttpSession session = event.getSession();
		User user = (User) session.getAttribute("user");
		user = userService.getUser(user);
		user.setState(0);
		userService.update(user);
		return;
	}

}
