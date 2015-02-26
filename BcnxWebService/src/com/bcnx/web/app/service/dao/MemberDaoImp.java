package com.bcnx.web.app.service.dao;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.bcnx.web.app.service.entity.Member;

public class MemberDaoImp implements MemberDao {
	private HibernateTemplate hibernateTemplate;
	public void setSessionFactory(SessionFactory sessionFactory){
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	@Transactional
	@Override
	public void save(Member member) throws SQLException {
		hibernateTemplate.save(member);
	}
	@Transactional
	@Override
	public Member getMember(final Member member) throws SQLException {
		return hibernateTemplate.execute(new HibernateCallback<Member>(){
			@Override
			public Member doInHibernate(Session session) throws HibernateException {
				String hql = "from Member m where m.memId = :id";
				Query query = session.createQuery(hql);
				query.setString("id", member.getMemId());
				return (Member) query.uniqueResult();
			}
			
		});
	}
	@Transactional
	@Override
	public Member getMemIin(final String iin) throws SQLException {
		return hibernateTemplate.execute(new HibernateCallback<Member>(){
			@Override
			public Member doInHibernate(Session session) throws HibernateException {
				String hql = "from Member m where m.iin = :iin";
				Query query = session.createQuery(hql);
				query.setString("iin", iin);
				return (Member) query.uniqueResult();
			}
			
		});
	}
	@Transactional
	@Override
	public List<Member> getMembers(int first, int max) throws SQLException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Member.class);
		return toList(hibernateTemplate.findByCriteria(criteria, first, max));
	}
	@Transactional
	@Override
	public List<Member> getMembers(Member member, int first, int max)
			throws SQLException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Member.class);
		criteria.add(Restrictions.like("memId", member.getMemId()));
		return toList(hibernateTemplate.findByCriteria(criteria, first, max));
	}
	@Transactional
	@Override
	public List<Member> getMembers() throws SQLException {
		String hql = "from Member m order by m.iin";
		return toList(hibernateTemplate.find(hql));
	}
	private List<Member> toList(final List<?> beans){
		if(beans == null ) return null;
		if(beans.isEmpty()) return null;
		int size = beans.size();
		Member[] list = new Member[size];
		list = beans.toArray(list);
		return Arrays.asList(list);
	}
}
