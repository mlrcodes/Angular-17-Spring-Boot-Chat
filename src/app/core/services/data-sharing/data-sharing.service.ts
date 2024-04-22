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


  openContactChatSubject: Subject<Chat | Contact | null> = new BehaviorSubject<Chat | Contact | null>(null);
  openContactChatObservable: Observable<Chat | Contact | null> = this.openContactChatSubject.asObservable();  

  emitChatInfo(chatInfo: Chat | Contact) {
    this.openContactChatSubject.next(chatInfo);
  }
}
