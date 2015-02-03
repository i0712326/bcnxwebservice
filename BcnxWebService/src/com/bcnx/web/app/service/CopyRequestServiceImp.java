package com.bcnx.web.app.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.bcnx.web.app.service.dao.DisputeTxnDao;
import com.bcnx.web.app.service.entity.DisputeTxn;

public class CopyRequestServiceImp implements CopyRequestService {
	private static final Logger logger = Logger.getLogger(CopyRequestServiceImp.class);
	private DisputeTxnDao disputeTxnDao;
	public void setDisputeTxnDao(DisputeTxnDao disputeTxnDao){
		this.disputeTxnDao = disputeTxnDao;
	}
	@Override
	public void save(DisputeTxn disp) {
		try {
			disputeTxnDao.save(disp);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to save copy request", e);
		}
	}

	@Override
	public List<DisputeTxn> getInCpReq(DisputeTxn disp, int first, int max) {
		try {
			return disputeTxnDao.getInDisp(disp, first, max);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get incomming copy request", e);
			return null;
		}
	}

	@Override
	public void respCpReq(DisputeTxn disp) {
		try {
			disputeTxnDao.update(disp);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to response copy request", e);
		}
	}

	@Override
	public List<DisputeTxn> getOutCpReq(DisputeTxn disp, int first, int max) {
		try {
			return disputeTxnDao.getOutDisp(disp, first, max);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get out going copy request", e);
			return null;
		}
	}

}
