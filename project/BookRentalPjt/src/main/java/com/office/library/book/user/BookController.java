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
import com.office.library.book.HopeBookVO;
import com.office.library.book.RentalBookVO;
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
		
//		if(loginedUserMemberVO == null) {
//			return "redirect:/user/member/loginForm";
//		}
		int result = bookService.rentalBookConfirm(b_no, loginedUserMemberVO.getU_m_no());
		
		if(result <= 0) {
			nextPage = "user/book/rental_book_ng";
		}
		return nextPage;
	}
	//나의 책장 => 대출 목록 보기
	@GetMapping("/enterBookshelf")
	public String enterBookshelf(HttpSession session, Model model) {
		System.out.println("[BookController] enterBookshelf()");
		
		String nextPage = "user/book/bookshelf";
		
		UserMemberVO loginedUserMemberVO =
				(UserMemberVO) session.getAttribute("loginedUserMemberVO");
		
		List<RentalBookVO> rentalBookVOs =
				bookService.enterBookshelf(loginedUserMemberVO.getU_m_no());
		
		model.addAttribute("rentalBookVOs", rentalBookVOs);
		
		return nextPage;
	}
	//전체 대출 이력 조회
	@GetMapping("/listupRentalBookHistory")
	public String listupRentalBookHistory(HttpSession session, Model model) {
		System.out.println("[BookController] listupRentalBookHistory()");
		
		String nextPage = "user/book/rental_book_history";
		
		UserMemberVO loginedUserMemberVO = (UserMemberVO) session.getAttribute("loginedUserMemberVO");
		
		List<RentalBookVO> rentalBookVOs =
				bookService.listupRentalBookHistory(loginedUserMemberVO.getU_m_no());
		
		model.addAttribute("rentalBookVOs", rentalBookVOs);
		
		return nextPage;
	}
	//희망 도서 요청 폼
	@GetMapping("/requestHopeBookForm")
	public String requestHopeBookForm() {
		System.out.println("[BookController] requestHopeBookForm()");
		
		String nextPage = "user/book/request_hope_book_form";
		
		return nextPage;
	}
	
	//희망 도서 요청 확인
	@GetMapping("/requestHopeBookConfirm")
	public String requestHopeBookConfirm(HopeBookVO hopeBookVO, HttpSession session) {
		System.out.println("[BookController] requestHopeBookConfirm()");
		
		String nextPage = "user/book/request_hope_book_ok";
		
		UserMemberVO loginedUserMemberVO =
				(UserMemberVO) session.getAttribute("loginedUserMemberVO");
		hopeBookVO.setU_m_no(loginedUserMemberVO.getU_m_no());
		
		int result = bookService.requestHopeBookConfirm(hopeBookVO);
		
		if(result <= 0) {
			nextPage = "user/book/request_hope_book_ng";
		}
		
		return nextPage;
	}
	//희망 도서 요청 목록 조회
	@GetMapping("/listupRequestHopeBook")
	public String listupRequestHopeBook(HttpSession session, Model model) {
		System.out.println("[BookController] listupRequestHopeBook()");
		
		String nextPage = "user/book/list_hope_book";
		
		UserMemberVO loginedUserMemberVO =
				(UserMemberVO) session.getAttribute("loginedUserMemberVO");
		
		List<HopeBookVO> hopeBookVOs =
				bookService.listupRequestHopeBook(loginedUserMemberVO.getU_m_no());
		
		model.addAttribute("hopeBookVOs", hopeBookVOs);
		
		return nextPage;
	}
	
}
