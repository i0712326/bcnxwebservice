package com.bcnx.web.app.service.report;

import java.util.List;

import com.bcnx.web.app.service.CardProcService;
import com.bcnx.web.app.service.entity.BcnxSettle;
import com.bcnx.web.app.service.entity.CardProc;
import com.bcnx.web.app.service.entity.DisputeTxn;

public class BcnxSettleFeeImp implements BcnxSettleFee{
	private CardProcService cardProcService;
	public void setCardProcService(CardProcService cardProcService){
		this.cardProcService = cardProcService;
	}
	@Override
	public List<BcnxSettle> setIssFee(List<BcnxSettle> settles) {
		for(int i=0;i<settles.size();i++){
			BcnxSettle settle = settles.get(i);
			String proc = settle.getProc();
			String cardType = settle.getCardType().getType();
			CardProc cardProc = cardProcService.getCardProc(cardType,proc);
			if(cardProc!=null)
			settles.get(i).setFee(cardProc.getIssFee());
		}
		return settles;
	}
	@Override
	public List<BcnxSettle> setAcqFee(List<BcnxSettle> settles) {
		for(int i=0;i<settles.size();i++){
			BcnxSettle settle = settles.get(i);
			String proc = settle.getProc();
			String cardType = settle.getCardType().getType();
			CardProc cardProc = cardProcService.getCardProc(cardType,proc);
			if(cardProc!=null)
				settles.get(i).setFee(cardProc.getAcqFee());
		}
		return settles;
	}
	@Override
	public List<DisputeTxn> setDispIssFee(List<DisputeTxn> list) {
		for(int i=0;i<list.size();i++){
			DisputeTxn disp = list.get(i);
			BcnxSettle settle = disp.getBcnxSettle();
			String cardType = settle.getCardType().getType();
			String proc = disp.getProcc();
			CardProc cardProc = cardProcService.getCardProc(cardType, proc);
			if(cardProc!=null)
				list.get(i).setFee(cardProc.getIssFee());
		}
		return list;
	}
	@Override
	public List<DisputeTxn> setDispAcqFee(List<DisputeTxn> list) {
		for(int i=0;i<list.size();i++){
			DisputeTxn disp = list.get(i);
			BcnxSettle settle = disp.getBcnxSettle();
			String cardType = settle.getCardType().getType();
			String proc = disp.getProcc();
			CardProc cardProc = cardProcService.getCardProc(cardType, proc);
			if(cardProc!=null)
				list.get(i).setFee(cardProc.getAcqFee());
		}
		return list;
	} 
}
