import { Component } from '@angular/core';
import { Message, MessageService } from 'primeng/api';
import { MessagesModule } from 'primeng/messages';
import { LoginFormComponent } from './login-form/login-form.component';
import { LoginRequest } from '../../../../core/models/login-request';
import { AuthService } from '../../../../core/services/auth/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [MessagesModule, LoginFormComponent],
  providers: [MessageService],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  constructor(
    private messageService: MessageService,
    private authService: AuthService
  ) {}

  sendLoginRequest(loginRequest: LoginRequest) {
    this.authService
    .post(loginRequest)
    .subscribe({
        next: (data: any) => {
          console.log(data)
        }
    })
  }

  notifyLoginResult() {
    this.messageService.add({
      severity: 'success',
      summary: 'Login Result',
      detail: 'Login Result Message',
      life: 3000
    })
  }
}
