import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HomeComponent } from './Component/home/home.component';
import { DisplayCustomerComponent } from './Component/display-customer/display-customer.component';
import { FormsModule } from '@angular/forms';
import { CustomerServiceService } from './customer-service.service';
import { HttpClientModule } from '@angular/common/http';
import { CustomerDirectoryComponent } from './Component/customer-directory/customer-directory.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    DisplayCustomerComponent,
    CustomerDirectoryComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [CustomerServiceService],
  bootstrap: [AppComponent]
})
export class AppModule { }
