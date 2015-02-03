package com.bcnx.web.app.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.transaction.annotation.Transactional;

import com.bcnx.web.app.service.dao.ReportDao;
import com.bcnx.web.app.service.entity.Member;
import com.bcnx.web.app.service.entity.Report;

public class ReportServiceImp implements ReportService {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ReportServiceImp.class);
	private ReportDao reportDao;
	public void setReportDao(ReportDao reportDao){
		this.reportDao = reportDao;
	}
	@Transactional
	@Override
	public void save(Report report) {
		try {
			reportDao.save(report);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to save report", e);
		}
	}
	@Transactional
	@Override
	public List<Report> getReports(Member member, Date start, Date end, int first, int max) {
		try {
			return reportDao.getReports(member,start, end, first, max);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get report", e);
			return null;
		}
	}
	@Transactional
	@Override
	public List<Report> getReports(Member member, Date date, int first, int max) {
		try {
			return reportDao.getReports(member, date, first, max);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get reports by member and date", e);
			return null;
		}
	}

}
