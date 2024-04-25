import { Component } from '@angular/core';
import { MessageService } from 'primeng/api';
import { MessagesModule } from 'primeng/messages';
import { Chat } from '../../../../core/models/chat';
import { ChatsService } from '../../../../core/services/chats/chats.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ContactsCardComponent } from '../../../resources/contacts-card/contacts-card.component';
import { RouterOutlet } from '@angular/router';

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
  ) {}

  userChats!: Chat[];

  getUserChats() {
    this.chatsService
    .getUserChats()
    .subscribe({
      next: (chats: Chat[]) => {
        this.userChats = chats;
        console.log(this.userChats)
      },
      error: (error: HttpErrorResponse) => {
        console.log(error)
        this.notifyErrors(error);
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
  }
}
