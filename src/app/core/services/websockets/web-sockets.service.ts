import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { Injectable, OnInit } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { Message } from '../../models/message';


@Injectable({
  providedIn: 'root'
})
export class WebSocketsService {

  constructor() { }

  private webSocket!: WebSocket;
  private messagesSubject = new Subject<Message>();
  public messages$ = this.messagesSubject.asObservable();

  public connect(chatId: number): void {
    if (!this.webSocket || this.webSocket.readyState === WebSocket.CLOSED) {
      this.webSocket = new WebSocket(`ws://localhost:8080/ws/chat/${chatId}`);
      this.webSocket.onmessage = (event) => {
        const message = JSON.parse(event.data);
        this.messagesSubject.next(message);
      };
    }
  }

  public sendMessage(chatId: number, messageText: string): void {
    this.webSocket.send(JSON.stringify({chatId, messageText}));
  }

  public closeWebSocket(): void {
    if (this.webSocket) {
      this.webSocket.close();
    }
  }
}
