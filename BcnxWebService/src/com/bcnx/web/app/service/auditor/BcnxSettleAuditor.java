package com.bcnx.web.app.service.auditor;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import com.bcnx.web.app.service.entity.BcnxSettle;

public interface BcnxSettleAuditor {
	public List<BcnxSettle> doWork(File file, Date date) throws IOException;
}
