import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { Chat } from '../../../models/chat'; 

@Injectable({
  providedIn: 'root'
})
export class ChatDataSharingService {

  constructor() { }

  private chats!: Chat[];
  private userChatsSource: BehaviorSubject<Chat[]> = new BehaviorSubject<Chat[]>(this.chats);
  private chatsRequestSource: Subject<void> = new Subject<void>();
  public userChats: Observable<Chat[]> = this.userChatsSource.asObservable();  
  public chatsRequest: Observable<void> = this.chatsRequestSource.asObservable();

  emitUserChats(userChats: Chat[]) {
    this.userChatsSource.next(userChats);
  }

  askForUserChats() {
    this.chatsRequestSource.next();
  }

  private chat!: Chat;
  private chatSource: BehaviorSubject<Chat> = new BehaviorSubject<Chat>(this.chat);
  private chatLoadedSource: BehaviorSubject<number> = new BehaviorSubject<number>(0);
  public currentChat: Observable<Chat> = this.chatSource.asObservable();
  public chatLoaded: Observable<number> = this.chatLoadedSource.asObservable();

  changeChat(chat: Chat) {
    this.chatSource.next(chat);
  }

  notifyChatComponentLoaded(chatId: number) {
    this.chatLoadedSource.next(chatId);
  }

  private crudObject = {chat: this.chat, action: ""}
  private chatCRUDSource: BehaviorSubject<{chat: Chat, action: string}> 
    = new BehaviorSubject<{chat: Chat, action: string}>(this.crudObject);
  public chatCRUD: Observable<{chat: Chat, action: string}> = this.chatCRUDSource.asObservable();

  emitChatCRUD(crudAction :{chat: Chat, action: string}) {
    this.chatCRUDSource.next(crudAction);
  }
}