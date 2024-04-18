import { Component  } from '@angular/core';
import { MessageService } from 'primeng/api';
import { MessagesModule } from 'primeng/messages';
import { RegisterFormComponent } from './register-form/register-form.component';
import { AuthService } from '../../../../core/services/auth/auth.service';
import { RegisterRequest } from '../../../../core/models/register-request';
import { Router } from '@angular/router';
import { RegisterResponse } from '../../../../core/models/register-response';
import { DataSharingService } from '../../../../core/services/data-sharing/data-sharing.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [RegisterFormComponent, MessagesModule],
  providers: [MessageService],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  constructor(
    private router: Router,
    private authService: AuthService,
    private dataSharingService: DataSharingService,
    private messageService: MessageService
  ) {}

  sendRegisterRequest(registerRequest: RegisterRequest) {
    this.authService
    .register(registerRequest)
    .subscribe({
        next: (registerResponse: RegisterResponse) => {
          this.router.navigate(["/auth/login"]);
          this.dataSharingService.notifyRegistrationSuccess(registerResponse);
        },
        error: (error: HttpErrorResponse) => {
          this.notifyRegistrationError(error)
        }
    })
  }

  notifyRegistrationError(error: HttpErrorResponse) {
    console.log(error.status)
    if (error.status === 0) {
      this.messageService.add({ severity: 'error', summary: 'Error: ', detail: "Unable to connect the server" });
    } else {
      this.messageService.add({ severity: 'error', summary: 'Error: ', detail: error.error.message });
    }
  }
}
