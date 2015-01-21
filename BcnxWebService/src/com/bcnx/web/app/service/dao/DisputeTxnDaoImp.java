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

import com.bcnx.web.app.service.entity.DisputeTxn;

public class DisputeTxnDaoImp implements DisputeTxnDao {
	private HibernateTemplate hibernateTemplate;
	public void setSessionFactory(SessionFactory sessionFactory){
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	@Transactional
	@Override
	public void save(DisputeTxn disputeTxn) throws SQLException,
			HibernateException {
		hibernateTemplate.save(disputeTxn);
	}
	@Transactional
	@Override
	public DisputeTxn getDisputeTxn(final DisputeTxn disputeTxn) throws SQLException,
			HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<DisputeTxn>(){
			@Override
			public DisputeTxn doInHibernate(Session session)
					throws HibernateException {
				Criteria criteria = session.createCriteria(DisputeTxn.class);
				criteria.add(Restrictions.and(
						Restrictions.eq("rrn", disputeTxn.getBcnxSettle().getRrn()),
						Restrictions.eq("procc", disputeTxn.getProcc())));
				criteria.add(Restrictions.or(Restrictions.eq("iss", disputeTxn.getIss()), Restrictions.eq("acq", disputeTxn.getAcq())));
				return (DisputeTxn) criteria.uniqueResult();
			}
			
		});
	}
	@Transactional
	@Override
	public List<DisputeTxn> getDisputeTxns(DisputeTxn disputeTxn,int first, int max)
			throws SQLException, HibernateException {
		DetachedCriteria criteria = DetachedCriteria.forClass(DisputeTxn.class);
		criteria.add(Restrictions.eq("procc", disputeTxn.getProcc()));
		criteria.add(Restrictions.or(Restrictions.eq("iss", disputeTxn.getIss()), Restrictions.eq("acq", disputeTxn.getAcq())));
		return toList(hibernateTemplate.findByCriteria(criteria, first, max));
	}
	private List<DisputeTxn> toList(final List<?> beans){
		if(beans==null) return null;
		if(beans.isEmpty()) return null;
		int size = beans.size();
		DisputeTxn[] list = new DisputeTxn[size];
		list = beans.toArray(list);
		return Arrays.asList(list);
	}
}
