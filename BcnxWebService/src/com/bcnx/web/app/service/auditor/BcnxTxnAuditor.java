package com.bcnx.web.app.service.auditor;

import java.io.File;
import java.io.IOException;

public interface BcnxTxnAuditor {
	public void toList(File file) throws IOException;
}
