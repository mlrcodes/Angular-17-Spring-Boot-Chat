package com.miguel.chatserver.VALIDATIONS;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
public @interface PasswordMatches {
  String message() default "Passwords and confirm password don't match";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
