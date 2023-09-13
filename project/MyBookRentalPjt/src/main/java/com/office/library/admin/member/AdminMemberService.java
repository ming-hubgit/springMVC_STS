package com.office.library.admin.member;

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
	
}
