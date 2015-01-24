package com.bcnx.web.app.service.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;

import com.bcnx.web.app.service.entity.CardType;

public interface CardTypeDao {
	public void save(CardType cardType) throws SQLException,HibernateException;
	public CardType getCardType(CardType cardType) throws SQLException, HibernateException;
	public List<CardType> getCardTypes(CardType cardType, int first, int max) throws SQLException, HibernateException;
}
