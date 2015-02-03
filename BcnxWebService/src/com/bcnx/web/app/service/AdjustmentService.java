package com.bcnx.web.app.service;

import java.util.List;

import com.bcnx.web.app.service.entity.DisputeTxn;

public interface AdjustmentService {
	public void save(DisputeTxn disp);
	public List<DisputeTxn> getInAdjust(DisputeTxn disp, int first, int max);
	public List<DisputeTxn> getOutAdjust(DisputeTxn disp, int first, int max);
}
