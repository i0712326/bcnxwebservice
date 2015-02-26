package com.bcnx.web.app.service.report;

import java.util.List;

import com.bcnx.web.app.service.entity.BcnxSettle;
import com.bcnx.web.app.service.entity.DisputeTxn;

public interface BcnxSettleFee {
	public List<BcnxSettle> setIssFee(List<BcnxSettle> settles);
	public List<BcnxSettle> setAcqFee(List<BcnxSettle> settles);
	
	public List<DisputeTxn> setDispIssFee(List<DisputeTxn> list);
	public List<DisputeTxn> setDispAcqFee(List<DisputeTxn> list);
}
