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

  userChats: Chat[] = [];
  messages!: Message[];

  subscribeToUsersChatsObserver() {
    this.chatDataSharingService
    .userChatsObservable
    .subscribe({
      next: (userChats: Chat[]) => {
        this.userChats = userChats;
        this.messages = [];

        if (userChats && !(userChats.length > 0)) {
          this.messages = [{ severity: "info", detail: "No contacts were found" }];
        }
      }
    })
  }

  addContactChat(chat: Chat) {
    this.messages = [];
    this.userChats.push(chat);
    this.chatDataSharingService.emitChatInfo(chat);
  }

  updateContactChat(chat: Chat) {
    const updatedChatIndex = this.userChats.findIndex(pointedChat => pointedChat.chatId === chat.chatId);
    this.userChats[updatedChatIndex] = chat;  
  }


  deleteContact(chat: Chat) {
    this.contactsService
    .deleteContact(chat.contact.contactId)
    .subscribe({
      next: (response: ResultResponse) => {
        this.chatDataSharingService.askForUserChats();
      },
      error: (error: unknown) => {
        this.messages = [{ severity: 'error', summary: 'Error: ', detail: 'Unable to delete contact' }];
      } 
    })
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
    if (this.userChats && !(this.userChats.length > 0)) {
      setTimeout(() => {
        this.messages = [{ severity: "info", detail: "No contacts were found" }];
      }, 3000)
    }
  }

  ngOnInit() {
    this.subscribeToUsersChatsObserver()
  }
}
