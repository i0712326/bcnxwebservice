package com.bcnx.web.app.service;

import java.sql.Date;
import java.util.List;

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
	
	// get settle module
	public List<DisputeTxn> issChb(Date date, String id);
	public List<DisputeTxn> acqChb(Date date, String id);
	public List<DisputeTxn> issAdj(Date date, String id);
	public List<DisputeTxn> acqAdj(Date date, String id);
	
}
