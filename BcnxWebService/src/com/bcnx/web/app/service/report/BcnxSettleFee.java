package com.bcnx.web.app.service.report;

import java.util.List;

import com.bcnx.web.app.service.entity.BcnxSettle;

public interface BcnxSettleFee {
	public List<BcnxSettle> setUpIssFee(List<BcnxSettle> settles);
	public List<BcnxSettle> setUpAcqFee(List<BcnxSettle> settles);
}
