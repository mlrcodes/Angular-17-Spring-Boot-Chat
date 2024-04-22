import { Component, EventEmitter, Output } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { HttpErrorResponse } from '@angular/common/http';
import { Contact } from '../../../../../core/models/contac';
import { ContactDialogComponent } from '../../../../resources/contact-dialog/contact-dialog.component';
import { ContactCreateRequest } from '../../../../../core/models/contactCreateRequest';
import { ContactsService } from '../../../../../core/services/contacts/contacts.service';

@Component({
  selector: 'app-add-contact',
  standalone: true,
  imports: [ ButtonModule, ContactDialogComponent],
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.scss'
})
export class ContactComponent {

  constructor(
    private contactsService: ContactsService
  ) {}

  @Output() errorEmitter: EventEmitter<HttpErrorResponse> = new EventEmitter<HttpErrorResponse>();
  @Output() createContactEmitter: EventEmitter<Contact> = new EventEmitter<Contact>();
  phoneNumber: string = "";
  visible: boolean = false;
  header: string = "Add New Contact"
  submitted: boolean = false;

  addContact(contactCreateRequest: ContactCreateRequest) {
    this.contactsService
    .createNewContact(contactCreateRequest)
    .subscribe({
      next: (contact: Contact) => {
        this.createContactEmitter.emit(contact);
      },
      error: (error: HttpErrorResponse) => {
        console.log(error)
        this.errorEmitter.emit(error)
      }
    })
  }

  openContactDialog() {
    this.visible = true;
  }
}
