package com.bcnx.web.app.service.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.bcnx.web.app.service.entity.BcnxTxn;
import com.bcnx.web.app.service.entity.DisputeTxn;
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
	public List<BcnxTxn> getBcnxTxns(BcnxTxn bcnxTxn, int first, int max, User user) throws SQLException, HibernateException {
		return toList(hibernateTemplate.execute(new GetBcnxTxns(bcnxTxn,first,max, user)));
	}
	private class GetBcnxTxns implements HibernateCallback<List<BcnxTxn>>{
		private BcnxTxn bcnxTxn;
		private int first;
		private int max;
		private User user;
		public GetBcnxTxns(BcnxTxn bcnxTxn, int first, int max, User user){
			this.bcnxTxn = bcnxTxn;
			this.first = first;
			this.max = max;
			this.user = user;
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<BcnxTxn> doInHibernate(Session session) throws HibernateException {
			String hql = "from BcnxTxn bx where bx.date= :date and ( bx.card like :card or bx.rrn like :rrn ) "
					+ "and ( bx.iss = :iss or bx.acq = :acq ) order by bx.id desc";
			Query query = session.createQuery(hql);
			query.setDate("date", bcnxTxn.getDate());
			query.setString("card", bcnxTxn.getCard());
			query.setString("rrn", bcnxTxn.getRrn());
			query.setString("iss", user.getMember().getIin());
			query.setString("acq", user.getMember().getIin());
			query.setFirstResult(first);
			query.setMaxResults(max);
			return query.list();
		}
		
	}
	@Override
	public List<BcnxTxn> getBcnxTxns(Date start, Date end, int first, int max, User user)
			throws SQLException, HibernateException {
		return toList(hibernateTemplate.execute(new GetBcnxTxnsByDate(start, end, first, max,user)));
	}
	private class GetBcnxTxnsByDate implements HibernateCallback<List<BcnxTxn>>{
		private Date start;
		private Date end;
		private int first;
		private int max;
		private User user;
		public GetBcnxTxnsByDate(Date start, Date end, int first, int max, User user){
			this.start = start;
			this.end = end;
			this.first = first;
			this.max = max;
			this.user = user;
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<BcnxTxn> doInHibernate(Session session)
				throws HibernateException {
			String hql = "from BcnxTxn bx where bx.date between :start and :end and "
					+ " (bx.iss = :iss or bx.acq = :acq ) order by bx.id desc";
			Query query = session.createQuery(hql);
			query.setDate("start", start);
			query.setDate("end", end);
			query.setString("iss", user.getMember().getIin());
			query.setString("acq", user.getMember().getIin());
			query.setFirstResult(first);
			query.setMaxResults(max);
			return query.list();
		}
	}
	@Override
	public List<BcnxTxn> getCopyRequest(int first, int max, User user)
			throws SQLException, HibernateException {
		DetachedCriteria criteria = DetachedCriteria.forClass(DisputeTxn.class);
		criteria.add(Restrictions.and(Restrictions.eq("flag", "C"),Restrictions.eq("iss", user.getMember().getIin())));
		criteria.addOrder(Property.forName("id").desc());
		return toList(hibernateTemplate.findByCriteria(criteria, first, max));
	}
	@Override
	public List<BcnxTxn> getChargeBack(int first, int max, User user)
			throws SQLException, HibernateException {
		DetachedCriteria criteria = DetachedCriteria.forClass(DisputeTxn.class);
		criteria.add(Restrictions.and(Restrictions.eq("flag", "B"), Restrictions.eq("iss",user.getMember().getIin())));
		criteria.addOrder(Property.forName("id").desc());
		return toList(hibernateTemplate.findByCriteria(criteria, first, max));
	}
	@Override
	public List<BcnxTxn> getAdjustment(int first, int max, User user)
			throws SQLException, HibernateException {
		DetachedCriteria criteria = DetachedCriteria.forClass(DisputeTxn.class);
		criteria.add(Restrictions.and(Restrictions.eq("flag", "A"),Restrictions.eq("acq", user.getMember().getIin())));
		criteria.addOrder(Property.forName("id").desc());
		return toList(hibernateTemplate.findByCriteria(criteria, first, max));
	}
	@Override
	public List<BcnxTxn> getRepresent(int first, int max, User user)
			throws SQLException, HibernateException {
		DetachedCriteria criteria = DetachedCriteria.forClass(DisputeTxn.class);
		criteria.add(Restrictions.and(Restrictions.eq("flag", "R"),Restrictions.eq("acq", user.getMember().getIin())));
		criteria.addOrder(Property.forName("id").desc());
		return toList(hibernateTemplate.findByCriteria(criteria, first, max));
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
