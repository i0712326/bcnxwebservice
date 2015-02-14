package com.bcnx.web.app.service.dao;

import java.sql.SQLException;

import org.hibernate.HibernateException;

import com.bcnx.web.app.service.entity.BcnxSettle;
import com.bcnx.web.app.service.entity.CardProc;

public interface CardProcDao {
	public CardProc getCardProc(BcnxSettle settle) throws SQLException, HibernateException;
}
