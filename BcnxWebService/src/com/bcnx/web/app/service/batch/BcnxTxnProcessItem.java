package com.bcnx.web.app.service.batch;

import java.util.List;

import org.springframework.batch.item.ItemProcessor;

import com.bcnx.web.app.service.entity.BcnxTxn;

public class BcnxTxnProcessItem implements ItemProcessor<List<BcnxTxn>, List<BcnxTxn>> {
	@Override
	public List<BcnxTxn> process(List<BcnxTxn> list) throws Exception {
		
		return null;
	}

}
