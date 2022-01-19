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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.newqcmplus.service.QuizService;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		model.addAttribute("listOfQuizzes", quizService.findAllQuizs());
		return "quizList";
	}
	
	@GetMapping("/create")
	public String showQuizCreateForm(@ModelAttribute Quiz quiz) {
		return "newQuizForm";
	}
	
	@PostMapping("/create")
	public String saveNewQuiz(@ModelAttribute Quiz quiz, RedirectAttributes redirectAttributes) {
		quizService.saveQuiz(quiz);
		redirectAttributes.addFlashAttribute("message", "Le questionnaire a bien été créé.");
		return "redirect:/quiz";
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
		result.setUser(LoginController.getAuthenticatedUser());
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
		List<Result> listOfResults = resultService.findResultsByUserAndQuiz(LoginController.getAuthenticatedUser(), quiz);
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
	
}