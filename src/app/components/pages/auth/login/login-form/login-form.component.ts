import { Component, EventEmitter, Output } from '@angular/core';
import { AbstractControl, FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ButtonModule } from 'primeng/button';
import { MessagesModule } from 'primeng/messages';
import { InputGroupModule } from 'primeng/inputgroup';
import { LoginRequest } from '../../../../../core/models/login-request';

@Component({
  selector: 'app-login-form',
  standalone: true,
  imports: [ReactiveFormsModule, InputTextModule, PasswordModule, ButtonModule, MessagesModule, InputGroupModule],
  templateUrl: './login-form.component.html',
  styleUrl: './login-form.component.scss'
})
export class LoginFormComponent {

  constructor(private formBuilder: FormBuilder) {}

  userLoginData: LoginRequest = {
    phoneNumber: '',
    password: ''
  }
  @Output() loginRequest = new EventEmitter<LoginRequest>();

  // PARA EL REGISTRO

  // loginForm = this.formBuilder.group({
  //   phoneNumber: ['', [
  //     Validators.required,
  //     Validators.pattern(/^\+\d{1,3}\d{4,14}$/), 
  //   ]],
  //   password: ['', [
  //     Validators.required, 
  //     Validators.minLength(8), 
  //     Validators.maxLength(16), 
  //     Validators.pattern(/[A-Z]/), 
  //     Validators.pattern(/[a-z]/), 
  //     Validators.pattern(/[0-9]/), 
  //     Validators.pattern( /[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]+/)
  //   ]]
  // })

  loginForm = this.formBuilder.group({
    phoneNumber: ['', [
      Validators.required,
      Validators.pattern(/^\+\d{1,3}\d{4,14}$/), 
    ]],
    password: ['', [
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

    if (this.loginForm.invalid) 

    this.loginRequest.emit({
      phoneNumber: phoneNumber || '',
      password: password || ''
    });

    console.log(JSON.stringify(this.loginForm.value, null, 2));
  }

  ngOnChanges() {
    this.loginForm.patchValue(this.userLoginData)
  }
}
