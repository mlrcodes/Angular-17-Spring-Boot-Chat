import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Message } from '../../../models/message';

@Injectable({
  providedIn: 'root'
})
export class MessagesDataSharingService {

  constructor() { }

  handleIncomingMessageSubject: Subject<Message> = new Subject<Message>();
  handleIncomingMessageObservable: Observable<Message> = this.handleIncomingMessageSubject.asObservable();  
  
  emitIncomingMessage(message: Message) {
    this.handleIncomingMessageSubject.next(message);
  }
}
