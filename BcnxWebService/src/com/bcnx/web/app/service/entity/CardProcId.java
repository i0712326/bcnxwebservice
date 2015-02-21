package com.bcnx.web.app.service.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
@Embeddable
public class CardProcId implements Serializable {
	private static final long serialVersionUID = 1L;
	@ManyToOne
	private CardType cardType;
	@ManyToOne
	private ProcCode procCode;
	public CardType getCardType() {
		return cardType;
	}
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	public ProcCode getProcCode() {
		return procCode;
	}
	public void setProcCode(ProcCode procCode) {
		this.procCode = procCode;
	}
}
