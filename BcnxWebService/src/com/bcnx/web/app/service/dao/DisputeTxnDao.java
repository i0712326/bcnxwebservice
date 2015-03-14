package com.bcnx.web.app.service.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;

import com.bcnx.web.app.service.entity.DisputeTxn;

public interface DisputeTxnDao {
	// save dispute
	public void save(DisputeTxn disputeTxn) throws SQLException, HibernateException;
	
	// response incoming dispute
	public void update(DisputeTxn disputeTxn) throws SQLException, HibernateException;
	
	// view incoming dispute
	public List<DisputeTxn> getInDisp(String id, int first, int max) throws SQLException, HibernateException;
	public int getInRecords(String id) throws SQLException, HibernateException;
	
	public List<DisputeTxn> getInDisp(String id,  String card, String rrn, String stan, String proc, Date start, Date end, int first, int max) throws SQLException, HibernateException;
	public int getInBy(String id, String card, String rrn, String stan, String proc, Date start, Date end) throws SQLException, HibernateException;
	
	// view outgoing
	public List<DisputeTxn> getOutDisp(String id, int first, int max) throws SQLException, HibernateException;
	public int getOutRecords(String id) throws SQLException, HibernateException;
	
	public List<DisputeTxn> getOutDisp(String id,  String card, String rrn, String stan, String proc, Date start, Date end, int first, int max) throws SQLException, HibernateException;
	public int getOutBy(String id,  String card, String rrn, String stan, String proc, Date start, Date end) throws SQLException, HibernateException;
	
	// related case
	public List<DisputeTxn> relatedCase(DisputeTxn disp, int first, int max) throws SQLException, HibernateException;
	public int relatedRecords(DisputeTxn disp) throws SQLException, HibernateException;
	
	// view incoming
	public DisputeTxn getDisputeTxn(DisputeTxn disputeTxn) throws SQLException, HibernateException;
	
	/*settlement data*/
	public List<DisputeTxn> outgoingChb(Date date, String id) throws SQLException, HibernateException;
	public List<DisputeTxn> incomingChb(Date date, String id) throws SQLException, HibernateException;
	
	public List<DisputeTxn> outgoingAdj(Date date, String id) throws SQLException, HibernateException;
	public List<DisputeTxn> incomingAdj(Date date, String id) throws SQLException, HibernateException;
	
	public List<DisputeTxn> outgoingRp(Date date, String id) throws SQLException, HibernateException;
	public List<DisputeTxn> incomingRp(Date date, String id) throws SQLException, HibernateException;
	
	public List<DisputeTxn> outgoinCp(Date date, String id) throws SQLException, HibernateException;
	public List<DisputeTxn> incomingCp(Date date, String id) throws SQLException, HibernateException;
	
	public List<DisputeTxn> outgoingCrs(Date date, String id) throws SQLException, HibernateException;
	public List<DisputeTxn> incomingCrs(Date date, String id) throws SQLException, HibernateException;
	
	public int getRecords(DisputeTxn disputeTxn) throws SQLException, HibernateException;
	
	// update valid transaction
	public List<DisputeTxn> getValidDispute() throws SQLException, HibernateException;
	public void updateAll(List<DisputeTxn> list) throws SQLException, HibernateException;
}
