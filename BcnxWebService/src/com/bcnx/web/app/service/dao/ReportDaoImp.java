package com.bcnx.web.app.service.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.bcnx.web.app.service.entity.Member;
import com.bcnx.web.app.service.entity.Report;

public class ReportDaoImp implements ReportDao {
	private HibernateTemplate hibernateTemplate;
	public void setSessionFactory(SessionFactory sessionFactory){
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	@Transactional
	@Override
	public void save(Report report) throws SQLException, HibernateException {
		hibernateTemplate.save(report);
	}
	@Transactional
	@Override
	public Report getReport(final int id) throws SQLException, HibernateException {
		return hibernateTemplate.execute(new HibernateCallback<Report>(){
			@Override
			public Report doInHibernate(Session session)
					throws HibernateException {
				String hql = "from Report r where r.id = :id";
				Query query = session.createQuery(hql);
				query.setInteger("id", id);
				return (Report) query.uniqueResult();
			}
		});
	}
	@Transactional
	@Override
	public List<Report> getReports(Date start, Date end, int first, int max)
			throws SQLException, HibernateException {
		return hibernateTemplate.execute(new GetReportByDates(start,end,first,max));
	}
	private class GetReportByDates implements HibernateCallback<List<Report>>{
		private Date start;
		private Date end;
		private int first;
		private int max;
		public GetReportByDates(Date start, Date end,int first, int max){
			this.start = start;
			this.end = end;
			this.first = first;
			this.max = max;
		}
		@Override
		public List<Report> doInHibernate(Session session)
				throws HibernateException {
			String hql = "from Report r where r.date between :start and :end";
			Query query = session.createQuery(hql);
			query.setDate("start", start);
			query.setDate("end", end);
			query.setFirstResult(first);
			query.setMaxResults(max);
			return toList(query.list());
		}
		
	}
	@Transactional
	@Override
	public List<Report> getReports(Date date,int first, int max) throws SQLException,
			HibernateException {
		return hibernateTemplate.execute(new GetReportByDate(date,first,max));
	}
	private class GetReportByDate implements HibernateCallback<List<Report>>{
		private Date date;
		private int first;
		private int max;
		public GetReportByDate(Date date, int first, int max){
			this.date = date;
			this.first = first;
			this.max = max;
		}
		@Override
		public List<Report> doInHibernate(Session session)
				throws HibernateException {
			String hql = "from Report r where r.date = :date";
			Query query = session.createQuery(hql);
			query.setDate("date", date);
			query.setFirstResult(first);
			query.setMaxResults(max);
			return toList(query.list());
		}
		
	}
	@Transactional
	@Override
	public List<Report> getReports(Member member, int first, int max) throws SQLException,
			HibernateException {
		return hibernateTemplate.execute(new GetReportByMem(member, first, max));
	}
	private class GetReportByMem implements HibernateCallback<List<Report>>{
		private Member member;
		private int first;
		private int max;
		public GetReportByMem(Member member, int first, int max){
			this.member = member;
			this.first = first;
			this.max = max;
		}
		@Override
		public List<Report> doInHibernate(Session session)
				throws HibernateException {
			String hql = "from Report r join r.member m where m.iin = :iin";
			Query query = session.createQuery(hql);
			query.setString("iin", member.getIin());
			query.setFirstResult(first);
			query.setMaxResults(max);
			return toList(query.list());
		}
	}
	private List<Report> toList(final List<?> beans){
		if(beans==null) return null;
		if(beans.isEmpty()) return null;
		int size = beans.size();
		Report[] list = new Report[size];
		list = beans.toArray(list);
		return Arrays.asList(list);
	}
}
