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
		for(BcnxSettle bcnx : bcnxSettles)
			hibernateTemplate.save(bcnx);
	}
	@Transactional
	@Override
	public BcnxSettle getBcnxSettle(final BcnxSettle bcnxSettle) throws SQLException,
			HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<BcnxSettle>(){
			@Override
			public BcnxSettle doInHibernate(Session session)
					throws HibernateException {
				String hql = "from BcnxSettle b where b.rrn = :rrn and b.mti = :mti and b.proc = :proc and b.card = :card";
				Query query = session.createQuery(hql);
				query.setString("rrn", bcnxSettle.getRrn());
				query.setString("mti", bcnxSettle.getMti());
				query.setString("proc", bcnxSettle.getProc());
				query.setString("card", bcnxSettle.getCard());
				return (BcnxSettle) query.uniqueResult();
			}
		});
	}
	@Transactional
	@Override
	public List<BcnxSettle> getBcnxSettles(BcnxSettle bcnxSettle, Date start, Date end, int first, int max)
			throws SQLException, HibernateException {
		return toList(hibernateTemplate.execute(new GetBcnxSettles(bcnxSettle,start,end,first,max)));
	}
	private class GetBcnxSettles implements HibernateCallback<List<BcnxSettle>>{
		private BcnxSettle bcnxSettle;
		private Date start;
		private Date end;
		private int first;
		private int max;
		public GetBcnxSettles(BcnxSettle bcnxSettle, Date start, Date end, int first, int max){
			this.bcnxSettle = bcnxSettle;
			this.start = start;
			this.end = end;
			this.first = first;
			this.max = max;
		}
		@Override
		public List<BcnxSettle> doInHibernate(Session session)
				throws HibernateException {
			String hql = "from BcnxSettle bs where bs.card like :card or bs.rrn like :rrn or bs.stan like :stan or bs.date between :start and :end";
			Query query = session.createQuery(hql);
			query.setString("card", bcnxSettle.getCard());
			query.setString("rrn", bcnxSettle.getRrn());
			query.setString("stan", bcnxSettle.getStan());
			query.setDate("start", start);
			query.setDate("end", end);
			query.setFirstResult(first);
			query.setMaxResults(max);
			return toList(query.list());
		}
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
