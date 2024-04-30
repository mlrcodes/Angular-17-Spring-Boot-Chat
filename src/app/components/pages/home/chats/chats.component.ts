import { Component, Input } from '@angular/core';
import { Message } from 'primeng/api';
import { MessagesModule } from 'primeng/messages';
import { Chat } from '../../../../core/models/chat';
import { ContactsCardComponent } from '../../../resources/contacts-card/contacts-card.component';
import { RouterOutlet } from '@angular/router';
import { ChatDataSharingService } from '../../../../core/services/data-sharing/chat-data/chat-data-sharing.service';

@Component({
  selector: 'app-chats',
  standalone: true,
  imports: [RouterOutlet, MessagesModule, ContactsCardComponent],
  templateUrl: './chats.component.html',
  styleUrl: './chats.component.scss'
})
export class ChatsComponent {

  constructor(
    private chatDataSharingService: ChatDataSharingService
  ) {}

  userChats: Chat[] = [];
  messages!: Message[];

  getUserChats() {
    this.chatDataSharingService.askForUserChats();
    this.chatDataSharingService
    .userChats
    .subscribe({
      next: (userChats: Chat[]) => {
        this.userChats = userChats      
        if (userChats && (!(userChats.length > 0) || !this.anyChatHaveMessages()))  {
          this.messages = [{ severity: 'info', detail: 'You do not have any active chat' }];
        }
      }
    })
  }

  anyChatHaveMessages(): boolean {
    if (this.userChats.find((chat: Chat) => chat.messages && chat.messages.length > 0)) return true
    else return false
  }

  ngOnInit() {
    this.getUserChats();
  }
}
