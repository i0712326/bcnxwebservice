package com.bcnx.web.app.service.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;

import com.bcnx.web.app.service.entity.Bin;

public interface BinDao {
	public void save(Bin bin) throws SQLException, HibernateException;
	public Bin getBin(Bin bin) throws SQLException, HibernateException;
	public List<Bin> getBins(String bin) throws SQLException, HibernateException;
}
