import { WebSocketSubject } from 'rxjs/webSocket';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { Message } from '../../models/message';


@Injectable({
  providedIn: 'root'
})
export class WebSocketsService {

  constructor() { }

  private socket: WebSocketSubject<any> = new WebSocketSubject('ws://localhost:8080/ws');


  private chatConnections: Map<number, WebSocketSubject<any>> = new Map();

connectToChat(chatId: number) {
  if (!this.chatConnections.has(chatId)) {
    this.chatConnections.set(chatId, new WebSocketSubject(`ws://localhost:8080/ws/chat/${chatId}`));
  }
}

sendMessage(chatId: number, message: string) {
  let chat = this.chatConnections.get(chatId);
  if (chat) {
    chat.next({ message });
  }
}

getMessages(chatId: number) {
  return this.chatConnections.get(chatId)?.asObservable();
}



  // stompClient: any;
  // messageSource: Subject<Message> = new Subject<Message>();
  // currentMessage: Observable<Message> = this.messageSource.asObservable();

  // connect() {
  //   const socket = new SockJS('http://localhost:8080/chat');
  //   this.stompClient = Stomp.over(socket);
  //   this.stompClient.connect({}, (frame: any) => {
  //     this.stompClient.subscribe('/topic/public', (message: Message) => {
  //       if (message.content) {
  //         this.messageSource.next(message);
  //       }
  //     });
  //   });
  // }

  // sendMessage(message: string) {
  //   this.stompClient.send("/app/chat.sendMessage", {}, JSON.stringify({content: message}));
  // }

}
