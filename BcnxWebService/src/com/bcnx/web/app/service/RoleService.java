package com.bcnx.web.app.service;

import java.util.List;

import com.bcnx.web.app.service.entity.Role;

public interface RoleService {
	public void save(Role role);
	public Role getRole(Role role);
	public List<Role> getRoles(int first, int max);
}
