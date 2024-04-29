import { Component, ViewChild } from '@angular/core';
import { ConfirmationService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { TokenService } from '../../../core/services/token/token.service';
import { Router } from '@angular/router';
import { WebsocketsService } from '../../../core/services/websockets/web-sockets.service';


@Component({
  selector: 'app-logout',
  standalone: true,
  imports: [ConfirmPopupModule, ButtonModule],
  providers: [ConfirmationService],
  templateUrl: './logout.component.html',
  styleUrl: './logout.component.scss'
})
export class LogoutComponent {

  constructor(
    private confirmationService: ConfirmationService,
    private tokenService: TokenService,
    private router: Router,
    private webSocketsService: WebsocketsService
  ) {}

  @ViewChild('logoutButton') deleteButton: any;

  confirmLogout(event: Event) {
    event.stopPropagation();
    this.confirmationService.confirm({
      target: this.deleteButton.nativeElement,
      message: 'Are you sure that you want to logout?',
      accept: () => {
        this.handleLogout()
      }
    });
  }

  handleLogout() {
    this.tokenService.logout();
    this.webSocketsService.disconnect()
    this.router.navigate(["/auth/login"]);
  }

}
