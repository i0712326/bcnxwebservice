package com.bcnx.web.app.service.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
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
			hibernateTemplate.saveOrUpdate(bcnx);
	}
	@Transactional
	@Override
	public BcnxSettle getBcnxSettle(final BcnxSettle bcnxSettle) throws SQLException,
			HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<BcnxSettle>(){
			@Override
			public BcnxSettle doInHibernate(Session session)
					throws HibernateException {
				String hql = "from BcnxSettle b where b.rrn = :rrn and b.mti = :mti and b.stan = :stan and b.slot = :slot";
				Query query = session.createQuery(hql);
				query.setString("rrn", bcnxSettle.getRrn());
				query.setString("mti", bcnxSettle.getMti());
				query.setString("stan", bcnxSettle.getStan());
				query.setString("slot", bcnxSettle.getSlot());
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
	@Transactional
	@Override
	public int getRecords(final BcnxSettle bs, final Date start, final Date end)
			throws SQLException, HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<Integer>(){
			@Override
			public Integer doInHibernate(Session session)
					throws HibernateException {
				String hql = "select count(bs) from BcnxSettle bs where bs.card like :card or bs.rrn like :rrn or bs.stan like :stan or bs.date between :start and :end";
				Query query = session.createQuery(hql);
				query.setString("card", bs.getCard());
				query.setString("rrn", bs.getRrn());
				query.setString("stan", bs.getStan());
				query.setDate("start", start);
				query.setDate("end", end);
				Long len = (Long) query.uniqueResult();
				return len.intValue();
			}
			
		});
	}
	@Transactional
	@Override
	public List<Date> getSettleDate() throws SQLException, HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<List<Date>>(){
			@Override
			public List<Date> doInHibernate(Session session)
					throws HibernateException {
				String sql = "select distinct(bs.date) from BcnxSettle bs order by bs.date desc";
				Query query = session.createQuery(sql);
				return toDates(query.list());
			}
			
		});
	}
	@Transactional
	@Override
	public Date getMaxDate()throws SQLException, HibernateException{
		return hibernateTemplate.execute(new HibernateCallback<Date>(){
			@Override
			public Date doInHibernate(Session session)
					throws HibernateException {
				String sql = "select max(bs.date) as date from BcnxSettle bs order by bs.date desc";
				Query query = session.createQuery(sql);
				return (Date) query.uniqueResult();
			}
		});
	}
	@Transactional
	@Override
	public List<BcnxSettle> getBcnxFinIss(Date date, String id)
			throws SQLException, HibernateException {
		String hql = "from BcnxSettle b where b.date = :date and b.iss = :id and b.res = :res and b.rrn "
				+ "not in (select bb.rrn from BcnxSettle bb where bb.date =:date and bb.iss = :id and bb.mti = :mti ) "
				+ "order by b.time";
		String[] param ={"date","id", "res", "mti"};
		Object[] values = {date, id, "00", "0420"};
		return toList(hibernateTemplate.findByNamedParam(hql, param, values));
	}
	@Transactional
	@Override
	public List<BcnxSettle> getBcnxFinAcq(Date date, String id)
			throws SQLException, HibernateException {
		String hql = "from BcnxSettle b where b.date = :date and b.acq = :id and b.res = :res and b.rrn "
				+ "not in (select bb.rrn from BcnxSettle bb where bb.date =:date and bb.acq = :id and bb.mti = :mti ) "
				+ "order by b.time";
		String[] param ={"date","id", "res", "mti"};
		Object[] values = {date, id, "00", "0420"};
		return toList(hibernateTemplate.findByNamedParam(hql, param, values));
	}
	@Transactional
	@Override
	public List<BcnxSettle> getBcnxRev(Date date, String id)
			throws SQLException, HibernateException {
		String hql = "from BcnxSettle b where b.date = :date and ( b.acq =:id or b.iss =:id )and b.res = :res and b.mti = :mti order by b.time";
		String[] param ={"date","id", "res", "mti"};
		Object[] values = {date, id, "00", "0420"};
		return toList(hibernateTemplate.findByNamedParam(hql, param, values));
	}
	@Transactional
	@Override
	public List<BcnxSettle> getBcnxErr(Date date, String id)
			throws SQLException, HibernateException {
		String hql = "from BcnxSettle b where b.date = :date and ( b.acq =:id or b.iss =:id )and b.res !=:res and b.mti = :mti and b.rrn not in "
				+ "( select bb.rrn from BcnxSettle bb where bb.date =:date and bb.acq = :id and bb.mti = :mti ) order by b.time";
		String[] param ={"date","id", "res", "mti"};
		Object[] values = {date, id, "00", "0420"};
		return toList(hibernateTemplate.findByNamedParam(hql, param, values));
	}
	private List<Date> toDates(final List<?> beans){
		if(beans == null ) return null;
		if(beans.isEmpty()) return null;
		int size = beans.size();
		Date[] list = new Date[size];
		list = beans.toArray(list);
		return Arrays.asList(list);
	}
	private List<BcnxSettle> toList(final List<?> beans){
		if(beans == null ) return new ArrayList<BcnxSettle>();
		if(beans.isEmpty()) return new ArrayList<BcnxSettle>();
		int size = beans.size();
		BcnxSettle[] list = new BcnxSettle[size];
		list = beans.toArray(list);
		return Arrays.asList(list);
	}
}
