import { Injectable } from '@angular/core';
import { AbstractControl, ValidatorFn } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ValidationService {

  constructor() { }

  validatePhoneNumber(): ValidatorFn {
    return (control: AbstractControl) => {
      const isValid = /^\+\d{1,3}\d{4,14}$/.test(control.value);
      return isValid ? {isValid : true} : null;
    }
  }

  validatePassword(): ValidatorFn {
    return (control: AbstractControl) => {
      const isValid = 
        control.value.length >= 8 && 
        control.value.length <= 16 && 
        /[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]+/.test(control.value);

      return isValid ? {isValid : true} : null;
    }
  }
}
