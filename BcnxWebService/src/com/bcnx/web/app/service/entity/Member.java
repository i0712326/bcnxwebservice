package com.bcnx.web.app.service.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnore;
@Entity
@Table(name="MEMDATA")
public class Member implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="IIN", nullable=true, unique=true, length=6)
	private String iin;
	@Column(name="MEMID", nullable=true, unique=true, length=6)
	private String memId;
	@Column(name="MEMENTRY", nullable=true, length=45)
	private String entry;
	@Column(name="TEL", nullable=true, unique=true, length=12)
	private String tel;
	@Column(name="FAX", nullable=true, unique=true, length=12)
	private String fax;
	@Column(name="ADDRESS", nullable=true, length=128)
	private String address;
	@Column(name="FLAG", nullable=false, length=1)
	private String flag;
	@Column(name="TYPE", nullable=false, length=10)
	private String type;
	@OneToMany(mappedBy="member",cascade=CascadeType.ALL)
	private List<Bin> bins;
	@OneToMany(mappedBy="member",cascade=CascadeType.ALL)
	private List<User> user;
	@OneToMany(mappedBy="member",cascade=CascadeType.ALL)
	private List<SettleBcnx> settleBcnx;
	@Transient
	private double netAmount;
	public String getIin() {
		return iin;
	}
	public void setIin(String iin) {
		this.iin = iin;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getEntry() {
		return entry;
	}
	public void setEntry(String entry) {
		this.entry = entry;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@JsonIgnore
	public List<Bin> getBins() {
		return bins;
	}
	public void setBins(List<Bin> bins) {
		this.bins = bins;
	}
	@JsonIgnore
	public List<User> getUser() {
		return user;
	}
	public void setUser(List<User> user) {
		this.user = user;
	}
	@JsonIgnore
	public List<SettleBcnx> getSettleBcnx() {
		return settleBcnx;
	}
	public void setSettleBcnx(List<SettleBcnx> settleBcnx) {
		this.settleBcnx = settleBcnx;
	}
	public double getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}
}
