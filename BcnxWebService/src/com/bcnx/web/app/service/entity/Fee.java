package com.bcnx.web.app.service.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name="FEEINFO")
public class Fee implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="ID")
	private int feeId;
	@Column(name="PROCC",length=6, nullable=false)
	private String procCode;
	@Column(name="ACQFEE")
	private double acqFee = 0;
	@Column(name="ISSFEE")
	private double issFee = 0;
	@Column(name="CARDTYPE")
	private String cardType;
	@OneToOne(fetch=FetchType.LAZY)
	public int getFeeId() {
		return feeId;
	}
	public void setFeeId(int feeId) {
		this.feeId = feeId;
	}
	public String getProcCode() {
		return procCode;
	}
	public void setProcCode(String procCode) {
		this.procCode = procCode;
	}
	public double getAcqFee() {
		return acqFee;
	}
	public void setAcqFee(double acqFee) {
		this.acqFee = acqFee;
	}
	public double getIssFee() {
		return issFee;
	}
	public void setIssFee(double issFee) {
		this.issFee = issFee;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
}
