package com.thinknxt.customerdetails.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thinknxt.customerdetails.constant.Constants;
import com.thinknxt.customerdetails.dto.GenericResponse;
import com.thinknxt.customerdetails.model.Customer;
import com.thinknxt.customerdetails.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(Constants.BASE_PATH)
@Slf4j
public class CustomerController {

	@Autowired
	private CustomerService customerService;


	@Operation(summary = "Customer Creation",
			responses= { 
					@ApiResponse(
							responseCode = "201",
							description = "Customer created successfully"),
					@ApiResponse(
							responseCode = "400", 
							description = "Bad request. Invalid input data"),
					@ApiResponse(
							responseCode = "409", 
							description = "Conflict. Customer already exists"),
					@ApiResponse(
							responseCode = "500", 
							description = "Internal server error") })
	@PostMapping(value = Constants.CREATE_CUSTOMER)
	public ResponseEntity<GenericResponse> createCustomer(@Valid @RequestBody Customer customer, HttpServletRequest request) {

		log.info("Register Mentor API Called!");
		return new ResponseEntity<>(customerService.createCustomer(customer, request),HttpStatus.CREATED);

	}

	@Operation(
			summary="Fetch Customer",
			description="Fetch Customer by CustId",
			responses= {
					@ApiResponse(
							description = "Successful",
							responseCode = "200"
							),
					@ApiResponse(
							description = "Unauthorised User",
							responseCode = "400"
							),
					@ApiResponse(
							description = "Internal Server Error",
							responseCode = "500"
							)
			})
	@GetMapping(value=Constants.GET_CUSTOMER)
	public ResponseEntity<GenericResponse> getCustomerById(@PathVariable int cifno,
			HttpServletRequest httpServletRequest){

		log.info("Searching for Customer by customer Id "+cifno);

		return new ResponseEntity<>(customerService.getCustomerById(cifno, httpServletRequest),HttpStatus.FOUND);
	}


	@Operation(
			summary="Fetch list of all Customer",
			description="Fetch list of all Customer",
			responses= {
					@ApiResponse(
							description = "Successful",
							responseCode = "200"
							),
					@ApiResponse(
							description = "Bad Request",
							responseCode = "400"
							),
					@ApiResponse(
							description = "Internal Server Error",
							responseCode = "500"
							)
			})

	@GetMapping(value = Constants.GET_ALL_CUSTOMER)
	public ResponseEntity<GenericResponse> getAllCustomers(HttpServletRequest httpServletRequest){

		log.info("Searching for all customers");

		return new ResponseEntity<>(customerService.getAllMentors(httpServletRequest),HttpStatus.OK);
	}

}
