package com.bcnx.web.app.service.batch;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemWriter;

import com.bcnx.web.app.service.BcnxTxnService;
import com.bcnx.web.app.service.entity.BcnxTxn;

public class BcnxTxnWirteItem implements ItemWriter<BcnxTxn> {
	private static Logger logger = Logger.getLogger(BcnxTxnWirteItem.class);
	private BcnxTxnService bcnxTxnService;
	public void setBcnxTxnService(BcnxTxnService bcnxTxnService){
		this.bcnxTxnService = bcnxTxnService;
	}
	@Override
	public void write(List<? extends BcnxTxn> bcnxTxns) throws Exception {
		for(BcnxTxn item : bcnxTxns){
			if(item.getIss()!=null){
				String mti = item.getMti();
				boolean check = mti.equals("0200") || mti.equals("0420");
				if (check) {
					BcnxTxn chk = bcnxTxnService.getBcnxTxn(item);
					if (chk == null) {
						logger.debug("Save >>>> "+item);
						bcnxTxnService.save(item);
					}
				} else {
					logger.debug("update >>>> "+item);
					bcnxTxnService.update(item);
				}

			}
		}
	}

}
