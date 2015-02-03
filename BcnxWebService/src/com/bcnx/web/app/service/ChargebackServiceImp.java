package com.bcnx.web.app.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.bcnx.web.app.service.dao.DisputeTxnDao;
import com.bcnx.web.app.service.entity.DisputeTxn;

public class ChargebackServiceImp implements ChargebackService {
	private static final Logger logger = Logger.getLogger(ChargebackServiceImp.class);
	private DisputeTxnDao disputeTxnDao;
	public void setDisputeTxnDao(DisputeTxnDao disputeTxnDao){
		this.disputeTxnDao = disputeTxnDao;
	}
	@Override
	public void save(DisputeTxn disputeTxn) {
		try {
			disputeTxnDao.save(disputeTxn);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to save charge back", e);
		}
	}

	@Override
	public List<DisputeTxn> getInChb(DisputeTxn disp, int first, int max) {
		try {
			return disputeTxnDao.getInDisp(disp, first, max);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get incoming charge back", e);
			return null;
		}
	}

	@Override
	public List<DisputeTxn> getOutChb(DisputeTxn disp, int first, int max) {
		try {
			return disputeTxnDao.getOutDisp(disp, first, max);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to save charge back", e);
			return null;
		}
	}

}
