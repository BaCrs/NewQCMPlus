package fr.newqcmplus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.newqcmplus.entity.User;
import fr.newqcmplus.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
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
	public String saveUser(@ModelAttribute User user, BindingResult bindingResult) {
		// TODO : check if username is unique
		if (bindingResult.hasErrors()) {
			return "add-user";
		} else {
			user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
			user.setEnabled(true);
			System.out.println(user);
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
