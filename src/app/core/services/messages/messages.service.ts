import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Message } from '../../models/message';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MessagesService {

  constructor(
    private httpClient: HttpClient
  ) { }

  private baseURL: string = 'http://localhost:/api/messages'

  getMessages<T>(chatId: number): Observable<Message[]> {
    return this.httpClient.get<T>(this.baseURL, {
      params: { chatId }
    }) as Observable<Message[]>;
  }
}
