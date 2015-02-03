package com.bcnx.web.app.service.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.transaction.annotation.Transactional;

import com.bcnx.web.app.service.entity.DisputeTxn;

public class AdjustmentDaoImp extends DisputeTxnDaoImp {
	private static final String ADJ = "700001";
	@Transactional
	@Override
	public void save(DisputeTxn disputeTxn) throws SQLException,
			HibernateException {
		super.save(disputeTxn);
	}
	@Transactional
	@Override
	public List<DisputeTxn> getInDisp(final DisputeTxn disp, final int first, final int max)
			throws SQLException, HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<List<DisputeTxn>>(){
			@Override
			public List<DisputeTxn> doInHibernate(Session session)
					throws HibernateException {
				String sql =  "SELECT D.ID, D.PROCC, D.REMARK, D.DATE, D.TIME, D.AMOUNT, D.FEE, D.FLAG, D.COUNT, D.ISSID, D.FILE, D.ACQID, D.PRB, D.USRDATA_USRID, "
						+"D.BCNXSETL_MTI, D.BCNXSETL_RRN, D.BCNXSETL_STAN, D.BCNXSETL_SLOT, D.REASONCODE_CODE FROM DISPUTETXN D, USRDATA U, MEMDATA M "
						+"WHERE U.USRID = D.USRDATA_USRID AND U.MEMDATA_IIN = M.IIN AND D.PROCC = :procc and D.BCNXSETL_RRN = :rrn AND D.BCNXSETL_STAN = :stan " 
						+"AND D.BCNXSETL_SLOT = :slot AND D.ISSID = :iin";
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				sqlQuery.addEntity(DisputeTxn.class);
				sqlQuery.setString("procc", ADJ);
				sqlQuery.setString("rrn", disp.getBcnxSettle().getRrn());
				sqlQuery.setString("stan", disp.getBcnxSettle().getStan());
				sqlQuery.setString("slot", disp.getBcnxSettle().getSlot());
				sqlQuery.setString("iin", disp.getUser().getMember().getIin());
				sqlQuery.setFirstResult(first);
				sqlQuery.setMaxResults(max);
				return toList(sqlQuery.list());
			}
		});
	}
	@Transactional
	@Override
	public List<DisputeTxn> getOutDisp(final DisputeTxn disp, final int first, final int max)
			throws SQLException, HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<List<DisputeTxn>>(){
			@Override
			public List<DisputeTxn> doInHibernate(Session session)
					throws HibernateException {
				String sql =  "SELECT D.ID, D.PROCC, D.REMARK, D.DATE, D.TIME, D.AMOUNT, D.FEE, D.FLAG, D.COUNT, D.ISSID, D.FILE, D.ACQID, D.PRB, D.USRDATA_USRID, "
						+"D.BCNXSETL_MTI, D.BCNXSETL_RRN, D.BCNXSETL_STAN, D.BCNXSETL_SLOT, D.REASONCODE_CODE FROM DISPUTETXN D, USRDATA U, MEMDATA M "
						+"WHERE U.USRID = D.USRDATA_USRID AND U.MEMDATA_IIN = M.IIN AND D.PROCC = :procc and D.BCNXSETL_RRN = :rrn AND D.BCNXSETL_STAN = :stan " 
						+"AND D.BCNXSETL_SLOT = :slot AND D.ACQID = :iin";
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				sqlQuery.addEntity(DisputeTxn.class);
				sqlQuery.setString("procc", ADJ);
				sqlQuery.setString("rrn", disp.getBcnxSettle().getRrn());
				sqlQuery.setString("stan", disp.getBcnxSettle().getStan());
				sqlQuery.setString("slot", disp.getBcnxSettle().getSlot());
				sqlQuery.setString("iin", disp.getUser().getMember().getIin());
				sqlQuery.setFirstResult(first);
				sqlQuery.setMaxResults(max);
				return toList(sqlQuery.list());
			}
		});
	}
	
}
