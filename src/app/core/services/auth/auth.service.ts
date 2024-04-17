import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterRequest } from '../../models/register-request';
import { LoginRequest } from '../../models/login-request';
import { RegisterResponse } from '../../models/register-response';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient) { }


  register<T>(body: RegisterRequest): Observable<RegisterResponse> { 
    return this.httpClient.post<T>('http://localhost:8080/api/auth/register', body) as Observable<RegisterResponse>;
  }

  login<T>(body: LoginRequest): Observable<any>{
    return this.httpClient.post<T>('http://localhost:8080/api/auth/login', body, { observe: 'response' });
  }

  test<T>(): Observable<any>{
    return this.httpClient.get<T>('http://localhost:8080/api/messages/getMessage',  { withCredentials: true });
  }
}


