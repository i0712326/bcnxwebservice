package com.bcnx.web.app.service.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.bcnx.web.app.service.entity.BcnxTxn;
import com.bcnx.web.app.service.entity.User;

public class BcnxTxnDaoImp implements BcnxTxnDao {
	private HibernateTemplate hibernateTemplate;
	public void setSessionFactory(SessionFactory sessionFactory){
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	@Override
	public void save(BcnxTxn bcnxTxn) throws SQLException, HibernateException {
		hibernateTemplate.save(bcnxTxn);
	}

	@Override
	public List<BcnxTxn> getBcnxTxns(BcnxTxn bcnxTxn, int first, int max,
			User user) throws SQLException, HibernateException {
		DetachedCriteria criteria = DetachedCriteria.forClass(BcnxTxn.class);
		criteria.add(Restrictions.or(Restrictions.like("card", bcnxTxn.getCard()), Restrictions.like("rrn", bcnxTxn.getRrn())));
		return toList(hibernateTemplate.findByCriteria(criteria, first, max));
	}

	@Override
	public List<BcnxTxn> getBcnxTxns(Date start, Date end, int first, int max,
			User user) throws SQLException, HibernateException {
		DetachedCriteria criteria = DetachedCriteria.forClass(BcnxTxn.class);
		criteria.add(Restrictions.between("date", start, end));
		return toList(hibernateTemplate.findByCriteria(criteria, first, max));
	}
	@Override
	public List<BcnxTxn> getCopyRequest(int first, int max, User user)
			throws SQLException, HibernateException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<BcnxTxn> getChargeBack(int first, int max, User user)
			throws SQLException, HibernateException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<BcnxTxn> getAdjustment(int first, int max, User user)
			throws SQLException, HibernateException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<BcnxTxn> getRepresent(int first, int max, User user)
			throws SQLException, HibernateException {
		// TODO Auto-generated method stub
		return null;
	}
	private List<BcnxTxn> toList(final List<?> beans){
		if(beans==null) return null;
		if(beans.isEmpty()) return null;
		int size = beans.size();
		BcnxTxn[] list = new BcnxTxn[size];
		list = beans.toArray(list);
		return Arrays.asList(list);
		
	}
}
