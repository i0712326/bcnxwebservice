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
@Table(name="CARDTYPE")
public class CardType implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="TYPE")
	private String type;
	@Column(name="REMARK")
	private String remark;
	@OneToMany(fetch=FetchType.LAZY, mappedBy="cardProcId.cardType",cascade=CascadeType.ALL)
	private List<CardProc> cardProcs = new ArrayList<CardProc>();
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
