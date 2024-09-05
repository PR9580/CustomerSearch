import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CustomerDirectoryComponent } from './customer-directory.component';

describe('CustomerDirectoryComponent', () => {
  let component: CustomerDirectoryComponent;
  let fixture: ComponentFixture<CustomerDirectoryComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CustomerDirectoryComponent]
    });
    fixture = TestBed.createComponent(CustomerDirectoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
