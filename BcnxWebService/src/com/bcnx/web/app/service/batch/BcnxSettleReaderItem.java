package com.bcnx.web.app.service.batch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.bcnx.web.app.service.entity.BcnxSettle;

public class BcnxSettleReaderItem implements ItemReader<BcnxSettle> {
	private static Logger logger = Logger.getLogger(BcnxSettleReaderItem.class);
	private static File file;
	private static BufferedReader br;
	private static String line;
	static{
		try {
			line = null;
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			logger.debug("Exception occured while try to open file",e);
		}
	}
	@Override
	public BcnxSettle read() throws Exception, UnexpectedInputException, ParseException,
			NonTransientResourceException {
		while((line=br.readLine())!=null){
			
		}
		return null;
	}

}
