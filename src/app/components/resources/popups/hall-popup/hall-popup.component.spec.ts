import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HallPopupComponent } from './hall-popup.component';

describe('HallPopupComponent', () => {
  let component: HallPopupComponent;
  let fixture: ComponentFixture<HallPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HallPopupComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HallPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
