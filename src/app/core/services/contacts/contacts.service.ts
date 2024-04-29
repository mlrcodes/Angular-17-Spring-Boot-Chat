import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Contact } from '../../models/contac';
import { ResultResponse } from '../../models/resultResponse';
import { ContactCreateRequest } from '../../models/contactCreateRequest';
import { ContactUpdateRequest } from '../../models/contactUpdateRequest';
import { Chat } from '../../models/chat';

@Injectable({
  providedIn: 'root'
})
export class ContactsService {


  constructor(
    private httpClient: HttpClient
  ) { }

  private baseURL: string = 'http://localhost:8080/api/contacts'

  
  createNewContact<T>(body: ContactCreateRequest): Observable<Chat> {
    return this.httpClient.post<T>(this.baseURL, body) as Observable<Chat>;
  }

  editContact<T>(body: ContactUpdateRequest, contactId: number): Observable<Chat> {
    return this.httpClient.put<T>(this.baseURL, body,  {
      params: {
        contactId
      }
    }) as Observable<Chat>;
  }
  
  deleteContact<T>(contactId: number): Observable<ResultResponse> {
    return this.httpClient.delete<T>(this.baseURL, { 
      params: { 
        contactId
      } 
    }) as Observable<ResultResponse>;
  }


}
