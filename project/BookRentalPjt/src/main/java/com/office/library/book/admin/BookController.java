package com.office.library.book.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.office.library.book.BookVO;
import com.office.library.book.HopeBookVO;
import com.office.library.book.RentalBookVO;
import com.office.library.book.admin.util.UploadFileService;

@Controller
@RequestMapping("/book/admin")
public class BookController {

	@Autowired
	BookService bookService;

	@Autowired
	UploadFileService uploadFileService;

	// 도서 등록 페이지
	@GetMapping("/registerBookForm")
	public String registerBookForm() {
		System.out.println("[BookController] registerBookForm()");

		String nextPage = "admin/book/register_book_form";
		return nextPage;
	}

	// 도서 등록 확인
	@PostMapping("/registerBookConfirm")
	public String registerBookConfirm(BookVO bookVO, @RequestParam("file") MultipartFile file) {
		System.out.println("[BookController] registerBookConfirm()");

		String nextPage = "admin/book/register_book_ok";
		// 파일 저장
		String savedFileName = uploadFileService.upload(file);

		if (savedFileName != null) {
			bookVO.setB_thumbnail(savedFileName);
			int result = bookService.registerBookConfirm(bookVO);

			if (result <= 0) {
				nextPage = "admin/book/register_book_ng";
			}
		} else {
			nextPage = "admin/book/register_book_ng";
		}
		return nextPage;
	}

	// 도서 검색
	@GetMapping("/searchBookConfirm")
	public String searchBookConfirm(BookVO bookVO, Model model) {
		System.out.println("[BookController] searchBookConfirm()");

		String nextPage = "admin/book/search_book";

		List<BookVO> bookVOs = bookService.searchBookConfirm(bookVO);

		model.addAttribute("bookVOs", bookVOs);
		return nextPage;
	}

	// 도서 상세 조회
	@GetMapping("/bookDetail")
	public String bookDetail(@RequestParam("b_no") int b_no, Model model) {
		System.out.println("[BookController] bookDetail()");

		String nextPage = "admin/book/book_detail";

		BookVO bookVO = bookService.bookDetail(b_no);

		model.addAttribute("bookVO", bookVO);

		return nextPage;
	}

	// 도서 수정 창
	@GetMapping("/modifyBookForm")
	public String modifyBookForm(@RequestParam("b_no") int b_no, Model model) {
		System.out.println("[BookController] modifyBookForm()");

		String nextPage = "admin/book/modify_book_form";

		BookVO bookVO = bookService.modifyBookForm(b_no);

		model.addAttribute("bookVO", bookVO);
		return nextPage;
	}

	// 도서 수정 기능
	@PostMapping("/modifyBookConfirm")
	public String modifyBookConfirm(BookVO bookVO, @RequestParam("file") MultipartFile file) {
		System.out.println("[BookController] modifyBookConfirm()");

		String nextPage = "admin/book/modify_book_ok";

		if (!file.getOriginalFilename().equals("")) {
			String savedFileName = uploadFileService.upload(file);
			if (savedFileName != null) {
				bookVO.setB_thumbnail(savedFileName);

			}
		}
		int result = bookService.modifyBookConfirm(bookVO);
		if (result <= 0) {
			nextPage = "admin/book/modify_book_ng";
		}
		return nextPage;
	}

	// 도서 삭제 기능
	@GetMapping("/deleteBookConfirm")
	public String deleteBookConfirm(@RequestParam("b_no") int b_no) {
		System.out.println("[BookController] deleteBookConfirm()");

		String nextPage = "admin/book/delete_book_ok";

		int result = bookService.deleteBookConfirm(b_no);

		if (result <= 0) {
			nextPage = "admin/book/delete_book_ng";
		}
		return nextPage;
	}

	// 대출도서 목록 -> 도서 반납 처리
	@GetMapping("/getRentalBooks")
	public String getRentalBooks(Model model) {
		System.out.println("[BookController] getRentalBooks()");

		String nextPage = "admin/book/rental_books";

		List<RentalBookVO> rentalBookVOs = bookService.getRentalBooks();

		model.addAttribute("rentalBookVOs", rentalBookVOs);

		return nextPage;
	}

	// 도서 반납 처리
	@GetMapping("/returnBookConfirm")
	public String returnBookConfirm(@RequestParam int b_no, @RequestParam int rb_no) {
		System.out.println("[BookController] returnBookConfirm()");

		String nextPage = "admin/book/return_book_ok";

		int result = bookService.returnBookConfirm(b_no, rb_no);

		if (result <= 0) {
			nextPage = "admin/book/return_book_ng";
		}
		return nextPage;
	}

	// 희망 도서 요청 목록보기
	@GetMapping("/getHopeBooks")
	public String getHopeBooks(Model model) {
		System.out.println("[BookController] getHopeBooks()");

		String nextPage = "admin/book/hope_books";

		List<HopeBookVO> hopeBookVOs = bookService.getHopeBooks();

		model.addAttribute("hopeBookVOs", hopeBookVOs);

		return nextPage;
	}

	// 희망도서 입고처리 폼
	@GetMapping("/registerHopeBookForm")
	public String registerHopeBookForm(Model model, HopeBookVO hopeBookVO) {
		System.out.println("[BookController] registerHopeBookForm()");

		String nextPage = "admin/book/register_hope_book_form";

		model.addAttribute("hopeBookVO", hopeBookVO);

		return nextPage;
	}

	// 희망도서 입고처리
	@PostMapping("/registerHopeBookConfirm")
	public String registerHopeBookConfirm(BookVO bookVO, @RequestParam("hb_no") int hb_no,
			@RequestParam("file") MultipartFile file) {
		System.out.println("[BookController] registerHopeBookConfirm()");

		System.out.println("hb_no : " + hb_no);

		String nextPage = "admin/book/register_book_ok";

		// 파일 저장
		String savedFileName = uploadFileService.upload(file);

		if (savedFileName != null) {
			bookVO.setB_thumbnail(savedFileName);
			int result = bookService.registerHopeBookConfirm(bookVO, hb_no);
			if (result <= 0) {
				nextPage = "admin/book/register_book_ng";
			}
		} else {
			nextPage = "admin/book/register_book_ng";
		}
		return nextPage;
	}
	//전체 도서 목록 조회
	@GetMapping("/getAllBooks")
	public String getAllBooks(Model model) {
		System.out.println("[BookController] getAllBooks()");
		
		String nextPage = "admin/book/full_list_of_books";
		
		List<BookVO> bookVOs = bookService.getAllBooks();
		
		model.addAttribute("bookVOs", bookVOs);
		
		return nextPage;
	}
}
