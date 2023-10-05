package com.office.library.book.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.office.library.book.BookVO;
import com.office.library.book.RentalBookVO;

@Component
public class BookDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	//도서 검색
	public List<BookVO> selectBooksBySearch(BookVO bookVO){
		System.out.println("[BookDao] selectBooksBySearch()");
		
		String sql = "SELECT * FROM tbl_book WHERE b_name LIKE ? ORDER BY b_no DESC";
		
		List<BookVO> bookVOs = null;
		
		try {
			bookVOs = jdbcTemplate.query(sql, new RowMapper<BookVO>() {
				@Override
				public BookVO mapRow(ResultSet rs, int rowNum) throws SQLException{
					BookVO bookVO = new BookVO();

					bookVO.setB_no(rs.getInt("b_no"));
					bookVO.setB_thumbnail(rs.getString("b_thumbnail"));
					bookVO.setB_name(rs.getString("b_name"));
					bookVO.setB_author(rs.getString("b_author"));
					bookVO.setB_publisher(rs.getString("b_publisher"));
					bookVO.setB_publish_year(rs.getString("b_publish_year"));
					bookVO.setB_isbn(rs.getString("b_isbn"));
					bookVO.setB_call_number(rs.getString("b_call_number"));
					bookVO.setB_rental_able(rs.getInt("b_rental_able"));
					bookVO.setB_reg_date(rs.getString("b_reg_date"));
					bookVO.setB_mod_date(rs.getString("b_mod_date"));

					return bookVO;
				}
			}, "%" + bookVO.getB_name() + "%");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return bookVOs.size() > 0 ? bookVOs : null;
	}
	// 도서 상세 조회
	public BookVO selectBook(int b_no) {
		System.out.println("[BookDao] selectBook()");

		String sql = "SELECT * FROM tbl_book WHERE b_no = ?";

		List<BookVO> bookVOs = null;

		try {
			bookVOs = jdbcTemplate.query(sql, new RowMapper<BookVO>() {
				@Override
				public BookVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					BookVO bookVO = new BookVO();

					bookVO.setB_no(rs.getInt("b_no"));
					bookVO.setB_thumbnail(rs.getString("b_thumbnail"));
					bookVO.setB_name(rs.getString("b_name"));
					bookVO.setB_author(rs.getString("b_author"));
					bookVO.setB_publisher(rs.getString("b_publisher"));
					bookVO.setB_publish_year(rs.getString("b_publish_year"));
					bookVO.setB_isbn(rs.getString("b_isbn"));
					bookVO.setB_call_number(rs.getString("b_call_number"));
					bookVO.setB_rental_able(rs.getInt("b_rental_able"));
					bookVO.setB_reg_date(rs.getString("b_reg_date"));
					bookVO.setB_mod_date(rs.getString("b_mod_date"));

					return bookVO;
				}
			}, b_no);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookVOs.size() > 0 ? bookVOs.get(0) : null;
	}
	//도서 대출 이력 남기기
	public int insertRentalBook(int b_no, int u_m_no) {
		System.out.println("[BookDao] insertRentalBook()");
		
		String sql = "INSERT INTO tbl_rental_book(b_no, u_m_no, rb_start_date, rb_reg_date, rb_mod_date) " + "VALUES(?, ?, NOW(), NOW(), NOW())";
		
		int result = -1;
		
		try {
			result = jdbcTemplate.update(sql, b_no, u_m_no);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//도서 대출 불가 처리 => 대출 가능여부 변경하기
	public void updateRentalBookAble(int b_no) {
		System.out.println("[BookDao] updateRentalBookAble()");
		
		String sql = "UPDATE tbl_book SET b_rental_able = 0 WHERE b_no = ?";
		
		try {
			jdbcTemplate.update(sql, b_no);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//도서 대출 목록 보기
	public List<RentalBookVO> selectRentalBooks(int u_m_no){
		System.out.println("[BookDao] selectRentalBooks()");
		
		String sql = "SELECT * FROM tbl_rental_book rb "
				+ "JOIN tbl_book b "
				+ "ON rb.b_no = b.b_no "
				+ "JOIN tbl_user_member um "
				+ "ON rb.u_m_no = um.u_m_no "
				+ "WHERE rb.u_m_no = ? AND rb.rb_end_date = '1000-01-01'";
		
		List<RentalBookVO> rentalBookVOs = new ArrayList<RentalBookVO>();
		
		try {
			rentalBookVOs = jdbcTemplate.query(sql, new RowMapper<RentalBookVO>() {
				@Override
				public RentalBookVO mapRow(ResultSet rs, int rowNum) throws SQLException{
					RentalBookVO rentalBookVO = new RentalBookVO();
					
					rentalBookVO.setRb_no(rs.getInt("rb_no"));
					rentalBookVO.setB_no(rs.getInt("b_no"));
					rentalBookVO.setU_m_no(rs.getInt("u_m_no"));
					rentalBookVO.setRb_start_date(rs.getString("rb_start_date"));
					rentalBookVO.setRb_end_date(rs.getString("rb_end_date"));
					rentalBookVO.setRb_reg_date(rs.getString("rb_reg_date"));
					rentalBookVO.setRb_mod_date(rs.getString("rb_mod_date"));
					
					rentalBookVO.setB_thumbnail(rs.getString("b_thumbnail"));
					rentalBookVO.setB_name(rs.getString("b_name"));
					rentalBookVO.setB_author(rs.getString("b_author"));
					rentalBookVO.setB_publisher(rs.getString("b_publisher"));
					rentalBookVO.setB_publish_year(rs.getString("b_publish_year"));
					rentalBookVO.setB_isbn(rs.getString("b_isbn"));
					rentalBookVO.setB_call_number(rs.getString("b_call_number"));
					rentalBookVO.setB_rental_able(rs.getInt("b_rental_able"));
					rentalBookVO.setB_reg_date(rs.getString("b_reg_date"));
					
					rentalBookVO.setU_m_id(rs.getString("u_m_id"));
					rentalBookVO.setU_m_pw(rs.getString("u_m_pw"));
					rentalBookVO.setU_m_name(rs.getString("u_m_name"));
					rentalBookVO.setU_m_gender(rs.getString("u_m_gender"));
					rentalBookVO.setU_m_mail(rs.getString("u_m_mail"));
					rentalBookVO.setU_m_phone(rs.getString("u_m_phone"));
					rentalBookVO.setU_m_reg_date(rs.getString("u_m_reg_date"));
					rentalBookVO.setU_m_mod_date(rs.getString("u_m_mod_date"));
					
					return rentalBookVO;
				}
			}, u_m_no);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rentalBookVOs;
	}
	//전체 대출 이력 조회
	public List<RentalBookVO> selectRentalBookHistory(int u_m_no){
		System.out.println("[BookDao] selectRentalBookHistory()");
		
		String sql = "SELECT * FROM tbl_rental_book rb "
				+ "JOIN tbl_book b "
				+ "ON rb.b_no = b.b_no "
				+ "JOIN tbl_user_member um "
				+ "ON rb.u_m_no = um.u_m_no "
				+ "WHERE rb.u_m_no = ? "
				+ "ORDER BY rb.rb_reg_date DESC";
		
		List<RentalBookVO> rentalBookVOs = new ArrayList<RentalBookVO>();
		
		try {
			rentalBookVOs = jdbcTemplate.query(sql, new RowMapper<RentalBookVO>() {
				@Override
				public RentalBookVO mapRow(ResultSet rs, int rowNum) throws SQLException{
					RentalBookVO rentalBookVO = new RentalBookVO();
					
					rentalBookVO.setRb_no(rs.getInt("rb_no"));
					rentalBookVO.setB_no(rs.getInt("b_no"));
					rentalBookVO.setU_m_no(rs.getInt("u_m_no"));
					rentalBookVO.setRb_start_date(rs.getString("rb_start_date"));
					rentalBookVO.setRb_end_date(rs.getString("rb_end_date"));
					rentalBookVO.setRb_reg_date(rs.getString("rb_reg_date"));
					rentalBookVO.setRb_mod_date(rs.getString("rb_mod_date"));
					
					rentalBookVO.setB_thumbnail(rs.getString("b_thumbnail"));
					rentalBookVO.setB_name(rs.getString("b_name"));
					rentalBookVO.setB_author(rs.getString("b_author"));
					rentalBookVO.setB_publisher(rs.getString("b_publisher"));
					rentalBookVO.setB_publish_year(rs.getString("b_publish_year"));
					rentalBookVO.setB_isbn(rs.getString("b_isbn"));
					rentalBookVO.setB_call_number(rs.getString("b_call_number"));
					rentalBookVO.setB_rental_able(rs.getInt("b_rental_able"));
					rentalBookVO.setB_reg_date(rs.getString("b_reg_date"));
					
					rentalBookVO.setU_m_id(rs.getString("u_m_id"));
					rentalBookVO.setU_m_pw(rs.getString("u_m_pw"));
					rentalBookVO.setU_m_name(rs.getString("u_m_name"));
					rentalBookVO.setU_m_gender(rs.getString("u_m_gender"));
					rentalBookVO.setU_m_mail(rs.getString("u_m_mail"));
					rentalBookVO.setU_m_phone(rs.getString("u_m_phone"));
					rentalBookVO.setU_m_reg_date(rs.getString("u_m_reg_date"));
					rentalBookVO.setU_m_mod_date(rs.getString("u_m_mod_date"));
					
					return rentalBookVO;
				}
			}, u_m_no);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rentalBookVOs;
		
	}
}

