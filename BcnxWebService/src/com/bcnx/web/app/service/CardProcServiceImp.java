package com.bcnx.web.app.service;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import com.bcnx.web.app.service.dao.CardProcDao;
import com.bcnx.web.app.service.entity.BcnxSettle;
import com.bcnx.web.app.service.entity.CardProc;

public class CardProcServiceImp implements CardProcService {
	private static final Logger logger = Logger.getLogger(CardProcServiceImp.class);
	private CardProcDao cardProcDao;
	public void setCardProcDao(CardProcDao cardProcDao){
		this.cardProcDao = cardProcDao;
	}
	@Override
	public CardProc getCardProc(BcnxSettle settle) {
		try {
			return cardProcDao.getCardProc(settle);
		} catch (HibernateException | SQLException e) {
			logger.debug("Exception occured while try to get Card Processing code", e);
			return null;
		}
	}

}
