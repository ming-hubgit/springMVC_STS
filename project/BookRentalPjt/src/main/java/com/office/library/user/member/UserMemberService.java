package com.office.library.user.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMemberService {
	
	@Autowired
	UserMemberDAO userMemberDAO;
	
	final static public int USER_ACCOUNT_ALREADY_EXIST = 0;
	final static public int USER_ACCOUNT_CREATE_SUCCESS = 1;
	final static public int USER_ACCOUNT_CREATE_FAIL = -1;
	
	//회원 가입
	public int createAccountConfirm(UserMemberVO userMemberVO) {
		System.out.println("[UserMemberService] createAccountConfirm()");
		
		boolean isMember = userMemberDAO.isUserMember(userMemberVO.getU_m_id());
		
		if(!isMember) {
			int result = userMemberDAO.insertUserAccount(userMemberVO);
			
			if(result > 0) {
				return USER_ACCOUNT_CREATE_SUCCESS;
			}else {
				return USER_ACCOUNT_CREATE_FAIL;
			}
		}else {
			return USER_ACCOUNT_ALREADY_EXIST;
		}
	}
	//로그인
	public UserMemberVO loginConfirm(UserMemberVO userMemberVO) {
		System.out.println("[UserMemberService] loginConfirm()");
		
		UserMemberVO loginedUserMemberVO = userMemberDAO.selectUser(userMemberVO);
		
		if(loginedUserMemberVO != null) {
			System.out.println("[UserMemberService] USER MEMBER LOGIN SUCCESS!!");
		}else {
			System.out.println("[UserMemberService] USER MEMBER LOGIN FAIL!!");
		}
		return loginedUserMemberVO;
	}
	
	//계정 수정
	public int modifyAccountConfirm(UserMemberVO userMemberVO) {
		System.out.println("[UserMemberService] modifyAccountConfirm()");
		
		return userMemberDAO.updateUserAccount(userMemberVO);
	}
	//변경된 사용자 정보를 새로 받아오기
	public UserMemberVO getLoginedUserMemberVO(int u_m_no) {
		System.out.println("[UserMemberService] getLoginedUserMemberVO()");
		
		return userMemberDAO.selectUser(u_m_no);
	}
}
