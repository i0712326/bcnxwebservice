package com.bcnx.web.app.service.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;

import com.bcnx.web.app.service.entity.BcnxSettle;

public interface BcnxSettleDao {
	public void save(BcnxSettle bcnxSettle) throws SQLException, HibernateException;
	public void saveAll(List<BcnxSettle> bcnxSettles) throws SQLException, HibernateException;
	public BcnxSettle getBcnxSettle(BcnxSettle bcnxSettle) throws SQLException, HibernateException;
	public List<BcnxSettle> getBcnxSettles(BcnxSettle bcnxSettle, Date start,
			Date end, int first, int max) throws SQLException,
			HibernateException;
	/*public List<Date> getSettleDate() throws SQLException, HibernateException;*/
	public Date getMaxDate() throws SQLException, HibernateException;
	
	public List<BcnxSettle> getBcnxFinIss(Date date, String id) throws SQLException, HibernateException;;
	public List<BcnxSettle> getBcnxFinAcq(Date date, String id) throws SQLException, HibernateException;;
	public List<BcnxSettle> getBcnxRev(Date date, String id) throws SQLException, HibernateException;;
	public List<BcnxSettle> getBcnxErr(Date date, String id) throws SQLException, HibernateException;
	public int getRecords(BcnxSettle bs, Date start, Date end) throws SQLException, HibernateException;
}
