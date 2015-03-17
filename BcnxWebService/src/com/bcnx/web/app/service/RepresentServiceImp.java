package com.bcnx.web.app.service;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.bcnx.web.app.service.dao.DisputeTxnDao;
import com.bcnx.web.app.service.entity.DisputeTxn;

public class RepresentServiceImp implements RepresentService {
	private static final Logger logger = Logger.getLogger(RepresentServiceImp.class);
	private DisputeTxnDao disputeTxnDao;
	public void setDisputeTxnDao(DisputeTxnDao disputeTxnDao){
		this.disputeTxnDao = disputeTxnDao;
	}
	@Override
	public void save(DisputeTxn disp) {
		try {
			disputeTxnDao.save(disp);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to save representment", e);
		}
	}
	@Override
	public void update(DisputeTxn disp) {
		try {
			disputeTxnDao.update(disp);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to update representment", e);
		}
	}
	
}
