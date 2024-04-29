import { Component, EventEmitter, Output } from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { HttpErrorResponse } from '@angular/common/http';
import { Contact } from '../../../../../core/models/contac';
import { ContactDialogComponent } from '../../../../resources/contact-dialog/contact-dialog.component';
import { ContactCreateRequest } from '../../../../../core/models/contactCreateRequest';
import { ContactsService } from '../../../../../core/services/contacts/contacts.service';
import { Chat } from '../../../../../core/models/chat';

@Component({
  selector: 'app-add-contact',
  standalone: true,
  imports: [ ButtonModule, ContactDialogComponent],
  templateUrl: './add-contact.component.html',
  styleUrl: './add-contact.component.scss'
})
export class AddContactComponent {

  constructor(
    private contactsService: ContactsService
  ) {}

  @Output() errorEmitter: EventEmitter<HttpErrorResponse> = new EventEmitter<HttpErrorResponse>();
  @Output() createContactEmitter: EventEmitter<Chat> = new EventEmitter<Chat>();
  phoneNumber: string = "";
  visible: boolean = false;
  header: string = "Add New Contact"
  submitted: boolean = false;

  addContact(contactCreateRequest: ContactCreateRequest) {
    this.contactsService
    .createNewContact(contactCreateRequest)
    .subscribe({
      next: (chat: Chat) => {
        this.createContactEmitter.emit(chat);
      },
      error: (error: HttpErrorResponse) => {
        this.errorEmitter.emit(error)
      }
    })
  }

  openContactDialog() {
    this.visible = true;
  }
}
