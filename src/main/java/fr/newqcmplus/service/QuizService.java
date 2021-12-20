package fr.newqcmplus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.newqcmplus.dao.IQuizDAO;
import fr.newqcmplus.entity.Quiz;
import fr.newqcmplus.exception.QuizNotFoundException;

@Service
public class QuizService {

	@Autowired
	private IQuizDAO quizDAO;

	public Quiz saveQuiz(Quiz quiz) {
		return quizDAO.save(quiz);
	}

	public Quiz findQuizById(Integer id) {
		return quizDAO.findById(id).orElseThrow(() -> new QuizNotFoundException("Quiz by id " + id + "was not found"));
	}

	public List<Quiz> findAllQuizs() {
		return quizDAO.findAll();
	}

	public void deleteQuiz(Integer id) {
		quizDAO.deleteById(id);
	}

}