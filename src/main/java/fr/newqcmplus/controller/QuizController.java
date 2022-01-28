package fr.newqcmplus.controller;

import fr.newqcmplus.entity.*;
import fr.newqcmplus.exception.QuizNotFoundException;
import fr.newqcmplus.exception.UserNotFoundException;
import fr.newqcmplus.security.CustomUserDetails;
import fr.newqcmplus.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.newqcmplus.service.QuizService;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/quiz")
public class QuizController {

	@Autowired
	private QuizService quizService;

	@Autowired
	private ResultService resultService;
	
	@GetMapping("")
	public String showAllQuizzes(Model model) {
		List<Quiz> listOfQuizzes = quizService.findAllQuizs();
		for (Quiz quiz : listOfQuizzes) {
			quiz.setAvailable(resultService.findResultsByUserAndQuiz(LoginController.getAuthenticatedUser(), quiz).isEmpty());
		}
		model.addAttribute("listOfQuizzes", listOfQuizzes);
		return "quizList";
	}
	
	@GetMapping("/create")
	public String showQuizCreateForm(@ModelAttribute Quiz quiz) {
		return "newQuizForm";
	}
	
	@PostMapping("/create")
	public String saveNewQuiz(@Valid @ModelAttribute Quiz quiz, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "newQuizForm";
		} else {
			quizService.saveQuiz(quiz);
			redirectAttributes.addFlashAttribute("message", "Le questionnaire a bien été créé.");
			return "redirect:/quiz";
		}
	}
	
	@GetMapping("/update")
	public String showQuizUpdateForm(@RequestParam int id, Model model) {
		try {
			model.addAttribute("quiz", quizService.findQuizById(id));
			return "updateQuizForm";
		} catch (QuizNotFoundException e) {
			throw new ResponseStatusException(NOT_FOUND);
		}
	}

	@PostMapping("/update")
	public String saveUpdatedQuiz(@ModelAttribute Quiz quiz, RedirectAttributes redirectAttributes) {
		quizService.saveQuiz(quiz);
		redirectAttributes.addFlashAttribute("message", "Le questionnaire a bien été modifié.");
		return "redirect:/quiz";
	}
	
	@PostMapping("/delete")
	public String deleteQuiz(@RequestParam int id, RedirectAttributes redirectAttributes) {
		quizService.deleteQuiz(id);
		redirectAttributes.addFlashAttribute("message", "Le questionnaire a bien été supprimé.");
		return "redirect:/quiz";
	}

	@GetMapping("/do")
	public String doQuiz(@RequestParam int quizId, @ModelAttribute Result result) {
		try {
			// 1. Check if user has already answered the quiz once and if the quiz contains questions.
			Quiz quiz = quizService.findQuizById(quizId);
			if (!resultService.findResultsByUserAndQuiz(LoginController.getAuthenticatedUser(), quiz).isEmpty() || quiz.getQuestions() == null || quiz.getQuestions().isEmpty()) {
				throw new ResponseStatusException(FORBIDDEN);
			}
			// 2. On cache les bonnes réponses du questionnaire.
			for (Question question : quiz.getQuestions()) {
				for (Item item : question.getItems()) {
					item.setResponse(false);
				}
			}
			result.setQuiz(quiz);
			// 3. On fixe la date et l'heure de début.
			result.setStart(new Date());
			return "doQuiz";
		} catch (QuizNotFoundException e) {
			throw new ResponseStatusException(NOT_FOUND);
		}
	}

	@PostMapping("/do")
	public String saveQuizAnswers(@RequestParam int quizId, @ModelAttribute Result result, RedirectAttributes redirectAttributes) {
		result.setUser(LoginController.getAuthenticatedUser());
		result.setEnd(new Date());
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
		redirectAttributes.addFlashAttribute("message", "Les réponses au questionnaire ont bien été enregistrées.");
		return "redirect:/quiz";
	}

	@GetMapping("result")
	public String showQuizResults(Model model) {
		List<Result> listOfResults = resultService.findResultsByUser(LoginController.getAuthenticatedUser());
		model.addAttribute("listOfResults", listOfResults);
		return "resultList";
	}

	private Item findItemInResult(Result result, int itemId) {
		for (Question question : result.getQuiz().getQuestions()) {
			for (Item item : question.getItems()) {
				if (item.getId() == itemId) return item;
			}
		}
		return null;
	}
	
}