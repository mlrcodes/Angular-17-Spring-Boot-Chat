import { Injectable } from '@angular/core';
import { CompatClient, IMessage, Stomp } from '@stomp/stompjs';
import { Observable, Subject } from 'rxjs';
import SockJS from 'sockjs-client';
import { TokenService } from '../token/token.service';

@Injectable({
  providedIn: 'root',
})
export class WebsocketsService {
  private serverUrl = 'http://localhost:8080/ws';
  private stompClient!: CompatClient;
  private messageSubject: Subject<IMessage> = new Subject<IMessage>();
  currentMessage: Observable<IMessage> = this.messageSubject.asObservable();

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
          this.messageSubject.next(message);
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
