import { Component, EventEmitter, Input, Output } from '@angular/core';
import { AbstractControl, FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ButtonModule } from 'primeng/button';
import { LoginRequest } from '../../../../../core/models/login-request';

@Component({
  selector: 'app-login-form',
  standalone: true,
  imports: [ReactiveFormsModule, InputTextModule, PasswordModule, ButtonModule],
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.scss'
})
export class LoginFormComponent {

  constructor(private formBuilder: FormBuilder) {}

  @Input() userLoginData: LoginRequest = {
    phoneNumber: '',
    password: ''
  }
  @Output() loginRequest = new EventEmitter<LoginRequest>();

  loginForm = this.formBuilder.group({
    phoneNumber: [this.userLoginData.phoneNumber, [
      Validators.required,
      Validators.pattern(/^\+\d{1,3}\d{4,14}$/), 
    ]],
    password: [this.userLoginData.password, [
      Validators.required
    ]]
  })

  submitted: boolean = false;

  get lf(): { [key: string]: AbstractControl } {
    return this.loginForm.controls;
  }

  onSubmit(event: Event) {
    event.preventDefault()

    const {phoneNumber, password} = this.loginForm.value;
    this.submitted = true;

    this.loginRequest.emit({
      phoneNumber: phoneNumber || '',
      password: password || ''
    });

  }

  ngOnChanges() {
    this.loginForm.patchValue(this.userLoginData)
  }
}
