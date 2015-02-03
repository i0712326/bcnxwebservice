package com.bcnx.web.app.service.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;

import com.bcnx.web.app.service.entity.DisputeTxn;

public interface DisputeTxnDao {
	// save dispute
	public void save(DisputeTxn disputeTxn) throws SQLException, HibernateException;
	// view incoming dispute
	public List<DisputeTxn> getInDisp(DisputeTxn disp, int first, int max) throws SQLException, HibernateException;
	// response incoming dispute
	public void update(DisputeTxn disputeTxn) throws SQLException, HibernateException;
	// view out outgoing dispute
	public List<DisputeTxn> getOutDisp(DisputeTxn disp, int first, int max) throws SQLException, HibernateException;
	
	public DisputeTxn getDisputeTxn(DisputeTxn disputeTxn) throws SQLException, HibernateException;
	public List<DisputeTxn> getDisputeTxns(DisputeTxn disputeTxn,int first, int max) throws SQLException, HibernateException;
}
