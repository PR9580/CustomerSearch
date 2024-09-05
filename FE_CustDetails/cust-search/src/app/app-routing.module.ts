import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './Component/home/home.component';
import { DisplayCustomerComponent } from './Component/display-customer/display-customer.component';
import { CustomerDirectoryComponent } from './Component/customer-directory/customer-directory.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent, 
  },
  {
    path:'home', component:HomeComponent
  },
  {
    path:'search', component:DisplayCustomerComponent
  },
  {
    path:'customer-directory', component:CustomerDirectoryComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
