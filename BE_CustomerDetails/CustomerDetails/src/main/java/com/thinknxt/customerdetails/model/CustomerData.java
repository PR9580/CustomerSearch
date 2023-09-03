package com.thinknxt.customerdetails.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerData {

	private int noOfCustomers;
	private List<Customer> listOfCustomers;
}


