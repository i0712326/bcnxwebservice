package com.bcnx.web.app.service;

import java.sql.SQLException;

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

}
