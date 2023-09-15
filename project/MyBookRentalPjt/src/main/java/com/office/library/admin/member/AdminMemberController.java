package com.office.library.admin.member;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
	//로그인 창
	@GetMapping("/loginForm")
	public String loginForm() {
		System.out.println("[AdminMemberController] loginForm()");
		
		String nextPage = "/admin/member/login_form";
		
		return nextPage;
	}
	
	//로그인 처리할 메서드
	@PostMapping("/loginConfirm")
	public String loginConfirm(AdminMemberVO adminMemberVO, HttpSession session) {
		System.out.println("[AdminMemberController] loginConfirm()");
		
		String nextPage = "admin/member/login_ok";
		
		AdminMemberVO loginedAdminMemberVO = 
				adminMemberService.loginConfirm(adminMemberVO);
		if(loginedAdminMemberVO == null) {
			nextPage = "admin/member/login_ng";
		}else {
			session.setAttribute("loginedAdminMemberVO", loginedAdminMemberVO);
			session.setMaxInactiveInterval(60*30);
		}
		return nextPage;
	}
	
	//로그아웃 처리
	@GetMapping("/logoutConfirm")
	public String logoutConfirm(HttpSession session) {
		System.out.println("[AdminMemberController] logoutConfirm()");
		String nextPage = "redirect:/admin";
		
		//세션이 초기화됩니다.
		session.invalidate();
		
		return nextPage;
	}
	
	//관리자 목록 - ModelAndView 사용
	@GetMapping("/listupAdmin")
	public ModelAndView listupAdmin(Model model) {
		System.out.println("[AdminMemberController] listupAdmin()");
		
		String nextPage = "admin/member/listup_admins";
		
		List<AdminMemberVO> adminMemberVOs = adminMemberService.listupAdmin();
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("adminMemberVOs", adminMemberVOs);
		modelAndView.setViewName(nextPage);
		
		return modelAndView;
	}
	
	//관리자 승인처리
	@GetMapping("/setAdminApproval")
	public String setAdminApproval(@RequestParam("a_m_no") int a_m_no) {
		System.out.println("[AdminMemberController] setAdminApproval()");
		
		String nextPage = "redirect:/admin/member/listupAdmin";
		
		adminMemberService.setAdminApproval(a_m_no);
		
		return nextPage;
	}
}
