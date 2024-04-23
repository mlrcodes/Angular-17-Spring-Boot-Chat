import { Component, Input, OnInit } from '@angular/core';
import { WebSocketsService } from '../../../core/services/websockets/web-sockets.service';
import { Message } from '../../../core/models/message';
import { Contact } from '../../../core/models/contac';
import { ChatsService } from '../../../core/services/chats/chats.service';
import { MessageComponent } from './message/message.component';
import { MessagesService } from '../../../core/services/messages/messages.service';
import { Chat } from '../../../core/models/chat';
import { MessageService } from 'primeng/api';
import { HttpErrorResponse } from '@angular/common/http';
import { DataSharingService } from '../../../core/services/data-sharing/chat-data/chat-data-sharing.service';
import { SendMessageComponent } from './send-message/send-message.component';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [MessageComponent, SendMessageComponent],
  providers: [MessageService],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.scss'
})
export class ChatComponent implements OnInit {

  constructor(
    private chatService: ChatsService,
    private messagesService: MessagesService,
    private messageService: MessageService,
    private dataSharingService: DataSharingService
  ) {}


  @Input() chat!: Chat;
  messages!: Message[];
  userPhoneNumber!: string ;

  getChatMessages() {
    this.messagesService
    .getMessages(this.chat.chatId)
    .subscribe({
      next: (messages: Message[]) => {
        this.messages = messages;
        console.log(this.messages)
      },
      error: (error: HttpErrorResponse) => {
        this.notifyErrors(error)
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

  ngOnInit(): void {
    this.userPhoneNumber = localStorage.getItem("userPhoneNumber") || "";
    this.dataSharingService
    .openContactChatObservable
    .subscribe({
      next: (chat: Chat) => {
        this.chat = chat;
        this.getChatMessages();
      }
    })

  }

  isSentByUser(senderPhoneNumber: string) {
    return this.userPhoneNumber === senderPhoneNumber;
  }

  // ngOnInit(): void {
  //   this.webSocketService.currentMessage.subscribe(msg => {
  //     if (msg) this.messages.push(msg);
  //   });
  //   this.webSocketService.connect();
  // }

  // sendMessage(message: string): void {
  //   this.webSocketService.sendMessage(message);
  // }

}
