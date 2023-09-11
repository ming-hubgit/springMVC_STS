package com.company.hello.member;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class MemberDAO {
	private Map<String, MemberVO> memberDB = new HashMap<String, MemberVO>();
	
	public void insertMember(MemberVO memberVO) {
		System.out.println("[MemberDAO] insertMember()");
		System.out.println("m_id : " + memberVO.getM_id());
		System.out.println("m_pw : " + memberVO.getM_pw());
		System.out.println("m_mail : " + memberVO.getM_mail());
		System.out.println("m_phone : " + memberVO.getM_phone());
	
		memberDB.put(memberVO.getM_id(), memberVO);
		this.printAllMember();
	}
	
	public MemberVO selectMember(MemberVO memberVO) {
		System.out.println("[MemberDAO] selectMember()");
		
		MemberVO signedInMember = memberDB.get(memberVO.getM_id());
		if(signedInMember != null && memberVO.getM_pw().equals(signedInMember.getM_pw())) {
			return signedInMember;
		}else {
			return null;
		}
	}
	
	private void printAllMember() {
		System.out.println("[MemberDAO] printAllMembers()");
		
		Set<String> keys = memberDB.keySet();
		Iterator<String> iterator = keys.iterator();
		
		while(iterator.hasNext()) {
			String key = iterator.next();
			MemberVO memberVO = memberDB.get(key);
			
			System.out.println("m_id : " + memberVO.getM_id());
			System.out.println("m_pw : " + memberVO.getM_pw());
			System.out.println("m_mail : " + memberVO.getM_mail());
			System.out.println("m_phone : " + memberVO.getM_phone());
		}
	}
}
