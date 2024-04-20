import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { AbstractControl, FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { DialogModule } from 'primeng/dialog';
import { InputGroupModule } from 'primeng/inputgroup';
import { InputGroupAddonModule } from 'primeng/inputgroupaddon';
import { InputTextModule } from 'primeng/inputtext';
import { User } from '../../../../core/models/user';
import { Contact } from '../../../../core/models/contac';

@Component({
  selector: 'app-contact-dialog',
  standalone: true,
  imports: [InputTextModule, ReactiveFormsModule, InputGroupAddonModule, InputGroupModule, DialogModule],
  templateUrl: './contact-dialog.component.html',
  styleUrl: './contact-dialog.component.scss'
})
export class ContactDialogComponent {

  constructor(
    private formBuilder: FormBuilder
  ) {}


  @Output() contactDataChange: EventEmitter<Contact> = new EventEmitter<Contact>();
  @Output() visibleChange: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Input() visible: boolean = false;
  @Input() contactData!: Contact;
  @Input() header: string = "";

  addContactForm = this.formBuilder.group(
    {
      contactName: ['', [
        Validators.required,
        Validators.pattern(/^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+(?: [A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)?$/)
      ]],
      firstname: '',
      surname: '',
      email: '',
      phoneNumber: ''
    }
  )

  submitted: boolean = false;

  get cf(): { [key: string]: AbstractControl } {
    return this.addContactForm.controls;
  }

  onSubmit(event: Event) {
    event.preventDefault();

    const { contactName, firstname, surname, email, phoneNumber } = this.addContactForm.value

    this.submitted = true;

    if (this.addContactForm.invalid) return

    this.contactData = {
      contactName: contactName || '',
      contactUser: {
        firstname: firstname ||'',
        surname: surname ||'',
        phoneNumber: phoneNumber ||'',
        email: email ||'',
      } 
    }

    this.contactDataChange.emit(this.contactData);

    this.visible = false;
    this.visibleChange.emit(this.visible)

  }

  ngOnChanges() {
    this.addContactForm.patchValue({
      contactName: this.contactData.contactName || '',
      firstname: this.contactData.contactUser.firstname,
      surname: this.contactData.contactUser.surname,
      phoneNumber: this.contactData.contactUser.phoneNumber,
      email: this.contactData.contactUser.email
    })
  }

}
