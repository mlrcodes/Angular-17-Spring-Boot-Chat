import { Component, EventEmitter, OnChanges, Output } from '@angular/core';
import { AbstractControl, FormBuilder, ReactiveFormsModule, ValidatorFn, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { CheckboxModule } from 'primeng/checkbox';
import { RegisterRequest } from '../../../../../core/models/register-request';
import { passwordMatchValidator } from '../../../../../core/validators/passwordMatch.validator';

@Component({
  selector: 'app-register-form',
  standalone: true,
  imports: [ReactiveFormsModule, InputTextModule, ButtonModule, CheckboxModule],
  templateUrl: './register-form.component.html',
  styleUrl: './register-form.component.scss'
})
export class RegisterFormComponent implements OnChanges {
  constructor(private formBuilder: FormBuilder) {}

  userRegisterData: RegisterRequest = {
    firstname: '',
    surname: '',
    phoneNumber: '',
    password: '',
    email: '',
    confirmPassword: '',
    acceptedTerms: false
  }

  @Output() registerRequest: EventEmitter<RegisterRequest> = new EventEmitter<RegisterRequest>();

  registerForm = this.formBuilder.group(
    {
      firstname: ['', [
        Validators.required,
        Validators.pattern(/^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+(?: [A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)?$/)
      ]],
      surname: ['', [
        Validators.required,
        Validators.pattern(/^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+(?: [A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)?$/)
      ]],
      phoneNumber: ['', [
        Validators.required,
        Validators.pattern(/^\+\d{1,3}\d{4,14}$/), 
      ]],
      email: ['', [
        Validators.required,
        Validators.pattern(/^[\w\.-]+@[a-zA-Z\d\.-]+\.[a-zA-Z]{2,}$/)
      ]],
      password: ['', [
        Validators.required, 
        Validators.minLength(8), 
        Validators.maxLength(16), 
        Validators.pattern(/[A-Z]/), 
        Validators.pattern(/[a-z]/), 
        Validators.pattern(/[0-9]/), 
        Validators.pattern(/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()-_+=])[A-Za-z\d!@#$%^&*()-_+=]{8,}$/)
      ]],
      confirmPassword: ['', [Validators.required
      ]],
      acceptedTerms: [false, [
        Validators.required,
        Validators.requiredTrue
      ]]
    }, { validator: passwordMatchValidator }
  )
  submitted: boolean = false;

  get rf(): { [key: string]: AbstractControl } {
    return this.registerForm.controls;
  }

  onSubmit(event: Event) {
    event.preventDefault()

    const {firstname, surname, phoneNumber, email, password, confirmPassword, acceptedTerms} = this.registerForm.value;
    this.submitted = true;

    if (this.registerForm.invalid) return

    this.registerRequest.emit({
      firstname: firstname || '',
      surname: surname || '',
      phoneNumber: phoneNumber || '',
      email: email ||'',
      password: password || '',
      confirmPassword: confirmPassword || '',
      acceptedTerms: acceptedTerms || false
    });
  }

  ngOnChanges() {
    this.registerForm.patchValue(this.userRegisterData)
  }
}
