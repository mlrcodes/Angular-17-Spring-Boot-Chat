import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MessagesModule } from 'primeng/messages';
import { Contact } from '../../../../core/models/contac';
import { ContactsService } from '../../../../core/services/contacts/contacts.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-contacts',
  standalone: true,
  imports: [MessagesModule],
  providers: [MessageService],
  templateUrl: './contacts.component.html',
  styleUrl: './contacts.component.scss'
})
export class ContactsComponent implements OnInit {

  constructor(
    private contactsService: ContactsService,
    private messageService: MessageService,
  ) {}

  private userContacts: Contact[] = [];

  getUserContacts() {
    this.contactsService
    .getUserContacts()
    .subscribe({
      next: (contacts: Contact[]) => {
        this.userContacts = contacts;
      },
      error: (error: HttpErrorResponse) => {
        this.notifyChatsError(error);
      }
    })
  }

  notifyChatsError(error: HttpErrorResponse) {
    if (error.status === 0) {
      this.messageService.add({ severity: 'info', summary: 'Error: ', detail: "Unable to connect the server" });
    } else {
      this.messageService.add({ severity: 'info', summary: 'Info: ', detail: error.error.message });
    }  
  }

  ngOnInit() {
    this.getUserContacts()
  }
}
