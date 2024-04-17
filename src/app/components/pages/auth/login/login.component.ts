import { Component, AfterViewInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { MessagesModule } from 'primeng/messages';
import { LoginFormComponent } from './login-form/login-form.component';
import { LoginRequest } from '../../../../core/models/login-request';
import { AuthService } from '../../../../core/services/auth/auth.service';
import { DataSharingService } from '../../../../core/services/data-sharing/data-sharing.service';
import { RegisterResponse } from '../../../../core/models/register-response';


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
    private dataSharingService: DataSharingService
  ) {}

  afterRegisterSuccess: boolean = false;

  sendLoginRequest(loginRequest: LoginRequest) {
    this.authService
    .login(loginRequest)
    .subscribe({
        next: (res: any) => {
          console.log(res.headers)
        }
    })
  }

  notifyRegisterSuccess(registerResponse: RegisterResponse) {
    this.messageService.add({ severity: 'success', summary: registerResponse.message });
  }

  ngAfterViewInit() {
    this.dataSharingService.registrationSuccessObservable.subscribe(registerResponse => {
      if (registerResponse && registerResponse.success) {
          this.notifyRegisterSuccess(registerResponse);
      }
    })
  }
}
