package com.miguel.chatserver.VALIDATIONS;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConfirmPasswordMatchesPasswordValidator.class)
public @interface ConfirmPasswordMatchesPassword {

  String message() default "Confirm Password Does Not Match";

  String password();

  String confirmPassword();

  @Target({ ElementType.TYPE })
  @Retention(RetentionPolicy.RUNTIME)
  @interface List {
    ConfirmPasswordMatchesPassword[] value();
  }
}
