package com.kh.quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizServiceImpl implements QuizService {
	
	@Autowired
	private QuizDAO quizDAO;
	
	public QuizDO submitAnswer(QuizDO answer) {
		return quizDAO.submitAnswer(answer);
	}
}
