package com.bcnx.web.app.service.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name="SETLMENT")
public class SettleBcnx implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="ID")
	private int id;
	@Column(name="DATE",unique=true)
	private Date date;
	@Column(name="ISSTXN")
	private int issNum = 0;
	@Column(name="ISSAMT")
	private double issAmt = 0;
	@Column(name="ISSFEE")
	private double issFee = 0;
	@Transient
	private double issTot = 0;
	@Column(name="ACQTXN")
	private int acqNum = 0;
	@Column(name="ACQAMT")
	private double acqAmt = 0;
	@Column(name="ACQFEE")
	private double acqFee = 0;
	@Transient
	private double acqTot = 0;
	@Column(name="REVTXN")
	private int revNum = 0;
	@Column(name="REVAMT")
	private double revAmt = 0;
	@Column(name="REVFEE")
	private double revFee = 0;
	@Transient
	private double revTot = 0;
	@Column(name="ERRTXN")
	private int errNum = 0;
	@Column(name="ERRAMT")
	private double errAmt = 0;
	@Column(name="ERRFEE")
	private double errFee = 0;
	@Transient
	private double errTol = 0;
	
	@Column(name="INCPRTXN")
	private int inCprNum = 0;
	@Transient
	private double inCprAmt =0;
	@Transient
	private double inCprFee = 0;
	
	@Column(name="OUCPRTXN")
	private int ouCprNum = 0;
	@Transient
	private double ouCprAmt = 0;
	@Transient
	private double ouCprFee = 0;
	
	@Column(name="INCRSTXN")
	private int inCrsNum = 0;
	@Transient
	private double inCrsAmt=0;
	@Transient
	private double inCrsFee=0;
	
	@Column(name="OUCRSTXN")
	private int ouCrsNum = 0;
	@Transient
	private double ouCrsAmt = 0;
	@Transient
	private double ouCrsFee = 0;
	
	@Column(name="INCHBTXN")
	private int inChbNum = 0;
	@Column(name="INCHBAMT")
	private double inChbAmt = 0;
	@Column(name="INCHBFEE")
	private double inChbFee = 0;
	@Transient
	private double inChbTot = 0;
	
	@Column(name="OUCHBTXN")
	private int ouChbNum = 0;
	@Column(name="OUCHBAMT")
	private double ouChbAmt = 0;
	@Column(name="OUCHBFEE")
	private double ouChbFee = 0;
	@Transient
	private double ouChbTot = 0;
	
	@Column(name="INADJTXN")
	private int inAdjNum = 0;
	@Column(name="INADJAMT")
	private double inAdjAmt = 0;
	@Column(name="INADJFEE")
	private double inAdjFee = 0;
	@Transient
	private double inAdjTot = 0;
	
	@Column(name="OUADJTXN")
	private int ouAdjNum = 0;
	@Column(name="OUADJAMT")
	private double ouAdjAmt = 0;
	@Column(name="OUADJFEE")
	private double ouAdjFee = 0;
	@Transient
	private double ouAdjTot = 0;
	
	@Column(name="INRPMTXN")
	private int inRpmNum = 0;
	@Column(name="INRPMAMT")
	private double inRpmAmt = 0;
	@Column(name="INRPMFEE")
	private double inRpmFee = 0;
	@Transient
	private double inRpmTot = 0;
	
	@Column(name="OURPMTXN")
	private int ouRpmNum = 0;
	@Column(name="OURPMAMT")
	private double ouRpmAmt = 0;
	@Column(name="OURPMFEE")
	private double ouRpmFee = 0;
	@Transient
	private double ouRpmTot = 0;
	
	@Column(name="NETSETL")
	private double netAmt = 0;
	@Column(name="SETLFILE")
	private String setlFile;
	@Column(name="RECFILE")
	private String recFile;
	@ManyToOne
	@JoinColumn(name="MEMDATA_IIN")
	private Member member;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getIssNum() {
		return issNum;
	}
	public void setIssNum(int issNum) {
		this.issNum = issNum;
	}
	public double getIssAmt() {
		return issAmt;
	}
	public void setIssAmt(double issAmt) {
		this.issAmt = issAmt;
	}
	public double getIssFee() {
		return issFee;
	}
	public void setIssFee(double issFee) {
		this.issFee = issFee;
	}
	public double getIssTot() {
		return issTot;
	}
	public void setIssTot(double issTot) {
		this.issTot = issTot;
	}
	public int getAcqNum() {
		return acqNum;
	}
	public void setAcqNum(int acqNum) {
		this.acqNum = acqNum;
	}
	public double getAcqAmt() {
		return acqAmt;
	}
	public void setAcqAmt(double acqAmt) {
		this.acqAmt = acqAmt;
	}
	public double getAcqFee() {
		return acqFee;
	}
	public void setAcqFee(double acqFee) {
		this.acqFee = acqFee;
	}
	public double getAcqTot() {
		return acqTot;
	}
	public void setAcqTot(double acqTot) {
		this.acqTot = acqTot;
	}
	public int getRevNum() {
		return revNum;
	}
	public void setRevNum(int revNum) {
		this.revNum = revNum;
	}
	public double getRevAmt() {
		return revAmt;
	}
	public void setRevAmt(double revAmt) {
		this.revAmt = revAmt;
	}
	public double getRevFee() {
		return revFee;
	}
	public void setRevFee(double revFee) {
		this.revFee = revFee;
	}
	public double getRevTot() {
		return revTot;
	}
	public void setRevTot(double revTot) {
		this.revTot = revTot;
	}
	public int getErrNum() {
		return errNum;
	}
	public void setErrNum(int errNum) {
		this.errNum = errNum;
	}
	public double getErrAmt() {
		return errAmt;
	}
	public void setErrAmt(double errAmt) {
		this.errAmt = errAmt;
	}
	public double getErrFee() {
		return errFee;
	}
	public void setErrFee(double errFee) {
		this.errFee = errFee;
	}
	public double getErrTol() {
		return errTol;
	}
	public void setErrTol(double errTol) {
		this.errTol = errTol;
	}
	public int getInCprNum() {
		return inCprNum;
	}
	public void setInCprNum(int inCprNum) {
		this.inCprNum = inCprNum;
	}
	public int getInCrsNum() {
		return inCrsNum;
	}
	public void setInCrsNum(int inCrsNum) {
		this.inCrsNum = inCrsNum;
	}
	public int getInChbNum() {
		return inChbNum;
	}
	public void setInChbNum(int inChbNum) {
		this.inChbNum = inChbNum;
	}
	public double getInChbAmt() {
		return inChbAmt;
	}
	public void setInChbAmt(double inChbAmt) {
		this.inChbAmt = inChbAmt;
	}
	public double getInChbFee() {
		return inChbFee;
	}
	public void setInChbFee(double inChbFee) {
		this.inChbFee = inChbFee;
	}
	public double getInChbTot() {
		return inChbTot;
	}
	public void setInChbTot(double inChbTot) {
		this.inChbTot = inChbTot;
	}
	public int getInAdjNum() {
		return inAdjNum;
	}
	public void setInAdjNum(int inAdjNum) {
		this.inAdjNum = inAdjNum;
	}
	public double getInAdjAmt() {
		return inAdjAmt;
	}
	public void setInAdjAmt(double inAdjAmt) {
		this.inAdjAmt = inAdjAmt;
	}
	public double getInAdjFee() {
		return inAdjFee;
	}
	public void setInAdjFee(double inAdjFee) {
		this.inAdjFee = inAdjFee;
	}
	public double getInAdjTot() {
		return inAdjTot;
	}
	public void setInAdjTot(double inAdjTot) {
		this.inAdjTot = inAdjTot;
	}
	public int getInRpmNum() {
		return inRpmNum;
	}
	public void setInRpmNum(int inRpmNum) {
		this.inRpmNum = inRpmNum;
	}
	public double getInRpmAmt() {
		return inRpmAmt;
	}
	public void setInRpmAmt(double inRpmAmt) {
		this.inRpmAmt = inRpmAmt;
	}
	public double getInRpmFee() {
		return inRpmFee;
	}
	public void setInRpmFee(double inRpmFee) {
		this.inRpmFee = inRpmFee;
	}
	public double getInRpmTot() {
		return inRpmTot;
	}
	public void setInRpmTot(double inRpmTot) {
		this.inRpmTot = inRpmTot;
	}
	public int getOuCprNum() {
		return ouCprNum;
	}
	public void setOuCprNum(int ouCprNum) {
		this.ouCprNum = ouCprNum;
	}
	public int getOuCrsNum() {
		return ouCrsNum;
	}
	public void setOuCrsNum(int ouCrsNum) {
		this.ouCrsNum = ouCrsNum;
	}
	public int getOuChbNum() {
		return ouChbNum;
	}
	public void setOuChbNum(int ouChbNum) {
		this.ouChbNum = ouChbNum;
	}
	public double getOuChbAmt() {
		return ouChbAmt;
	}
	public void setOuChbAmt(double ouChbAmt) {
		this.ouChbAmt = ouChbAmt;
	}
	public double getOuChbFee() {
		return ouChbFee;
	}
	public void setOuChbFee(double ouChbFee) {
		this.ouChbFee = ouChbFee;
	}
	public double getOuChbTot() {
		return ouChbTot;
	}
	public void setOuChbTot(double ouChbTot) {
		this.ouChbTot = ouChbTot;
	}
	public int getOuAdjNum() {
		return ouAdjNum;
	}
	public void setOuAdjNum(int ouAdjNum) {
		this.ouAdjNum = ouAdjNum;
	}
	public double getOuAdjAmt() {
		return ouAdjAmt;
	}
	public void setOuAdjAmt(double ouAdjAmt) {
		this.ouAdjAmt = ouAdjAmt;
	}
	public double getOuAdjFee() {
		return ouAdjFee;
	}
	public void setOuAdjFee(double ouAdjFee) {
		this.ouAdjFee = ouAdjFee;
	}
	public double getOuAdjTot() {
		return ouAdjTot;
	}
	public void setOuAdjTot(double ouAdjTot) {
		this.ouAdjTot = ouAdjTot;
	}
	public int getOuRpmNum() {
		return ouRpmNum;
	}
	public void setOuRpmNum(int ouRpmNum) {
		this.ouRpmNum = ouRpmNum;
	}
	public double getOuRpmAmt() {
		return ouRpmAmt;
	}
	public void setOuRpmAmt(double ouRpmAmt) {
		this.ouRpmAmt = ouRpmAmt;
	}
	public double getOuRpmFee() {
		return ouRpmFee;
	}
	public void setOuRpmFee(double ouRpmFee) {
		this.ouRpmFee = ouRpmFee;
	}
	public double getOuRpmTot() {
		return ouRpmTot;
	}
	public void setOuRpmTot(double ouRpmTot) {
		this.ouRpmTot = ouRpmTot;
	}
	public double getInCprAmt() {
		return inCprAmt;
	}
	public void setInCprAmt(double inCprAmt) {
		this.inCprAmt = inCprAmt;
	}
	public double getInCprFee() {
		return inCprFee;
	}
	public void setInCprFee(double inCprFee) {
		this.inCprFee = inCprFee;
	}
	public double getOuCprAmt() {
		return ouCprAmt;
	}
	public void setOuCprAmt(double ouCprAmt) {
		this.ouCprAmt = ouCprAmt;
	}
	public double getOuCprFee() {
		return ouCprFee;
	}
	public void setOuCprFee(double ouCprFee) {
		this.ouCprFee = ouCprFee;
	}
	public double getInCrsAmt() {
		return inCrsAmt;
	}
	public void setInCrsAmt(double inCrsAmt) {
		this.inCrsAmt = inCrsAmt;
	}
	public double getInCrsFee() {
		return inCrsFee;
	}
	public void setInCrsFee(double inCrsFee) {
		this.inCrsFee = inCrsFee;
	}
	public double getOuCrsAmt() {
		return ouCrsAmt;
	}
	public void setOuCrsAmt(double ouCrsAmt) {
		this.ouCrsAmt = ouCrsAmt;
	}
	public double getOuCrsFee() {
		return ouCrsFee;
	}
	public void setOuCrsFee(double ouCrsFee) {
		this.ouCrsFee = ouCrsFee;
	}
	public double getNetAmt() {
		return netAmt;
	}
	public void setNetAmt(double netAmt) {
		this.netAmt = netAmt;
	}
	public String getSetlFile() {
		return setlFile;
	}
	public void setSetlFile(String setlFile) {
		this.setlFile = setlFile;
	}
	public String getRecFile() {
		return recFile;
	}
	public void setRecFile(String recFile) {
		this.recFile = recFile;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
}
