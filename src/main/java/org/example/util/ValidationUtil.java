package org.example.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public class ValidationUtil {
    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = validatorFactory.getValidator();

    public static <T> String validate(T entity) {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        if (violations.isEmpty()) {
            return null;
        }

        StringBuilder errors = new StringBuilder();
        for (ConstraintViolation<T> violation : violations) {
            errors.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("\n");
        }
        return errors.toString();
    }
}
