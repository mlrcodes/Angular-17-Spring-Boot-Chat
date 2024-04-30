import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { Contact } from '../../../core/models/contac';
import { ButtonModule } from 'primeng/button';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { ConfirmationService } from 'primeng/api';
import { AddContactComponent } from '../../pages/home/contacts/add-contact/add-contact.component';
import { HttpErrorResponse } from '@angular/common/http';
import { ContactDialogComponent } from '../contact-dialog/contact-dialog.component';
import { Chat } from '../../../core/models/chat';
import { ContactsService } from '../../../core/services/contacts/contacts.service';
import { ContactUpdateRequest } from '../../../core/models/contactUpdateRequest';
import { Router } from '@angular/router';
import { ChatDataSharingService } from '../../../core/services/data-sharing/chat-data/chat-data-sharing.service';
import { ActionBtnsComponent } from './action-btns/action-btns.component';


@Component({
  selector: 'app-contacts-card',
  standalone: true,
  imports: [ButtonModule, ConfirmPopupModule, AddContactComponent, ContactDialogComponent, ActionBtnsComponent],
  templateUrl: './contacts-card.component.html',
  styleUrl: './contacts-card.component.scss'
})
export class ContactsCardComponent {

  constructor(
    private router: Router,
    private chatDataSharingService: ChatDataSharingService
  ) {}

  @Input() chat!: Chat;
  @Input() showEditBtn: boolean = false;
  @Output() deletedContactChatEmitter: EventEmitter<Chat> = new EventEmitter<Chat>;
  @Output() updatedContactChatEmitter: EventEmitter<Chat> = new EventEmitter<Chat>;
  @Output() errorEmitter: EventEmitter<HttpErrorResponse> = new EventEmitter<HttpErrorResponse>;
  contact!: Contact;

  openContactChat() {
    this.router.navigate(['/home/chats/chat', this.chat.chatId])
  }

  updateContact(updatedContactChat: Chat) {
    this.contact = updatedContactChat.contact;
    this.updatedContactChatEmitter.emit(updatedContactChat);
  }

  emitDeletedContactChat() {
    this.deletedContactChatEmitter.emit(this.chat);
  }

  notifyErrors(error: HttpErrorResponse) {
    this.errorEmitter.emit(error);
  }

  ngOnInit() {
    this.contact = this.chat.contact
  }

}
