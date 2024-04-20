import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { Contact } from '../../../core/models/contac';
import { ButtonModule } from 'primeng/button';
import { ConfirmPopupModule } from 'primeng/confirmpopup';
import { ConfirmationService } from 'primeng/api';
import { AddContactComponent } from '../../pages/home/contacts/add-contact/add-contact.component';
import { HttpErrorResponse } from '@angular/common/http';
import { ContactDialogComponent } from '../popups/contact-dialog/contact-dialog.component';


@Component({
  selector: 'app-contacts-card',
  standalone: true,
  imports: [ButtonModule, ConfirmPopupModule, AddContactComponent, ContactDialogComponent],
  providers: [ConfirmationService],
  templateUrl: './contacts-card.component.html',
  styleUrl: './contacts-card.component.scss'
})
export class ContactsCardComponent {

  constructor(
    private confirmationService: ConfirmationService
  ) {}

  @Input() contact!: Contact;
  @Output() deleteContactEmitter: EventEmitter<Contact> = new EventEmitter<Contact>;
  @Output() editContactEmitter: EventEmitter<Contact> = new EventEmitter<Contact>;
  @Output() errorEmitter: EventEmitter<HttpErrorResponse> = new EventEmitter<HttpErrorResponse>;
  @ViewChild('deleteButton') deleteButton: any;
  visible: boolean = false;
  header: string = "Edit Contact"

  confirmDelete(event: Event) {
    this.confirmationService.confirm({
      target: this.deleteButton.nativeElement,
      message: 'Are you sure that you want to delete this contact?',
      accept: () => {
        this.deleteContactEmitter.emit(this.contact);
      }
    });
  }

  editContact() {
    this.visible = true;
  }

  notifyErrors(error: HttpErrorResponse) {
    this.errorEmitter.emit(error);
  }

  ngOnInit() {
    console.log(this.contact)
  }

}
