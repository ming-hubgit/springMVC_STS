package com.office.library.admin.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("admin/member")
public class AdminMemberController {
	
	@Autowired
	AdminMemberService adminMemberService;
	
	@RequestMapping(value="/createAccountForm", method=RequestMethod.GET)
	public String createAccountForm() {
		System.out.println("[AdminMemberController] createAccountForm()");
		String nextPage = "admin/member/create_account_form";
		
		return nextPage;
	}
	
	//회원 가입 확인
	@PostMapping("/createAccountConfirm")
	public String createAccountConfirm(AdminMemberVO adminMemberVO) {
		System.out.println("[AdminMemberController createAccountConfirm()");
		
		String nextPage = "admin/member/create_account_ok";
		
		int result = adminMemberService.createAccountConfirm(adminMemberVO);
		
		if(result <= 0) {
			nextPage = "admin/member/create_account_ng";
		}
		return nextPage;
	}
	
}
