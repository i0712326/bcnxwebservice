package com.bcnx.web.app.service.dao;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.bcnx.web.app.service.entity.CardType;

public class CardTypeDaoImp implements CardTypeDao {
	private HibernateTemplate hibernateTemplate;
	public void setSessionFactory(SessionFactory sessionFactory){
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	@Transactional
	@Override
	public void save(CardType cardType) throws SQLException, HibernateException {
		hibernateTemplate.save(cardType);
	}
	@Transactional
	@Override
	public CardType getCardType(final CardType cardType) throws SQLException,
			HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<CardType>(){

			@Override
			public CardType doInHibernate(Session session)
					throws HibernateException {
				Criteria criteria = session.createCriteria(CardType.class);
				criteria.add(Restrictions.eqOrIsNull("type", cardType.getType()));
				return (CardType) criteria.uniqueResult();
			}
			
		});
	}
	@Transactional
	@Override
	public List<CardType> getCardTypes(CardType cardType, int first, int max)
			throws SQLException, HibernateException {
		DetachedCriteria criteria = DetachedCriteria.forClass(CardType.class);
		criteria.add(Restrictions.like("type", cardType.getType()));
		return toList(hibernateTemplate.findByCriteria(criteria, first, max));
	}
	private List<CardType> toList(final List<?> beans){
		if(beans==null) return null;
		if(beans.isEmpty()) return null;
		int size = beans.size();
		CardType[] list = new CardType[size];
		list = beans.toArray(list);
		return Arrays.asList(list);
	}

}
