package com.office.library.user.member;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/member")
public class UserMemberController {
	
	@Autowired
	UserMemberService userMemberService;
	
	//회원가입 창
	@GetMapping("/createAccountForm")
	public String createAccountForm() {
		System.out.println("[UserMemberController] createAccountForm()");
		
		String nextPage = "user/member/create_account_form";
		
		return nextPage;
	}
	
	//회원가입 기능
	@PostMapping("/createAccountConfirm")
	public String createAccountConfirm(UserMemberVO userMemberVO) {
		System.out.println("[UserMemberController] createAccountConfirm()");
		
		String nextPage = "user/member/create_account_ok";
		
		int result = userMemberService.createAccountConfirm(userMemberVO);
		
		if(result <= 0) {
			nextPage = "user/member/create_account_ng";
		}
		return nextPage;
	}
	//로그인 창
	@GetMapping("/loginForm")
	public String loginForm() {
		System.out.println("[UserMemberController] loginForm()");
		String nextPage = "user/member/login_form";
		return nextPage;
	}
	
	//로그인 기능
	@PostMapping("/loginConfirm")
	public String loginConfirm(UserMemberVO userMemberVO, HttpSession session) {
		System.out.println("[UserMemberController] loginConfirm()");
		
		String nextPage = "user/member/login_ok";
		
		UserMemberVO loginedUserMemberVO =
				userMemberService.loginConfirm(userMemberVO);
		
		if(loginedUserMemberVO == null) {
			nextPage = "user/member/login_ng";
		}else {
			session.setAttribute("loginedUserMemberVO", loginedUserMemberVO);
			session.setMaxInactiveInterval(60*30);
		}
		return nextPage;
	}
	
	//계정 수정 창
	@GetMapping("/modifyAccountForm")
	public String modifyAccountForm(HttpSession session) {
		System.out.println("[UserMemberController] modifyAccountForm()");
		
		String nextPage = "user/member/modify_account_form";
		
//		UserMemberVO loginedUserMemberVO =
//				(UserMemberVO) session.getAttribute("loginedUserMemberVO");
//		
//		if(loginedUserMemberVO == null) {
//			nextPage = "redirect:/user/member/loginForm";
//		}
		return nextPage;
	}
	//계정 수정
	@PostMapping("/modifyAccountConfirm")
	public String modifyAccountConfirm(UserMemberVO userMemberVO, HttpSession session) {
		System.out.println("[UserMemberController] modifyAccountConfirm()");
		
		String nextPage = "user/member/modify_account_ok";
		
		int result = userMemberService.modifyAccountConfirm(userMemberVO);
		
		if(result > 0) {
			UserMemberVO loginedUserMemberVO =
					userMemberService.getLoginedUserMemberVO(userMemberVO.getU_m_no());
			session.setAttribute("loginedUserMemberVO", loginedUserMemberVO);
			session.setMaxInactiveInterval(60*30);
		}else {
			nextPage = "user/member/modify_account_ng";
		}
		return nextPage;
	}
	
	//로그아웃
	@GetMapping("/logoutConfirm")
	public String logoutConfirm(HttpSession session) {
		System.out.println("[UserMemberController] logoutConfirm()");
		
		String nextPage = "redirect:/";
		session.invalidate();
		return nextPage;
	}
	//비밀번호 찾기 창
	@GetMapping("/findPasswordForm")
	public String findPasswordForm() {
		System.out.println("[UserMemberController] findPasswordForm()");
		
		String nextPage = "user/member/find_password_form";
		
		return nextPage;
	}
	//비밀번호 찾기 기능
	@PostMapping("/findPasswordConfirm")
	public String findPasswordConfirm(UserMemberVO userMemberVO) {
		System.out.println("[UserMemberController] findPasswordConfirm()");
		
		String nextPage = "user/member/find_password_ok";
		
		int result = userMemberService.findPasswordConfirm(userMemberVO);
		
		if(result <= 0) {
			nextPage = "user/member/find_password_ng";
		}
		
		return nextPage;
	}
}
