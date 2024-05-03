import { Component } from '@angular/core';
import { ActivatedRoute, RouterOutlet } from '@angular/router';
import { Message } from 'primeng/api';
import { TabMenuComponent } from './../../resources/tab-menu/tab-menu.component';
import { WebsocketsService } from '../../../core/services/websockets/web-sockets.service';
import { LogoutComponent } from '../../resources/logout/logout.component';
import { Chat } from '../../../core/models/chat';
import { ChatDataSharingService } from '../../../core/services/data-sharing/chat-data/chat-data-sharing.service';
import { MessagesModule } from 'primeng/messages';
import { ResultResponse } from '../../../core/models/resultResponse';
import { ContactsService } from '../../../core/services/contacts/contacts.service';
import { ChatsService } from '../../../core/services/chats/chats.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterOutlet, TabMenuComponent, LogoutComponent, MessagesModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  constructor(
    private webSocketsService: WebsocketsService,
    private chatsService: ChatsService,
    private chatDataSharingService: ChatDataSharingService,
    private route: ActivatedRoute
  ) {}

  userChats!: Chat[];
  messages!: Message[];

  getRouteData() {
    this.route.data.subscribe({
      next: (data: any) => {
        this.userChats = data.userChats
        this.chatDataSharingService.emitUserChats(this.userChats);
      }
    });
  }

  getUserChats() {
    this.chatsService
    .getUserChats()
    .subscribe({
      next: (chats: Chat[]) => {
        this.userChats = chats;
        this.chatDataSharingService.emitUserChats(this.userChats);
      }
    })
  }

  webSocketsConnect() {
    try {
      this.webSocketsService.initializeWebSocketConnection();
    } catch (error: unknown) {
      this.messages = [{ severity: 'error', summary: 'Error: ', detail: 'Connection failed' }];
    }
  }

  sendChatsWhenAsked() {
    this.chatDataSharingService
    .chatsRequest
    .subscribe({
      next: () => {
        this.chatDataSharingService.emitUserChats(this.userChats);
      }
    })
  }

  sendChatWhenAsked() {
    this.chatDataSharingService
    .chatLoaded
    .subscribe({
      next: (chatId: number) => {
        if (chatId !== 0) {
          let chat: Chat | undefined = this.userChats.find((chat: Chat) => chat.chatId === Number(chatId));
          if (chat) {
            this.chatDataSharingService.changeChat(chat)
          }
        }
      }
    })
  }

  updateChatsArray() {
    this.chatDataSharingService
    .chatCRUD
    .subscribe({
      next: (crudAction: {chat: Chat, action: string}) => {
        this.updateUserChatsArray(crudAction);
      }
    })
  }

  updateUserChatsArray(crudAction: {chat: Chat, action: string}) {
    let action = crudAction.action,
      crudChat = crudAction.chat;
    switch (action) {
      case "create":
        this.userChats.push(crudChat);
        break;
      case "update":
      case "delete":
        let foundChat: Chat | undefined = this.userChats.find((userChat: Chat) => userChat.chatId === crudChat.chatId);
        if (foundChat) {
          let foundChatIndex: number = this.userChats.indexOf(foundChat);
          switch (action) {
            case "delete":
              this.userChats.splice(foundChatIndex, 1);
              break;
            case "update":
              this.userChats.splice(foundChatIndex, 1, crudChat);
              break;
          }
        }
        break;
    }
    this.chatDataSharingService.emitUserChats(this.userChats);
  }

  ngOnInit() {
    this.getRouteData();
    this.webSocketsConnect();
    this.sendChatWhenAsked();
    this.sendChatsWhenAsked();
    this.updateChatsArray();
  }

}
