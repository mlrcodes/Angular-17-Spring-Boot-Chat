import { Component, AfterViewInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { MessagesModule } from 'primeng/messages';
import { LoginFormComponent } from './login-form/login-form.component';
import { LoginRequest } from '../../../../core/models/login-request';
import { AuthService } from '../../../../core/services/auth/auth.service';
import { RegisterResponse } from '../../../../core/models/register-response';
import { Router } from '@angular/router';
import { TokenService } from '../../../../core/services/token/token.service';
import { LoginResponse } from '../../../../core/models/login-response';
import { HttpErrorResponse } from '@angular/common/http';
import { DataSharingService } from '../../../../core/services/data-sharing/registration-data/registration-data-sharing';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [MessagesModule, LoginFormComponent],
  providers: [MessageService],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements AfterViewInit {

  constructor(
    private messageService: MessageService,
    private authService: AuthService,
    private dataSharingService: DataSharingService,
    private router: Router,
    private tokenService: TokenService
  ) {}

  afterRegisterSuccess: boolean = false;

  sendLoginRequest(loginRequest: LoginRequest) {
    this.authService
    .login(loginRequest)
    .subscribe({
        next: (response: LoginResponse) => {
          this.handleLoginSuccess(response)
        },
        error: (error: HttpErrorResponse) => {
          this.notifyLoginError(error)        
        }
    })
  }

  notifyRegisterSuccess(registerResponse: RegisterResponse) {
    this.messageService.clear();
    this.messageService.add({ severity: 'success', summary: registerResponse.message });
  }

  notifyLoginError(error: HttpErrorResponse) {
    this.messageService.clear();
    if (error.status === 0) {
      this.messageService.add({ severity: 'error', summary: 'Error: ', detail: "Unable to connect the server" });
    } else {
      this.messageService.add({ severity: 'error', summary: 'Error: ', detail: error.error.message });
    }
  }

  handleLoginSuccess(response: LoginResponse) {
    localStorage.setItem('userPhoneNumber', response.userPhoneNumber);
    this.tokenService.setToken(response.token);
    this.router.navigate(['/home']);
  }

  ngAfterViewInit() {
    this.dataSharingService.registrationSuccessObservable.subscribe(registerResponse => {
      if (registerResponse && registerResponse.success) {
          this.notifyRegisterSuccess(registerResponse);
      }
    })
  }
}
