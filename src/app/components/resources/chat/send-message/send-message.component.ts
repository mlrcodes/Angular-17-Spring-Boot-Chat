import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { InputGroupModule } from 'primeng/inputgroup';
import { ButtonModule } from 'primeng/button';
import { WebsocketsService } from '../../../../core/services/websockets/web-sockets.service';
import { Chat } from '../../../../core/models/chat';
import { Contact } from '../../../../core/models/contac';

@Component({
  selector: 'app-send-message',
  standalone: true,
  imports: [FormsModule, InputTextModule, InputGroupModule, ButtonModule],
  templateUrl: './send-message.component.html',
  styleUrl: './send-message.component.scss'
})
export class SendMessageComponent {

  constructor(
    private webSocketService: WebsocketsService
  ) {}

  @Output() errorEmitter: EventEmitter<unknown> = new EventEmitter<unknown>();
  @Input() contact!: Contact;
  @Input() chat!: Chat;
  messageText: string = "";

  sendMessage(): void {
    if (this.messageText) {
      try {
        this.webSocketService.sendMessage({
          chatId: this.chat.chatId,
          messageText: this.messageText
        });
        this.messageText = "";
      } catch (error: unknown) {
        this.errorEmitter.emit(error);
      }
    }
  }

  handleKeyPress(event: KeyboardEvent): void {
    if (event.key === 'Enter' && !event.shiftKey) {
      this.sendMessage();
    }
  }
}
