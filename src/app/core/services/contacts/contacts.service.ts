import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Contact } from '../../models/contac';
import { ResultResponse } from '../../models/resultResponse';

@Injectable({
  providedIn: 'root'
})
export class ContactsService {


  constructor(
    private httpClient: HttpClient
  ) { }

  private baseURL: string = 'http://localhost:8080/api/contacts'

  getUserContacts<T>(): Observable<Contact[]> {
    return this.httpClient.get<T>(this.baseURL) as Observable<Contact[]>;
  }

  createNewContact<T>(body: Contact): Observable<Contact> {
    return this.httpClient.post<T>(this.baseURL, body) as Observable<Contact>;
  }

  
  deleteContact<T>(contactId: number | undefined): Observable<ResultResponse> {
    return this.httpClient.delete<T>(this.baseURL, { 
      params: { 
        contactId: contactId ? contactId : 0
      } 
    }) as Observable<ResultResponse>;
  }


}
