package com.bcnx.web.app.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.bcnx.web.app.service.dao.DisputeTxnDao;
import com.bcnx.web.app.service.entity.BcnxSettle;
import com.bcnx.web.app.service.entity.DisputeTxn;

public class DisputeTxnServiceImp implements DisputeTxnService {
	private static final Logger logger = Logger.getLogger(DisputeTxnServiceImp.class);
	private DisputeTxnDao disputeTxnDao;
	public void setDisputeTxnDao(DisputeTxnDao disputeTxnDao){
		this.disputeTxnDao = disputeTxnDao;
	}
	@Override
	public void save(DisputeTxn disputeTxn) {
		try {
			disputeTxnDao.save(disputeTxn);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try save dispute data", e);
		}
	}

	@Override
	public DisputeTxn getDisputeTxn(DisputeTxn disputeTxn) {
		try {
			return disputeTxnDao.getDisputeTxn(disputeTxn);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try save dispute data", e);
			return null;
		}
	}

	@Override
	public List<DisputeTxn> getDisputeTxns(DisputeTxn disputeTxn, int first, int max) {
		try {
			return disputeTxnDao.getDisputeTxns(disputeTxn,first,max);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try get dispute data", e);
			return null;
		}
	}
	// incoming
	@Override
	public List<DisputeTxn> getIncoming(String id, int first, int max) {
		try {
			return disputeTxnDao.getInDisp(id, first, max);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try get dispute data", e);
			return null;
		}
	}
	@Override
	public int getInRecords(String id) {
		try {
			return disputeTxnDao.getInRecords(id);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try get dispute data", e);
			return 0;
		}
	}
	@Override
	public List<DisputeTxn> getIncoming(String id, String proc, int first,
			int max) {
		try {
			return disputeTxnDao.getInDisp(id, proc, first, max);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try get dispute data", e);
			return null;
		}
	}
	@Override
	public int getInRecords(String id, String proc) {
		try {
			return disputeTxnDao.getInByProc(id, proc);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try get dispute data", e);
			return 0;
		}
	}
	// outgoing
	@Override
	public List<DisputeTxn> getOutgoing(String id, int first, int max) {
		try {
			return disputeTxnDao.getOutDisp(id, first, max);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try get dispute data", e);
			return null;
		}
	}
	@Override
	public int getOutRecords(String id) {
		try {
			return disputeTxnDao.getOutRecords(id);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try get dispute data", e);
			return 0;
		}
	}
	@Override
	public List<DisputeTxn> getOutgoing(String id, String proc, int first,
			int max) {
		try {
			return disputeTxnDao.getOutDisp(id, proc, first, max);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try get dispute data", e);
			return null;
		}
	}
	@Override
	public int getOutRecords(String id, String proc) {
		try {
			return disputeTxnDao.getOutByProc(id, proc);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try get dispute data", e);
			return 0;
		}
	}
	// related

	@Override
	public List<DisputeTxn> getRelated(DisputeTxn disp, int first, int max) {
		try {
			return disputeTxnDao.relatedCase(disp, first, max);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try get dispute data", e);
			return null;
		}
	}
	@Override
	public int getRelatedRecords(DisputeTxn disp) {
		try {
			return disputeTxnDao.relatedRecords(disp);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try get dispute data", e);
			return 0;
		}
	}
	
	// get records number
	@Override
	public int getRecords(DisputeTxn disputeTxn){
		try {
			return disputeTxnDao.getRecords(disputeTxn);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get dispute records", e);
			return 0;
		}
	}
	
	// settlement
	@Override
	public List<BcnxSettle> issChb(Date date, String id) {
		try {
			return disputeTxnDao.outGoingChb(date,id);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get issuer charge back", e);
			return null;
		}
	}
	@Override
	public List<BcnxSettle> acqChb(Date date, String id) {
		try {
			return disputeTxnDao.incomingChb(date, id);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get acquirer charge back", e);
			return null;
		}
	}
	@Override
	public List<BcnxSettle> issAdj(Date date, String id) {
		try {
			return disputeTxnDao.outGoingAdj(date,id);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get issuer charge back", e);
			return null;
		}
	}
	@Override
	public List<BcnxSettle> acqAdj(Date date, String id) {
		try {
			return disputeTxnDao.incomingAdj(date, id);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get issuer charge back", e);
			return null;
		}
	}

}
