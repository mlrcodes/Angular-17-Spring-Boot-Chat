import { Injectable } from '@angular/core';
import { RegisterResponse } from '../../models/register-response';
import { BehaviorSubject, Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataSharingService {

  constructor() { }

  registrationSuccessSubject: BehaviorSubject<RegisterResponse | null> = new BehaviorSubject<RegisterResponse | null>(null);
  registrationSuccessObservable: Observable<RegisterResponse | null> = this.registrationSuccessSubject.asObservable();

  notifyRegistrationSuccess(registerResponse: RegisterResponse) {
    this.registrationSuccessSubject.next(registerResponse)
  } 
}
