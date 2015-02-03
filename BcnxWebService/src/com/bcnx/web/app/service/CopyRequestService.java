package com.bcnx.web.app.service;

import java.util.List;

import com.bcnx.web.app.service.entity.DisputeTxn;

public interface CopyRequestService {
	public void save(DisputeTxn disp);
	public List<DisputeTxn> getInCpReq(DisputeTxn disp, int first, int max);
	public void respCpReq(DisputeTxn disp);
	public List<DisputeTxn> getOutCpReq(DisputeTxn disp, int first, int max);
}
