import { Component, EventEmitter, OnChanges, Output, SimpleChanges } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { MenubarModule } from 'primeng/menubar';
import { UsersService } from '../../../../../core/services/users/users.service';
import { HttpErrorResponse } from '@angular/common/http';
import { User } from '../../../../../core/models/user';
import { InputTextModule } from 'primeng/inputtext';
import { Contact } from '../../../../../core/models/contac';
import { ContactDialogComponent } from '../../../../resources/popups/contact-dialog/contact-dialog.component';
import { InputGroupModule } from 'primeng/inputgroup';

@Component({
  selector: 'app-add-contact',
  standalone: true,
  imports: [ FormsModule, InputTextModule, ButtonModule, MenubarModule, ContactDialogComponent, InputGroupModule],
  templateUrl: './add-contact.component.html',
  styleUrl: './add-contact.component.scss'
})
export class AddContactComponent implements OnChanges {
  constructor(
    private usersService: UsersService
  ) {}

  @Output() errorEmitter: EventEmitter<HttpErrorResponse> = new EventEmitter<HttpErrorResponse>();
  @Output() contactEmitter: EventEmitter<Contact> = new EventEmitter<Contact>();
  contact!: Contact;
  phoneNumber: string = "";
  visible: boolean = false;
  header: string = "Add New Contact"
  submitted: boolean = false;

  searchUser() {
    this.submitted = true;
    if (this.phoneNumber) {
      this.usersService
      .searchUser(this.phoneNumber)
      .subscribe({
        next: (user: User) => {
          this.contact.contactUser = user;
          this.visible = true;
        },
        error: (error: HttpErrorResponse) => {
          this.errorEmitter.emit(error)
        }
      })
    }
  }

  ngOnChanges(changes: SimpleChanges) {
    console.log(changes)
    this.contactEmitter.emit(this.contact);
  }
}
