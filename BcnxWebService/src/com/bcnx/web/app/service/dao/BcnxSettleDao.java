package com.bcnx.web.app.service.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;

import com.bcnx.web.app.service.entity.BcnxSettle;

public interface BcnxSettleDao {
	public void save(BcnxSettle bcnxSettle) throws SQLException, HibernateException;
	public void saveAll(List<BcnxSettle> bcnxSettles) throws SQLException, HibernateException;
	public BcnxSettle getBcnxSettle(BcnxSettle bcnxSettle) throws SQLException, HibernateException;
	public List<BcnxSettle> getBcnxSettles(BcnxSettle bcnxSettle,int first, int max) throws SQLException, HibernateException;
}
