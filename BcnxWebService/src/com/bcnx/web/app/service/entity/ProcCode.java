package com.bcnx.web.app.service.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cardProcId.procCode", cascade=CascadeType.ALL)
	private List<CardProc> cardProcs = new ArrayList<CardProc>();
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
	public List<CardProc> getCardProcs() {
		return cardProcs;
	}
	public void setCardProcs(List<CardProc> cardProcs) {
		this.cardProcs = cardProcs;
	}
}
