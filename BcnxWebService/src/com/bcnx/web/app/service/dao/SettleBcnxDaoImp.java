package com.bcnx.web.app.service.dao;

import java.sql.Date;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.bcnx.web.app.service.entity.SettleBcnx;

public class SettleBcnxDaoImp implements SettleBcnxDao {
	private HibernateTemplate hibernateTemplate;
	public void setSessionFactory(SessionFactory sessionFactory){
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	@Transactional
	@Override
	public void save(SettleBcnx settleBcnx) throws SQLException,
			HibernateException {
		hibernateTemplate.save(settleBcnx);
	}
	@Transactional
	@Override
	public SettleBcnx getSettleBcnx(final Date date, final String id) throws SQLException,
			HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<SettleBcnx>(){

			@Override
			public SettleBcnx doInHibernate(Session session)
					throws HibernateException {
				Query query = session.getNamedQuery("getSettleBcnx");
				query.setDate("date", date);
				query.setString("id", id);
				return (SettleBcnx) query.uniqueResult();
			}
		});
	}

}
