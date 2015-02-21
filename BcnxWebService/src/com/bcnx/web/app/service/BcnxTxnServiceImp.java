package com.bcnx.web.app.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.transaction.annotation.Transactional;

import com.bcnx.web.app.service.dao.BcnxTxnDao;
import com.bcnx.web.app.service.entity.BcnxTxn;

public class BcnxTxnServiceImp implements BcnxTxnService {
	private static final Logger logger = Logger.getLogger(BcnxTxnServiceImp.class);
	private BcnxTxnDao bcnxTxnDao;
	public void setBcnxTxnDao(BcnxTxnDao bcnxTxnDao){
		this.bcnxTxnDao = bcnxTxnDao;
	}
	@Transactional
	@Override
	public void save(BcnxTxn bcnxTxn){
		try {
			bcnxTxnDao.save(bcnxTxn);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to save transaction", e);
		}
	}
	@Transactional
	@Override
	public void update(BcnxTxn bcnxTxn){
		try {
			bcnxTxnDao.update(bcnxTxn);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to update transaction", e);
		}
	}
	@Transactional
	@Override
	public BcnxTxn getBcnxTxn(BcnxTxn bcnxTxn) {
		try {
			return bcnxTxnDao.getBcnxTxn(bcnxTxn);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get transaction", e);
			return null;
		}
	}
	@Transactional
	@Override
	public List<BcnxTxn> getBcnxTxns(BcnxTxn bcnxTxn, int first, int max) {
		try {
			return bcnxTxnDao.getBcnxTxns(bcnxTxn, first, max);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get transaction data", e);
			return null;
		}
	}
	@Override
	public void saveAll(List<BcnxTxn> bcnxTxns) {
		try {
			bcnxTxnDao.saveAll(bcnxTxns);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to save list of bcnx list", e);
		}
	}
}
