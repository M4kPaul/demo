package dev.m4k.demo.validator;

import javax.validation.ConstraintValidator;

import dev.m4k.demo.dto.RegisterRequest;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegisterRequest> {

  @Override
  public boolean isValid(final RegisterRequest user, final ConstraintValidatorContext context) {
    return user.getPassword().equals(user.getMatchingPassword());
  }
}
