package com.bcnx.web.app.service.batch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.bcnx.web.app.service.entity.BcnxTxn;

public class BcnxTxnReaderItem implements ItemReader<BcnxTxn> {
	private List<BcnxTxn> list;
	private int count = 0;
	@Override
	public BcnxTxn read() throws Exception, UnexpectedInputException, ParseException,
			NonTransientResourceException {
		System.out.println("Reading item ..............");
		BcnxTxn bcnx1 = new BcnxTxn();
		BcnxTxn bcnx2 = new BcnxTxn();
		BcnxTxn bcnx3 = new BcnxTxn();
		list = new ArrayList<BcnxTxn>();
		list.add(bcnx1);
		list.add(bcnx2);
		list.add(bcnx3);
		
		if(count<list.size())
			return list.get(count++);
		else
			return null;
	}

}
