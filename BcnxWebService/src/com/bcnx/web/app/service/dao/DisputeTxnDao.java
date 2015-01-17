package com.bcnx.web.app.service.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;

import com.bcnx.web.app.service.entity.DisputeTxn;

public interface DisputeTxnDao {
	public void save(DisputeTxn disputeTxn) throws SQLException, HibernateException;
	public DisputeTxn getDisputeTxn(DisputeTxn disputeTxn) throws SQLException, HibernateException;
	public List<DisputeTxn> getDisputeTxns(DisputeTxn disputeTxn,int first, int max) throws SQLException, HibernateException;
}
