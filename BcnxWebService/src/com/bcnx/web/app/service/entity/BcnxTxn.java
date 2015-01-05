package com.bcnx.web.app.service.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="BCNXTXN")
@Inheritance(strategy=InheritanceType.JOINED)
public class BcnxTxn implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name="ID")
	private int id;
	@Column(name="SLOT", nullable=false, length=3)
	private String slot;
	@Column(name="MTI", nullable=false, length=4)
	private String mti;
	@Column(name="DATE",nullable=false)
	private Date date;
	@Column(name="TIME",nullable=false, length=8)
	private String time;
	@Column(name="CARD", nullable=false,length=19)
	private String card;
	@Column(name="PROCC",nullable=false, length=6)
	private String proc;
	@Column(name="AMOUNT")
	private double amount = 0;
	@Column(name="FEE")
	private double fee = 0;
	@Column(name="TRACE", nullable=false, length=6)
	private String trace;
	@Column(name="RRNO", nullable=false, length=12)
	private String rrn;
	@Column(name="APPR", nullable=true, length=6)
	private String appr;
	@Column(name="RES", nullable=false, length=2)
	private String res;
	@Column(name="TERMID", nullable=false, length=8)
	private String termId;
	@Column(name="LOCATION", nullable=false, length=42)
	private String location;
	@Column(name="CURR", nullable=false, length=3)
	private String curr="418";
	@Column(name="MCC", nullable=false, length=4)
	private String mcc="6011";
	@Column(name="COUNTRY", nullable=false, length=3)
	private String country="418";
	@Column(name="POS", nullable=false, length=3)
	private String pos="021";
	@Column(name="CONDCODE", nullable=false,length=2)
	private String condCode="01";
	@Column(name="ISS", nullable=false, length=6)
	private String iss;
	@Column(name="ACQ", nullable=false, length=6)
	private String acq;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USRDATA_USRID")
	private User user;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
