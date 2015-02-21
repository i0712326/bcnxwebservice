package com.bcnx.web.app.service.dao;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.bcnx.web.app.service.entity.ReasonCode;

public class ReasonCodeDaoImp implements ReasonCodeDao{
	private HibernateTemplate hibernateTemplate;
	public void setSessionFactory(SessionFactory sessionFactory){
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	@Transactional
	@Override
	public ReasonCode getReasonCode(final String code) throws SQLException, HibernateException{
		return hibernateTemplate.execute(new HibernateCallback<ReasonCode>(){
			@Override
			public ReasonCode doInHibernate(Session session)
					throws HibernateException {
				String sql = "from ReasonCode rc where rc.code = :code";
				Query query = session.createQuery(sql);
				query.setString("code", code);
				return (ReasonCode) query.uniqueResult();
			}
		});
	}
	@Transactional
	@Override
	public List<ReasonCode> getReasonCodes() throws SQLException,
			HibernateException {
		String hql = "from ReasonCode rc order by rc.code";
		return toList(hibernateTemplate.find(hql));
	}
	@Transactional
	@Override
	public List<ReasonCode> getReasonCodes(String proc) throws SQLException,
			HibernateException {
		String hql = "select r from ReasonCode r inner join r.procCode p where p.code = :code order by r.code";
		String param = "code";
		return toList(hibernateTemplate.findByNamedParam(hql, param, proc));
	}
	private List<ReasonCode> toList(final List<?> beans){
		if(beans==null) return null;
		if(beans.isEmpty()) return null;
		int size = beans.size();
		ReasonCode[] list = new ReasonCode[size];
		list = beans.toArray(list);
		return Arrays.asList(list);
	}
}
