package fr.newqcmplus.service;

import fr.newqcmplus.dao.IQuestionDAO;
import fr.newqcmplus.dao.IResultDAO;
import fr.newqcmplus.entity.Question;
import fr.newqcmplus.entity.Quiz;
import fr.newqcmplus.entity.Result;
import fr.newqcmplus.entity.User;
import fr.newqcmplus.exception.QuestionNotFoundException;
import fr.newqcmplus.exception.ResultNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {

	@Autowired
	private IResultDAO resultDAO;

	public Result save(Result result) {
		return resultDAO.save(result);
	}

	public Result findResultById(Integer id) {
		return resultDAO.findById(id).orElseThrow(() -> new ResultNotFoundException("Result by id " + id + "was not found"));
	}

	public List<Result> findResultsByUser(User user) {
		return resultDAO.getResultsByUser(user);
	}

	public List<Result> findAllResults() {
		return resultDAO.findAll();
	}

	public void deleteResult(Integer id) {
		resultDAO.deleteById(id);
	}

}