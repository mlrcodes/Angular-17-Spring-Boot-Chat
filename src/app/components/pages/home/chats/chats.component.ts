import { Component } from '@angular/core';
import { MessageService } from 'primeng/api';
import { MessagesModule } from 'primeng/messages';
import { Chat } from '../../../../core/models/chat';
import { ChatsService } from '../../../../core/services/chats/chats.service';

@Component({
  selector: 'app-chats',
  standalone: true,
  imports: [MessagesModule],
  templateUrl: './chats.component.html',
  styleUrl: './chats.component.scss'
})
export class ChatsComponent {

  constructor(
    private chatsService: ChatsService,
    private messageService: MessageService,
  ) {}

  private userChats: Chat[] = [];

  getUserChats() {
    this.chatsService
    .getUserChats()
    .subscribe({
      next: (chats: Chat[]) => {
        this.userChats = chats;
      },
      error: (error: Error) => {
        this.notifyChatsError(error);
      }
    })
  }

  notifyChatsError(error: Error) {
    this.messageService.add({ severity: 'danger', summary: error.message });
  }
}
