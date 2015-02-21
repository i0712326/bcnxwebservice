package com.bcnx.web.app.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.bcnx.web.app.service.dao.MemberDao;
import com.bcnx.web.app.service.entity.Member;

public class MemberServiceImp implements MemberService {
	private static final Logger logger = Logger.getLogger(MemberServiceImp.class);
	private MemberDao memberDao;
	public void setMemberDao(MemberDao memberDao){
		this.memberDao = memberDao;
	}
	@Override
	public void save(Member member) {
		try {
			memberDao.save(member);
		} catch (SQLException e) {
			logger.debug("Exception occured why try to save member data", e);
		}
	}
	@Override
	public Member getMember(Member member) {
		try {
			return memberDao.getMember(member);
		} catch (SQLException e) {
			logger.debug("Exception occured while try to get member", e);
			return null;
		}
	}
	@Override
	public List<Member> getMembers(int first, int max) {
		try {
			return memberDao.getMembers(first, max);
		} catch (SQLException e) {
			logger.debug("Exception occured while try to get members", e);
			return null;
		}
	}
	@Override
	public List<Member> getMembers(Member member, int first, int max) {
		try {
			return memberDao.getMembers(member, first, max);
		} catch (SQLException e) {
			logger.debug("Exception occured while try to get members", e);
			return null;
		}
	}
	@Override
	public List<Member> getMembers() {
		try {
			return memberDao.getMembers();
		} catch (SQLException e) {
			logger.debug("Exception occured while try to get members", e);
			return null;
		}
	}

}
