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
		return toList(hibernateTemplate.execute(new GetDisputes(disputeTxn,first,max)));
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
		return toList(hibernateTemplate.execute(new HibernateCallback<List<DisputeTxn>>(){
			@Override
			public List<DisputeTxn> doInHibernate(Session session)
					throws HibernateException {
				String hql = "select icr from DisputeTxn icr where icr.proc = '500001' and icr.acq = :id order by icr.date, icr.time "
						+ " union select irr from DisputeTxn irr where irr.proc = '500002' and irr.iss = :id order by irr.date, irr.time "
						+ " union select icb from DisputeTxn icb where icb.proc = '600001' and icb.acq = :id order by icb.date, icb.time "
						+ " union select iad from DisputeTxn iad where iad.proc = '700001' and iad.iss = :id order by iad.date, iad.time "
						+ " union select irp from DisputeTxn irp where irp.proc = '800001' and irp.iss = :id order by irp.date, irp.time ";
				Query query = session.createQuery(hql);
				query.setString("id", id);
				query.setFirstResult(first);
				query.setMaxResults(max);
				return toList(query.list());
			}
			
		}));
	}
	@Transactional
	@Override
	public List<DisputeTxn> getInDisp(DisputeTxn disp, int first, int max)
			throws SQLException, HibernateException {
		return null;
	}
	@Transactional
	@Override
	public List<DisputeTxn> getOutDisp(final String id, final int first, final int max) throws SQLException,
			HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<List<DisputeTxn>>(){
			@Override
			public List<DisputeTxn> doInHibernate(Session session)
					throws HibernateException {
				String hql = "select icr from DisputeTxn icr where icr.proc = '500001' and icr.iss = :id order by icr.date, icr.time "
					+ " union select irr from DisputeTxn irr where irr.proc = '500002' and irr.acq = :id order by irr.date, irr.time "
					+ " union select icb from DisputeTxn icb where icb.proc = '600001' and icb.iss = :id order by icb.date, icb.time "
					+ " union select iad from DisputeTxn iad where iad.proc = '700001' and iad.acq = :id order by iad.date, iad.time "
					+ " union select irp from DisputeTxn irp where irp.proc = '800001' and irp.acq = :id order by irp.date, irp.time ";
				Query query = session.createQuery(hql);
				query.setString("id", id);
				query.setFirstResult(first);
				query.setMaxResults(max);
				return toList(query.list());
			}
			
		});
	}
	@Transactional
	@Override
	public List<DisputeTxn> getOutDisp(DisputeTxn disp, int first, int max)
			throws SQLException, HibernateException {
		return null;
	}
	@Transactional
	@Override
	public void update(DisputeTxn disputeTxn) throws SQLException,
			HibernateException {
		hibernateTemplate.update(disputeTxn);
	}
	@Transactional
	public List<DisputeTxn> outCopyRequest(String id, Date date, int first, int max) throws SQLException, HibernateException{
		String hql = "from DisputeTxn d where d.date = :date and d.iss = :id and d.procc = :proc order by d.time";
		String[] param = {"date","id","proc"};
		Object[] values = {date, id, "500001"};
		return toList(hibernateTemplate.findByNamedParam(hql,param,values));
	}
	@Transactional
	public List<DisputeTxn> inCopyRequest(String id, Date date, int first, int max) throws SQLException, HibernateException{
		String hql = "from DisputeTxn d where d.date = :date and d.acq = :id and d.procc = :proc order by d.time";
		String[] param = {"date","id","proc"};
		Object[] values = {date, id, "500001"};
		return toList(hibernateTemplate.findByNamedParam(hql,param,values));
	}
	@Transactional
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
