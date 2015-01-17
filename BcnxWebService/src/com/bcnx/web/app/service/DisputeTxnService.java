package com.bcnx.web.app.service;

import java.util.List;

import com.bcnx.web.app.service.entity.DisputeTxn;

public interface DisputeTxnService {
	// save copy request, charge back, adjustment, represent
	public void save(DisputeTxn disputeTxn);
	// view copy request, charge back, adjustment, represent
	// get unique data
	public DisputeTxn getDisputeTxn(DisputeTxn disputeTxn);
	// get list of data
	public List<DisputeTxn> getDisputeTxns(DisputeTxn disputeTxn, int first, int max);
	
	
}
