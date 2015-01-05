package com.bcnx.web.app.service;

import java.util.List;

import com.bcnx.web.app.service.entity.BcnxTxn;
import com.bcnx.web.app.service.entity.User;

public interface BcnxTxnService {
	public List<BcnxTxn> getBcnxTxns(BcnxTxn bcnxTxn, int first, int max, User user);
}
