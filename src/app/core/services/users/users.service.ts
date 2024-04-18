import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../../models/user';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  constructor(
    private httpClient: HttpClient
  ) { }

  
  private baseURL: string = 'http://localhost:8080/api/users'

  searchUser<T>(phoneNumber: string): Observable<User> {
    return this.httpClient.get<T>(this.baseURL, {
      params: {
        phone: phoneNumber
      }
    }) as Observable<User>;
  }
}
