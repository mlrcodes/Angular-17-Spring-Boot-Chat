import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MessagesModule } from 'primeng/messages';
import { Contact } from '../../../../core/models/contac';
import { ContactsService } from '../../../../core/services/contacts/contacts.service';
import { MessageService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { InputGroupModule } from 'primeng/inputgroup';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
import { FormsModule } from '@angular/forms';
import { UsersService } from '../../../../core/services/users/users.service';
import { User } from '../../../../core/models/user';
import { MenubarModule } from 'primeng/menubar';

@Component({
  selector: 'app-contacts',
  standalone: true,
  imports: [MessagesModule, InputGroupAddonModule, InputGroupModule, ButtonModule, FormsModule, MenubarModule],
  providers: [MessageService],
  templateUrl: './contacts.component.html',
  styleUrl: './contacts.component.scss'
})
export class ContactsComponent implements OnInit {

  constructor(
    private contactsService: ContactsService,
    private usersService: UsersService,
    private messageService: MessageService,
  ) {}

  private userContacts: Contact[] = [];

  phoneNumber: string = ""

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

  notifyErrors(error: HttpErrorResponse) {
    if (error.status === 0) {
      this.messageService.add({ severity: 'info', summary: 'Error: ', detail: "Unable to connect the server" });
    } else {
      this.messageService.add({ severity: 'info', summary: 'Info: ', detail: error.error.message });
    }  
  }

  searchUser() {
    if (this.phoneNumber) {
      this.usersService
      .searchUser(this.phoneNumber)
      .subscribe({
        next: (user: User) => {
          console.log(user)
        },
        error: (error: HttpErrorResponse) => {
          this.notifyErrors(error);
        }
      })
    }
  }

  ngOnInit() {
    this.getUserContacts()
  }
}
