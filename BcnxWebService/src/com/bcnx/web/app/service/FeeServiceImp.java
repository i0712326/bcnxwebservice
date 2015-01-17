package com.bcnx.web.app.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.bcnx.web.app.service.dao.FeeDao;
import com.bcnx.web.app.service.entity.Fee;

public class FeeServiceImp implements FeeService {
	private static final Logger logger = Logger.getLogger(FeeServiceImp.class);
	private FeeDao feeDao;
	public void setFeeDao(FeeDao feeDao) {
		this.feeDao = feeDao;
	}
	@Override
	public void save(Fee fee) {
		try {
			feeDao.save(fee);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to save fee", e);
		}
	}
	@Override
	public Fee getFee(Fee fee) {
		try {
			return feeDao.getFee(fee);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get fee data",e);
			return null;
		}
	}

	@Override
	public List<Fee> getFees(String cardType) {
		try {
			return feeDao.getFees(cardType);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get fee data",e);
			return null;
		}
	}

}
