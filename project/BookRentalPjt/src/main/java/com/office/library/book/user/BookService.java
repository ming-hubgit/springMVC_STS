package com.office.library.book.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.office.library.book.BookVO;

@Service
public class BookService {
	
	@Autowired
	BookDao bookDao;
	//도서 검색
	public List<BookVO> searchBookConfirm(BookVO bookVO){
		System.out.println("[BookService] searchBookConfirm()");
		
		return bookDao.selectBooksBySearch(bookVO);
	}
	
	//도서 상세
	public BookVO bookDetail(int b_no) {
		System.out.println("[BookService] bookDetail()");
		
		return bookDao.selectBook(b_no);	
	}
	//도서 대출 기능
	public int rentalBookConfirm(int b_no, int u_m_no) {
		System.out.println("[BookService] rentalBookConfirm()");
		
		int result = bookDao.insertRentalBook(b_no, u_m_no);
		
		if(result >= 0) {
			bookDao.updateRentalBookAble(b_no);
		}
		
		return result;
	}
}
