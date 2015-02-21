package com.bcnx.web.app.service;

import java.util.List;

import com.bcnx.web.app.service.entity.ReasonCode;

public interface ReasonCodeService {
	public List<ReasonCode> getReasonCodes();
	public ReasonCode getReasonCode(String code);
	public List<ReasonCode> getReasonCodes(String proc);
}
