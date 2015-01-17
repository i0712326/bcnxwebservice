package com.bcnx.web.app.service.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;

import com.bcnx.web.app.service.entity.Fee;

public interface FeeDao {
	public void save(Fee fee) throws SQLException, HibernateException;
	public Fee getFee(Fee fee) throws SQLException, HibernateException;
	public List<Fee> getFees(String cardType) throws SQLException, HibernateException;
}
