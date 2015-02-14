package com.bcnx.web.app.service;

import java.sql.Date;
import java.util.List;

import com.bcnx.web.app.service.entity.BcnxSettle;
import com.bcnx.web.app.service.entity.DisputeTxn;

public interface DisputeTxnService {
	// save copy request, charge back, adjustment, represent
	public void save(DisputeTxn disputeTxn);
	// view copy request, charge back, adjustment, represent
	// get unique data
	public DisputeTxn getDisputeTxn(DisputeTxn disputeTxn);
	// get list of data
	public List<DisputeTxn> getDisputeTxns(DisputeTxn disputeTxn, int first, int max);
	public List<BcnxSettle> issChb(Date date, String id);
	public List<BcnxSettle> acqChb(Date date, String id);
	public List<BcnxSettle> issAdj(Date date, String id);
	public List<BcnxSettle> acqAdj(Date date, String id);
}
