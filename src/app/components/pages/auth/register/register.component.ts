import { Component } from '@angular/core';
import { RegisterFormComponent } from './register-form/register-form.component';
import { AuthService } from '../../../../core/services/auth/auth.service';
import { RegisterRequest } from '../../../../core/models/register-request';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [RegisterFormComponent],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  constructor(private authService: AuthService) {}

  sendRegisterRequest(registerRequest: RegisterRequest) {
    this.authService
    .post(registerRequest)
    .subscribe({
        next: (data: any) => {
          console.log(data)
        }
    })
  }
}
