package com.bcnx.web.app.service.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name="BCNXSETL")
public class BcnxSettle implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name="ID")
	private int id;
	@Column(name="DATE",nullable=false)
	private Date date;
	@Column(name="TIME",nullable=false,length=8)
	private String time;
	@Column(name="MTI", nullable=false,length=4)
	private String mti;
	@Column(name="CARD", nullable=false, length=19)
	private String card;
	@Column(name="PROCC", nullable=false, length=6)
	private String proc;
	@Column(name="RES", nullable=false, length=2)
	private String res;
	@Column(name="AMOUNT")
	private double amount = 0;
	@Column(name="FEE")
	private double fee = 0;
	@Column(name="STAN", nullable=false, length=6)
	private String stan;
	@Column(name="RRN", nullable=false, length=12)
	private String rrn;
	@Column(name="TERMID", nullable=false, length=8)
	private String termId;
	@Column(name="ACQID", nullable=false, length=6)
	private String acqId;
	@Column(name="ISSID", nullable=false, length=6)
	private String issId;
	@Column(name="SLOT", nullable=false, length=3)
	private String slot;
	@Column(name="TYPE", nullable=false, length=4)
	private String type;
	@Transient
	private int first;
	@Transient
	private int max;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getMti() {
		return mti;
	}
	public void setMti(String mti) {
		this.mti = mti;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	public String getProc() {
		return proc;
	}
	public void setProc(String proc) {
		this.proc = proc;
	}
	public String getRes() {
		return res;
	}
	public void setRes(String res) {
		this.res = res;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	public String getStan() {
		return stan;
	}
	public void setStan(String stan) {
		this.stan = stan;
	}
	public String getRrn() {
		return rrn;
	}
	public void setRrn(String rrn) {
		this.rrn = rrn;
	}
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public String getAcqId() {
		return acqId;
	}
	public void setAcqId(String acqId) {
		this.acqId = acqId;
	}
	public String getIssId() {
		return issId;
	}
	public void setIssId(String issId) {
		this.issId = issId;
	}
	public String getSlot() {
		return slot;
	}
	public void setSlot(String slot) {
		this.slot = slot;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getFirst() {
		return first;
	}
	public void setFirst(int first) {
		this.first = first;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
}
