import { Component, OnInit } from '@angular/core';
import { WebsocketsService } from '../../../core/services/websockets/web-sockets.service';
import { Message } from '../../../core/models/message';
import { Contact } from '../../../core/models/contac';
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
    private messagesService: MessagesService,
    private messageService: MessageService,
    private dataSharingService: DataSharingService,
    private webSocketsService: WebsocketsService
  ) {}

  contact!: Contact;
  chat!: Chat;
  messages!: Message[];
  userPhoneNumber!: string ;
  connected: boolean = false;

  getChatMessages() {
    // Recuperar mensajes sólo por contacto para recibir notificación de error!!!!
    this.messagesService
    .getMessages(this.chat.chatId)
    .subscribe({
      next: (messages: Message[]) => {
        this.messages = messages;
        console.log(this.messages)
      },
      error: (error: HttpErrorResponse) => {
        console.log(error)
        this.notifyErrors(error)
      }
    })
  }

  addMessage(message: Message) {
    this.messages.push(message);
  }

  notifyErrors(error: HttpErrorResponse) {
    if (error.status === 0) {
      this.messageService.add({ severity: 'info', summary: 'Error: ', detail: "Unable to connect the server" });
    } else {
      this.messageService.add({ severity: 'info', summary: 'Info: ', detail: error.error.message });
    }  
  }

  suscribeToContactChatObservable(): void {
    this.dataSharingService
    .openContactChatObservable
    .subscribe({
      next: (chat: Chat) => {
        this.chat = chat;
        this.getChatMessages();
        this.webSocketsConnect()
      }
    })
  }

  suscribeToNoInfoChatObservable(): void {
    this.dataSharingService
    .openNoInfoChatObservable
    .subscribe({
      next: (contact: Contact) => {
        this.contact = contact;
      }
    })
  }

  webSocketsConnect() {
    if (this.chat) {
      this.webSocketsService.initializeWebSocketConnection();
      this.connected = true;
      this.handleIncomingMassages();
    }
  }

  handleIncomingMassages() {
    this.webSocketsService
    .currentMessage.subscribe({
      next: (message: any) => {
        this.addMessage(message);
      }
    })
  }

  isSentByUser(senderPhoneNumber: string): boolean {
    return this.userPhoneNumber === senderPhoneNumber;
  }

  
  ngOnInit(): void {
    this.userPhoneNumber = localStorage.getItem("userPhoneNumber") || "";
    this.suscribeToContactChatObservable();
    this.suscribeToNoInfoChatObservable();
  }
}
