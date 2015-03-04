package com.bcnx.web.app.service.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;

import com.bcnx.web.app.service.entity.SettleBcnx;

public interface SettleBcnxDao {
	public void save(SettleBcnx settleBcnx) throws SQLException, HibernateException;
	public SettleBcnx getSettleBcnx(Date date, String id) throws SQLException, HibernateException;
	public void saveAll(List<SettleBcnx> settleBcnxs) throws SQLException, HibernateException;
	public List<SettleBcnx> getSettleBcnxs(Date date) throws SQLException,	HibernateException;
}
