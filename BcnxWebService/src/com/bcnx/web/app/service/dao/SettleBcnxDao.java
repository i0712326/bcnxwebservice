package com.bcnx.web.app.service.dao;

import java.sql.Date;
import java.sql.SQLException;

import org.hibernate.HibernateException;

import com.bcnx.web.app.service.entity.SettleBcnx;

public interface SettleBcnxDao {
	public void save(SettleBcnx settleBcnx) throws SQLException, HibernateException;
	public SettleBcnx getBcnx(Date date, String id) throws SQLException, HibernateException;
}
