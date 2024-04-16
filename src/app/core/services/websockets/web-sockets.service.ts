import * as Stomp from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { Message } from '../../models/message';


@Injectable({
  providedIn: 'root'
})
export class WebSocketsService {

  constructor() { }

  stompClient: any;
  messageSource: Subject<Message> = new Subject<Message>();
  currentMessage: Observable<Message> = this.messageSource.asObservable();

  connect() {
    const socket = new SockJS('http://localhost:8080/chat');
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, (frame: any) => {
      this.stompClient.subscribe('/topic/public', (message: Message) => {
        if (message.content) {
          this.messageSource.next(message);
        }
      });
    });
  }

  sendMessage(message: string) {
    this.stompClient.send("/app/chat.sendMessage", {}, JSON.stringify({content: message}));
  }

}
