import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
import { Customer } from './customer';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CustomerServiceService {

  private baseURL = "http://localhost:8080/api/v1/customer";

  constructor(private httpClient: HttpClient) { }
  
  getCustomerList(): Observable<Customer[]>{
    const additionalPath = "/getallcustomers";
    return this.httpClient.get<Customer[]>(`${this.baseURL}${additionalPath}`);
  }

  createCustomer(customer: Customer): Observable<Object>{
    console.log(customer);
    const additionalPath = "/create";
    return this.httpClient.post(`${this.baseURL}${additionalPath}`, customer);
  }

  getCustomerById(cifNumber: number): Observable<Customer>{
    const additionalPath = `/get/${cifNumber}`;
    return this.httpClient.get<Customer>(`${this.baseURL}${additionalPath}`);
  }
}
