package com.bcnx.web.app.service;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import com.bcnx.web.app.service.entity.Member;
import com.bcnx.web.app.service.entity.Report;

public interface ReportService extends Serializable {
	public void save(Report report);
	public List<Report> getReports(Member member, Date start, Date end, int first, int max);
	public List<Report> getReports(Member member, Date date, int first, int max);
}
