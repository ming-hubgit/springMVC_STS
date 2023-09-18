package com.office.library.admin.member;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class AdminMemberService {
	
	final static public int ADMIN_ACCOUNT_ALREADY_EXIST = 0;
	final static public int ADMIN_ACCOUNT_CREATE_SUCCESS = 1;
	final static public int ADMIN_ACCOUNT_CREATE_FAIL = -1;
	
	
	@Autowired
	AdminMemberDAO adminMemberDAO;
	
	@Autowired
	JavaMailSenderImpl javaMailSenderImpl;
	
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
	
	//계정 수정
	public int modifyAccountConfirm(AdminMemberVO adminMemberVO) {
		System.out.println("[AdminMemberService] modifyAccountConfirm()");
		
		return adminMemberDAO.updateAdminAccount(adminMemberVO);
	}
	
	public AdminMemberVO getLoginedAdminMemberVO(int a_m_no) {
		System.out.println("[AdminMemberService] getLoginedAdminMemberVO()");
		
		return adminMemberDAO.selectAdmin(a_m_no);
	}
	
	//비밀번호 찾기
	public int findPasswordConfirm(AdminMemberVO adminMemberVO) {
		System.out.println("[AdminMemberService] findPasswordConfirm()");
		
		AdminMemberVO selectedAdminMemberVO =
				adminMemberDAO.selectAdmin(adminMemberVO.getA_m_id(),
							adminMemberVO.getA_m_name(),
							adminMemberVO.getA_m_mail());
		
		int result = 0;
		
		if(selectedAdminMemberVO != null) {
			String newPassword = createNewPassword();
			result = adminMemberDAO.updatePassword(adminMemberVO.getA_m_id(), newPassword);
			
			if(result > 0) {
				sendNewPasswordByMail(adminMemberVO.getA_m_mail(), newPassword);
			}
		}
			return result;
		}
	//난수로 임시비밀번호 만들기
	private String createNewPassword() {
		System.out.println("[AdminMemberService] createNewPassword()");
		
		char[] chars = new char[] {
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
				'u', 'v', 'w', 'x', 'y', 'z'
		};
		
		StringBuffer stringBuffer = new StringBuffer();
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.setSeed(new Date().getTime());
		
		int index = 0;
		int length = chars.length;
		for(int i = 0; i < 8; i++) {
			index = secureRandom.nextInt(length);
			
			if(index % 2 == 0) {
				stringBuffer.append(String.valueOf(chars[index]).toUpperCase());
			}else {
				stringBuffer.append(String.valueOf(chars[index]).toLowerCase());
			}
		}
		
		System.out.println("[AdminMemberService] NEW PASSWORD: " 
		+ stringBuffer.toString());
		
		return stringBuffer.toString();
	}
	//임시비밀번호 메일로 보내기
	private void sendNewPasswordByMail(String toMailAddr, String newPassword) {
		System.out.println("[AdminMemberService] sendNewPasswordByMail()");
		
		//메일 내용 저장하는 공간 MimeMessagePreparator
		final MimeMessagePreparator mimeMessagePreparator 
		= new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper mimeMessageHelper
				= new MimeMessageHelper(mimeMessage, true, "UTF-8");
				mimeMessageHelper.setTo(toMailAddr);
				mimeMessageHelper.setSubject("[한국도서관] 새비밀번호 안내입니다.");
				mimeMessageHelper.setText("새비밀번호 : " + newPassword, true);
			}
		};
		javaMailSenderImpl.send(mimeMessagePreparator);
	}
}
