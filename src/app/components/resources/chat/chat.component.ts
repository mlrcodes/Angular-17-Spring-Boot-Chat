import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Message } from '../../../core/models/message';
import { Contact } from '../../../core/models/contac';
import { MessageComponent } from './message/message.component';
import { Chat } from '../../../core/models/chat';
import { ChatDataSharingService } from '../../../core/services/data-sharing/chat-data/chat-data-sharing.service';
import { SendMessageComponent } from './send-message/send-message.component';
import { MessagesDataSharingService } from '../../../core/services/data-sharing/messages-data/messages-data-sharing.service';
import { ActivatedRoute, Params } from '@angular/router';
import { MessagesModule } from 'primeng/messages';
import { Message as AlertMessage} from 'primeng/api';
import { Subscription } from 'rxjs';


@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [MessageComponent, SendMessageComponent,MessagesModule],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.scss'
})
export class ChatComponent implements OnInit, OnDestroy {

  constructor(
    private chatDataSharingService: ChatDataSharingService,
    private messagesDataSharingService: MessagesDataSharingService,
    private route: ActivatedRoute
  ) {}

  @ViewChild('messagesBox') messagesBox!: ElementRef;
  contact!: Contact;
  chat!: Chat;
  messages: Message[] = [];
  alertMessages!: AlertMessage[];
  userPhoneNumber!: string;
  chatSubscription!: Subscription;
  messageSubscription!: Subscription;
  incomingMessagesSubscription!: Subscription;

  notifyMessageError(error: unknown) {
    this.alertMessages = [{ severity: 'error', summary: 'Error: ', detail: 'Message could not be sent', life: 3000 }];
  }

  getMessages(): void {
    this.incomingMessagesSubscription = this.messagesDataSharingService
    .handleIncomingMessageObservable
    .subscribe({
      next: (message: Message) => {
        this.messages.push(message);
      }
    })
  }
 
  isSentByUser(message: Message): boolean {
    return this.userPhoneNumber === message.senderPhoneNumber;
  }

  ngAfterViewChecked() {
    this.messagesBox.nativeElement.scrollTop = this.messagesBox.nativeElement.scrollHeight;  
  }

  getChatId(): number {
    let chatId!: number; 
    this.route.params.subscribe({
      next: (params: Params) => {
        chatId = params['chatId'];
      }
    })
    return chatId;
  }

  getChat() {
    const chatId = this.getChatId();
    if (chatId) {
      this.chatDataSharingService.notifyChatComponentLoaded(chatId);
      this.chatSubscription = this.chatDataSharingService
      .currentChat
      .subscribe({
        next: (chat: Chat) => {
          if (chat && chat.chatId === Number(chatId)) {
            this.chat = chat;
            this.messages = chat.messages;
            this.getMessages();
          }        
        }
      })
    }
  }
 
  ngOnInit(): void {
    this.userPhoneNumber = localStorage.getItem("userPhoneNumber") || "";
    this.getChat();
  }

  ngOnDestroy(): void {
    this.chatDataSharingService.emitChatCRUD({chat: this.chat, action: "update"});
    if (this.messageSubscription) {
      this.messageSubscription.unsubscribe();
    }
    if (this.chatSubscription) {
      this.chatSubscription.unsubscribe();
    }
    if (this.incomingMessagesSubscription) {
      this.incomingMessagesSubscription.unsubscribe();
    }
  }

}
