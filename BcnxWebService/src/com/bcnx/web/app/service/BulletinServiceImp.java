package com.bcnx.web.app.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.bcnx.web.app.service.dao.BulletinDao;
import com.bcnx.web.app.service.entity.Bulletin;

public class BulletinServiceImp implements BulletinService {
	private static final Logger logger = Logger.getLogger(BulletinServiceImp.class);
	private BulletinDao bulletinDao;
	public void setBulletinDao(BulletinDao bulletinDao){
		this.bulletinDao = bulletinDao;
	}
	@Override
	public void save(Bulletin bulletin) {
		try {
			bulletinDao.save(bulletin);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to save bulletin", e);
		}
	}

	@Override
	public List<Bulletin> getBulletin(int first, int max) {
		try {
			return bulletinDao.getBulletins(first, max);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to save bulletin", e);
			return null;
		}
	}

}
