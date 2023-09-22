package com.office.library.user.member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMemberDAO {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	//이미 존재하는 회원인지 확인하기 일치 : true/ 불일치 : false전달
	public boolean isUserMember(String u_m_id){
		System.out.println("[UserMemberDAO] isUserMember()");
		
		String sql = "SELECT COUNT(*) FROM tbl_user_member WHERE u_m_id = ?";
		
		int result = jdbcTemplate.queryForObject(sql, Integer.class, u_m_id);
		
		return result > 0 ? true : false;
	}
	
	//회원 가입
	public int insertUserAccount(UserMemberVO userMemberVO) {
		System.out.println("[UserMemberDAO] insertUserAccount()");
		
		String sql = "INSERT INTO tbl_user_member(u_m_id, "
						+ "u_m_pw, "
						+ "u_m_name, "
						+ "u_m_gender, "
						+ "u_m_mail, "
						+ "u_m_phone, "
						+ "u_m_reg_date, "
						+ "u_m_mod_date) VALUES(?, ?, ?, ?, ?, ?, NOW(), NOW())";
		
		int result = -1;
		
		try {
			result = jdbcTemplate.update(sql,
						userMemberVO.getU_m_id(),
						passwordEncoder.encode(userMemberVO.getU_m_pw()),
						userMemberVO.getU_m_name(),
						userMemberVO.getU_m_gender(),
						userMemberVO.getU_m_mail(),
						userMemberVO.getU_m_phone());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//로그인
	public UserMemberVO selectUser(UserMemberVO userMemberVO) {
		System.out.println("[UserMemberDAO] selectUser()");
		
		String sql = "SELECT * FROM tbl_user_member WHERE u_m_id = ?";
		
		List<UserMemberVO> userMemberVOs = new ArrayList<UserMemberVO>();
		
		try {
			userMemberVOs = jdbcTemplate.query(sql, new RowMapper<UserMemberVO>() {
				@Override
				public UserMemberVO mapRow(ResultSet rs, int rowNum) throws SQLException{
					UserMemberVO userMemberVO = new UserMemberVO();
					
					userMemberVO.setU_m_no(rs.getInt("u_m_no"));
					userMemberVO.setU_m_id(rs.getString("u_m_id"));
					userMemberVO.setU_m_pw(rs.getString("u_m_pw"));
					userMemberVO.setU_m_name(rs.getString("u_m_name"));
					userMemberVO.setU_m_gender(rs.getString("u_m_gender"));
					userMemberVO.setU_m_mail(rs.getString("u_m_mail"));
					userMemberVO.setU_m_phone(rs.getString("u_m_phone"));
					userMemberVO.setU_m_reg_date(rs.getString("u_m_reg_date"));
					userMemberVO.setU_m_mod_date(rs.getString("u_m_mod_date"));
					
					return userMemberVO;
				}
			}, userMemberVO.getU_m_id());
			
			if(!passwordEncoder.matches(userMemberVO.getU_m_pw(), userMemberVOs.get(0).getU_m_pw())) {
				userMemberVOs.clear();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return userMemberVOs.size() > 0 ? userMemberVOs.get(0) : null;
	}
	//계정 수정
	public int updateUserAccount(UserMemberVO userMemberVO) {
		System.out.println("[UserMemberDAO] updateUserAccount()");
		
		String sql = "UPDATE tbl_user_member SET "
						+ "u_m_name = ?, "
						+ "u_m_gender = ?, "
						+ "u_m_mail = ?, "
						+ "u_m_phone = ?, "
						+ "u_m_mod_date = NOW() "
						+ "WHERE u_m_no = ?";
		
		int result = -1;
		
		try {
			result = jdbcTemplate.update(sql,
					userMemberVO.getU_m_name(),
					userMemberVO.getU_m_gender(),
					userMemberVO.getU_m_mail(),
					userMemberVO.getU_m_phone(),
					userMemberVO.getU_m_no());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	//변경된 정보 조회하기
	public UserMemberVO selectUser(int u_m_no) {
		System.out.println("[UserMemberDAO] selectUser()");
		
		String sql = "SELECT * FROM tbl_user_member WHERE u_m_no = ?";
		
		List<UserMemberVO> userMemberVOs = new ArrayList<UserMemberVO>();
		
		try {
			userMemberVOs = jdbcTemplate.query(sql, new RowMapper<UserMemberVO>() {
				@Override
				public UserMemberVO mapRow(ResultSet rs, int rowNum) throws SQLException{
					UserMemberVO userMemberVO = new UserMemberVO();
					
					userMemberVO.setU_m_no(rs.getInt("u_m_no"));
					userMemberVO.setU_m_id(rs.getString("u_m_id"));
					userMemberVO.setU_m_pw(rs.getString("u_m_pw"));
					userMemberVO.setU_m_name(rs.getString("u_m_name"));
					userMemberVO.setU_m_gender(rs.getString("u_m_gender"));
					userMemberVO.setU_m_mail(rs.getString("u_m_mail"));
					userMemberVO.setU_m_phone(rs.getString("u_m_phone"));
					userMemberVO.setU_m_reg_date(rs.getString("u_m_reg_date"));
					userMemberVO.setU_m_mod_date(rs.getString("u_m_mod_date"));
					
					return userMemberVO;
				}
			}, u_m_no);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return userMemberVOs.size() > 0 ? userMemberVOs.get(0) : null;
	}
}
