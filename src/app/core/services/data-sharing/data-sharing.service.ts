import { Injectable } from '@angular/core';
import { RegisterResponse } from '../../models/register-response';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { Contact } from '../../models/contac';
import { Chat } from '../../models/chat';

@Injectable({
  providedIn: 'root'
})
export class DataSharingService {

  constructor() { }

  registrationSuccessSubject: BehaviorSubject<RegisterResponse | null> = new BehaviorSubject<RegisterResponse | null>(null);
  registrationSuccessObservable: Observable<RegisterResponse | null> = this.registrationSuccessSubject.asObservable();

  notifyRegistrationSuccess(registerResponse: RegisterResponse) {
    this.registrationSuccessSubject.next(registerResponse)
  } 

  openContactChatSubject: Subject<Chat> = new Subject<Chat>();
  openContactChatObservable: Observable<Chat> = this.openContactChatSubject.asObservable();  

  emitChatInfo(chat: Chat) {
    console.log(chat)
    this.openContactChatSubject.next(chat);
  }
}
