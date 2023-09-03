package com.thinknxt.customerdetails.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import com.thinknxt.customerdetails.dto.GenericResponse;
import com.thinknxt.customerdetails.model.Customer;
import com.thinknxt.customerdetails.service.CustomerService;

import jakarta.servlet.http.HttpServletRequest;

public class CustomerControllerTest {

	@InjectMocks
	private CustomerController customerController;

	@Mock
	private CustomerService customerService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateCustomerSuccess() {


		Customer mockcCustomer = createSampleCustomer();

		HttpServletRequest mockRequest = new MockHttpServletRequest();
		((MockHttpServletRequest)mockRequest).setRequestURI("api/v1/customer/create");
		when(customerService.createCustomer(eq(mockcCustomer),any(HttpServletRequest.class))) .thenReturn(new GenericResponse(201,
				"Customer Created successfully", null, LocalDateTime.now(),mockRequest.getRequestURI(), mockcCustomer));

		ResponseEntity<GenericResponse> responseEntity = customerController.createCustomer(mockcCustomer, mockRequest);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals("api/v1/customer/create", mockRequest.getRequestURI());
		assertNotNull(responseEntity.getBody());
		assertEquals("Customer Created successfully",responseEntity.getBody().getMessage());

		verify(customerService, times(1)).createCustomer(mockcCustomer, mockRequest);

	}

	@Test
	public void testgetCustomerByIdSuccess() {

		Customer mockcCustomer = createSampleCustomer();
		int cifno = mockcCustomer.getCifNumber();
		HttpServletRequest mockRequest = new MockHttpServletRequest();
		((MockHttpServletRequest)mockRequest).setRequestURI("api/v1/customer/get/10001");
		when(customerService.getCustomerById(eq(cifno),any(HttpServletRequest.class))) .thenReturn(new GenericResponse(200,
				"Customer fetched successfully", null, LocalDateTime.now(),mockRequest.getRequestURI(), "Customer Data"));

		ResponseEntity<GenericResponse> responseEntity = customerController.getCustomerById(cifno, mockRequest);

		assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
		assertEquals("api/v1/customer/get/10001", mockRequest.getRequestURI());
		assertNotNull(responseEntity.getBody());
		assertEquals("Customer fetched successfully",responseEntity.getBody().getMessage());

		verify(customerService, times(1)).getCustomerById(cifno, mockRequest);

	}
	
	@Test
	public void testgetAllCustomersSuccess() {
		
		HttpServletRequest mockRequest = new MockHttpServletRequest();
		((MockHttpServletRequest)mockRequest).setRequestURI("api/v1/customer/getallcustomers");
		when(customerService.getAllMentors(any(HttpServletRequest.class))) .thenReturn(new GenericResponse(200,
				"List of Customers Fetched Successfully", null, LocalDateTime.now(),mockRequest.getRequestURI(), "Customer Data"));

		ResponseEntity<GenericResponse> responseEntity = customerController.getAllCustomers(mockRequest);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("api/v1/customer/getallcustomers", mockRequest.getRequestURI());
		assertNotNull(responseEntity.getBody());
		assertEquals("List of Customers Fetched Successfully",responseEntity.getBody().getMessage());

		verify(customerService, times(1)).getAllMentors(mockRequest);

	}


	private Customer createSampleCustomer() {
		Customer customer = new Customer();
		customer.setCifNumber(10001);
		customer.setFirstName("Rohit");
		customer.setLastName("Sharma");
		customer.setDobirth(LocalDate.of(1993,02,01)); 
		customer.setIdtype("Passport");
		customer.setIdNumber("524860049");
		customer.setIdIssDate(LocalDate.of(2001,05,01));
		customer.setIdExpDate(LocalDate.of(2052,02,01));
		customer.setActive(true);
		return customer;
	}
}