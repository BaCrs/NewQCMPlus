package fr.newqcmplus.controller;

import fr.newqcmplus.validator.QuestionValidator;
import fr.newqcmplus.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import fr.newqcmplus.entity.User;
import fr.newqcmplus.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@InitBinder()
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new UserValidator(userService));
	}

	@GetMapping("")
	public String showAllUsers(Model model) {
		model.addAttribute("listOfUsers", userService.findAllUsers());
		return "user";
	}
	
	@GetMapping("/create")
	public String showUserCreateForm(@ModelAttribute User user) {
		return "add-user";
	}
	
	@PostMapping("/save")
	public String saveUser(@Validated @ModelAttribute User user, BindingResult bindingResult) {
		// TODO : check if username is unique
		if (bindingResult.hasErrors()) {
			return "add-user";
		} else {
			user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
			user.setEnabled(true);
			userService.saveUser(user);
			return "redirect:/user";
		}
	}
	
	@GetMapping("/update")
	public String showUserUpdateForm(@RequestParam int id, Model model) {
		model.addAttribute("user", userService.findUserById(id));
		return "add-user";
	}
	
	@PostMapping("/delete")
	public String deleteUser(@RequestParam int id) {
		userService.deleteUser(id);
		return "redirect:/user";
	}

}
