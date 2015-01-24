package com.bcnx.web.app.service.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name="BCNXSETL")
public class BcnxSettle implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="MTI")
	private String mti;
	@Id
	@Column(name="RRN")
	private String rrn;
	@Id
	@Column(name="SLOT")
	private String slot;
	@Id
	@Column(name="STAN")
	private String stan;
	@Column(name="DATE",nullable=false)
	private Date date;
	@Column(name="TIME",nullable=false,length=8)
	private String time;
	@Column(name="CARD", nullable=false, length=19)
	private String card;
	@Column(name="PROCC", nullable=false, length=6)
	private String proc;
	@Column(name="RES", nullable=false, length=2)
	private String res;
	@Column(name="AMOUNT")
	private double amount = 0;
	@Column(name="TERMID", nullable=false, length=8)
	private String termId;
	@Column(name="ACQID", nullable=false, length=6)
	private String acq;
	@Column(name="ISSID", nullable=false, length=6)
	private String iss;
	@ManyToOne
	@JoinColumn(name="CARDTYPE_TYPE")
	private CardType cardType;
	@OneToMany(fetch=FetchType.LAZY,mappedBy="bcnxSettle",cascade=CascadeType.ALL)
	private List<DisputeTxn> disputeTxns;
	@OneToOne
	@JoinColumns({
		@JoinColumn(name="BCNXTXN_SLOT", referencedColumnName="SLOT"),
		@JoinColumn(name="BCNXTXN_MTI", referencedColumnName="MTI"),
		@JoinColumn(name="BCNXTXN_STAN", referencedColumnName="STAN"),
		@JoinColumn(name="BCNXTXN_RRN", referencedColumnName="RRN")
	})
	private BcnxTxn bcnxTxn;
	public String getMti() {
		return mti;
	}
	public void setMti(String mti) {
		this.mti = mti;
	}
	public String getRrn() {
		return rrn;
	}
	public void setRrn(String rrn) {
		this.rrn = rrn;
	}
	public String getSlot() {
		return slot;
	}
	public void setSlot(String slot) {
		this.slot = slot;
	}
	public String getStan() {
		return stan;
	}
	public void setStan(String stan) {
		this.stan = stan;
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
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public String getAcq() {
		return acq;
	}
	public void setAcq(String acq) {
		this.acq = acq;
	}
	public String getIss() {
		return iss;
	}
	public void setIss(String iss) {
		this.iss = iss;
	}
	public CardType getCardType() {
		return cardType;
	}
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	public List<DisputeTxn> getDisputeTxns() {
		return disputeTxns;
	}
	public void setDisputeTxns(List<DisputeTxn> disputeTxns) {
		this.disputeTxns = disputeTxns;
	}
	public BcnxTxn getBcnxTxn() {
		return bcnxTxn;
	}
	public void setBcnxTxn(BcnxTxn bcnxTxn) {
		this.bcnxTxn = bcnxTxn;
	}
	@Override
	public String toString() {
		return "BcnxSettle [mti=" + mti + ", rrn=" + rrn + ", slot=" + slot
				+ ", stan=" + stan + ", date=" + date + ", time=" + time
				+ ", card=" + card + ", proc=" + proc + ", res=" + res
				+ ", amount=" + amount + ", termId=" + termId + ", acq=" + acq
				+ ", iss=" + iss + ", cardType=" + cardType + ", disputeTxns="
				+ disputeTxns + ", bcnxTxn=" + bcnxTxn + "]";
	}
	
}
