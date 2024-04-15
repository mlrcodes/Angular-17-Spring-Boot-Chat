package com.miguel.chatserver.VALIDATIONS;

import com.miguel.chatserver.DTO.AuthRegisterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

  public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public boolean isValid(Object request, ConstraintValidatorContext context) {
      AuthRegisterRequest authRegisterRequest = (AuthRegisterRequest) request;
      Boolean passwordsMatch = authRegisterRequest.getPassword().equals(authRegisterRequest.getConfirmPassword());
      if (!passwordsMatch) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("Passwords and confirm password don't match")
          .addPropertyNode("confirmPassword").addConstraintViolation();
      }
      return passwordsMatch;
    }
}
