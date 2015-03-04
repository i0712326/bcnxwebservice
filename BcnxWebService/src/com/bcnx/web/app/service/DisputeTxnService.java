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
	public List<DisputeTxn> outgoingChb(Date date, String id);
	public List<DisputeTxn> incomingChb(Date date, String id);
	
	public List<DisputeTxn> outgoingAdj(Date date, String id);
	public List<DisputeTxn> incomingAdj(Date date, String id);
	
	public List<DisputeTxn> outgoingRpm(Date date, String id);
	public List<DisputeTxn> incomingRpm(Date date, String id);
	
	public List<DisputeTxn> outgoingCp(Date date, String id);
	public List<DisputeTxn> incomingCp(Date date, String id);
	
	public List<DisputeTxn> outgoingCrs(Date date, String id);
	public List<DisputeTxn> incomingCrs(Date date, String id);
	
	// update count
	public List<DisputeTxn> getValidDispute();
	public void updateAll(List<DisputeTxn> list);
}
