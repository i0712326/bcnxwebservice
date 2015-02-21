package com.bcnx.web.app.service.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;

import com.bcnx.web.app.service.entity.BcnxTxn;

public interface BcnxTxnDao {
	public void save(BcnxTxn bcnxTxn) throws SQLException, HibernateException;

	public void saveAll(List<BcnxTxn> bcnxTxns) throws SQLException,
			HibernateException;

	public void update(BcnxTxn bcnxTxn) throws SQLException, HibernateException;

	public BcnxTxn getBcnxTxn(BcnxTxn bcnxTxn) throws SQLException,
			HibernateException;

	public List<BcnxTxn> getBcnxTxns(BcnxTxn bcnxTxn, int first, int max)
			throws SQLException, HibernateException;

	public List<BcnxTxn> getBcnxTxns(String id,Date start, Date end, int first, int max)
			throws SQLException, HibernateException;
}
