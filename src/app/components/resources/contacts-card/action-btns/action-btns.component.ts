
import { Component, ElementRef, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { ContactDialogComponent } from '../../contact-dialog/contact-dialog.component';
import { Contact } from '../../../../core/models/contac';
import { ConfirmationService } from 'primeng/api';
import { ContactUpdateRequest } from '../../../../core/models/contactUpdateRequest';
import { ContactsService } from '../../../../core/services/contacts/contacts.service';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { HttpErrorResponse } from '@angular/common/http';
import { Chat } from '../../../../core/models/chat';
import { ButtonModule } from 'primeng/button';


@Component({
  selector: 'app-action-btns',
  standalone: true,
  imports: [ContactDialogComponent, ConfirmPopupModule, ButtonModule],
  providers: [ConfirmationService],
  templateUrl: './action-btns.component.html',
  styleUrl: './action-btns.component.scss'
})
export class ActionBtnsComponent {

  constructor(
    private confirmationService: ConfirmationService,
    private contactsService: ContactsService
  ) {}

  @Input() contact!: Contact;
  @Output() deleteContactEmitter: EventEmitter<void> = new EventEmitter<void>;
  @Output() updatedContactChatEmitter: EventEmitter<Chat> = new EventEmitter<Chat>;
  @Output() errorEmitter: EventEmitter<HttpErrorResponse> = new EventEmitter<HttpErrorResponse>;
  @ViewChild('deleteButton') deleteButton!: ElementRef;
  visible: boolean = false;
  header: string = "Edit Contact"

  confirmDelete(event: Event) {
    event.stopPropagation();
    this.confirmationService.confirm({
      target: this.deleteButton.nativeElement,
      message: 'Are you sure that you want to delete this contact?',
      accept: () => {
        this.deleteContactEmitter.emit();
      }
    });
  }

  updateContact(updateContactRequest: ContactUpdateRequest) {
    this.contactsService
    .editContact(
      updateContactRequest, 
      this.contact.contactId
    )
    .subscribe({
      next: (updatedContactChat: Chat) => { 
        console.log(updatedContactChat)
        this.contact = updatedContactChat.contact;
        this.updatedContactChatEmitter.emit(updatedContactChat);
      },
      error: (error: HttpErrorResponse) => {
        this.errorEmitter.emit(error);
      }
    })
  }

  openContactDialog(event: Event) {
    event.stopPropagation();
    this.visible = true;  
  }

}
