package com.miguel.chatserver.VALIDATIONS;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class ConfirmPasswordMatchesPasswordValidator implements ConstraintValidator<ConfirmPasswordMatchesPassword, Object> {

  private String password;
  private String confirmPassword;

  public void initialize(ConfirmPasswordMatchesPassword constraintAnnotation) {
    this.password = constraintAnnotation.password();
    this.confirmPassword = constraintAnnotation.confirmPassword();
  }
  @Override
  public boolean isValid(
    Object value,
    ConstraintValidatorContext constraintValidatorContext
  ) {
      Object passwordValue = new BeanWrapperImpl(value)
        .getPropertyValue(password);
      Object confirmPasswordValue = new BeanWrapperImpl(value)
        .getPropertyValue(confirmPassword);

      if (passwordValue != null) {
        return passwordValue.equals(confirmPasswordValue);
      } else {
        return false;
      }


  }
}
