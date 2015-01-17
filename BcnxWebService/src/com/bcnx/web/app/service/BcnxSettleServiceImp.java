package com.bcnx.web.app.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.bcnx.web.app.service.dao.BcnxSettleDao;
import com.bcnx.web.app.service.entity.BcnxSettle;

public class BcnxSettleServiceImp implements BcnxSettleService {
	private static final Logger logger = Logger.getLogger(BcnxSettleServiceImp.class);
	private BcnxSettleDao bcnxSettleDao;
	public void setBcnxSettleDao(BcnxSettleDao bcnxSettleDao){
		this.bcnxSettleDao = bcnxSettleDao;
	}
	@Override
	public void save(BcnxSettle bcnxSettle) {
		try {
			bcnxSettleDao.save(bcnxSettle);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to save bcnxSettle", e);
		}
	}

	@Override
	public void saveAll(List<BcnxSettle> bcnxSettles) {
		try {
			bcnxSettleDao.saveAll(bcnxSettles);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to save bcnx settle list", e);;
		}
	}
	@Override
	public BcnxSettle getBcnxSettle(BcnxSettle bcnxSettle) {
		try {
			return bcnxSettleDao.getBcnxSettle(bcnxSettle);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get unique bcnx settle", e);
			return null;
		}
	}

	@Override
	public List<BcnxSettle> getBcnxSettles(BcnxSettle bcnxSettle, int first,
			int max) {
		try {
			return bcnxSettleDao.getBcnxSettles(bcnxSettle, first, max);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get bcnx settles", e);
			return null;
		}
	}

}
