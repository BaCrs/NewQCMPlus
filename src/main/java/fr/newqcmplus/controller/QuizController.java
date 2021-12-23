package fr.newqcmplus.controller;

import fr.newqcmplus.entity.*;
import fr.newqcmplus.security.CustomUserDetails;
import fr.newqcmplus.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.newqcmplus.service.QuizService;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/quiz")
public class QuizController {

	@Autowired
	private QuizService quizService;

	@Autowired
	private ResultService resultService;
	
	@GetMapping("")
	public String showAllQuizzes(Model model) {
		model.addAttribute("listOfQuizzes", quizService.findAllQuizs());
		return "quiz";
	}
	
	@GetMapping("/create")
	public String showQuizCreateForm(@ModelAttribute Quiz quiz) {
		return "add-quiz";
	}
	
	@PostMapping("/save")
	public String saveQuiz(@ModelAttribute Quiz quiz) {
		quizService.saveQuiz(quiz);
		return "redirect:/quiz";
	}
	
	@GetMapping("/update")
	public String showQuizUpdateForm(@RequestParam int id, Model model) {
		model.addAttribute("quiz", quizService.findQuizById(id));
		return "add-quiz";
	}
	
	@PostMapping("/delete")
	public String deleteQuiz(@RequestParam int id) {
		quizService.deleteQuiz(id);
		return "redirect:/quiz";
	}

	@GetMapping("/do")
	public String doQuiz(@RequestParam int quizId, @ModelAttribute Result result) {
		Quiz quiz = quizService.findQuizById(quizId);
		// On cache les bonnes réponses du questionnaire.
		for (Question question : quiz.getQuestions()) {
			for (Item item : question.getItems()) {
				item.setResponse(false);
			}
		}
		result.setQuiz(quiz);
		result.setDateDebut(new Date());
		return "do-quiz";
	}

	@PostMapping("/do")
	public String saveQuizAnswers(@RequestParam int quizId, @ModelAttribute Result result) {
		// TODO : indiquer dans la base si le stagiaire n'a pas du tout répondu à la question.
		result.setUser(this.getAuthenticatedUser());
		result.setDateFin(new Date());
		Quiz originalQuiz = quizService.findQuizById(quizId);
		List<Answer> answers = new ArrayList<>();
		for (Question question : originalQuiz.getQuestions()) {
			for (Item item : question.getItems()) {
				Answer answer = new Answer();
				answer.setItem(item);
				Item formItem = this.findItemInResult(result, item.getId());
				answer.setResponse(formItem.isResponse());
				answers.add(answer);
			}
		}
		result.setAnswers(answers);
		result.setQuiz(originalQuiz);
		resultService.save(result);
		return "close-quiz";
	}

	@GetMapping("results")
	public String showQuizResults(@RequestParam int quizId, Model model) {
		Quiz quiz = quizService.findQuizById(quizId);
		List<Result> listOfResults = resultService.findResultsByUserAndQuiz(this.getAuthenticatedUser(), quiz);
		model.addAttribute("listOfResults", listOfResults);
		return "show-quiz-results";
	}

	private Item findItemInResult(Result result, int itemId) {
		for (Question question : result.getQuiz().getQuestions()) {
			for (Item item : question.getItems()) {
				if (item.getId() == itemId) return item;
			}
		}
		return null;
	}

	private User getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		return customUserDetails.getUser();
	}
	
}
