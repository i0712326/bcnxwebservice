package com.bcnx.web.app.service.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;

import com.bcnx.web.app.service.entity.Member;
import com.bcnx.web.app.service.entity.Report;

public interface ReportDao {
	public void save(Report report) throws SQLException, HibernateException;
	public Report getReport(int id) throws SQLException, HibernateException;
	public List<Report> getReports(Date date, int first, int max) throws SQLException, HibernateException;
	public List<Report> getReports(Date start, Date end, int first, int max) throws SQLException, HibernateException;
	public List<Report> getReports(Member member, int first, int max) throws SQLException, HibernateException;
}
