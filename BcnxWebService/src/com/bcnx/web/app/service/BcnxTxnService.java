package com.bcnx.web.app.service;

import java.util.List;

import com.bcnx.web.app.service.entity.BcnxTxn;
import com.bcnx.web.app.service.entity.User;

public interface BcnxTxnService {
	public void save(BcnxTxn bcnx);
	public void update(BcnxTxn bcnx);
	public BcnxTxn getBcnxTxn(BcnxTxn bcnxTxn);
	public List<BcnxTxn> getBcnxTxns(BcnxTxn bcnxTxn, int first, int max, User user);
}
