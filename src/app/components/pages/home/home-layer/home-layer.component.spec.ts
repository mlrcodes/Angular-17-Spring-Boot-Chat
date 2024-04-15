import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeLayerComponent } from './home-layer.component';

describe('HomeLayerComponent', () => {
  let component: HomeLayerComponent;
  let fixture: ComponentFixture<HomeLayerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomeLayerComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(HomeLayerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
