package com.office.library.admin.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminMemberService {
	
	final static public int ADMIN_ACCOUNT_ALREADY_EXIST = 0;
	final static public int ADMIN_ACCOUNT_CREATE_SUCCESS = 1;
	final static public int ADMIN_ACCOUNT_CREATE_FAIL = -1;
	
	
	@Autowired
	AdminMemberDAO adminMemberDAO;
	
	public int createAccountConfirm(AdminMemberVO adminMemberVO) {
		System.out.println("[AdminMemberService] createAccountConfirm()");
		
		boolean isMember = adminMemberDAO.isAdminMember(adminMemberVO.getA_m_id());
		
		if(!isMember) {
			int result = adminMemberDAO.insertAdminAccount(adminMemberVO);
			
			if(result > 0) {
				return ADMIN_ACCOUNT_CREATE_SUCCESS; //return 1;
			}else {
				return ADMIN_ACCOUNT_CREATE_FAIL; //return -1;
			}
		}else {
			return ADMIN_ACCOUNT_ALREADY_EXIST; //return 0;
		}
	}
	
	public AdminMemberVO loginConfirm(AdminMemberVO adminMemberVO) {
		System.out.println("[AdminMemberService] loginConfirm()");
		
		//결과를 받아줄 객체를 만들어 줍니다.
		AdminMemberVO loginedAdminMemberVO =
				adminMemberDAO.selectAdmin(adminMemberVO);
		
		if(loginedAdminMemberVO != null) {
			System.out.println("[AdminMemberService] ADMIN MEMBER LOGIN SUCCESS!");
		}else {
			System.out.println("[AdminMemberService] ADMIN MEMBER LOGIN FAIL!!");
		}
		return loginedAdminMemberVO;
	}
	//관리자 목록 조회
	public List<AdminMemberVO> listupAdmin(){
		System.out.println("[AdminMemberService] listupAdmin()");
		
		return adminMemberDAO.selectAdmins();
	}
	
	//관리자 승인 처리
	public void setAdminApproval(int a_m_no) {
		System.out.println("[AdminMemberService] setAdminApproval()");
		
		int result = adminMemberDAO.updateAdminAccount(a_m_no);
	}
	
}
