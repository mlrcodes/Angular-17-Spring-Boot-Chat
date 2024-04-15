import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Chat } from './../../../core/models/chat';


@Injectable({
  providedIn: 'root'
})
export class ChatsService {

  constructor(private httpClient: HttpClient) {}

  private baseURL: string = 'http://localhost:8080/api/chats/';

  getUserChats(): Observable<Chat[]> {
    return this.httpClient.get(this.baseURL) as Observable<Chat[]>;
  }
}
