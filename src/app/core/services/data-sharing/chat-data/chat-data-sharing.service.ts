import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Chat } from '../../../models/chat'; 

@Injectable({
  providedIn: 'root'
})
export class DataSharingService {

  constructor() { }

  chat!: Chat;
  openContactChatSubject: BehaviorSubject<Chat> = new BehaviorSubject<Chat>(this.chat);
  openContactChatObservable: Observable<Chat> = this.openContactChatSubject.asObservable();  

  emitChatInfo(chat: Chat) {
    this.openContactChatSubject.next(chat);
  }
}
