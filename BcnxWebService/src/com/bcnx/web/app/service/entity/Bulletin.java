package com.bcnx.web.app.service.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.providers.multipart.PartType;
@Entity
@Table(name="BULLETIN")
public class Bulletin implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq1")
	@SequenceGenerator(name = "id_seq1", 
	                   sequenceName = "SEQ1",
	                   allocationSize = 1)
	@Column(name="TXNID")
	private int id;
	@Column(name="TITLE")
	private String title;
	@Column(name="DES")
	private String desc;
	@Column(name="TXNDATE")
	private Timestamp date;
	@Column(name="FILENAME")
	private String file;
	@Transient
	private byte[] bytes;
	@Transient
	private String type;
	@ManyToOne
	@JoinColumn(name="USRDATA_USRID")
	private User user;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	@FormParam("title")
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public byte[] getBytes() {
		return bytes;
	}
	@FormParam("file")
	@PartType("application/octet-stream")
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	public String getType(){
		return type;
	}
	@FormParam("type")
	public void setType(String type){
		this.type = type;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
