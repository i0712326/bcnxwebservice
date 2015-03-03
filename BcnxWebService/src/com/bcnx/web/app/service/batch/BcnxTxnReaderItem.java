package com.bcnx.web.app.service.batch;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.bcnx.web.app.service.entity.BcnxTxn;

public class BcnxTxnReaderItem implements ItemReader<List<BcnxTxn>> {
	
	@Override
	public List<BcnxTxn> read() throws Exception, UnexpectedInputException, ParseException,
			NonTransientResourceException {
		
		return null;
	}

}
