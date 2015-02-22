package com.bcnx.web.app.service;

import java.sql.Date;
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
	public List<BcnxSettle> getBcnxSettles(BcnxSettle bcnxSettle, Date start, Date end, int first,
			int max) {
		try {
			return bcnxSettleDao.getBcnxSettles(bcnxSettle, start, end,first, max);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get bcnx settles", e);
			return null;
		}
	}
	
	@Override
	public int getRecords(BcnxSettle bs, Date start, Date end) {
		try {
			return bcnxSettleDao.getRecords(bs,start,end);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to count records from bcnx settl",e);
			return 0;
		}
	}
	// batch date checking
	
	@Override
	public Date getMaxDate() {
		try {
			return bcnxSettleDao.getMaxDate();
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get max settle date", e);
			return null;
		}
	}

	// settlement API
	@Override
	public List<BcnxSettle> getBcnxFinIss(Date date, String id) {
		try {
			return bcnxSettleDao.getBcnxFinIss(date,id);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to issing transaction", e);
			return null;
		}
	}
	@Override
	public List<BcnxSettle> getBcnxFinAcq(Date date, String id) {
		try {
			return bcnxSettleDao.getBcnxFinAcq(date,id);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to acquiring transaction", e);
			return null;
		}
	}
	@Override
	public List<BcnxSettle> getBcnxRev(Date date, String id) {
		try {
			return bcnxSettleDao.getBcnxRev(date,id);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to acquiring transaction", e);
			return null;
		}
	}
	@Override
	public List<BcnxSettle> getBcnxErr(Date date, String id) {
		try {
			return bcnxSettleDao.getBcnxErr(date,id);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to acquiring transaction", e);
			return null;
		}
	}
	
}
