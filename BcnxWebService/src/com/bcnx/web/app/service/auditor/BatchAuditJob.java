package com.bcnx.web.app.service.auditor;

import com.bcnx.web.app.service.BcnxSettleService;
import com.bcnx.web.app.service.BcnxTxnService;
import com.bcnx.web.app.service.BinService;

public class BatchAuditJob {
	protected BinService binService;
	protected BcnxSettleService bcnxSettleService;
	protected BcnxTxnService bcnxTxnService;
	public void setBinService(BinService binService){
		this.binService = binService;
	}
	public void setBcnxSettleService(BcnxSettleService bcnxSettleService){
		this.bcnxSettleService = bcnxSettleService;
	}
	public void setBcnxTxnService(BcnxTxnService bcnxTxnService){
		this.bcnxTxnService = bcnxTxnService;
	}
}
