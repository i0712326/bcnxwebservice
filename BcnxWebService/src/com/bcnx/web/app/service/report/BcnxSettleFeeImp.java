package com.bcnx.web.app.service.report;

import java.util.List;

import com.bcnx.web.app.service.CardProcService;
import com.bcnx.web.app.service.entity.BcnxSettle;
import com.bcnx.web.app.service.entity.CardProc;

public class BcnxSettleFeeImp implements BcnxSettleFee{
	private CardProcService cardProcService;
	public void setCardProcService(CardProcService cardProcService){
		this.cardProcService = cardProcService;
	}
	@Override
	public List<BcnxSettle> setUpIssFee(List<BcnxSettle> settles) {
		for(int i=0;i<settles.size();i++){
			CardProc cardProc = cardProcService.getCardProc(settles.get(i));
			if(cardProc!=null)
			settles.get(i).setFee(cardProc.getIssFee());
		}
		return settles;
	}
	@Override
	public List<BcnxSettle> setUpAcqFee(List<BcnxSettle> settles) {
		for(int i=0;i<settles.size();i++){
			CardProc cardProc = cardProcService.getCardProc(settles.get(i));
			if(cardProc!=null)
				settles.get(i).setFee(cardProc.getAcqFee());
		}
		return settles;
	} 
}
