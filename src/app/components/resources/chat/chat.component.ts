import { Component, Input } from '@angular/core';
import { WebSocketsService } from '../../../core/services/websockets/web-sockets.service';
import { Message } from '../../../core/models/message';
import { Contact } from '../../../core/models/contac';
import { ChatsService } from '../../../core/services/chats/chats.service';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.scss'
})
export class ChatComponent {

  constructor(
    private chatService: ChatsService
  ) {}


  @Input() contact!: Contact;
  @Input() messages: Message[] = [];

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
