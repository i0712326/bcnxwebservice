package com.bcnx.web.app.service.dao;

import java.sql.SQLException;
import java.util.List;

import com.bcnx.web.app.service.entity.Role;

public interface RoleDao {
	public void save(Role role) throws SQLException;
	public Role getRole(Role role) throws SQLException;
	public List<Role> getRoles(int first, int max) throws SQLException;
}
