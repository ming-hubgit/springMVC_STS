package com.office.library.user.member;

import java.security.SecureRandom;
import java.util.Date;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class UserMemberService {

	@Autowired
	UserMemberDAO userMemberDAO;
	
	@Autowired
	JavaMailSenderImpl javaMailSenderImpl;

	final static public int USER_ACCOUNT_ALREADY_EXIST = 0;
	final static public int USER_ACCOUNT_CREATE_SUCCESS = 1;
	final static public int USER_ACCOUNT_CREATE_FAIL = -1;

	// 회원 가입
	public int createAccountConfirm(UserMemberVO userMemberVO) {
		System.out.println("[UserMemberService] createAccountConfirm()");

		boolean isMember = userMemberDAO.isUserMember(userMemberVO.getU_m_id());

		if (!isMember) {
			int result = userMemberDAO.insertUserAccount(userMemberVO);

			if (result > 0) {
				return USER_ACCOUNT_CREATE_SUCCESS;
			} else {
				return USER_ACCOUNT_CREATE_FAIL;
			}
		} else {
			return USER_ACCOUNT_ALREADY_EXIST;
		}
	}

	// 로그인
	public UserMemberVO loginConfirm(UserMemberVO userMemberVO) {
		System.out.println("[UserMemberService] loginConfirm()");

		UserMemberVO loginedUserMemberVO = userMemberDAO.selectUser(userMemberVO);

		if (loginedUserMemberVO != null) {
			System.out.println("[UserMemberService] USER MEMBER LOGIN SUCCESS!!");
		} else {
			System.out.println("[UserMemberService] USER MEMBER LOGIN FAIL!!");
		}
		return loginedUserMemberVO;
	}

	// 계정 수정
	public int modifyAccountConfirm(UserMemberVO userMemberVO) {
		System.out.println("[UserMemberService] modifyAccountConfirm()");

		return userMemberDAO.updateUserAccount(userMemberVO);
	}

	// 변경된 사용자 정보를 새로 받아오기
	public UserMemberVO getLoginedUserMemberVO(int u_m_no) {
		System.out.println("[UserMemberService] getLoginedUserMemberVO()");

		return userMemberDAO.selectUser(u_m_no);
	}

	// 비밀번호 찾기
	public int findPasswordConfirm(UserMemberVO userMemberVO) {
		System.out.println("[UserMemberService] findPasswordConfirm()");

		UserMemberVO selectedUserMemberVO = userMemberDAO.selectUser(userMemberVO.getU_m_id(),
				userMemberVO.getU_m_name(), userMemberVO.getU_m_mail());

		int result = 0;

		if (selectedUserMemberVO != null) {
			String newPassword = createNewPassword();
			result = userMemberDAO.updatePassword(userMemberVO.getU_m_id(), newPassword);
			if (result > 0) {
				sendNewPasswordByMail(userMemberVO.getU_m_mail(), newPassword);
			}
		}
		return result;
	}

	// 새로운 비밀번호 설정
	private String createNewPassword() {
		System.out.println("[UserMemberService] createNewPassword()");

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
		for (int i = 0; i < 8; i++) {
			index = secureRandom.nextInt(length);

			if (index % 2 == 0) {
				stringBuffer.append(String.valueOf(chars[index]).toUpperCase());
			} else {
				stringBuffer.append(String.valueOf(chars[index]).toLowerCase());
			}
		}
		
		System.out.println("[UserMemberService] NEW PASSWORD: " + stringBuffer.toString());
		
		return stringBuffer.toString();
	}

	// 새로운 비밀번호 이메일로 발송
	private void sendNewPasswordByMail(String toMailAddr, String newPassword) {
		System.out.println("[UserMemberService] sendNewPasswordBymail()");

		final MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
				//mimeMessageHelper.setTo(toMailAddr);
				// 받고싶은 메일이 하나로 고정이라면 이렇게 넣어줄 수 있겠죠?
				mimeMessageHelper.setTo("taehoedu@gmail.com");
				mimeMessageHelper.setSubject("[한국도서관] 새로운 비밀번호 안내입니다.");
				mimeMessageHelper.setText("새 비밀번호: " + newPassword, true);
			}
		};
		javaMailSenderImpl.send(mimeMessagePreparator);
	}
	
}
