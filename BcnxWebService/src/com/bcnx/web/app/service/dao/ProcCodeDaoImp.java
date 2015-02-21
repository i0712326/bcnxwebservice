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

import com.bcnx.web.app.service.entity.ProcCode;

public class ProcCodeDaoImp implements ProcCodeDao {
	private HibernateTemplate hibernateTemplate;
	public void setSessionFactory(SessionFactory sessionFactory){
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	@Transactional
	@Override
	public void save(ProcCode procCode) throws SQLException, HibernateException {
		hibernateTemplate.save(procCode);
	}
	@Transactional
	@Override
	public ProcCode getProcCode(final ProcCode procCode) throws SQLException,
			HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<ProcCode>(){
			@Override
			public ProcCode doInHibernate(Session session)
					throws HibernateException {
				Criteria criteria = session.createCriteria(ProcCode.class);
				criteria.add(Restrictions.eq("code", procCode.getCode()));
				return (ProcCode) criteria.uniqueResult();
			}
		});
	}
	@Transactional
	@Override
	public List<ProcCode> getProcCodes(ProcCode procCode, int first, int max) throws SQLException,
			HibernateException {
		DetachedCriteria criteria = DetachedCriteria.forClass(ProcCode.class);
		criteria.add(Restrictions.like("code",procCode.getCode()));
		return toList(hibernateTemplate.findByCriteria(criteria, first, max));
	}
	private List<ProcCode> toList(final List<?> beans){
		if(beans==null) return null;
		if(beans.isEmpty()) return null;
		int size = beans.size();
		ProcCode[] list = new ProcCode[size];
		list = beans.toArray(list);
		return Arrays.asList(list);
	}
}
