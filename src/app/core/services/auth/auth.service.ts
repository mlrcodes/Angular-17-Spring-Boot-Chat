import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient) { }

  get<T>(url:string): Observable<T> {
    return this.httpClient.get<T>(url);
  }

  post<T>(url: string, body: any) {
    return this.httpClient.post<T>(url, body);
  }

  put<T>(url: string, body: any) {
    return this.httpClient.put(url, body);
  }

  delete<T>(url: string) {
    return this.httpClient.delete(url);
  }


}


