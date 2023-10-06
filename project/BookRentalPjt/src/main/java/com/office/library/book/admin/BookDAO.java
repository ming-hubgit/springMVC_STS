package com.office.library.book.admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.office.library.book.BookVO;
import com.office.library.book.HopeBookVO;
import com.office.library.book.RentalBookVO;

@Component
public class BookDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public boolean isISBN(String b_isbn) {
		System.out.println("[BookDAO] isISBN()");
		String sql = "SELECT COUNT(*) FROM tbl_book WHERE b_isbn = ?";

		int result = jdbcTemplate.queryForObject(sql, Integer.class, b_isbn);

		return result > 0 ? true : false;
	}

	// 도서 등록
	public int insertBook(BookVO bookVO) {
		System.out.println("[BookDAO] insertBook()");

		String sql = "INSERT INTO tbl_book(b_thumbnail, " + "b_name, " + "b_author, " + "b_publisher, "
				+ "b_publish_year, " + "b_isbn, " + "b_call_number, " + "b_rental_able, " + "b_reg_date, "
				+ "b_mod_date) " + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
		int result = -1;

		try {
			result = jdbcTemplate.update(sql, bookVO.getB_thumbnail(), bookVO.getB_name(), bookVO.getB_author(),
					bookVO.getB_publisher(), bookVO.getB_publish_year(), bookVO.getB_isbn(), bookVO.getB_call_number(),
					bookVO.getB_rental_able());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// 도서 검색
	public List<BookVO> selectBooksBySearch(BookVO bookVO) {
		System.out.println("[BookDAO] selectBookBySearch()");

		String sql = "SELECT * FROM tbl_book WHERE b_name LIKE ? ORDER BY b_no DESC";

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
			}, "%" + bookVO.getB_name() + "%");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookVOs.size() > 0 ? bookVOs : null;
	}

	// 도서 상세 조회
	public BookVO selectBook(int b_no) {
		System.out.println("[BookDAO] selectBook()");

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
	
	//도서 수정 기능
	public int updateBook(BookVO bookVO) {
		System.out.println("[BookDAO] updateBook()");
		
		List<String> args = new ArrayList<String>();
		
		String sql = "UPDATE tbl_book SET ";
		if(bookVO.getB_thumbnail() != null) {
			sql += "b_thumnail = ?, ";
			args.add(bookVO.getB_thumbnail());
		}
		sql += "b_name = ?, ";
		args.add(bookVO.getB_name());
		
		sql += "b_author = ?, ";
		args.add(bookVO.getB_author());
		
		sql += "b_publisher = ?, ";
		args.add(bookVO.getB_publisher());
		
		sql += "b_publish_year = ?, ";
		args.add(bookVO.getB_publish_year());
		
		sql += "b_isbn = ?, ";
		args.add(bookVO.getB_isbn());
		
		sql += "b_call_number = ?, ";
		args.add(bookVO.getB_call_number());
		
		sql += "b_rental_able = ?, ";
		args.add(Integer.toString(bookVO.getB_rental_able()));
		
		sql += "b_mod_date = NOW() ";
		
		sql += "WHERE b_no = ?";
		args.add(Integer.toString(bookVO.getB_no()));
		
		int result = -1;
		
		try {
			result = jdbcTemplate.update(sql, args.toArray());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;		
	}
	//도서 삭제 기능
	public int deleteBook(int b_no) {
		System.out.println("[BookDAO] deleteBook()");
		
		String sql = "DELETE FROM tbl_book WHERE b_no = ?";
		
		int result = -1;
		
		try {
			result = jdbcTemplate.update(sql, b_no);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//대출도서 목록 조회
	public List<RentalBookVO> selectRentalBooks(){
		System.out.println("[BookDAO] selectRentalBooks()");
		
		String sql = "SELECT * FROM tbl_rental_book rb "
				+ "JOIN tbl_book b "
				+ "ON rb.b_no = b.b_no "
				+ "JOIN tbl_user_member um "
				+ "ON rb.u_m_no = um.u_m_no "
				+ "WHERE rb.rb_end_date = '1000-01-01' "
				+ "ORDER BY um.u_m_id ASC, rb.rb_reg_date DESC";
		
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
			});
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rentalBookVOs;
	}
	//도서 반납 처리
	public int updateRentalBook(int rb_no) {
		System.out.println("[BookDAO] updateRentalBook()");
		
		String sql = "UPDATE tbl_rental_book "
				+ "SET rb_end_date = NOW() "
				+ "WHERE rb_no = ?";
		
		int result = -1;
		
		try {
			result = jdbcTemplate.update(sql, rb_no);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//대출 가능여부 수정
	public int updateBook(int b_no) {
		System.out.println("[BookDAO] updateBook()");
		
		String sql = "UPDATE tbl_book "
				+ "SET b_rental_able = 1 "
				+ "WHERE b_no = ?";
		
		int result = -1;
		
		try {
			result = jdbcTemplate.update(sql, b_no);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//희망 도서 요청 목록보기
	public List<HopeBookVO> selectHopeBooks(){
		System.out.println("[BookDAO] selectHopeBooks()");
		
		String sql = "SELECT * FROM tbl_hope_book hb "
					+ "JOIN tbl_user_member um "
					+ "ON hb.u_m_no = um.u_m_no "
					+ "ORDER BY hb.hb_no DESC";
		
		List<HopeBookVO> hopeBookVOs = new ArrayList<HopeBookVO>();
		
		try {
			hopeBookVOs = jdbcTemplate.query(sql, new RowMapper<HopeBookVO>() {
				@Override
				public HopeBookVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					HopeBookVO hopeBookVO = new HopeBookVO();
					
					hopeBookVO.setHb_no(rs.getInt("hb_no"));
					hopeBookVO.setHb_name(rs.getString("hb_name"));
					hopeBookVO.setHb_author(rs.getString("hb_author"));
					hopeBookVO.setHb_publisher(rs.getString("hb_publisher"));
					hopeBookVO.setHb_publish_year(rs.getString("hb_publish_year"));
					hopeBookVO.setHb_reg_date(rs.getString("hb_reg_date"));
					hopeBookVO.setHb_mod_date(rs.getString("hb_mod_date"));
					hopeBookVO.setHb_result(rs.getInt("hb_result"));
					hopeBookVO.setHb_result_last_date(rs.getString("hb_result_last_date"));
					
					hopeBookVO.setU_m_no(rs.getInt("u_m_no"));
					hopeBookVO.setU_m_id(rs.getString("u_m_id"));
					hopeBookVO.setU_m_pw(rs.getString("u_m_pw"));
					hopeBookVO.setU_m_name(rs.getString("u_m_name"));
					hopeBookVO.setU_m_gender(rs.getString("u_m_gender"));
					hopeBookVO.setU_m_mail(rs.getString("u_m_mail"));
					hopeBookVO.setU_m_phone(rs.getString("u_m_phone"));
					hopeBookVO.setU_m_reg_date(rs.getString("u_m_reg_date"));
					hopeBookVO.setU_m_mod_date(rs.getString("u_m_mod_date"));
					
					return hopeBookVO;
				}
			});
		}catch(Exception e) {
			e.printStackTrace();
		}
		return hopeBookVOs;
	}
	// 희망도서 입고처리
	public void updateHopeBookResult(int hb_no) {
		System.out.println("[BookDAO] updateHopeBookResult()");
		
		String sql = "UPDATE tbl_hope_book "
				+ "SET hb_result = 1, hb_result_last_date = NOW() "
				+ "WHERE hb_no = ?";
		
		try {
			jdbcTemplate.update(sql, hb_no);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	//전체 도서 목록 조회
	public List<BookVO> selectAllBooks(){
		System.out.println("[BookDAO] selectAllBooks()");
		
		String sql = "SELECT * FROM tbl_book "
				+ "ORDER BY b_reg_date DESC";
		
		List<BookVO> books = new ArrayList<BookVO>();
		
		try {
			books = jdbcTemplate.query(sql, new RowMapper<BookVO>() {
				@Override
				public BookVO mapRow(ResultSet rs, int rowNum) throws SQLException {
					BookVO bookVO = new BookVO();
					
					bookVO.setB_no(rs.getInt("b_no"));
					bookVO.setB_thumbnail(rs.getString("b_thumbnail"));
					bookVO.setB_name(rs.getString("b_name"));
					bookVO.setB_publisher(rs.getString("b_publisher"));
					bookVO.setB_publish_year(rs.getString("b_publish_year"));
					bookVO.setB_isbn(rs.getString("b_isbn"));
					bookVO.setB_call_number(rs.getString("b_call_number"));
					bookVO.setB_rental_able(rs.getInt("b_rental_able"));
					bookVO.setB_reg_date(rs.getString("b_reg_date"));
					bookVO.setB_mod_date(rs.getString("b_mod_date"));
					
					return bookVO;
				}
			});
		}catch(Exception e) {
			e.printStackTrace();
		}
		return books.size() > 0 ? books : null;
	}
}
