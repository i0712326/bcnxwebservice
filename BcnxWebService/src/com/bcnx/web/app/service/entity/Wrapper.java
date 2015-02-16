package com.bcnx.web.app.service.entity;

import java.io.Serializable;

public class Wrapper implements Serializable {
	private static final long serialVersionUID = 1L;
	private int total;
	private int records;
	private int page;
	private Object rows;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getRecords() {
		return records;
	}
	public void setRecords(int records) {
		this.records = records;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public Object getRows() {
		return rows;
	}
	public void setRows(Object rows) {
		this.rows = rows;
	}
}
