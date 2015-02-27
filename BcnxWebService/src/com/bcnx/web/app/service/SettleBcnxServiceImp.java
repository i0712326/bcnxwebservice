package com.bcnx.web.app.service;

import java.sql.Date;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.bcnx.web.app.service.dao.SettleBcnxDao;
import com.bcnx.web.app.service.entity.SettleBcnx;

public class SettleBcnxServiceImp implements SettleBcnxService {
	private static final Logger logger = Logger.getLogger(SettleBcnxServiceImp.class);
	private SettleBcnxDao settleBcnxDao;
	public void setSettleBcnxDao(SettleBcnxDao settleBcnxDao){
		this.settleBcnxDao = settleBcnxDao;
	}
	@Override
	public void save(SettleBcnx settleBcnx) {
		try {
			settleBcnxDao.save(settleBcnx);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to save settleBcnx data", e);
		}
	}

	@Override
	public SettleBcnx getSettleBcnx(Date date, String id) {
		try {
			return settleBcnxDao.getSettleBcnx(date,id);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get Settle Bcnx date", e);
			return null;
		}
	}

}
