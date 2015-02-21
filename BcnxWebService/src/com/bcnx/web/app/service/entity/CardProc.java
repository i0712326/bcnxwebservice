package com.bcnx.web.app.service.entity;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
@Entity
@Table(name="CARDTYPE_has_PROCC")
@AssociationOverrides({
	@AssociationOverride(name = "cardProcId.cardType", 
		joinColumns = @JoinColumn(name = "CARDTYPE_TYPE")),
	@AssociationOverride(name = "cardProcId.procCode", 
		joinColumns = @JoinColumn(name = "PROCC_PCODE")) })
public class CardProc implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private CardProcId cardProcId;
	@Column(name="ACQFEE")
	private double acqFee;
	@Column(name="ISSFEE")
	private double issFee;
	public CardProcId getCardProcId() {
		return cardProcId;
	}
	public void setCardProcId(CardProcId cardProcId) {
		this.cardProcId = cardProcId;
	}
	public double getIssFee() {
		return issFee;
	}
	public void setIssFee(double issFee) {
		this.issFee = issFee;
	}
	public double getAcqFee() {
		return acqFee;
	}
	public void setAcqFee(double acqFee) {
		this.acqFee = acqFee;
	}
	
}
