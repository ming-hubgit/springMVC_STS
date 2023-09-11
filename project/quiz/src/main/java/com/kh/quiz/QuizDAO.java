package com.kh.quiz;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QuizDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	public QuizDO submitAnswer(QuizDO answer) {
		return sqlSession.selectOne("quizMapper.submitAnswer", answer);
	}
}
