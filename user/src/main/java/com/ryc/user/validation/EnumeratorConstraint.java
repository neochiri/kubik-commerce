package com.ryc.user.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class EnumeratorConstraint implements ConstraintValidator<EnumeratorValidator, String>{

	private EnumeratorValidator annotation;
	
	@Override
	public void initialize(EnumeratorValidator annotation) {
		this.annotation = annotation;
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean isValid = false;
		Field[] enumValues = this.annotation.enumClass().getFields();

		if (value == null) {
			isValid = false;
		} else {
			for (Field enumValue : enumValues) {
				String eValue = enumValue.getName();
				if (value.equalsIgnoreCase(eValue)) {
					isValid = true;
					break;
				}
			}
		}
		
		return isValid;
	}

}
