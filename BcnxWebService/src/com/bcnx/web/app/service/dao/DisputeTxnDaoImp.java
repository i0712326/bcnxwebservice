package com.bcnx.web.app.service.dao;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.hibernate.HibernateException;
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
	
	@Override
	public List<DisputeTxn> getInDisp(DisputeTxn disp, int first, int max) throws SQLException,
			HibernateException {
		return null;
	}
	@Override
	public void update(DisputeTxn disputeTxn) throws SQLException,
			HibernateException {
	}
	@Override
	public List<DisputeTxn> getOutDisp(DisputeTxn disp, int first, int max) throws SQLException,
			HibernateException {
		return null;
	}
	protected List<DisputeTxn> toList(final List<?> beans){
		if(beans==null) return null;
		if(beans.isEmpty()) return null;
		int size = beans.size();
		DisputeTxn[] list = new DisputeTxn[size];
		list = beans.toArray(list);
		return Arrays.asList(list);
	}
	
}
