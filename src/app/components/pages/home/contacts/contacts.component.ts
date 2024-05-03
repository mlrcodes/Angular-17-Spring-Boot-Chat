import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MessagesModule } from 'primeng/messages';
import { Message, MessageService } from 'primeng/api';
import { AddContactComponent } from './add-contact/add-contact.component';
import { ContactsCardComponent } from '../../../resources/contacts-card/contacts-card.component';
import { Chat } from '../../../../core/models/chat';
import { ChatDataSharingService } from '../../../../core/services/data-sharing/chat-data/chat-data-sharing.service';
import { ResultResponse } from '../../../../core/models/resultResponse';
import { ContactsService } from '../../../../core/services/contacts/contacts.service';

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
    private chatDataSharingService: ChatDataSharingService,
    private contactsService: ContactsService
  ) {}

  userChats!: Chat[];
  messages!: Message[];

  getUserChats() {
    this.chatDataSharingService.askForUserChats();
    this.chatDataSharingService
    .userChats
    .subscribe({
      next: (userChats: Chat[]) => {
        this.userChats = userChats;
        this.messages = [];
        console.log(userChats);
        if (userChats && !(userChats.length > 0)) {
          this.messages = [{ severity: "info", detail: "No contacts were found" }];
        }
      }
    })
  }

  addContactChat(chat: Chat) {
    this.chatDataSharingService.emitChatCRUD({chat, action: "create"});
    this.messages = [];
    console.log(this.userChats)
  }

  updateContactChat(chat: Chat) {
    this.chatDataSharingService.emitChatCRUD({chat, action: "update"});
  }


  deleteContact(chat: Chat) {
    this.contactsService
    .deleteContact(chat.contact.contactId)
    .subscribe({
      next: () => {  
        this.chatDataSharingService.emitChatCRUD({chat, action: "delete"});
      },
      error: (error: unknown) => {
        this.messages = [{ severity: 'error', summary: 'Error: ', detail: 'Unable to delete contact', life: 3000 }];
      } 
    })
    this.userHaveContacts();
  }

  notifyErrors(error: HttpErrorResponse) {
    if (error.status === 0) {
      this.messages = [{severity: 'error', summary: 'Error: ', detail: "Unable to connect the server", life: 3000}];
    } else {
      this.messages = [{ severity: 'error', summary: 'Error: ', detail: error.error.message, life: 3000 }];
    }  
    this.userHaveContacts()
  }

  userHaveContacts() {
    console.log(this.userChats && !(this.userChats.length > 0))
    console.log(this.userChats)
    console.log(this.userChats.length)
    if (this.userChats && !(this.userChats.length > 0)) {
      setTimeout(() => {
        this.messages = [{ severity: "info", detail: "No contacts were found" }];
      }, 3000)
    }
  }

  ngOnInit() {
    this.getUserChats()
  }
}
