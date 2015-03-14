package com.bcnx.web.app.service.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.bcnx.web.app.service.entity.BcnxTxn;

public class BcnxTxnWirteItem implements ItemWriter<BcnxTxn> {
	
	@Override
	public void write(List<? extends BcnxTxn> list) throws Exception {
		System.out.println("Writing item.............."+list);
	}

}
