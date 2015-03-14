package com.bcnx.web.app.service.dao;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.bcnx.web.app.service.entity.CardProc;

public class CardProcDaoImp implements CardProcDao {
	private HibernateTemplate hibernateTemplate;
	public void setSessionFactory(SessionFactory sessionFactory){
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	@Transactional
	@Override
	public CardProc getCardProc(final String cardType, final String proc) throws SQLException,
			HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<CardProc>(){
			@Override
			public CardProc doInHibernate(Session session)
					throws HibernateException {
				String sql ="select ISSFEE, ACQFEE, CARDTYPE_TYPE, PROCC_PCODE from CARDTYPE_has_PROCC where CARDTYPE_TYPE =:type and PROCC_PCODE =:code";
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				sqlQuery.addEntity(CardProc.class);
				sqlQuery.setString("type", cardType);
				sqlQuery.setString("code", proc);
				return (CardProc) sqlQuery.uniqueResult();
			}
			
		});
	}

}
