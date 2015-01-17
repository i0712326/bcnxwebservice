package com.bcnx.web.app.service;

import java.util.List;

import com.bcnx.web.app.service.entity.Fee;

public interface FeeService {
	public void save(Fee fee);
	public Fee getFee(Fee fee);
	public List<Fee> getFees(String cardType);
}
