package com.bcnx.web.app.service.entity;

import java.io.IOException;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
@Entity
@Table(name="USRDATA")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name="ID")
	@GeneratedValue
	private int id;
	@Id
	@Column(name="USRID")
	private String userId;
	@Column(name="PASSWD",unique=true,nullable=false,length=45)
	private String passwd;
	@Column(name="NAME",nullable=false,length=45)
	private String name;
	@Column(name="EMAIL",nullable=false,length=45)
	private String email;
	@Column(name="COUNT",nullable=false,length=45)
	private int count = 0;
	@Column(name="STATUS",nullable=false,length=1)
	private String status = "U";
	@Column(name="STATE",nullable=false)
	private int state = 0;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="MEMDATA_IIN")
	private Member member;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ROLEDATA_ROLEID")
	private Role role;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	@Override
	public String toString() {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			String json = ow.writeValueAsString(this);
			return json;
		} catch (JsonGenerationException e) {
			e.printStackTrace();
			return null;
		} catch (JsonMappingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
