package com.bcnx.web.app.service.auditor;

import java.io.File;
import java.io.IOException;

public interface BcnxTxnAuditor {
	public void toBcnxTxn(File file) throws IOException;
	public void refine();
}
