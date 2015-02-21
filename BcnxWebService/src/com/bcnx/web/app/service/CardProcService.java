package com.bcnx.web.app.service;

import com.bcnx.web.app.service.entity.BcnxSettle;
import com.bcnx.web.app.service.entity.CardProc;

public interface CardProcService {
	public CardProc getCardProc(BcnxSettle settle);
}
