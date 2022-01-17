package fr.newqcmplus.controller;

import fr.newqcmplus.entity.Password;
import fr.newqcmplus.validator.PasswordValidator;
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

	@InitBinder("user")
	protected void initUserBinder(WebDataBinder binder) {
		binder.setValidator(new UserValidator(userService));
	}

	@InitBinder("password")
	protected void initPasswordBinder(WebDataBinder binder) {
		binder.setValidator(new PasswordValidator());
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

	@GetMapping("/password/update")
	public String showUserPasswordUpdateForm(@ModelAttribute Password password) {
		return "update-password";
	}

	@PostMapping("/password/save")
	public String savePassword(@Validated @ModelAttribute Password password, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "update-password";
		} else {
			User user = LoginController.getAuthenticatedUser();
			user.setPassword(BCrypt.hashpw(password.getNewPassword(), BCrypt.gensalt()));
			userService.saveUser(user);
			return "redirect:/user";
		}
	}
	
	@PostMapping("/delete")
	public String deleteUser(@RequestParam int id) {
		userService.deleteUser(id);
		return "redirect:/user";
	}

}
