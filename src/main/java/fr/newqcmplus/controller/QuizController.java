package fr.newqcmplus.controller;

import fr.newqcmplus.security.CustomUserDetails;
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

import fr.newqcmplus.entity.Quiz;
import fr.newqcmplus.service.QuizService;

import java.time.Duration;
import java.time.Instant;

@Controller
@RequestMapping("/quiz")
public class QuizController {

	@Autowired
	private QuizService quizService;

	private Instant start;
	
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
	public String doQuiz(@RequestParam int id, Model model) {
		model.addAttribute("listOfQuizzes", quuestionService)
		//start = Instant.now();
		return "do-quiz";
	}

	@PostMapping("/do")
	public String saveQuizAnswers(@RequestParam int id, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		int userId = customUserDetails.getUserId();
		long timeElapsed = Duration.between(start, Instant.now()).toSeconds();
		System.out.println(timeElapsed);
		return "/quiz";
	}
	
}
