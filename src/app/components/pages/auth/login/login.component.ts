import { Component, AfterViewInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { MessagesModule } from 'primeng/messages';
import { LoginFormComponent } from './login-form/login-form.component';
import { LoginRequest } from '../../../../core/models/login-request';
import { AuthService } from '../../../../core/services/auth/auth.service';
import { DataSharingService } from '../../../../core/services/data-sharing/data-sharing.service';
import { RegisterResponse } from '../../../../core/models/register-response';
import { Router } from '@angular/router';
import { TokenService } from '../../../../core/services/token/token.service';
import { LoginResponse } from '../../../../core/models/login-response';


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
          console.log(response)
          this.handleLoginSuccess(response)
        },
        error: (error: Error) => {
          this.notifyLoginError(error)        
        }
    })
  }

  notifyRegisterSuccess(registerResponse: RegisterResponse) {
    this.messageService.add({ severity: 'success', summary: registerResponse.message });
  }

  notifyLoginError(error: Error) {
    console.log(error)
    this.messageService.add({ severity: 'warm', summary: error.message });
  }

  handleLoginSuccess(response: LoginResponse) {
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
