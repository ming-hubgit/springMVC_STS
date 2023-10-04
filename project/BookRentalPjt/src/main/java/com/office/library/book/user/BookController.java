package com.office.library.book.user;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.office.library.book.BookVO;
import com.office.library.user.member.UserMemberVO;

@Controller
@RequestMapping("/book/user")
public class BookController {
	@Autowired
	BookService bookService;
	//도서 검색
	@GetMapping("/searchBookConfirm")
	public String searchBookConfirm(BookVO bookVO, Model model) {
		System.out.println("[BookController] searchBookConfirm()");
		
		String nextPage = "user/book/search_book";
		
		List<BookVO> bookVOs = bookService.searchBookConfirm(bookVO);
		
		model.addAttribute("bookVOs", bookVOs);
		
		return nextPage;
	}
	//도서 상세
	@GetMapping("/bookDetail")
	public String bookDetail(@RequestParam("b_no") int b_no, Model model) {
		System.out.println("[BookController] bookDetail()");
		
		String nextPage = "user/book/book_detail";
		
		BookVO bookVO = bookService.bookDetail(b_no);
		
		model.addAttribute("bookVO", bookVO);
		
		return nextPage;
	}
	//도서 대출 기능
	@GetMapping("/rentalBookConfirm")
	public String rentalBookConfirm(@RequestParam("b_no") int b_no, HttpSession session) {
		System.out.println("[BookController] rentalBookConfirm()");
		
		String nextPage = "user/book/rental_book_ok";
		
		UserMemberVO loginedUserMemberVO = 
				(UserMemberVO)session.getAttribute("loginedUserMemberVO");
		
		if(loginedUserMemberVO == null) {
			return "redirect:/user/member/loginForm";
		}
		int result = bookService.rentalBookConfirm(b_no, loginedUserMemberVO.getU_m_no());
		
		if(result <= 0) {
			nextPage = "user/book/rental_book_ng";
		}
		return nextPage;
	}
}
