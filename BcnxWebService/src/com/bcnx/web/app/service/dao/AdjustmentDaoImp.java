package com.bcnx.web.app.service.dao;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.springframework.transaction.annotation.Transactional;

import com.bcnx.web.app.service.entity.DisputeTxn;

public class AdjustmentDaoImp extends DisputeTxnDaoImp {
	@Transactional
	@Override
	public void save(DisputeTxn disputeTxn) throws SQLException,
			HibernateException {
		super.save(disputeTxn);
	}
	
}
