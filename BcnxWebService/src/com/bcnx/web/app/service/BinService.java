package com.bcnx.web.app.service;

import java.util.List;

import com.bcnx.web.app.service.entity.Bin;

public interface BinService {
	public void saveBin(Bin bin);
	public Bin getBin(Bin bin);
	public List<Bin> getBins(String bin);
}
