package com.bcnx.web.app.service.batch;

import org.springframework.batch.item.ItemProcessor;

import com.bcnx.web.app.service.entity.BcnxSettle;

public class BcnxSettleProcessItem implements ItemProcessor<BcnxSettle, BcnxSettle> {

	@Override
	public BcnxSettle process(BcnxSettle bcnxSettle) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
