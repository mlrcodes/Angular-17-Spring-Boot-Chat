import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor() { }

  private baseURL: String = "http://localhost:8080/api/chat";
}
