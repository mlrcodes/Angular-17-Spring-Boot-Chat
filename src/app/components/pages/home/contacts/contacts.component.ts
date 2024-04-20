import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MessagesModule } from 'primeng/messages';
import { Contact } from '../../../../core/models/contac';
import { ContactsService } from '../../../../core/services/contacts/contacts.service';
import { MessageService } from 'primeng/api';
import { AddContactComponent } from './add-contact/add-contact.component';
import { ContactsCardComponent } from '../../../resources/contacts-card/contacts-card.component';
import { ResultResponse } from '../../../../core/models/resultResponse';

@Component({
  selector: 'app-contacts',
  standalone: true,
  imports: [MessagesModule, AddContactComponent, ContactsCardComponent],
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
    this.contactsService
    .createNewContact(contact)
    .subscribe({
      next: (contact: Contact) => {
        this.messageService.clear()
        this.userContacts.push(contact);
      },
      error: (error: HttpErrorResponse) => {
        this.notifyErrors(error)
      }
    })
  }

  deleteContact(contact: Contact) {
    this.contactsService
    .deleteContact(contact.contactId)
    .subscribe({
      next: (response: ResultResponse) => {
        this.handleContactDeletion(response);
      },
      error: (error: HttpErrorResponse) => {
        this.notifyErrors(error);
      } 
    })
  }

  handleContactDeletion(response: ResultResponse) {
    const deletedContact: Contact | undefined = this.userContacts.find(
      contact => contact.contactId === contact.contactId
     )
     if (deletedContact) {
       const deletedContactIndex = this.userContacts.indexOf(deletedContact)
       this.userContacts.splice(deletedContactIndex, 1)
     }

     this.notifySuccess(response)
  }

  notifyErrors(error: HttpErrorResponse) {
    console.log(error)
    if (error.status === 0) {
      this.messageService.add({ severity: 'error', summary: 'Error: ', detail: "Unable to connect the server" });
    } else {
      this.messageService.add({ severity: 'info', summary: 'Info: ', detail: error.error.message });
    }  
  }

  notifySuccess(response: ResultResponse) {
    this.messageService.add({ severity: 'success', detail: response.message, life: 3000 })
  }

  ngOnInit() {
    this.getUserContacts()
  }
}
