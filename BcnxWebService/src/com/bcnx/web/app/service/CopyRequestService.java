package com.bcnx.web.app.service;

import com.bcnx.web.app.service.entity.DisputeTxn;

public interface CopyRequestService {
	public void save(DisputeTxn disp);
	public void update(DisputeTxn disp);
}
