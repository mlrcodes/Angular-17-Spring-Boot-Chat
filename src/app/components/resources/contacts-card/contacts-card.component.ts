import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { Contact } from '../../../core/models/contac';
import { ButtonModule } from 'primeng/button';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { ConfirmationService } from 'primeng/api';
import { ContactComponent } from '../../pages/home/contacts/add-contact/contact.component';
import { HttpErrorResponse } from '@angular/common/http';
import { ContactDialogComponent } from '../contact-dialog/contact-dialog.component';
import { Chat } from '../../../core/models/chat';
import { ContactsService } from '../../../core/services/contacts/contacts.service';
import { ContactUpdateRequest } from '../../../core/models/contactUpdateRequest';
import { Router } from '@angular/router';
import { DataSharingService } from '../../../core/services/data-sharing/data-sharing.service';


@Component({
  selector: 'app-contacts-card',
  standalone: true,
  imports: [ButtonModule, ConfirmPopupModule, ContactComponent, ContactDialogComponent],
  providers: [ConfirmationService],
  templateUrl: './contacts-card.component.html',
  styleUrl: './contacts-card.component.scss'
})
export class ContactsCardComponent {

  constructor(
    private confirmationService: ConfirmationService,
    private contactsService: ContactsService,
    private router: Router,
    private dataSharingService: DataSharingService
  ) {}

  @Input() contact!: Contact;
  @Input() chat!: Chat;
  @Input() showEditBtns: boolean = false;
  @Output() deleteContactEmitter: EventEmitter<Contact> = new EventEmitter<Contact>;
  @Output() updatedContactEmitter: EventEmitter<Contact> = new EventEmitter<Contact>;
  @Output() errorEmitter: EventEmitter<HttpErrorResponse> = new EventEmitter<HttpErrorResponse>;
  @ViewChild('deleteButton') deleteButton: any;
  visible: boolean = false;
  header: string = "Edit Contact"

  confirmDelete() {
    this.confirmationService.confirm({
      target: this.deleteButton.nativeElement,
      message: 'Are you sure that you want to delete this contact?',
      accept: () => {
        this.deleteContactEmitter.emit(this.contact);
      }
    });
  }

  openContactChat() {
    this.router.navigate(['/home/chats/chat'])
    if (this.chat) this.dataSharingService.emitChatInfo(this.chat)
    else if (this.contact) this.dataSharingService.emitChatInfo(this.contact)
    
  }

  openContactDialog() {
    this.visible = true;
  }

  editContact(updateContactRequest: ContactUpdateRequest) {
    this.contactsService
    .editContact(updateContactRequest, this.contact.contactId)
    .subscribe({
      next: (updatedContact: Contact) => { 
        this.contact = updatedContact;
        this.updatedContactEmitter.emit(updatedContact);
      },
      error: (error: HttpErrorResponse) => {
        this.errorEmitter.emit(error);
      }
    })
  }

  notifyErrors(error: HttpErrorResponse) {
    this.errorEmitter.emit(error);
  }

  ngOnInit() {
    if (this.chat) this.contact = this.chat.contact
  }

}
