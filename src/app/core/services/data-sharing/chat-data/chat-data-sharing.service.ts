import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { Chat } from '../../../models/chat'; 

@Injectable({
  providedIn: 'root'
})
export class ChatDataSharingService {

  constructor() { }

  private userChats!: Chat[];
  private userChatsSubject: BehaviorSubject<Chat[]> = new BehaviorSubject<Chat[]>(this.userChats);
  public userChatsObservable: Observable<Chat[]> = this.userChatsSubject.asObservable();  

  emitUserChats(userChats: Chat[]) {
    this.userChatsSubject.next(userChats);
  }

  private openContactChatSubject: Subject<Chat> = new Subject<Chat>();
  public openContactChatObservable: Observable<Chat> = this.openContactChatSubject.asObservable();  

  emitChatInfo(chat: Chat) {
    console.log("EMITING CHAT =>>>", chat)
    this.openContactChatSubject.next(chat);
  }

  private askForChatSubject: Subject<number> = new Subject<number>();
  public askForChatObservable: Observable<number> = this.askForChatSubject.asObservable();

  askForContactChat(chatId: number) {
    this.askForChatSubject.next(chatId);
  }

  private askForUserChatsSubject: Subject<void> = new Subject<void>();
  public askForUserChatsObservable: Observable<void> = this.askForUserChatsSubject.asObservable();

  askForUserChats() {
    console.log("ASKING")
    this.askForUserChatsSubject.next();
  }
}
