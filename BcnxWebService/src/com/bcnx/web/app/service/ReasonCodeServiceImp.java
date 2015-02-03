package com.bcnx.web.app.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.bcnx.web.app.service.dao.ReasonCodeDao;
import com.bcnx.web.app.service.entity.ReasonCode;

public class ReasonCodeServiceImp implements ReasonCodeService{
	private static final Logger logger = Logger.getLogger(ReasonCodeServiceImp.class);
	private ReasonCodeDao reasonCodeDao;
	public void setReasonCodeDao(ReasonCodeDao reasonCodeDao){
		this.reasonCodeDao = reasonCodeDao;
	}
	@Override
	public List<ReasonCode> getReasonCodes() {
		try {
			return reasonCodeDao.getReasonCodes();
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get reason codes", e);
			return null;
		}
	}
	@Override
	public ReasonCode getReasonCode(String code) {
		try {
			return reasonCodeDao.getReasonCode(code);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get reason code", e);
			return null;
		}
	}
	@Override
	public List<ReasonCode> getReasonCodes(String proc) {
		try {
			return reasonCodeDao.getReasonCodes(proc);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get reason code", e);
			return null;
		}
	}

}
