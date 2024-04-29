import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError } from 'rxjs';
import { of } from 'rxjs';
import { RegisterRequest } from '../../models/register-request';
import { LoginRequest } from '../../models/login-request';
import { RegisterResponse } from '../../models/register-response';
import { LoginResponse } from '../../models/login-response';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient) { }

  private baseUrl: string = 'http://localhost:8080/api/auth';

  register<T>(body: RegisterRequest): Observable<RegisterResponse> { 
    return this.httpClient
      .post<T>(this.baseUrl + '/register', body) as Observable<RegisterResponse>;
  }

  login<T>(body: LoginRequest): Observable<LoginResponse> {
    return this.httpClient.post<T>(this.baseUrl + '/login', body) as Observable<LoginResponse>;
  }
}


