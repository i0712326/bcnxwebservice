package com.bcnx.web.app.service.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
@Entity
@Table(name="PROCC")
public class ProcCode implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PCODE")
	private String code;
	@Column(name="REMARK")
	private String remark;
	@Column(name="LIMIT")
	private int limit;
	@OneToMany(mappedBy = "cardProcId.procCode", cascade=CascadeType.ALL)
	private List<CardProc> cardProcs;
	@OneToMany(mappedBy = "procCode", cascade=CascadeType.ALL)
	private List<ReasonCode> reasonCodes;
	public ProcCode(){};
	public ProcCode(String code){
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	@JsonIgnore
	public List<CardProc> getCardProcs() {
		return cardProcs;
	}
	public void setCardProcs(List<CardProc> cardProcs) {
		this.cardProcs = cardProcs;
	}
	@JsonIgnore
	public List<ReasonCode> getReasonCodes() {
		return reasonCodes;
	}
	public void setReasonCodes(List<ReasonCode> reasonCodes) {
		this.reasonCodes = reasonCodes;
	}
}
