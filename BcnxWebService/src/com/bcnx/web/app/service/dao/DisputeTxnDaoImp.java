package com.bcnx.web.app.service.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.bcnx.web.app.service.entity.DisputeTxn;

public class DisputeTxnDaoImp implements DisputeTxnDao {
	protected HibernateTemplate hibernateTemplate;
	public void setSessionFactory(SessionFactory sessionFactory){
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	@Transactional
	@Override
	public void save(DisputeTxn disputeTxn) throws SQLException,
			HibernateException {
		hibernateTemplate.save(disputeTxn);
	}
	
	/// view incoming
	@Transactional
	@Override
	public DisputeTxn getDisputeTxn(final DisputeTxn disputeTxn) throws SQLException,
			HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<DisputeTxn>(){
			@Override
			public DisputeTxn doInHibernate(Session session)
					throws HibernateException {
				Query query = session.getNamedQuery("getDisputeTxn");
				query.setString("procc", disputeTxn.getProcc());
				query.setString("rrn", disputeTxn.getRrn());
				query.setString("slot", disputeTxn.getSlot());
				query.setString("stan", disputeTxn.getStan());
				return (DisputeTxn) query.uniqueResult();
			}
			
		});
	}
	@Transactional
	@Override
	public List<DisputeTxn> getInDisp(final String id, final int first, final int max) throws SQLException,
			HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<List<DisputeTxn>>(){
			@Override
			public List<DisputeTxn> doInHibernate(Session session)
					throws HibernateException {
				Query query = session.getNamedQuery("getInComing");
				query.setString("id", id);
				query.setFirstResult(first);
				query.setMaxResults(max);
				return toList(query.list());
			}
		});
	}
	@Transactional
	@Override
	public int getInRecords(final String id) throws SQLException,
			HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<Integer>(){
			@Override
			public Integer doInHibernate(Session session)
					throws HibernateException {
				Query query = session.getNamedQuery("countInComing");
				query.setString("id", id);
				Long len = (Long) query.uniqueResult();
				return len.intValue();
			}
			
		});
	}
	@Transactional
	@Override
	public List<DisputeTxn> getInDisp(final String id, final String card, final String rrn, final String stan, final String proc, final Date start, final Date end, final int first, final int max)
			throws SQLException, HibernateException {
		return toList(hibernateTemplate.execute(new HibernateCallback<List<DisputeTxn>>(){
			@Override
			public List<DisputeTxn> doInHibernate(Session session)
					throws HibernateException {
				Query query = session.getNamedQuery("getProcIncoming");
				query.setString("id", id);
				query.setString("card", card);
				query.setString("rrn", rrn);
				query.setString("stan", stan);
				query.setString("proc", proc);
				query.setDate("start", start);
				query.setDate("end", end);
				query.setFirstResult(first);
				query.setMaxResults(max);
				return toList(query.list());
			}
		}));
	}
	@Transactional
	@Override
	public int getInBy(final String id, final String card, final String rrn, final String stan, final String proc, final Date start, final Date end) throws SQLException, HibernateException{
		return hibernateTemplate.execute(new HibernateCallback<Integer>(){
			@Override
			public Integer doInHibernate(Session session)
					throws HibernateException {
				Query query = session.getNamedQuery("countProcIncoming");
				query.setString("id", id);
				query.setString("card", card);
				query.setString("rrn", rrn);
				query.setString("stan", stan);
				query.setString("proc", proc);
				query.setDate("start", start);
				query.setDate("end", end);
				Long len = (Long) query.uniqueResult();
				return len.intValue();
			}
		});
	}
	// filter data module;
	
	
	// outgoing retrieval module
	@Transactional
	@Override
	public List<DisputeTxn> getOutDisp(final String id, final int first, final int max) throws SQLException,
			HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<List<DisputeTxn>>(){
			@Override
			public List<DisputeTxn> doInHibernate(Session session)
					throws HibernateException {
				Query query = session.getNamedQuery("getOutgoings");
				query.setString("id", id);
				query.setFirstResult(first);
				query.setMaxResults(max);
				return toList(query.list());
			}
			
		});
	}
	@Transactional
	@Override
	public int getOutRecords(final String id) throws SQLException, HibernateException{
		return hibernateTemplate.execute(new HibernateCallback<Integer>(){
			@Override
			public Integer doInHibernate(Session session)
					throws HibernateException {
				Query query = session.getNamedQuery("countOutgoings");
				query.setString("id", id);
				Long len = (Long) query.uniqueResult();
				return len.intValue();
			}
			
		});
	}
	@Transactional
	@Override
	public List<DisputeTxn> getOutDisp(final String id, final String card, final String rrn, final String stan, final String proc, final Date start, final Date end, final int first, final int max)
			throws SQLException, HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<List<DisputeTxn>>(){
			@Override
			public List<DisputeTxn> doInHibernate(Session session)
					throws HibernateException {
				Query query = session.getNamedQuery("getProcOutgoings");
				query.setString("id", id);
				query.setString("card", card);
				query.setString("rrn", rrn);
				query.setString("stan", stan);
				query.setString("proc", proc);
				query.setDate("start", start);
				query.setDate("end", end);
				query.setFirstResult(first);
				query.setMaxResults(max);
				return toList(query.list());
			}
			
		});
	}
	@Transactional
	@Override
	public int getOutBy(final String id, final String card, final String rrn, final String stan, final String proc, final Date start, final Date end)
			throws SQLException, HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<Integer>(){
			@Override
			public Integer doInHibernate(Session session)
					throws HibernateException {
				Query query = session.getNamedQuery("countProcOutgoing");
				query.setString("id", id);
				query.setString("card", card);
				query.setString("rrn", rrn);
				query.setString("stan", stan);
				query.setString("proc", proc);
				query.setDate("start", start);
				query.setDate("end", end);
				Long len = (Long) query.uniqueResult();
				return len.intValue();
			}
			
		});
	}
	
	@Transactional
	@Override
	public void update(DisputeTxn disputeTxn) throws SQLException,
			HibernateException {
		hibernateTemplate.update(disputeTxn);
	}
	
	// related cases
	
	@Transactional
	@Override
	public List<DisputeTxn> relatedCase(final DisputeTxn disp, final int first, final int max) throws SQLException, HibernateException{
		return hibernateTemplate.execute(new HibernateCallback<List<DisputeTxn>>(){
			@Override
			public List<DisputeTxn> doInHibernate(Session session)
					throws HibernateException {
				Query query = session.getNamedQuery("getRelated");
				query.setString("slot", disp.getSlot());
				query.setString("rrn", disp.getRrn());
				query.setString("stan", disp.getStan());
				query.setFirstResult(first);
				query.setMaxResults(max);
				List<DisputeTxn> list = toList(query.list());
				return list;
			}
		});
	}
	@Transactional
	@Override
	public int relatedRecords(final DisputeTxn disp) throws SQLException,
			HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<Integer>(){
			@Override
			public Integer doInHibernate(Session session)
					throws HibernateException {
				Query query = session.getNamedQuery("countRelated");
				query.setString("rrn", disp.getRrn());
				query.setString("slot", disp.getSlot());
				query.setString("stan", disp.getStan());
				Long len = (Long) query.uniqueResult();
				return len.intValue();
			}
		});
	}
	
	// settlement API
	@Transactional
	@Override
	public List<DisputeTxn> outgoingRp(final Date date, final String id) throws SQLException, HibernateException{
		return hibernateTemplate.execute(new HibernateCallback<List<DisputeTxn>>(){
			@Override
			public List<DisputeTxn> doInHibernate(Session session)
					throws HibernateException {
				Query query = session.getNamedQuery("disputeAcq");
				query.setDate("date", date);
				query.setString("id", id);
				query.setString("proc", "800001");
				return toList(query.list());
			}
			
		});
	}
	@Transactional
	@Override
	public List<DisputeTxn> incomingRp(final Date date, final String id) throws SQLException, HibernateException{
		return hibernateTemplate.execute(new HibernateCallback<List<DisputeTxn>>(){
			@Override
			public List<DisputeTxn> doInHibernate(Session session)
					throws HibernateException {
				Query query = session.getNamedQuery("disputeIss");
				query.setDate("date", date);
				query.setString("id", id);
				query.setString("proc", "800001");
				return toList(query.list());
			}
			
		});
	}
	
	@Transactional
	@Override
	public List<DisputeTxn> outgoingChb(final Date date, final String id) throws SQLException,
			HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<List<DisputeTxn>>(){
			@Override
			public List<DisputeTxn> doInHibernate(Session session)
					throws HibernateException {
				Query query = session.getNamedQuery("disputeIss");
				query.setDate("date", date);
				query.setString("id", id);
				query.setString("proc", "600001");
				return toList(query.list());
			}
			
		});
	}
	@Transactional
	@Override
	public List<DisputeTxn> incomingChb(final Date date, final String id) throws SQLException,
			HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<List<DisputeTxn>>(){
			@Override
			public List<DisputeTxn> doInHibernate(Session session)
					throws HibernateException {
				Query query = session.getNamedQuery("disputeAcq");
				query.setDate("date", date);
				query.setString("id", id);
				query.setString("proc", "600001");
				return toList(query.list());
			}
			
		});
	}
	@Transactional
	@Override
	public List<DisputeTxn> outgoingAdj(final Date date, final String id) throws SQLException,
			HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<List<DisputeTxn>>(){
			@Override
			public List<DisputeTxn> doInHibernate(Session session)
					throws HibernateException {
				Query query = session.getNamedQuery("disputeAcq");
				query.setDate("date", date);
				query.setString("id", id);
				query.setString("proc", "700001");
				return toList(query.list());
			}
			
		});
	}
	@Transactional
	@Override
	public List<DisputeTxn> incomingAdj(final Date date, final String id) throws SQLException,
			HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<List<DisputeTxn>>(){
			@Override
			public List<DisputeTxn> doInHibernate(Session session)
					throws HibernateException {
				Query query = session.getNamedQuery("disputeIss");
				query.setDate("date", date);
				query.setString("id", id);
				query.setString("proc", "700001");
				return toList(query.list());
			}
			
		});
	}
	@Transactional
	@Override
	public List<DisputeTxn> outgoinCp(final Date date, final String id)
			throws SQLException, HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<List<DisputeTxn>>(){
			@Override
			public List<DisputeTxn> doInHibernate(Session session)
					throws HibernateException {
				Query query = session.getNamedQuery("disputeIss");
				query.setDate("date", date);
				query.setString("id", id);
				query.setString("proc", "500001");
				return toList(query.list());
			}
			
		});
	}
	@Transactional
	@Override
	public List<DisputeTxn> incomingCp(final Date date, final String id)
			throws SQLException, HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<List<DisputeTxn>>(){
			@Override
			public List<DisputeTxn> doInHibernate(Session session)
					throws HibernateException {
				Query query = session.getNamedQuery("disputeAcq");
				query.setDate("date", date);
				query.setString("id", id);
				query.setString("proc", "500001");
				return toList(query.list());
			}
			
		});
	}
	@Transactional
	@Override
	public List<DisputeTxn> outgoingCrs(final Date date, final String id)
			throws SQLException, HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<List<DisputeTxn>>(){
			@Override
			public List<DisputeTxn> doInHibernate(Session session)
					throws HibernateException {
				Query query = session.getNamedQuery("disputeAcq");
				query.setDate("date", date);
				query.setString("id", id);
				query.setString("proc", "500002");
				return toList(query.list());
			}
			
		});
	}
	@Transactional
	@Override
	public List<DisputeTxn> incomingCrs(final Date date, final String id)
			throws SQLException, HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<List<DisputeTxn>>(){
			@Override
			public List<DisputeTxn> doInHibernate(Session session)
					throws HibernateException {
				Query query = session.getNamedQuery("disputeIss");
				query.setDate("date", date);
				query.setString("id", id);
				query.setString("proc", "500002");
				return toList(query.list());
			}
			
		});
	}
	@Transactional
	@Override
	public int getRecords(final DisputeTxn dispute) throws SQLException, HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<Integer>(){
			@Override
			public Integer doInHibernate(Session session)
					throws HibernateException {
				String hql = "select count(*) from DisputeTxn d join inner d.bcnxSettle b where b.mti = :mti and b.rrn = :rrn and b.stan = :stan and b.slot = :slot";
				SQLQuery sqlQuery = session.createSQLQuery(hql);
				sqlQuery.addEntity(DisputeTxn.class);
				sqlQuery.setString("mti", dispute.getMti());
				sqlQuery.setString("rrn", dispute.getRrn());
				sqlQuery.setString("stan", dispute.getStan());
				sqlQuery.setString("slot", dispute.getSlot());
				Long len = (Long) sqlQuery.uniqueResult();
				return len.intValue();
			}
			
		});
	}
	@Transactional
	@Override
	public List<DisputeTxn> getValidDispute() throws SQLException,
			HibernateException {
		String hql = "from DisputeTxn d where d.count> 0 order by d.date, d.time desc";
		return toList(hibernateTemplate.find(hql));
	}
	@Transactional
	@Override
	public void updateAll(final List<DisputeTxn> list) throws SQLException,
			HibernateException {
		hibernateTemplate.execute(new HibernateCallback<Void>(){
			@Override
			public Void doInHibernate(Session session)
					throws HibernateException {
				int count = 0;
				for(DisputeTxn disp : list){
					session.update(disp);
					if(++count%50==0){
						session.flush();
						session.clear();
					}
				}
				return null;
			}
			
		});
	}
	private List<DisputeTxn> toList(final List<?> beans){
		if(beans==null) return new ArrayList<DisputeTxn>();
		if(beans.isEmpty()) return new ArrayList<DisputeTxn>();
		int size = beans.size();
		DisputeTxn[] list = new DisputeTxn[size];
		list = beans.toArray(list);
		return Arrays.asList(list);
	}
}
