import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Chat } from '../../../models/chat'; 
import { Contact } from '../../../models/contac';

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


  contact!: Contact;
  openNoInfoChatSubject: BehaviorSubject<Contact> = new BehaviorSubject<Contact>(this.contact);
  openNoInfoChatObservable: Observable<Contact> = this.openNoInfoChatSubject.asObservable();  

  emitNoInfoChat(contact: Contact) {
    this.openNoInfoChatSubject.next(contact);   
  }
}
