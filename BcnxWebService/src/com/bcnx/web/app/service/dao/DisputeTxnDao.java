package com.bcnx.web.app.service.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;

import com.bcnx.web.app.service.entity.BcnxSettle;
import com.bcnx.web.app.service.entity.DisputeTxn;

public interface DisputeTxnDao {
	// save dispute
	public void save(DisputeTxn disputeTxn) throws SQLException, HibernateException;
	
	// response incoming dispute
	public void update(DisputeTxn disputeTxn) throws SQLException, HibernateException;
	
	// view incoming dispute
	public List<DisputeTxn> getInDisp(String id, int first, int max) throws SQLException, HibernateException;
	public int getInRecords(String id) throws SQLException, HibernateException;
	public List<DisputeTxn> getInDisp(String id, String proc, int first, int max) throws SQLException, HibernateException;
	public int getInByProc(String id, String proc) throws SQLException, HibernateException;
	
	// view outgoing
	public List<DisputeTxn> getOutDisp(String id, int first, int max) throws SQLException, HibernateException;
	public int getOutRecords(String id) throws SQLException, HibernateException;
	public List<DisputeTxn> getOutDisp(String id, String proc, int first, int max) throws SQLException, HibernateException;
	public int getOutByProc(String id, String proc) throws SQLException, HibernateException;
	
	// related case
	public List<DisputeTxn> relatedCase(DisputeTxn disp, int first, int max) throws SQLException, HibernateException;
	public int relatedRecords(DisputeTxn disp) throws SQLException, HibernateException;
	
	// view incoming
	public DisputeTxn getDisputeTxn(DisputeTxn disputeTxn) throws SQLException, HibernateException;
	public List<DisputeTxn> getDisputeTxns(DisputeTxn disputeTxn,int first, int max) throws SQLException, HibernateException;
	
	/*settlement data*/
	public List<BcnxSettle> outGoingChb(Date date, String id) throws SQLException, HibernateException;
	public List<BcnxSettle> incomingChb(Date date, String id) throws SQLException, HibernateException;
	public List<BcnxSettle> outGoingAdj(Date date, String id) throws SQLException, HibernateException;
	public List<BcnxSettle> incomingAdj(Date date, String id) throws SQLException, HibernateException;
	
	public int getRecords(DisputeTxn disputeTxn) throws SQLException, HibernateException;
	
	
	
}
