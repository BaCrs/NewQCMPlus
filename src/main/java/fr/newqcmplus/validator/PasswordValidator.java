package fr.newqcmplus.validator;

import fr.newqcmplus.controller.LoginController;
import fr.newqcmplus.entity.Password;
import fr.newqcmplus.entity.User;
import fr.newqcmplus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PasswordValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Password.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Password password = (Password) target;
		// 1. Check if the confirmation password is the same as the new password.
		if (!password.getNewPassword().equals(password.getNewPasswordConfirmation())) {
			errors.rejectValue("newPasswordConfirmation", "invalid.password.confirmation");
		}
		// 2. Check if the new password is different from the old password.
		if (password.getOldPassword().equals(password.getNewPassword())) {
			errors.rejectValue("newPassword", "invalid.password.new");
		}
		// 3. Check if the old password is correct.
		User user = LoginController.getAuthenticatedUser();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if (!passwordEncoder.matches(password.getOldPassword(), user.getPassword())) {
			errors.rejectValue("oldPassword", "invalid.password.old");
		}
	}

}
