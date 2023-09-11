package com.company.hello.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	
	@Autowired
	MemberDAO memberDAO;
	
	public int signUpConfirm(MemberVO memberVO) {
		System.out.println("[MemberService] signUpConfirm()");
		
		System.out.println("m_id : " + memberVO.getM_id());
		System.out.println("m_pw : " + memberVO.getM_pw());
		System.out.println("m_mail : " + memberVO.getM_mail());
		System.out.println("m_phone : " + memberVO.getM_phone());
		
		memberDAO.insertMember(memberVO);
		return 0;
	}
	
	public MemberVO signInConfirm(MemberVO memberVO) {
		System.out.println("[MemberService] signInConfirm()");
		
		MemberVO signedInMember = memberDAO.selectMember(memberVO);
		
		return signedInMember;
	}
}
