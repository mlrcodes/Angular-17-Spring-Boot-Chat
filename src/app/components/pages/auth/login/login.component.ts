import { Component } from '@angular/core';
import { Message, MessageService } from 'primeng/api';
import { MessagesModule } from 'primeng/messages';
import { LoginFormComponent } from './login-form/login-form.component';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [MessagesModule, LoginFormComponent],
  providers: [MessageService],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  constructor(private messageService: MessageService) {}

  notifyLoginResult(message: Message) {
    this.messageService.add({
      severity: 'success',
      summary: 'Login Result',
      detail: 'Login Result Message',
      life: 3000
    })
  }
}
