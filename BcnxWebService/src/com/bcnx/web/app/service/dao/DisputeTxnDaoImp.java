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

import com.bcnx.web.app.service.entity.BcnxSettle;
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
	@Transactional
	@Override
	public DisputeTxn getDisputeTxn(final DisputeTxn disputeTxn) throws SQLException,
			HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<DisputeTxn>(){
			@Override
			public DisputeTxn doInHibernate(Session session)
					throws HibernateException {
				String sql = "SELECT * FROM DISPUTETXN D WHERE D.PROCC = :procc AND D.BCNXSETL_RRN = :rrn AND D.BCNXSETL_SLOT = :slot AND D.BCNXSETL_STAN = :stan";
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				sqlQuery.addEntity(DisputeTxn.class);
				sqlQuery.setString("procc", disputeTxn.getProcc());
				sqlQuery.setString("rrn", disputeTxn.getBcnxSettle().getRrn());
				sqlQuery.setString("slot", disputeTxn.getBcnxSettle().getSlot());
				sqlQuery.setString("stan", disputeTxn.getBcnxSettle().getStan());
				return (DisputeTxn) sqlQuery.uniqueResult();
			}
			
		});
	}
	@Transactional
	@Override
	public List<DisputeTxn> getDisputeTxns(DisputeTxn disputeTxn,int first, int max)
			throws SQLException, HibernateException {
		return hibernateTemplate.execute(new GetDisputes(disputeTxn,first,max));
	}
	private class GetDisputes implements HibernateCallback<List<DisputeTxn>>{
		private DisputeTxn disputeTxn;
		private int first;
		private int max;
		public GetDisputes(DisputeTxn disputeTxn, int first, int max){
			this.disputeTxn = disputeTxn;
			this.first = first;
			this.max = max;
		}
		@Override
		public List<DisputeTxn> doInHibernate(Session session)
				throws HibernateException {
			String sql = "SELECT D.ID, D.PROCC, D.REMARK, D.DATE, D.TIME, D.AMOUNT, D.FEE, D.FLAG, D.COUNT, D.ISSID, D.FILE, D.ACQID, D.PRB, D.USRDATA_USRID, "
					+"D.BCNXSETL_MTI, D.BCNXSETL_RRN, D.BCNXSETL_STAN, D.BCNXSETL_SLOT, D.REASONCODE_CODE FROM DISPUTETXN D, USRDATA U, MEMDATA M "
					+"WHERE U.USRID = D.USRDATA_USRID AND U.MEMDATA_IIN = M.IIN AND D.PROCC = :procc and D.BCNXSETL_RRN = :stan AND D.BCNXSETL_STAN = :stan" 
					+"AND D.BCNXSETL_SLOT = :slot AND D.ACQID = :iin";
			SQLQuery sqlQuery = session.createSQLQuery(sql);
			sqlQuery.addEntity(DisputeTxn.class);
			sqlQuery.setString("procc", disputeTxn.getProcc());
			sqlQuery.setString("slot", disputeTxn.getBcnxSettle().getSlot());
			sqlQuery.setString("rrn", disputeTxn.getBcnxSettle().getRrn());
			sqlQuery.setString("stan", disputeTxn.getBcnxSettle().getStan());
			sqlQuery.setString("mti", disputeTxn.getBcnxSettle().getMti());
			sqlQuery.setFirstResult(first);
			sqlQuery.setMaxResults(max);
			return toList(sqlQuery.list());
		}
	}
	@Transactional
	@Override
	public List<DisputeTxn> getInDisp(final String id, final int first, final int max) throws SQLException,
			HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<List<DisputeTxn>>(){
			@Override
			public List<DisputeTxn> doInHibernate(Session session)
					throws HibernateException {
				String sql = "select * from DISPUTETXN icr where icr.PROCC = '500001' and icr.ACQID = :id "
						+ " union select * from DISPUTETXN irr where irr.PROCC = '500002' and irr.ISSID = :id "
						+ " union select * from DISPUTETXN icb where icb.PROCC = '600001' and icb.ACQID = :id "
						+ " union select * from DISPUTETXN iad where iad.PROCC = '700001' and iad.ISSID = :id "
						+ " union select * from DISPUTETXN irp where irp.PROCC = '800001' and irp.ISSID = :id ORDER BY TIME, DATE DESC";
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				sqlQuery.addEntity(DisputeTxn.class);
				sqlQuery.setString("id", id);
				sqlQuery.setFirstResult(first);
				sqlQuery.setMaxResults(max);
				return toList(sqlQuery.list());
			}
			
		});
	}
	@Override
	public int getInRecords(final String id) throws SQLException,
			HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<Integer>(){
			@Override
			public Integer doInHibernate(Session session)
					throws HibernateException {
				String sql = "select COUNT(*) from ( select * from DISPUTETXN icr where icr.PROCC = '500001' and icr.ACQID = :id "
					 +" union select * from DISPUTETXN irr where irr.PROCC = '500002' and irr.ISSID = :id "
					 +" union select * from DISPUTETXN icb where icb.PROCC = '600001' and icb.ACQID = :id " 
					 +" union select * from DISPUTETXN iad where iad.PROCC = '700001' and iad.ISSID = :id "
					 +" union select * from DISPUTETXN irp where irp.PROCC = '800001' and irp.ISSID = :id ) TXN";
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				sqlQuery.addEntity(DisputeTxn.class);
				sqlQuery.setString("id", id);
				Long len = (Long) sqlQuery.uniqueResult();
				return len.intValue();
			}
			
		});
	}
	@Transactional
	@Override
	public List<DisputeTxn> getInDisp(final String id, final String proc, final int first, final int max)
			throws SQLException, HibernateException {
		return toList(hibernateTemplate.execute(new HibernateCallback<List<DisputeTxn>>(){
			@Override
			public List<DisputeTxn> doInHibernate(Session session)
					throws HibernateException {
				String sql = "select * from DISPUTETXN icr where icr.PROCC = :proc and icr.ACQID = :id "
						+ " union select * from DISPUTETXN irr where irr.PROCC = :proc and irr.ISSID = :id "
						+ " union select * from DISPUTETXN icb where icb.PROCC = :proc and icb.ACQID = :id "
						+ " union select * from DISPUTETXN iad where iad.PROCC = :proc and iad.ISSID = :id "
						+ " union select * from DISPUTETXN irp where irp.PROCC = :proc and irp.ISSID = :id ORDER BY TIME, DATE DESC";
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				sqlQuery.addEntity(DisputeTxn.class);
				sqlQuery.setString("id", id);
				sqlQuery.setString("proc", proc);
				sqlQuery.setFirstResult(first);
				sqlQuery.setMaxResults(max);
				return toList(sqlQuery.list());
			}
			
		}));
	}
	@Transactional
	@Override
	public int getInByProc(final String id, final String proc) throws SQLException, HibernateException{
		return hibernateTemplate.execute(new HibernateCallback<Integer>(){
			@Override
			public Integer doInHibernate(Session session)
					throws HibernateException {
				String sql = "select COUNT(*) from ( select * from DISPUTETXN icr where icr.PROCC = :proc and icr.ACQID = :id "
						+ " union select * from DISPUTETXN irr where irr.PROCC = :proc and irr.ISSID = :id "
						+ " union select * from DISPUTETXN icb where icb.PROCC = :proc and icb.ACQID = :id "
						+ " union select * from DISPUTETXN iad where iad.PROCC = :proc and iad.ISSID = :id "
						+ " union select * from DISPUTETXN irp where irp.PROCC = :proc and irp.ISSID = :id ) TXN";
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				sqlQuery.addEntity(DisputeTxn.class);
				sqlQuery.setString("id", id);
				sqlQuery.setString("proc", proc);
				Long len = (Long) sqlQuery.uniqueResult();
				return len.intValue();
			}
		});
	}
	
	// outgoing retrieval module
	@Transactional
	@Override
	public List<DisputeTxn> getOutDisp(final String id, final int first, final int max) throws SQLException,
			HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<List<DisputeTxn>>(){
			@Override
			public List<DisputeTxn> doInHibernate(Session session)
					throws HibernateException {
				String sql = "select * from DISPUTETXN icr where icr.PROCC = '500001' and icr.ISSID = :id "
					+ " union select * from DISPUTETXN irr where irr.PROCC = '500002' and irr.ACQID = :id "
					+ " union select * from DISPUTETXN icb where icb.PROCC = '600001' and icb.ISSID = :id "
					+ " union select * from DISPUTETXN iad where iad.PROCC = '700001' and iad.ACQID = :id "
					+ " union select * from DISPUTETXN irp where irp.PROCC = '800001' and irp.ACQID = :id ORDER BY TIME, DATE ";
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				sqlQuery.addEntity(DisputeTxn.class);
				sqlQuery.setString("id", id);
				sqlQuery.setFirstResult(first);
				sqlQuery.setMaxResults(max);
				return toList(sqlQuery.list());
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
				String sql = "select COUNT(*) from ( select * from DISPUTETXN icr where icr.PROCC = '500001' and icr.ISSID = :id "
						+ " union select * from DISPUTETXN irr where irr.PROCC = '500002' and irr.ACQID = :id "
						+ " union select * from DISPUTETXN icb where icb.PROCC = '600001' and icb.ISSID = :id "
						+ " union select * from DISPUTETXN iad where iad.PROCC = '700001' and iad.ACQID = :id "
						+ " union select * from DISPUTETXN irp where irp.PROCC = '800001' and irp.ACQID = :id ) TXN ";
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				sqlQuery.addEntity(DisputeTxn.class);
				sqlQuery.setString("id", id);
				Long len = (Long) sqlQuery.uniqueResult();
				return len.intValue();
			}
			
		});
	}
	@Transactional
	@Override
	public List<DisputeTxn> getOutDisp(final String id, final String proc, final int first, final int max)
			throws SQLException, HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<List<DisputeTxn>>(){
			@Override
			public List<DisputeTxn> doInHibernate(Session session)
					throws HibernateException {
				String sql = "select * from DISPUTETXN icr where icr.PROCC = :proc and icr.ISSID = :id "
					+ " union select * from DISPUTETXN irr where irr.PROCC = :proc and irr.ACQID = :id "
					+ " union select * from DISPUTETXN icb where icb.PROCC = :proc and icb.ISSID = :id "
					+ " union select * from DISPUTETXN iad where iad.PROCC = :proc and iad.ACQID = :id "
					+ " union select * from DISPUTETXN irp where irp.PROCC = :proc and irp.ACQID = :id ORDER BY TIME, DATE ";
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				sqlQuery.addEntity(DisputeTxn.class);
				sqlQuery.setString("id", id);
				sqlQuery.setString("proc", proc);
				sqlQuery.setFirstResult(first);
				sqlQuery.setMaxResults(max);
				return toList(sqlQuery.list());
			}
			
		});
	}
	@Transactional
	@Override
	public int getOutByProc(final String id, final String proc)
			throws SQLException, HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<Integer>(){
			@Override
			public Integer doInHibernate(Session session)
					throws HibernateException {
				String sql = "select COUNT(*) from ( select * from DISPUTETXN icr where icr.PROCC = :proc and icr.ISSID = :id "
					+ " union select * from DISPUTETXN irr where irr.PROCC = :proc and irr.ACQID = :id "
					+ " union select * from DISPUTETXN icb where icb.PROCC = :proc and icb.ISSID = :id "
					+ " union select * from DISPUTETXN iad where iad.PROCC = :proc and iad.ACQID = :id "
					+ " union select * from DISPUTETXN irp where irp.PROCC = :proc and irp.ACQID = :id ) TXN";
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				sqlQuery.addEntity(DisputeTxn.class);
				sqlQuery.setString("id", id);
				sqlQuery.setString("proc", proc);
				Long len = (Long) sqlQuery.uniqueResult();
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
				String hql = "select d from DisputeTxn d inner join d.BcnxSettle db where db.rrn = :rrn and db.slot = :slot and db.stan = :stan and db.mti = :mti";
				Query query = session.createQuery(hql);
				query.setString("rrn", disp.getBcnxSettle().getRrn());
				query.setString("slot", disp.getBcnxSettle().getSlot());
				query.setString("stan", disp.getBcnxSettle().getStan());
				query.setString("mti", disp.getBcnxSettle().getMti());
				query.setFirstResult(first);
				query.setMaxResults(max);
				return toList(query.list());
			}
		});
	}
	@Override
	public int relatedRecords(final DisputeTxn disp) throws SQLException,
			HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<Integer>(){
			@Override
			public Integer doInHibernate(Session session)
					throws HibernateException {
				String hql = "select count(d) from DisputeTxn d inner join d.BcnxSettle db where db.rrn = :rrn and db.slot = :slot and db.stan = :stan and db.mti = :mti";
				Query query = session.createQuery(hql);
				query.setString("rrn", disp.getBcnxSettle().getRrn());
				query.setString("slot", disp.getBcnxSettle().getSlot());
				query.setString("stan", disp.getBcnxSettle().getStan());
				query.setString("mti", disp.getBcnxSettle().getMti());
				Long len = (Long) query.uniqueResult();
				return len.intValue();
			}
		});
	}
	
	// settlement API
	@Transactional
	public List<BcnxSettle> outGoingRp(Date date, String id) throws SQLException, HibernateException{
		String hql = "from DisputeTxn d where d.date = :date and d.acq = :id and d.procc = :proc order by d.time";
		String[] param = {"date","id","proc"};
		Object[] values = {date, id, "800001"};
		return toSettles(toList(hibernateTemplate.findByNamedParam(hql,param,values)));
	}
	@Transactional
	public List<BcnxSettle> inComingRp(Date date, String id) throws SQLException, HibernateException{
		String hql = "from DisputeTxn d where d.date = :date and d.iss = :id and d.procc = :proc order by d.time";
		String[] param = {"date","id","proc"};
		Object[] values = {date, id, "800001"};
		return toSettles(toList(hibernateTemplate.findByNamedParam(hql,param,values)));
	}
	
	@Transactional
	@Override
	public List<BcnxSettle> outGoingChb(Date date, String id) throws SQLException,
			HibernateException {
		String hql = "from DisputeTxn d where d.date = :date and d.iss = :id and d.procc = :proc order by d.time";
		String[] param = {"date","id","proc"};
		Object[] values = {date, id, "600001"};
		return toSettles(toList(hibernateTemplate.findByNamedParam(hql,param,values)));
	}
	@Transactional
	@Override
	public List<BcnxSettle> incomingChb(Date date, String id) throws SQLException,
			HibernateException {
		String hql = "from DisputeTxn d where d.date = :date and d.acq = :id and d.procc = :proc order by d.time";
		String[] param = {"date","id","proc"};
		Object[] values = {date, id, "600001"};
		return toSettles(toList(hibernateTemplate.findByNamedParam(hql,param,values)));
	}
	@Transactional
	@Override
	public List<BcnxSettle> outGoingAdj(Date date, String id) throws SQLException,
			HibernateException {
		String hql = "from DisputeTxn d where d.date = :date and d.acq = :id and d.procc = :proc order by d.time";
		String[] param = {"date","id","proc"};
		Object[] values = {date, id, "700001"};
		return toSettles(toList(hibernateTemplate.findByNamedParam(hql,param,values)));
	}
	@Transactional
	@Override
	public List<BcnxSettle> incomingAdj(Date date, String id) throws SQLException,
			HibernateException {
		String hql = "from DisputeTxn d where d.date = :date and d.iss = :id and d.procc = :proc order by d.time";
		String[] param = {"date","id","proc"};
		Object[] values = {date, id, "700001"};
		return toSettles(toList(hibernateTemplate.findByNamedParam(hql,param,values)));
	}
	
	@Transactional
	@Override
	public int getRecords(final DisputeTxn dispute) throws SQLException, HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<Integer>(){
			@Override
			public Integer doInHibernate(Session session)
					throws HibernateException {
				String sql = "SELECT COUNT(*) FROM DISPUTETXN WHERE BCNXSETL_MTI = :mti AND BCNXSETL_RRN = :rrn and AND BCNXSETL_STAN = :stan and BCNXSETL_SLOT = :slot";
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				sqlQuery.addEntity(DisputeTxn.class);
				sqlQuery.setString("mti", dispute.getBcnxSettle().getMti());
				sqlQuery.setString("rrn", dispute.getBcnxSettle().getRrn());
				sqlQuery.setString("stan", dispute.getBcnxSettle().getStan());
				sqlQuery.setString("slot", dispute.getBcnxSettle().getSlot());
				Long len = (Long) sqlQuery.uniqueResult();
				return len.intValue();
			}
			
		});
	}
	
	protected List<DisputeTxn> toList(final List<?> beans){
		if(beans==null) return new ArrayList<DisputeTxn>();
		if(beans.isEmpty()) return new ArrayList<DisputeTxn>();
		int size = beans.size();
		DisputeTxn[] list = new DisputeTxn[size];
		list = beans.toArray(list);
		return Arrays.asList(list);
	}
	private List<BcnxSettle> toSettles(List<DisputeTxn> disps){
		List<BcnxSettle> list = new ArrayList<BcnxSettle>();
		for(DisputeTxn bs : disps){
			BcnxSettle settle = new BcnxSettle();
			settle = bs.getBcnxSettle();
			settle.setAmount(bs.getAmount());
			settle.setProc(bs.getProcc());
			settle.setDate(bs.getDate());
			settle.setTime(bs.getTime());
			settle.setFee(bs.getFee());
			list.add(settle);
		}
		return list;
	}
}
