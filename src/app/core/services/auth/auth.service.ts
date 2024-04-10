import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient) { }

  url: string = 'http://localhost/practicas/FETCH+PHP%20Chat/ASSETS/PHP/CONTROLLERS/front-controller.php?authenticate-user';

  get<T>(url:string): Observable<T> {
    return this.httpClient.get<T>(url);
  }

  post<T>(body: any): Observable<T>{
    return this.httpClient.post<T>(this.url, body);
  }

  put<T>(url: string, body: any) {
    return this.httpClient.put(url, body);
  }

  delete<T>(url: string) {
    return this.httpClient.delete(url);
  }


}


