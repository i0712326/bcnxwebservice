package com.bcnx.web.app.service.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="REASONCODE")
public class ReasonCode implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="CODE")
	private String code;
	@Column(name="DES")
	private String des;
	@ManyToOne
	@JoinColumn(name="PROCC_PCODE")
	private ProcCode procCode;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public ProcCode getProcCode() {
		return procCode;
	}
	public void setProcCode(ProcCode procCode) {
		this.procCode = procCode;
	}
}
