import { Injectable } from '@angular/core';
import { CompatClient, IMessage, Message, Stomp } from '@stomp/stompjs';
import { Observable, Subject } from 'rxjs';
import SockJS from 'sockjs-client';
import { TokenService } from '../token/token.service';

@Injectable({
  providedIn: 'root',
})
export class WebsocketsService {
  private serverUrl = 'http://localhost:8080/ws';
  private stompClient!: CompatClient;
  private messageSubject: Subject<Message> = new Subject<Message>();
  messageObservable: Observable<Message> = this.messageSubject.asObservable();

  constructor(
    private tokenService: TokenService
  ) { }

  initializeWebSocketConnection() {
    const ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    this.joinChat();
  }

  joinChat() {
    const userPhoneNumber = localStorage.getItem("userPhoneNumber");
    this.stompClient.connect({}, (): void => {
      this.stompClient.subscribe(`/user/${userPhoneNumber}/queue/messages`, (message: IMessage): void => {
        const binaryBody = Object.values(message.binaryBody),
          jsonBody = String.fromCharCode.apply(null, binaryBody),
          parsedMessage = JSON.parse(jsonBody);
        this.messageSubject.next(parsedMessage);
    });
    }, this.errorCallBack);
  }

  sendMessage(message: {chatId: number, messageText: string}) {
    this.stompClient.send(
      '/app/chat', {}, JSON.stringify(message)
    );
  }

  errorCallBack(error: any) {
    console.log(error)
  }

  disconnect() {
    if (this.stompClient !== null) {
      this.stompClient.disconnect();
    }
  }
}
