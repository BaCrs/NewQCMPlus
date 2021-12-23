package fr.newqcmplus.validator;

import fr.newqcmplus.entity.Item;
import fr.newqcmplus.entity.Question;
import fr.newqcmplus.entity.User;
import fr.newqcmplus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

	@Autowired
	private UserService userService;

	public UserValidator(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		if (userService.findUserByUsername(user.getUsername()) != null) {
			errors.rejectValue("username", "invalid.user.username");
		}
	}

}
