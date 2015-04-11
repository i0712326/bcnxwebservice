package com.bcnx.web.app.service;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.bcnx.web.app.service.dao.ProcCodeDao;
import com.bcnx.web.app.service.entity.ProcCode;

public class ProcCodeServiceImp implements ProcCodeService {
	private static Logger logger = Logger.getLogger(ProcCodeServiceImp.class);
	private ProcCodeDao procCodeDao;
	public void setProcCodeDao(ProcCodeDao procCodeDao){
		this.procCodeDao = procCodeDao;
	}
	@Override
	public ProcCode getProcCode(ProcCode procCode) {
		try {
			return procCodeDao.getProcCode(procCode);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get processing code",e);
			return null;
		}
	}

}
