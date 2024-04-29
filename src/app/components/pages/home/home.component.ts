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

  observeChildrenDataAsk() {
    this.chatDataSharingService
    .askForChatObservable
    .subscribe({
      next: (chatId: number) => {
        let chat: Chat | undefined = this.userChats.find((chat: Chat) => chat.chatId === Number(chatId));
        if (chat) {
          console.log("ASKED")
          this.chatDataSharingService.emitChatInfo(chat)
        }
      }
    })
  }

  observeChildChatsAsk() {
    this.chatDataSharingService
    .askForUserChatsObservable
    .subscribe({
      next: () => {
        this.getUserChats();
      }
    });
  }

  ngOnChanges() {
    console.log(this.userChats)
  }

  ngOnInit() {
    this.getRouteData();
    this.webSocketsConnect();
    this.observeChildrenDataAsk();
    this.observeChildChatsAsk();
  }

}
