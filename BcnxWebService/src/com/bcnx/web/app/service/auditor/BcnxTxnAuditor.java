package com.bcnx.web.app.service.auditor;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.bcnx.web.app.service.entity.BcnxTxn;

public interface BcnxTxnAuditor {
	public List<BcnxTxn> toList(File file) throws IOException;
}
