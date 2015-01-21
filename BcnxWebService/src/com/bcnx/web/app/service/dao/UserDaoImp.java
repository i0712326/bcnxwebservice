package com.bcnx.web.app.service.dao;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.bcnx.web.app.service.entity.User;

public class UserDaoImp implements UserDao {
	private HibernateTemplate hibernateTemplate;
	public void setSessionFactory(SessionFactory sessionFactory){
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	@Transactional
	@Override
	public void save(User user) throws SQLException {
		hibernateTemplate.save(user);
	}
	@Transactional
	@Override
	public void update(User user) throws SQLException{
		hibernateTemplate.update(user);
	}
	@Transactional
	@Override
	public void updatePasswd(User user) throws SQLException {
		hibernateTemplate.update(user);
	}
	@Transactional
	@Override
	public User getUser(final User user) throws SQLException {
		return hibernateTemplate.execute(new HibernateCallback<User>(){
			@Override
			public User doInHibernate(Session session)
					throws HibernateException {
				String hql = "from User u where u.userId = :id";
				Query query = session.createQuery(hql);
				query.setString("id", user.getUserId());
				return (User) query.uniqueResult();
			}
		});
	}
	@Transactional
	@Override
	public List<User> getUsers(int first, int max) throws SQLException {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		return toList(hibernateTemplate.findByCriteria(criteria,first,max));
	}
	@Transactional
	@Override
	public List<User> getUsers(User user, int first, int max)
			throws SQLException {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		criteria.add(Restrictions.like("userId", user.getUserId()));
		return toList(hibernateTemplate.findByCriteria(criteria, first, max));
	}
	private List<User> toList(final List<?> beans){
		if(beans == null ) return null;
		if(beans.isEmpty()) return null;
		int size = beans.size();
		User[] list = new User[size];
		list = beans.toArray(list);
		return Arrays.asList(list);
	}
	
}
