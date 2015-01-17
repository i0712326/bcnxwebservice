package com.bcnx.web.app.service.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name="BCNXTXN")
public class BcnxTxn implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name="ID")
	private int id;
	@Column(name="SLOT")
	private String slot;
	@Column(name="MTI", nullable=false, length=4)
	private String mti;
	@Column(name="DATE")
	private String date;
	@Column(name="TIME")
	private String time;
	@Column(name="CARD", nullable=false,length=19)
	private String card;
	@Column(name="EXP")
	private String exp;
	@Column(name="PROCC")
	private String proc;
	@Column(name="AMOUNT")
	private double amount = 0;
	@Column(name="FEE")
	private double fee = 0;
	@Column(name="TRACE")
	private String trace;
	@Column(name="RRN", nullable=false, length=12)
	private String rrn;
	@Column(name="APPR")
	private String appr;
	@Column(name="RES", length=2)
	private String res;
	@Column(name="TERMID", length=8)
	private String termId;
	@Column(name="LOCATION", length=42)
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
	@Transient
	private int first;
	@Transient
	private int max;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public String getTrace() {
		return trace;
	}
	public void setTrace(String trace) {
		this.trace = trace;
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
