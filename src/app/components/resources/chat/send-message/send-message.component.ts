import { Component, Input, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { InputGroupModule } from 'primeng/inputgroup';
import { ButtonModule } from 'primeng/button';
import { ChatsService } from '../../../../core/services/chats/chats.service';
import { MessageService } from 'primeng/api';
import { MessagesService } from '../../../../core/services/messages/messages.service';
import { Message } from '../../../../core/models/message';
import { WebSocketsService } from '../../../../core/services/websockets/web-sockets.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-send-message',
  standalone: true,
  imports: [FormsModule, InputTextModule, InputGroupModule, ButtonModule],
  templateUrl: './send-message.component.html',
  styleUrl: './send-message.component.scss'
})
export class SendMessageComponent implements OnInit {

  constructor(
    private messagesService: MessagesService,
    private webSocketService: WebSocketsService
  ) {}

  @Input() chatId!: number;
  messages!: Message[];
  messageText!: string;
  private wsSubscription!: Subscription;

  sendMessage(): void {
    this.webSocketService.sendMessage(this.chatId, this.messageText);
    this.messageText = '';
  }

  getMessages() {
    this.messagesService
    .getMessages(this.chatId)
    .subscribe({
      next: (messages: Message[]) => {
        this.messages = messages
      }
    });
  }

  ngOnInit(): void {
    this.getMessages();
    this.webSocketService.connect(this.chatId);
    this.wsSubscription = this.webSocketService.messages$.subscribe((message: Message) => {
      this.messages.push(message);
    });
  }

  handleKeyPress(event: KeyboardEvent): void {
    if (event.key === 'Enter' && !event.shiftKey) {
      this.sendMessage();
    }
  }

  ngOnDestroy(): void {
    this.webSocketService.closeWebSocket();
    this.wsSubscription.unsubscribe();
  }
}
