package com.bcnx.web.app.service.dao;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.bcnx.web.app.service.entity.Fee;

public class FeeDaoImp implements FeeDao {
	private HibernateTemplate hibernateTemplate;
	public void setSessionFactory(SessionFactory sessionFactory){
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	@Transactional
	@Override
	public void save(Fee fee) throws SQLException, HibernateException {
		hibernateTemplate.save(fee);
	}
	@Transactional
	@Override
	public Fee getFee(final Fee fee) throws SQLException, HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<Fee>(){
			@Override
			public Fee doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(Fee.class);
				criteria.add(Restrictions.eq("procCode", fee.getProcCode()));
				criteria.add(Restrictions.eq("cardType", fee.getCardType()));
				return (Fee) criteria.uniqueResult();
			}
			
		});
	}
	@Transactional
	@Override
	public List<Fee> getFees(String cardType) throws SQLException,
			HibernateException {
		String hql = "from Fee f where f.cardType = :cardType";
		return toList(hibernateTemplate.findByNamedParam(hql, "cardType", cardType));
	}
	private List<Fee> toList(final List<?> beans){
		if(beans == null) return null;
		if(beans.isEmpty()) return null;
		int size = beans.size();
		Fee[] list = new Fee[size];
		list = beans.toArray(list);
		return Arrays.asList(list);
	}
}
