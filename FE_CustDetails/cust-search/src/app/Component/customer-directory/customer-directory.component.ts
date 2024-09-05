import { Component, OnInit } from '@angular/core';
import { Customer } from 'src/app/customer';
import { CustomerServiceService } from 'src/app/customer-service.service';

@Component({
  selector: 'app-customer-directory',
  templateUrl: './customer-directory.component.html',
  styleUrls: ['./customer-directory.component.css']
})
export class CustomerDirectoryComponent implements OnInit{
  customers: Customer[] = []; 
  constructor(private customerService: CustomerServiceService) {}

  ngOnInit(): void {
    // Call the service method to get customer data
    this.customerService.getCustomerList().subscribe(
      (response: any) => {
        if (response && response.data && Array.isArray(response.data.listOfCustomers)) {
          this.customers = response.data.listOfCustomers;
        } else {
          console.error('Invalid response format:', response);
        }
      },
      (error) => {
        console.error('Error fetching customer data:', error);
      }
    );
  }
}






