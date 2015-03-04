package com.bcnx.web.app.service.dao;

import java.sql.Date;
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
	public void saveAll(final List<SettleBcnx> settleBcnxs) throws SQLException,
			HibernateException {
		hibernateTemplate.execute(new HibernateCallback<Void>(){
			@Override
			public Void doInHibernate(Session session)
					throws HibernateException {
				int count = 0;
				for(SettleBcnx item : settleBcnxs){
					session.save(item);
					if(++count%50==0){
						session.flush();
						session.clear();
					}
				}
				return null;
			}
		});
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
	@Transactional
	@Override
	public List<SettleBcnx> getSettleBcnxs(Date date) throws SQLException, HibernateException{
		String hql = "from SettleBcnx sb where sb.date = :date";
		return toList(hibernateTemplate.findByNamedParam(hql, "date", date));
	}
	private List<SettleBcnx> toList(final List<?> beans){
		if(beans==null) return null;
		if(beans.isEmpty()) return null;
		int size = beans.size();
		SettleBcnx[] list = new SettleBcnx[size];
		list = beans.toArray(list);
		return Arrays.asList(list);
	}
}
