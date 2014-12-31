package com.bcnx.web.app.service.dao;

import java.sql.SQLException;
import java.util.List;

import com.bcnx.web.app.service.entity.Member;

public interface MemberDao {
	public void save(Member member) throws SQLException;
	public Member getMember(Member member) throws SQLException;
	public List<Member> getMembers(int first, int max) throws SQLException;
	public List<Member> getMembers(Member member, int first, int max) throws SQLException;
}
