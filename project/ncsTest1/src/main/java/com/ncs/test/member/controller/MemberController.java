package com.ncs.test.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ncs.test.member.model.service.MemberService;
import com.ncs.test.member.model.vo.Member;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;

	@RequestMapping("/")
	public String toMainPage() {
		return "index";
	}

	@RequestMapping("/login")
	public String memberLogin(Member id, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Member member = memberService.memberLogin(id);
		if (member == null) {
			model.addAttribute("msg", "로그인 실패");
			return "error";
		} else {
			session.setAttribute("loginMember", member);
			return "index";
		}
	}
}
