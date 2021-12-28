package br.com.vendaspringboot.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyListValidator  implements ConstraintValidator<NotEmptyList, List>{

	@Override
	public boolean isValid(List value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		return value != null && !value.isEmpty();
	}

}
