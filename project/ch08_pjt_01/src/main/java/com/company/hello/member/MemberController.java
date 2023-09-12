package com.company.hello.member;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MemberController {
	@Autowired
	MemberService memberService;
	
	@RequestMapping("/signUp")
	public String signUp() {
		return "sign_up";
	}
	
	@RequestMapping("/signIn")
	public String signIn() {
		return "sign_in";
	}
	
	@RequestMapping("/signUpConfirm")
	public String signUpConfirm(MemberVO memberVO) {
		System.out.println("[MemberController] signUpConfirm()");
		
		System.out.println("m_id : " + memberVO.getM_id());
		System.out.println("m_pw : " + memberVO.getM_pw());
		System.out.println("m_mail : " + memberVO.getM_mail());
		System.out.println("m_phone : " + memberVO.getM_phone());
		
		memberService.signUpConfirm(memberVO);
		return "sign_up_ok";
	}
	
	@RequestMapping("/signInConfirm")
	public String signInConfirm(MemberVO memberVO) {
		System.out.println("[MemberController] signInConfirm()");
		
		MemberVO signedInMember = memberService.signInConfirm(memberVO);
		if(signedInMember != null) {
			return "sign_in_ok";
		}else {
			return "sign_in_ng";
		}
	}
	
	@RequestMapping("/signout")
	public ModelAndView signout(HttpSession session) {
		session.invalidate();
		ModelAndView mv = new ModelAndView("/sign_in");
		return mv;
	}
}
