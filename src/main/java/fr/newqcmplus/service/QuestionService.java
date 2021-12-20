package fr.newqcmplus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.newqcmplus.dao.IQuestionDAO;
import fr.newqcmplus.entity.Question;
import fr.newqcmplus.exception.QuestionNotFoundException;

@Service
public class QuestionService {

	@Autowired
	private IQuestionDAO questionDAO;

	public Question saveQuestion(Question question) {
		return questionDAO.save(question);
	}

	public Question findQuestionById(Integer id) {
		return questionDAO.findById(id).orElseThrow(() -> new QuestionNotFoundException("Question by id " + id + "was not found"));
	}

	public List<Question> findAllQuestions() {
		return questionDAO.findAll();
	}

	public void deleteQuestion(Integer id) {
		questionDAO.deleteById(id);
	}

}