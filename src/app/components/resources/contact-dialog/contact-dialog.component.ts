import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { AbstractControl, FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { DialogModule } from 'primeng/dialog';
import { InputGroupModule } from 'primeng/inputgroup';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { ContactCreateRequest } from '../../../core/models/contactCreateRequest';
import { sqlInjectionValidator } from '../../../core/validators/sqlInjectionValidator.validator';
import { User } from '../../../core/models/user';
import { Contact } from '../../../core/models/contac';
import { ContactUpdateRequest } from '../../../core/models/contactUpdateRequest';

@Component({
  selector: 'app-contact-dialog',
  standalone: true,
  imports: [ButtonModule, InputTextModule, InputTextareaModule, ReactiveFormsModule, InputGroupModule, DialogModule],
  templateUrl: './contact-dialog.component.html',
  styleUrl: './contact-dialog.component.scss'
})
export class ContactDialogComponent implements OnInit {

  constructor(
    private formBuilder: FormBuilder
  ) {}


  @Output() createContactEmitter: EventEmitter<ContactCreateRequest> = new EventEmitter<ContactCreateRequest>();
  @Output() updateContactEmitter: EventEmitter<ContactCreateRequest> = new EventEmitter<ContactCreateRequest>();
  @Output() visibleChange: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Input() visible!: boolean;
  @Input() header: string = "";
  @Input() isEditMode: boolean = false;
  @Input() contact!: Contact  | null;
  createContactRequest!: ContactCreateRequest;
  updateContactRequest!: ContactUpdateRequest;
  submitted: boolean = false;
  messagePlaceHolder = this.isEditMode ? "" : "Write an optional message";

  addContactForm = this.formBuilder.group(
    {
      contactName: ['', [
        Validators.required,
        Validators.pattern(/^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+(?: [A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)?$/)
      ]],
      phoneNumber: ['', [
          Validators.required,
          Validators.pattern(/^\+\d{1,3}\d{4,14}$/),
      ]],
      message: ['', [
        Validators.maxLength(300), sqlInjectionValidator
      ]]
    }
  )
  
  get cf(): { [key: string]: AbstractControl } {
    return this.addContactForm.controls;
  }

  onSubmit(event: Event) {

    event.preventDefault();
    
    this.submitted = true;
    
    if (this.addContactForm.invalid) return

    if (this.isEditMode) this.emitUpdateRequest()
    else this.emitCreateRequest()
     

    this.visible = false;
    this.visibleChange.emit(this.visible)

    this.addContactForm.reset()

    this.submitted = false
  }

  emitCreateRequest() {
    const { contactName, phoneNumber, message } = this.addContactForm.value
 
    this.createContactRequest = {
      contactName: contactName || '',
      contactPhoneNumber: phoneNumber || '',
      message: message || ''
    }

    this.createContactEmitter.emit(this.createContactRequest);
  }

  emitUpdateRequest() {
    const { contactName } = this.addContactForm.value
    this.createContactRequest = {
      contactName: contactName || ''
    }

    this.updateContactEmitter.emit(this.createContactRequest);
  }

  onDisplayChange() {
    this.visibleChange.emit(this.visible);
    this.submitted = false;
    this.addContactForm.reset();
  }

  ngOnInit() {
    this.setContactDialgoData()
  }

  ngOnChanges() {
    this.setContactDialgoData
  }

  setContactDialgoData() {
    if (this.isEditMode && this.contact) {
      this.addContactForm.get('contactName')!.setValue(this.contact.contactName)
      this.addContactForm.get('phoneNumber')!.setValue(this.contact.contactUser.phoneNumber);
      this.addContactForm.get('phoneNumber')!.disable();
      this.addContactForm.get('message')!.disable();
    }
  }

}
