package com.bcnx.web.app.service.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.bcnx.web.app.service.entity.BcnxSettle;

public class BcnxSettleWriteItem implements ItemWriter<BcnxSettle> {

	@Override
	public void write(List<? extends BcnxSettle> list) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
