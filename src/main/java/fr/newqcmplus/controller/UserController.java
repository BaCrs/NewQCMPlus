package fr.newqcmplus.controller;

import fr.newqcmplus.dao.IAuthorityDAO;
import fr.newqcmplus.entity.Authority;
import fr.newqcmplus.entity.Password;
import fr.newqcmplus.entity.User;
import fr.newqcmplus.exception.UserNotFoundException;
import fr.newqcmplus.service.AuthorityService;
import fr.newqcmplus.service.UserService;
import fr.newqcmplus.validator.PasswordValidator;
import fr.newqcmplus.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthorityService authorityService;

	@InitBinder("user")
	protected void initUserBinder(WebDataBinder binder) {
		binder.addValidators(new UserValidator(userService));
	}

	@InitBinder("password")
	protected void initPasswordBinder(WebDataBinder binder) {
		binder.addValidators(new PasswordValidator(userService));
	}

	@GetMapping("")
	public String showAllUsers(Model model) {
		List<User> listOfUsers = userService.findAllUsers();
		int totalAdmin = 0;
		int totalIntern = 0;
		for (User user : listOfUsers) {
			if (user.hasAuthority("ADMIN")) totalAdmin++;
			if (user.hasAuthority("STAGIAIRE")) totalIntern++;
		}
		listOfUsers.sort(Comparator.comparingInt(User::getId));
		model.addAttribute("totalAdmin", totalAdmin);
		model.addAttribute("totalIntern", totalIntern);
		model.addAttribute("listOfUsers", listOfUsers);
		return "userList";
	}
	
	@GetMapping("/create")
	public String showUserCreateForm(@ModelAttribute User user) {
		return "newUserForm";
	}
	
	@PostMapping("/create")
	public String saveNewUser(@Valid @ModelAttribute User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "newUserForm";
		} else {
			// 1. Encrypt password using the BCrypt hash function.
			user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
			// 2. Set the newly created user as active.
			user.setEnabled(true);
			// 3. A new user has by default the STAGIAIRE authority.
			Set<Authority> authorities = new HashSet<>();
			authorities.add(authorityService.findAuthorityByName("STAGIAIRE"));
			user.setAuthorities(authorities);
			// 4. Save the new user in the database and redirect to the list of users.
			userService.saveUser(user);
			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("message.user.new", null, Locale.FRENCH));
			return "redirect:/user";
		}
	}
	
	@GetMapping("/update")
	public String showUserUpdateForm(@RequestParam int id, Model model) {
		try {
			model.addAttribute("user", userService.findUserById(id));
			return "updateUserForm";
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(NOT_FOUND);
		}
	}

	@PostMapping("/update")
	public String saveUpdatedUser(@Valid @ModelAttribute User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "updateUserForm";
		} else {
			try {
				// 1. Retrieve the original user information with the id in order to keep the password, authorities and status.
				User updatedUser = userService.findUserById(user.getId());
				// 2. Update attributes that can be updated with the form.
				updatedUser.setFirstname(user.getFirstname());
				updatedUser.setLastname(user.getLastname());
				updatedUser.setCompany(user.getCompany());
				updatedUser.setUsername(user.getUsername());
				// 3. Save the user in the database and redirect to the list of users.
				userService.saveUser(updatedUser);
				redirectAttributes.addFlashAttribute("message", messageSource.getMessage("message.user.update", null, Locale.FRENCH));
				return "redirect:/user";
			} catch (UserNotFoundException e) {
				throw new ResponseStatusException(NOT_FOUND);
			}
		}
	}

	@GetMapping("/password/update")
	public String showUserPasswordUpdateForm(@RequestParam int id, @ModelAttribute Password password, Model model) {
		try {
			model.addAttribute("id", id);
			return "updatePasswordForm";
		} catch (UserNotFoundException e) {
			throw new ResponseStatusException(NOT_FOUND);
		}
	}

	@PostMapping("/password/update")
	public String savePassword(@RequestParam int id, @Validated @ModelAttribute Password password, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("id", id);
			return "updatePasswordForm";
		} else {
			try {
				User user = userService.findUserById(id);
				user.setPassword(BCrypt.hashpw(password.getNewPassword(), BCrypt.gensalt()));
				userService.saveUser(user);
				redirectAttributes.addAttribute("id", id);
				redirectAttributes.addFlashAttribute("message", messageSource.getMessage("message.password.update", null, Locale.FRENCH));
				return "redirect:/user/update";
			} catch (UserNotFoundException e) {
				throw new ResponseStatusException(NOT_FOUND);
			}
		}
	}
	
	@PostMapping("/delete")
	public String deleteUser(@RequestParam int id, RedirectAttributes redirectAttributes) {
		userService.deleteUser(id);
		redirectAttributes.addFlashAttribute("message", messageSource.getMessage("message.user.delete", null, Locale.FRENCH));
		return "redirect:/user";
	}

}
