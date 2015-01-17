package com.bcnx.web.app.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.bcnx.web.app.service.dao.DisputeTxnDao;
import com.bcnx.web.app.service.entity.DisputeTxn;

public class DisputeTxnServiceImp implements DisputeTxnService {
	private static final Logger logger = Logger.getLogger(DisputeTxnServiceImp.class);
	private DisputeTxnDao disputeTxnDao;
	public void setDisputeTxnDao(DisputeTxnDao disputeTxnDao){
		this.disputeTxnDao = disputeTxnDao;
	}
	@Override
	public void save(DisputeTxn disputeTxn) {
		try {
			disputeTxnDao.save(disputeTxn);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try save dispute data", e);
		}
	}

	@Override
	public DisputeTxn getDisputeTxn(DisputeTxn disputeTxn) {
		try {
			return disputeTxnDao.getDisputeTxn(disputeTxn);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try save dispute data", e);
			return null;
		}
	}

	@Override
	public List<DisputeTxn> getDisputeTxns(DisputeTxn disputeTxn, int first, int max) {
		try {
			return disputeTxnDao.getDisputeTxns(disputeTxn,first,max);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try save dispute data", e);
			return null;
		}
	}

}
