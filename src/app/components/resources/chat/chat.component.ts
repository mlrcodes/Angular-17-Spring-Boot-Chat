import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
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
export class ChatComponent implements OnInit {

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
  contactChatSubscription!: Subscription;

  notifyMessageError(error: unknown) {
    this.alertMessages = [{ severity: 'error', summary: 'Error: ', detail: 'Message could not be sent', life: 3000 }];
  }

  subscribeToChatObservable(): void {
    console.log("SUBSCRIBING")
    this.contactChatSubscription = this.chatDataSharingService
    .openContactChatObservable
    .subscribe({
      next: (chat: Chat | null) => {
        if (chat) {
          console.log("RECEIVED CHAT =>>", chat)
          this.chat = chat;
          this.messages = chat.messages
          if (this.messages && !(this.messages.length > 0)) {
            this.alertMessages = [{ severity: 'info', detail: 'Void conversation' }];
          }
          this.subscribeToMessagesObservable();
        }
      }
    })
  }

  subscribeToMessagesObservable(): void {
    this.messagesDataSharingService
    .handleIncomingMessageObservable
    .subscribe({
      next: (message: Message) => {
        console.log(message)
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

  askForChat() {
    let chatId!: number; 
    this.route.params.subscribe({
      next: (params: Params) => {
        console.log("ASKNG FOR CHAT")
        chatId = params['chatId'];
        if (chatId) {
          this.chatDataSharingService.askForContactChat(chatId)
        }
      }
    })
  }
  
  ngOnInit(): void {
    this.userPhoneNumber = localStorage.getItem("userPhoneNumber") || "";
    this.subscribeToChatObservable();
    this.askForChat();
  }

  ngOnDestroy() {
    if (this.contactChatSubscription) {
      this.contactChatSubscription.unsubscribe();
    }
    // Repite para otras suscripciones si es necesario
  }
}
