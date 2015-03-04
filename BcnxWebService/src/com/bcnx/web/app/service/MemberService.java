package com.bcnx.web.app.service;

import java.util.List;

import com.bcnx.web.app.service.entity.Member;

public interface MemberService {
	public void save(Member member);
	public Member getMember(Member member);
	public Member getMemIin(String iin);
	public List<Member> getMembers(int first, int max);
	public List<Member> getMembers(Member member, int first, int max);
	public List<Member> getMembers();
	public Member getOwner();
}
