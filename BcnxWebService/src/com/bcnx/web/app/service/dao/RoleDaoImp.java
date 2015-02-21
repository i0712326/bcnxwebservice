package com.bcnx.web.app.service.dao;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.bcnx.web.app.service.entity.Role;

public class RoleDaoImp implements RoleDao {
	private HibernateTemplate hibernateTemplate;
	public void setSessionFactory(SessionFactory sessionFactory){
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	@Transactional
	@Override
	public void save(Role role) throws SQLException {
		hibernateTemplate.save(role);
	}
	@Transactional
	@Override
	public Role getRole(final Role role) throws SQLException {
		return hibernateTemplate.execute(new HibernateCallback<Role>(){
			@Override
			public Role doInHibernate(Session session) throws HibernateException {
				String hql = "from Role r where r.roleId = :id";
				Query query = session.createQuery(hql);
				query.setString("id", role.getRoleId());
				return (Role) query.uniqueResult();
			}
			
		});
	}
	@Transactional
	@Override
	public List<Role> getRoles(int first, int max) throws SQLException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Role.class);
		return toList(hibernateTemplate.findByCriteria(criteria, first, max));
	}
	private List<Role> toList(final List<?> beans){
		if(beans==null) return null;
		if(beans.isEmpty()) return null;
		int size = beans.size();
		Role[] list = new Role[size];
		list = beans.toArray(list);
		return Arrays.asList(list);
	}

}
