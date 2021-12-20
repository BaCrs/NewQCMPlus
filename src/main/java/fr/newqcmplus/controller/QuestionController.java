package fr.newqcmplus.controller;

import fr.newqcmplus.entity.Quiz;
import fr.newqcmplus.service.QuizService;
import fr.newqcmplus.validator.QuestionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import fr.newqcmplus.entity.Item;
import fr.newqcmplus.entity.Question;
import fr.newqcmplus.service.QuestionService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController {

	private static final int MAX_QUESTION_ITEMS = 5;

	@Autowired
	private  QuestionService questionService;
	@Autowired
	private QuizService quizService;

	@InitBinder()
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new QuestionValidator());
	}

	@GetMapping("/create")
	public String showQuestionCreateForm(@RequestParam int quizId, Model model, @ModelAttribute Question question) {
		model.addAttribute("quizId", quizId);
		List<Item> items = new ArrayList<>();
		for (int i = 0; i < MAX_QUESTION_ITEMS; i++) {
			Item item = new Item();
			if (i == 0) item.setResponse(true);
			items.add(item);
		}
		question.setItems(items);
		return "add-question";	
	}
	
	@PostMapping("/save")
	public String saveQuestion(@RequestParam int quizId, @Validated @ModelAttribute Question question, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("quizId", quizId);
	      	return "add-question";
	    }
		Quiz quiz = quizService.findQuizById(quizId);
		// On supprime les réponses vides.
		question.getItems().removeIf(item -> item.getTitle() == null || item.getTitle().isBlank());
		// On ajoute la question au quiz ou on la met à jour si elle existe déjà.
		quiz.getQuestions().removeIf(q -> q.getId() == question.getId());
		quiz.getQuestions().add(question);
		// On sauvegarde le quiz pour enregistrer en cascade.
		System.out.println(quiz);
		quizService.saveQuiz(quiz);
		return "redirect:/quiz/update?id=" + quizId;
	}
	
	@GetMapping("/update")
	public String showQuestionUpdateForm(@RequestParam int id, @RequestParam int quizId, Model model) {
		model.addAttribute("quizId", quizId);
		model.addAttribute("question", questionService.findQuestionById(id));
		return "add-question";
	}
	
	@PostMapping("/delete")
	public String deleteQuestion(@RequestParam int id, @RequestParam int quizId) {
		questionService.deleteQuestion(id);
		return "redirect:/quiz/update?id=" + quizId;
	}
	
}
