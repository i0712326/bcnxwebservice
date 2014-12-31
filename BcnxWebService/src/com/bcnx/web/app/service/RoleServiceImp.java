package com.bcnx.web.app.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.bcnx.web.app.service.dao.RoleDao;
import com.bcnx.web.app.service.entity.Role;

public class RoleServiceImp implements RoleService {
	private static final Logger logger = Logger.getLogger(RoleServiceImp.class);
	private RoleDao roleDao;
	public void setRoleDao(RoleDao roleDao){
		this.roleDao = roleDao;
	}
	@Override
	public void save(Role role) {
		try {
			roleDao.save(role);
		} catch (SQLException e) {
			logger.debug("Exception occured while try to save role data", e);
		}

	}
	@Override
	public Role getRole(Role role) {
		try {
			return roleDao.getRole(role);
		} catch (SQLException e) {
			logger.debug("Exception occured at get role",e);
			return null;
		}
	}
	@Override
	public List<Role> getRoles(int first, int max) {
		try {
			return roleDao.getRoles(first,max);
		} catch (SQLException e) {
			logger.debug("Exception occured while try to get roles", e);
			return null;
		}
	}

}
