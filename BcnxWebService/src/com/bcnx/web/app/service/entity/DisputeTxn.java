package com.bcnx.web.app.service.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="DISPUTETXN") 
public class DisputeTxn implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="ID")
	private int id;
	@Id
	@Column(name="PROCC")
	private String procc;
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
	@Column(name="COUNT")
	private int count;
	@Column(name="ISSID")
	private String iss;
	@Column(name="ACQID")
	private String acq;
	@Column(name="PRB")
	private String status="N";
	@Column(name="FILE")
	private String fileName;
	@ManyToOne
	@JoinColumn(name="REASONCODE_CODE")
	private ReasonCode rc;
	@ManyToOne
	@JoinColumn(name="USRDATA_USRID")
	private User user;
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "BCNXSETL_MTI", referencedColumnName="MTI"),
			@JoinColumn(name = "BCNXSETL_RRN", referencedColumnName="RRN"),
			@JoinColumn(name = "BCNXSETL_SLOT",  referencedColumnName="SLOT"),
			@JoinColumn(name = "BCNXSETL_STAN",  referencedColumnName="STAN") })
	private BcnxSettle bcnxSettle;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ReasonCode getRc() {
		return rc;
	}
	public void setRc(ReasonCode rc) {
		this.rc = rc;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public BcnxSettle getBcnxSettle() {
		return bcnxSettle;
	}
	public void setBcnxSettle(BcnxSettle bcnxSettle) {
		this.bcnxSettle = bcnxSettle;
	}
}
