package com.bcnx.web.app.service.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;
@Entity
@Table(name="DISPUTETXN")
@PrimaryKeyJoinColumns({
	@PrimaryKeyJoinColumn(name="BCNXSETL_MTI",  referencedColumnName="MTI"),
	@PrimaryKeyJoinColumn(name="BCNXSETL_STAN", referencedColumnName="STAN"),
	@PrimaryKeyJoinColumn(name="BCNXSETL_SLOT", referencedColumnName="SLOT"),
	@PrimaryKeyJoinColumn(name="BCNXSETL_RRN", referencedColumnName="RRN"),
	@PrimaryKeyJoinColumn(name="BCNXSETL_PROCC", referencedColumnName="PROCC")
})
public class DisputeTxn extends BcnxSettle {
	private static final long serialVersionUID = 1L;
	@Column(name="ID")
	private int id;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String getProc() {
		return super.getProc();
	}
	@Override
	public void setProc(String proc) {
		super.setProc(proc);
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
	@Override
	public String getMti() {
		return super.getMti();
	}
	@Override
	public void setMti(String mti) {
		super.setMti(mti);
	}
	@Override
	public String getRrn() {
		return super.getRrn();
	}
	@Override
	public void setRrn(String rrn) {
		super.setRrn(rrn);
	}
	@Override
	public String getSlot() {
		return super.getSlot();
	}
	@Override
	public void setSlot(String slot) {
		super.setSlot(slot);
	}
	@Override
	public String getStan() {
		return super.getStan();
	}
	@Override
	public void setStan(String stan) {
		super.setStan(stan);
	}
	@Override
	public String getIss() {
		return super.getIss();
	}
	@Override
	public String getAcq() {
		return super.getAcq();
	}
}
