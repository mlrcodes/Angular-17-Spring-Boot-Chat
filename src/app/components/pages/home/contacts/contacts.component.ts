import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MessagesModule } from 'primeng/messages';
import { Contact } from '../../../../core/models/contac';
import { ContactsService } from '../../../../core/services/contacts/contacts.service';
import { MessageService } from 'primeng/api';
import { AddContactComponent } from './add-contact/add-contact.component';

@Component({
  selector: 'app-contacts',
  standalone: true,
  imports: [MessagesModule, AddContactComponent],
  providers: [MessageService],
  templateUrl: './contacts.component.html',
  styleUrl: './contacts.component.scss'
})
export class ContactsComponent implements OnInit {

  constructor(
    private contactsService: ContactsService,
    private messageService: MessageService
  ) {}

  userContacts: Contact[] = [];

  getUserContacts() {
    this.contactsService
    .getUserContacts()
    .subscribe({
      next: (contacts: Contact[]) => {
        this.userContacts = contacts;
      },
      error: (error: HttpErrorResponse) => {
        this.notifyErrors(error);
      }
    })
  }

  addContact(contact: Contact) {
    console.log(contact)
    this.contactsService
    .createNewContact(contact)
    .subscribe({
      next: (contact: Contact) => {
        console.log(contact)
      },
      error: (error: HttpErrorResponse) => {
        this.notifyErrors(error)
      }
    })
  }

  notifyErrors(error: HttpErrorResponse) {
    if (error.status === 0) {
      this.messageService.add({ severity: 'info', summary: 'Error: ', detail: "Unable to connect the server" });
    } else {
      console.log(error)
      this.messageService.add({ severity: 'info', summary: 'Info: ', detail: error.error.message });
    }  
  }

  ngOnInit() {
    this.getUserContacts()
  }
}
