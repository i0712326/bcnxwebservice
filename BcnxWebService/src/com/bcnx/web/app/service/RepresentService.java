package com.bcnx.web.app.service;

import java.util.List;

import com.bcnx.web.app.service.entity.DisputeTxn;

public interface RepresentService {
	public void save(DisputeTxn disp);
	public List<DisputeTxn> getInRpm(DisputeTxn disp, int first, int max);
	public List<DisputeTxn> getOutRpm(DisputeTxn disp, int first, int max);
}
