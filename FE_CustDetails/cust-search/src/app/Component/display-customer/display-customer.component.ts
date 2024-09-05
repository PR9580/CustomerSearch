import { Component } from '@angular/core';
import { CustomerServiceService } from 'src/app/customer-service.service';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';


@Component({
  selector: 'app-display-customer',
  templateUrl: './display-customer.component.html',
  styleUrls: ['./display-customer.component.css']
})
export class DisplayCustomerComponent {
  cifNumber!: number;
  customer: any = null;

  constructor(private customerService: CustomerServiceService) {}

  onSearch() {
    if (isNaN(this.cifNumber)) {
      alert("Please enter a valid numeric CIF Number");
    } else if (this.cifNumber === null || this.cifNumber === undefined) {
      alert("Please enter CIF Number");
    } else {
      this.customerService.getCustomerById(this.cifNumber)
        .pipe(
          catchError((error) => {
            console.error('Error fetching customer data:', error);
            return of(null);
          })
        )
        .subscribe((data) => {
          if (data === null) {
            alert("Resource not found with id " + this.cifNumber);
          } else {
            this.customer = data;
            console.log(this.customer);
          }
        });
    }
  }
}

