package com.bcnx.web.app.service.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.bcnx.web.app.service.entity.Bulletin;

public class BulletinDaoImp implements BulletinDao {
	private HibernateTemplate hibernateTemplate;
	public void setSessionFactory(SessionFactory sessionFactory){
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	@Override
	public void save(Bulletin bulletin) throws SQLException, HibernateException {
		hibernateTemplate.save(bulletin);
	}

	@Override
	public List<Bulletin> getBulletins(int first, int max) throws SQLException,
			HibernateException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Bulletin.class);
		criteria.addOrder(Property.forName("id").desc());
		return toList(hibernateTemplate.findByCriteria(criteria, first, max));
	}
	@Override
	public List<Bulletin> getBulletins(Timestamp start,Timestamp end, int first, int max)
			throws SQLException, HibernateException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Bulletin.class);
		criteria.add(Restrictions.between("date", start, end));
		criteria.addOrder(Property.forName("id").desc());
		return toList(hibernateTemplate.findByCriteria(criteria, first, max));
	}
	private List<Bulletin> toList(final List<?> beans){
		if(beans==null) return null;
		if(beans.isEmpty()) return null;
		int size = beans.size();
		Bulletin[] list = new Bulletin[size];
		list = beans.toArray(list);
		return Arrays.asList(list);
	}

}
