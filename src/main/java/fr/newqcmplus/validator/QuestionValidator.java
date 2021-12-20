package fr.newqcmplus.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import fr.newqcmplus.entity.Item;
import fr.newqcmplus.entity.Question;

@Component
public class QuestionValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Question.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Question question = (Question) target;
		int validItems = 0;
		boolean isOneItemCorrect = false;
		for (Item item : question.getItems()) {
			if (item.getTitle() != null) {
				if (!item.getTitle().isBlank()) {
					validItems++;
					isOneItemCorrect = item.isResponse() || isOneItemCorrect;
				}
			}
		}
		if (validItems < 2) errors.rejectValue("items", "count.question.items");
		if (!isOneItemCorrect) errors.rejectValue("items", "response.question.items");
	}

}
