package com.bcnx.web.app.service.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;

import com.bcnx.web.app.service.entity.Bulletin;

public interface BulletinDao {
	public void save(Bulletin bulletin) throws SQLException, HibernateException;
	public List<Bulletin> getBulletins(int first, int max) throws SQLException, HibernateException;
}
