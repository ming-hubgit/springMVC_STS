package com.office.library.book.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.library.book.BookVO;

@Service
public class BookService {
	final static public int BOOK_ISBN_ALREADY_EXIST = 0;//이미 등록된 도서
	final static public int BOOK_REGISTER_SUCCESS = 1;//신규 도서 등록 성공
	final static public int BOOK_REGISTER_FAIL = -1;//신규 도서 등록 실패
	
	@Autowired
	BookDAO bookDAO;

	//도서 등록
	public int registerBookConfirm(BookVO bookVO) {
		System.out.println("[BookService] registerBookConfirm()");
		
		boolean isISBN = bookDAO.isISBN(bookVO.getB_isbn());
		
		if(!isISBN) {
			int result = bookDAO.insertBook(bookVO);
			
			if(result > 0) {
				return BOOK_REGISTER_SUCCESS;
			}else {
				return BOOK_REGISTER_FAIL;
			}
		}else {
			return BOOK_ISBN_ALREADY_EXIST;
		}
	}
}
