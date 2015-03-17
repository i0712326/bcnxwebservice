package com.bcnx.web.app.service.batch;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import com.bcnx.web.app.service.BinService;
import com.bcnx.web.app.service.entity.BcnxTxn;
import com.bcnx.web.app.service.entity.Bin;

public class BcnxTxnProcessItem implements ItemProcessor<BcnxTxn, BcnxTxn> {
	private static Logger logger = Logger.getLogger(BcnxTxnProcessItem.class);
	private BinService binService;
	public void setBinService(BinService binService){
		this.binService = binService;
	}
	@Override
	public BcnxTxn process(BcnxTxn bcnxTxn) throws Exception {
		String mti = bcnxTxn.getMti();
		if(mti==null){
			logger.debug("Error transaction >>>>>>> "+bcnxTxn);
			return null;
		}
		boolean check = mti.equals("0200")||mti.equals("0210")||mti.equals("0420")||mti.equals("0430");
		if(check){
			Bin bin = new Bin();
			bin.setBin(bcnxTxn.getCard().substring(0, 6));
			bin = binService.getBin(bin);
			String iin = "621354";
			if(bin!=null)
				iin = bin.getMember().getIin();
			bcnxTxn.setIss(iin);
		}
		return bcnxTxn;
	}

}
