package com.bcnx.web.app.service.task;

import java.util.List;

import com.bcnx.web.app.service.DisputeTxnService;
import com.bcnx.web.app.service.entity.DisputeTxn;

public class BatchDispUpdate implements BatchJob {
	private DisputeTxnService disputeTxnService;
	public void setDisputeTxnService(DisputeTxnService disputeTxnService){
		this.disputeTxnService = disputeTxnService;
	}
	@Override
	public void doWork() {
		List<DisputeTxn> list = disputeTxnService.getValidDispute();
		for(int i=0;i<list.size();i++){
			DisputeTxn disp = list.get(i);
			int count = disp.getCount() - 1;
			list.get(i).setCount(count);
		}
		disputeTxnService.updateAll(list);
	}

}
