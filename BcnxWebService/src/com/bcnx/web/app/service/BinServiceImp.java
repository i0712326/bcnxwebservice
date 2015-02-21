package com.bcnx.web.app.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.bcnx.web.app.service.dao.BinDao;
import com.bcnx.web.app.service.entity.Bin;

public class BinServiceImp implements BinService {
	private static final Logger logger = Logger.getLogger(BinServiceImp.class);
	private BinDao binDao;
	public void setBinDao(BinDao binDao){
		this.binDao = binDao;
	}
	@Override
	public void saveBin(Bin bin) {
		try {
			binDao.save(bin);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to save bin", e);
		}
	}

	@Override
	public Bin getBin(Bin bin) {
		try {
			return binDao.getBin(bin);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get bin", e);
			return null;
		}
	}

	@Override
	public List<Bin> getBins(String bin) {
		try {
			return binDao.getBins(bin);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get bins", e);
			return null;
		}
	}

}
