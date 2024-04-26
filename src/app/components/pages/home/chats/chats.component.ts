import { Component } from '@angular/core';
import { MessageService } from 'primeng/api';
import { MessagesModule } from 'primeng/messages';
import { Chat } from '../../../../core/models/chat';
import { ChatsService } from '../../../../core/services/chats/chats.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ContactsCardComponent } from '../../../resources/contacts-card/contacts-card.component';
import { RouterOutlet } from '@angular/router';
import { WebsocketsService } from '../../../../core/services/websockets/web-sockets.service';

@Component({
  selector: 'app-chats',
  standalone: true,
  imports: [RouterOutlet, MessagesModule, ContactsCardComponent],
  providers: [MessageService],
  templateUrl: './chats.component.html',
  styleUrl: './chats.component.scss'
})
export class ChatsComponent {

  constructor(
    private chatsService: ChatsService,
    private messageService: MessageService,
    private webSocketsService: WebsocketsService
  ) {}

  userChats!: Chat[];

  getUserChats() {
    this.chatsService
    .getUserChats()
    .subscribe({
      next: (chats: Chat[]) => {
        this.userChats = chats;
      },
      error: (error: HttpErrorResponse) => {
        this.notifyErrors(error);
      }
    })
  }

  webSocketsConnect() {
    this.webSocketsService.initializeWebSocketConnection();
    this.handleIncomingMassages();
  }

  handleIncomingMassages() {
    this.webSocketsService
    .messageObservable.subscribe({
      next: (message: any) => {
        console.log(message)
      }
    })
  }


  notifyErrors(error: HttpErrorResponse) {
    if (error.status === 0) {
      this.messageService.add({ severity: 'info', summary: 'Error: ', detail: "Unable to connect the server" });
    } else {
      this.messageService.add({ severity: 'info', summary: 'Info: ', detail: error.error.message });
    }  
  }

  ngOnInit() {
    this.getUserChats()
    this.webSocketsConnect()
  }
}
