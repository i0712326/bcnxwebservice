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
	
	// get incoming
	public List<DisputeTxn> getIncoming(String id, int first, int max);
	public int getInRecords(String id);
	public List<DisputeTxn> getIncoming(String id, String proc, int first, int max);
	public int getInRecords(String id, String proc);
	// get outgoing
	public List<DisputeTxn> getOutgoing(String id, int first, int max);
	public int getOutRecords(String id);
	public List<DisputeTxn> getOutgoing(String id, String proc, int first, int max);
	public int getOutRecords(String id, String proc);
	
	// get related cases
	public List<DisputeTxn> getRelated(DisputeTxn disp, int first, int max);
	public int getRelatedRecords(DisputeTxn disp);
	
	// get list of data
	public List<DisputeTxn> getDisputeTxns(DisputeTxn disputeTxn, int first, int max);
	// get related data
	public int getRecords(DisputeTxn disputeTxn);
	
	// get settle module
	public List<BcnxSettle> issChb(Date date, String id);
	public List<BcnxSettle> acqChb(Date date, String id);
	public List<BcnxSettle> issAdj(Date date, String id);
	public List<BcnxSettle> acqAdj(Date date, String id);
	
}
