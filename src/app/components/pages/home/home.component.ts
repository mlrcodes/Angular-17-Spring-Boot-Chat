import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { TabMenuComponent } from './../../resources/tab-menu/tab-menu.component';
import { AuthService } from '../../../core/services/auth/auth.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterOutlet, TabMenuComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  isUserAuthenticated() {
    this.authService
    .isUserAuthenticated()
    .subscribe({
      next: (isAuthenticated: boolean) => {

      },
      error: (error: HttpErrorResponse) => {

      }
    })
  }


  redirect(): void {
    this.router.navigate(['/auth/login'])
  }

}
