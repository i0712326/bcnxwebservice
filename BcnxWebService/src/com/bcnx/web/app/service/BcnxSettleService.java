package com.bcnx.web.app.service;

import java.sql.Date;
import java.util.List;

import com.bcnx.web.app.service.entity.BcnxSettle;

public interface BcnxSettleService {
	public void save(BcnxSettle bcnxSettle);
	public void saveAll(List<BcnxSettle> bcnxSettles);
	public BcnxSettle getBcnxSettle(BcnxSettle bcnxSettle);
	public List<BcnxSettle> getBcnxSettles(BcnxSettle bcnxSettle, Date start, Date end, int first, int max);
	public List<Date> getSettleDates();
	public Date getMaxDate();
	public List<BcnxSettle> getBcnxFinIss(Date date, String id);
	public List<BcnxSettle> getBcnxFinAcq(Date date, String id);
	public List<BcnxSettle> getBcnxRev(Date date, String id);
	public List<BcnxSettle> getBcnxErr(Date date, String id);
}
