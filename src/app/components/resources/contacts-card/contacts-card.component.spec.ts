import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContactsCardComponent } from './contacts-card.component';

describe('ContactsCardComponent', () => {
  let component: ContactsCardComponent;
  let fixture: ComponentFixture<ContactsCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContactsCardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ContactsCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
