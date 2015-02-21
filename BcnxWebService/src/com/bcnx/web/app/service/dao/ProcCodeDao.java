package com.bcnx.web.app.service.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;

import com.bcnx.web.app.service.entity.ProcCode;

public interface ProcCodeDao {
	public void save(ProcCode procCode) throws SQLException, HibernateException;
	public ProcCode getProcCode(ProcCode procCode) throws SQLException, HibernateException;
	public List<ProcCode> getProcCodes(ProcCode procCode, int first, int max) throws SQLException, HibernateException;
}
