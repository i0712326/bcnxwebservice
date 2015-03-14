package com.bcnx.web.app.service.batch;

import org.springframework.batch.item.ItemProcessor;

import com.bcnx.web.app.service.entity.BcnxTxn;

public class BcnxTxnProcessItem implements ItemProcessor<BcnxTxn, BcnxTxn> {
	@Override
	public BcnxTxn process(BcnxTxn list) throws Exception {
		System.out.println("processing item.............");
		return new BcnxTxn();
	}

}
