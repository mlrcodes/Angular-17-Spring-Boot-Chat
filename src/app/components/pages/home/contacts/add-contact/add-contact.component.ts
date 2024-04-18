import { Component, EventEmitter, Output } from '@angular/core';
import { AbstractControl, FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputGroupModule } from 'primeng/inputgroup';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
import { MenubarModule } from 'primeng/menubar';
import { UsersService } from '../../../../../core/services/users/users.service';
import { HttpErrorResponse } from '@angular/common/http';
import { User } from '../../../../../core/models/user';
import { InputTextModule } from 'primeng/inputtext';
import { Contact } from '../../../../../core/models/contac';
import { first } from 'rxjs';

@Component({
  selector: 'app-add-contact',
  standalone: true,
  imports: [ ReactiveFormsModule, FormsModule, InputTextModule, InputGroupAddonModule, InputGroupModule, ButtonModule, MenubarModule, DialogModule],
  templateUrl: './add-contact.component.html',
  styleUrl: './add-contact.component.scss'
})
export class AddContactComponent {
  constructor(
    private formBuilder: FormBuilder,
    private usersService: UsersService
  ) {}

  @Output() errorEmitter: EventEmitter<HttpErrorResponse> = new EventEmitter<HttpErrorResponse>();
  @Output() contactEmitter: EventEmitter<Contact> = new EventEmitter<Contact>();
  visible: boolean = false;
  phoneNumber: string = "";
  newContactData!: Contact;

  addContactForm = this.formBuilder.group(
    {
      contactName: ['', [
        Validators.required,
        Validators.pattern(/^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+(?: [A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)?$/)
      ]],
      firstname: '',
      surname: '',
      email: '',
      phoneNumber: ''
    }
  )

  submitted: boolean = false;

  get cf(): { [key: string]: AbstractControl } {
    return this.addContactForm.controls;
  }

  searchUser() {
    if (this.phoneNumber) {
      this.usersService
      .searchUser(this.phoneNumber)
      .subscribe({
        next: (user: User) => {
          this.addContactHandler(user)
        },
        error: (error: HttpErrorResponse) => {
          this.errorEmitter.emit(error)
        }
      })
    }
  }

  addContactHandler(contactUser: User) {

    this.addContactForm.patchValue({
      contactName: '',
      firstname: contactUser.firstname,
      surname: contactUser.surname,
      phoneNumber: contactUser.phoneNumber,
      email: contactUser.email
    })

    this.visible = true;
  }

  onSubmit(event: Event) {
    event.preventDefault();

    const { contactName, firstname, surname, email, phoneNumber } = this.addContactForm.value

    this.submitted = true;

    if (this.addContactForm.invalid) return

    this.newContactData = {
      contactName: contactName || '',
      contactUser: {
        firstname: firstname ||'',
        surname: surname ||'',
        phoneNumber: phoneNumber ||'',
        email: email ||'',
      } 
    }

    this.contactEmitter.emit(this.newContactData);

    this.visible = false;

  }
}
