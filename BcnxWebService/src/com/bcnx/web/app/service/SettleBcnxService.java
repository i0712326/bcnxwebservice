package com.bcnx.web.app.service;

import java.sql.Date;

import com.bcnx.web.app.service.entity.SettleBcnx;

public interface SettleBcnxService {
	public void save(SettleBcnx settleBcnx);
	public SettleBcnx getSettleBcnx(Date date, String id);
}
