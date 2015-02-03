package com.bcnx.web.app.service;

import java.sql.SQLException;
import java.util.List;

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
	public List<DisputeTxn> getInRpm(DisputeTxn disp, int first, int max) {
		// TODO Auto-generated method stub
		try {
			return disputeTxnDao.getInDisp(disp, first, max);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get incoming representment", e);
			return null;
		}
	}

	@Override
	public List<DisputeTxn> getOutRpm(DisputeTxn disp, int first, int max) {
		try {
			return disputeTxnDao.getOutDisp(disp, first, max);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get outgoing representment", e);
			return null;
		}
	}

}
