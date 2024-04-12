import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterRequest } from '../../models/register-request';
import { LoginRequest } from '../../models/login-request';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient) { }


  register<T>(body: RegisterRequest): Observable<T> {
    return this.httpClient.post<T>('http://localhost:8080/api/auth/register', body);
  }

  login<T>(body: LoginRequest): Observable<T>{
    return this.httpClient.post<T>('http://localhost:8080/api/auth/login', body);
  }
}


