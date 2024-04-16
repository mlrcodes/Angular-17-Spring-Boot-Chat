import { Component } from '@angular/core';
import { WebSocketsService } from '../../../core/services/websockets/web-sockets.service';
import { Message } from '../../../core/models/message';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.scss'
})
export class ChatComponent {

  constructor() {}

  private messages: Message[] = [];

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
