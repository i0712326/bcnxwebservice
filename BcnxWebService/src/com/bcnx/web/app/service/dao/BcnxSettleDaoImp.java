package com.bcnx.web.app.service.dao;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.bcnx.web.app.service.entity.BcnxSettle;

public class BcnxSettleDaoImp implements BcnxSettleDao {
	private HibernateTemplate hibernateTemplate;
	public void setSessionFactory(SessionFactory sessionFactory){
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	@Transactional
	@Override
	public void save(BcnxSettle bcnxSettle) throws SQLException,
			HibernateException {
		hibernateTemplate.save(bcnxSettle);
	}
	@Transactional
	@Override
	public void saveAll(List<BcnxSettle> bcnxSettles) throws SQLException,
			HibernateException {
		hibernateTemplate.save(bcnxSettles);
	}
	@Transactional
	@Override
	public BcnxSettle getBcnxSettle(final BcnxSettle bcnxSettle) throws SQLException,
			HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<BcnxSettle>(){
			@Override
			public BcnxSettle doInHibernate(Session session)
					throws HibernateException {
				Criteria criteria = session.createCriteria(BcnxSettle.class);
				criteria.add(Restrictions.and(
						Restrictions.eq("rrn", bcnxSettle.getRrn()),
						Restrictions.ne("mti", bcnxSettle.getMti())));
				return (BcnxSettle) criteria.uniqueResult();
			}
		});
	}
	@Transactional
	@Override
	public List<BcnxSettle> getBcnxSettles(BcnxSettle bcnxSettle, int first, int max)
			throws SQLException, HibernateException {
		DetachedCriteria criteria = DetachedCriteria.forClass(BcnxSettle.class);
		criteria.add(Restrictions.or(Restrictions.like("card", bcnxSettle.getCard()), 
				Restrictions.or(Restrictions.like("rrn",
				bcnxSettle.getRrn()), Restrictions.or(
				Restrictions.like("stan", bcnxSettle.getStan()),
				Restrictions.eq("date", bcnxSettle.getDate())))));
		criteria.add(Restrictions.or(
				Restrictions.eq("issId", bcnxSettle.getIssId()),
				Restrictions.eq("acqId", bcnxSettle.getAcqId())));
		return toList(hibernateTemplate.findByCriteria(criteria, first, max));
	}
	private List<BcnxSettle> toList(final List<?> beans){
		if(beans == null ) return null;
		if(beans.isEmpty()) return null;
		int size = beans.size();
		BcnxSettle[] list = new BcnxSettle[size];
		list = beans.toArray(list);
		return Arrays.asList(list);
	}
}
