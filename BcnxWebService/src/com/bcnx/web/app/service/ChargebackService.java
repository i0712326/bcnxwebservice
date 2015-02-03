package com.bcnx.web.app.service;

import java.util.List;

import com.bcnx.web.app.service.entity.DisputeTxn;

public interface ChargebackService {
	public void save(DisputeTxn disputeTxn);
	public List<DisputeTxn> getInChb(DisputeTxn disp,int first, int max);
	public List<DisputeTxn> getOutChb(DisputeTxn disp, int first, int max);
}
