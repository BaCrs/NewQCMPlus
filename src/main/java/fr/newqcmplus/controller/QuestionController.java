package fr.newqcmplus.controller;

import fr.newqcmplus.entity.Item;
import fr.newqcmplus.entity.Question;
import fr.newqcmplus.entity.Quiz;
import fr.newqcmplus.exception.QuestionNotFoundException;
import fr.newqcmplus.exception.QuizNotFoundException;
import fr.newqcmplus.service.QuestionService;
import fr.newqcmplus.service.QuizService;
import fr.newqcmplus.validator.QuestionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/question")
public class QuestionController {

	private static final int MAX_QUESTION_ITEMS = 5;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private  QuestionService questionService;

	@Autowired
	private QuizService quizService;

	@InitBinder()
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new QuestionValidator());
	}

	@GetMapping("")
	public String showAllQuestions(@RequestParam int quizId, Model model) {
		List<Question> listOfQuestions = quizService.findQuizById(quizId).getQuestions();
		listOfQuestions.sort(Comparator.comparingInt(Question::getId));
		model.addAttribute("quizId", quizId);
		model.addAttribute("listOfQuestions", listOfQuestions);
		return "questionList";
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
		return "newQuestionForm";
	}
	
	@PostMapping("/create")
	public String saveNewQuestion(@RequestParam int quizId, @Valid @ModelAttribute Question question, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("quizId", quizId);
	      	return "newQuestionForm";
	    } else {
			try {
				Quiz quiz = quizService.findQuizById(quizId);
				// Delete null or blank answers.
				question.getItems().removeIf(item -> item.getTitle() == null || item.getTitle().isBlank());
				// Add question to quiz or update it if it already exists.
				quiz.getQuestions().removeIf(q -> q.getId() == question.getId()); // Delete & add instead of update
				quiz.getQuestions().add(question);
				// Save the quiz to save the questions and redirect back to the list of questions.
				quizService.saveQuiz(quiz);
				redirectAttributes.addAttribute("quizId", quizId);
				redirectAttributes.addFlashAttribute("message", messageSource.getMessage("message.question.new", null, Locale.FRENCH));
				return "redirect:/question";
			} catch (QuizNotFoundException e) {
				throw new ResponseStatusException(NOT_FOUND);
			}
		}
	}
	
	@GetMapping("/update")
	public String showQuestionUpdateForm(@RequestParam int id, @RequestParam int quizId, Model model) {
		try {
			Question question = questionService.findQuestionById(id);
			for (int i = question.getItems().size(); i < MAX_QUESTION_ITEMS; i++) question.getItems().add(new Item());
			question.getItems().sort(Comparator.comparingInt(Item::getId));
			model.addAttribute("quizId", quizId);
			model.addAttribute("question", question);
			return "updateQuestionForm";
		} catch (QuestionNotFoundException e) {
			throw new ResponseStatusException(NOT_FOUND);
		}
	}
	
	@PostMapping("/update")
	public String saveUpdatedQuestion(@RequestParam int quizId, @Valid @ModelAttribute Question question, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("quizId", quizId);
	      	return "updateQuestionForm";
	    } else {
			try {
				Quiz quiz = quizService.findQuizById(quizId);
				// Delete null or blank answers.
				question.getItems().removeIf(item -> item.getTitle() == null || item.getTitle().isBlank());
				// Add question to quiz or update it if it already exists.
				quiz.getQuestions().removeIf(q -> q.getId() == question.getId()); // Delete & add instead of update
				quiz.getQuestions().add(question);
				// Save the quiz to save the questions and redirect back to the list of questions.
				quizService.saveQuiz(quiz);
				redirectAttributes.addAttribute("quizId", quizId);
				redirectAttributes.addFlashAttribute("message", messageSource.getMessage("message.question.new", null, Locale.FRENCH));
				return "redirect:/question";
			} catch (QuizNotFoundException e) {
				throw new ResponseStatusException(NOT_FOUND);
			}
		}
	}
	
	@PostMapping("/delete")
	public String deleteQuestion(@RequestParam int id, @RequestParam int quizId) {
		questionService.deleteQuestion(id);
		return "redirect:/question?quizId=" + quizId;
	}
	
}
