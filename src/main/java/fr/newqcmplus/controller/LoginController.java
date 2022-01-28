package fr.newqcmplus.controller;

import fr.newqcmplus.entity.User;
import fr.newqcmplus.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

	public static User getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		return customUserDetails.getUser();
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

}