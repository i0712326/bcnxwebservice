package com.bcnx.web.app.service.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="DISPUTETXN")
public class DisputeTxn implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name="ID")
	private int id;
	@Column(name="PROCC")
	private String procc;
	@Column(name="RC")
	private String rc;
	@Column(name="REMARK")
	private String remark;
	@Column(name="DATE")
	private Date date;
	@Column(name="TIME")
	private String time;
	@Column(name="AMOUNT")
	private double amount;
	@Column(name="FEE")
	private double fee;
	@Column(name="FLAG")
	private String flag;
	@Column(name="RRN")
	private String rrn;
	@Column(name="COUNT")
	private int count;
	@Column(name="ISSID")
	private String issId;
	@Column(name="ACQID")
	private String acqId;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="URSDATA_USRID")
	private User user;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProcc() {
		return procc;
	}
	public void setProcc(String procc) {
		this.procc = procc;
	}
	public String getRc() {
		return rc;
	}
	public void setRc(String rc) {
		this.rc = rc;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getRrn() {
		return rrn;
	}
	public void setRrn(String rrn) {
		this.rrn = rrn;
	}
	public String getIssId() {
		return issId;
	}
	public void setIssId(String issId) {
		this.issId = issId;
	}
	public String getAcqId() {
		return acqId;
	}
	public void setAcqId(String acqId) {
		this.acqId = acqId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
