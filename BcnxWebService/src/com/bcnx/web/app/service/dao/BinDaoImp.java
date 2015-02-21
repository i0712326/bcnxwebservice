package com.bcnx.web.app.service.dao;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.bcnx.web.app.service.entity.Bin;

public class BinDaoImp implements BinDao {
	private HibernateTemplate hibernateTemplate;
	public void setSessionFactory(SessionFactory sessionFactory){
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	@Transactional
	@Override
	public void save(Bin bin) throws SQLException, HibernateException {
		hibernateTemplate.save(bin);
	}
	@Transactional
	@Override
	public Bin getBin(final Bin bin) throws SQLException, HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<Bin>(){
			@Override
			public Bin doInHibernate(Session session) throws HibernateException {
				String hql = "from Bin b where b.bin = :bin";
				Query query = session.createQuery(hql);
				query.setString("bin", bin.getBin());
				return (Bin) query.uniqueResult();
			}
		});
	}
	@Transactional
	@Override
	public List<Bin> getBins(String bin) throws SQLException,
			HibernateException {
		String hql = "from Bin b where b.bin like :bin";
		return toList(hibernateTemplate.findByNamedParam(hql, "bin", bin));
	}
	private List<Bin> toList(final List<?> beans){
		if(beans == null) return null;
		if(beans.isEmpty()) return null;
		int size = beans.size();
		Bin[] list = new Bin[size];
		list = beans.toArray(list);
		return Arrays.asList(list);
	}
}
