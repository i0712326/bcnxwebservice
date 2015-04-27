package com.bcnx.web.app.service.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="BCNXTXN")
public class BcnxTxn implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="SLOT")
	private String slot;
	@Id
	@Column(name="MTI")
	private String mti;
	@Id
	@Column(name="STAN")
	private String stan;
	@Id
	@Column(name="RRN")
	private String rrn;
	@Column(name="TXNDATE")
	private String date;
	@Column(name="TXNTIME")
	private String time;
	@Column(name="CARD")
	private String card;
	@Column(name="EXP")
	private String exp;
	@Column(name="PROCC")
	private String proc;
	@Column(name="AMOUNT")
	private double amount = 0;
	@Column(name="FEE")
	private double fee = 0;
	@Column(name="APPR")
	private String appr;
	@Column(name="RES", length=2)
	private String res;
	@Column(name="TERMID", length=8)
	private String termId;
	@Column(name="LOCATE", length=42)
	private String location;
	@Column(name="CURR", length=3)
	private String curr="418";
	@Column(name="MCC", length=4)
	private String mcc="6011";
	@Column(name="COUNTRY", length=3)
	private String country="418";
	@Column(name="POS", length=3)
	private String pos="021";
	@Column(name="CONCODE", length=2)
	private String condCode="01";
	@Column(name="ISS",length=6)
	private String iss;
	@Column(name="ACQ", length=6)
	private String acq;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
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
	public String getRrn() {
		return rrn;
	}
	public void setRrn(String rrn) {
		this.rrn = rrn;
	}
	public String getAppr() {
		return appr;
	}
	public void setAppr(String appr) {
		this.appr = appr;
	}
	public String getRes() {
		return res;
	}
	public void setRes(String res) {
		this.res = res;
	}
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCurr() {
		return curr;
	}
	public void setCurr(String curr) {
		this.curr = curr;
	}
	public String getMcc() {
		return mcc;
	}
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPos() {
		return pos;
	}
	public void setPos(String pos) {
		this.pos = pos;
	}
	public String getCondCode() {
		return condCode;
	}
	public void setCondCode(String condCode) {
		this.condCode = condCode;
	}
	public String getIss() {
		return iss;
	}
	public void setIss(String iss) {
		this.iss = iss;
	}
	public String getAcq() {
		return acq;
	}
	public void setAcq(String acq) {
		this.acq = acq;
	}
	public String getSlot() {
		return slot;
	}
	public void setSlot(String slot) {
		this.slot = slot;
	}
	public String getMti() {
		return mti;
	}
	public void setMti(String mti) {
		this.mti = mti;
	}
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
	public String getStan() {
		return stan;
	}
	public void setStan(String stan) {
		this.stan = stan;
	}
	@Override
	public String toString() {
		return "BcnxTxn [slot=" + slot + ", mti=" + mti + ", stan=" + stan
				+ ", rrn=" + rrn + ", date=" + date + ", time=" + time
				+ ", card=" + card + ", exp=" + exp + ", proc=" + proc
				+ ", amount=" + amount + ", fee=" + fee + ", appr=" + appr
				+ ", res=" + res + ", termId=" + termId + ", location="
				+ location + ", curr=" + curr + ", mcc=" + mcc + ", country="
				+ country + ", pos=" + pos + ", condCode=" + condCode
				+ ", iss=" + iss + ", acq=" + acq + "]";
	}
}
