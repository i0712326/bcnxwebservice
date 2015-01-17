package com.bcnx.web.app.service.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="BININFO")
public class Bin implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="BIN")
	private String bin;
	@Column(name="TYPE")
	private String type;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="MEMDATA_IIN")
	private Member member;
	public String getBin() {
		return bin;
	}
	public void setBin(String bin) {
		this.bin = bin;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
}
