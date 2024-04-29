import { Injectable } from '@angular/core';
import { CompatClient, IMessage, Stomp } from '@stomp/stompjs';
import { Observable, Subject } from 'rxjs';
import SockJS from 'sockjs-client';
import { TokenService } from '../token/token.service';
import { Message } from '../../models/message';
import { MessagesDataSharingService } from '../data-sharing/messages-data/messages-data-sharing.service';
import { UsersService } from '../users/users.service';

@Injectable({
  providedIn: 'root',
})
export class WebsocketsService {
  private serverUrl = 'http://localhost:8080/ws';
  private stompClient!: CompatClient;

  constructor(
    private messagesDataSharingService: MessagesDataSharingService
  ) { }

  initializeWebSocketConnection() {
    try {
      const ws = new SockJS(this.serverUrl);
      this.stompClient = Stomp.over(ws);
      this.joinChat(); 
    } catch (error: unknown) {
      throw error;
    }
  }

  joinChat() {
    const userPhoneNumber = localStorage.getItem("userPhoneNumber");
    this.stompClient.connect({}, () => {
      this.stompClient.subscribe(
        `/user/${userPhoneNumber}/queue/messages`, 
        (message: IMessage): void => {
          const decoder = new TextDecoder('utf-8');
          const jsonBody = decoder.decode(new Uint8Array(message.binaryBody));
          const parsedMessage = JSON.parse(jsonBody);
          console.log(parsedMessage);
            console.log(parsedMessage)
          this.messagesDataSharingService.emitIncomingMessage(parsedMessage);
        }
      );
    }, this.errorCallBack);
  }

  sendMessage(message: {chatId: number, messageText: string}) {
    try {
      this.stompClient.send(
        '/app/chat', {}, JSON.stringify(message)
      );
    } catch (error: unknown) {
      throw error;
    }
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
