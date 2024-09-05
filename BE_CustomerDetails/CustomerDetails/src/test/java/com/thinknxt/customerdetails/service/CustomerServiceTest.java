package com.thinknxt.customerdetails.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;

import com.thinknxt.customerdetails.constant.MessageConstants;
import com.thinknxt.customerdetails.dto.GenericResponse;
import com.thinknxt.customerdetails.exception.CustomerAlreadyExistsException;
import com.thinknxt.customerdetails.exception.ResourceNotFoundException;
import com.thinknxt.customerdetails.model.Customer;
import com.thinknxt.customerdetails.model.CustomerData;
import com.thinknxt.customerdetails.repository.CustomerRepository;
import com.thinknxt.customerdetails.utils.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

public class CustomerServiceTest {


	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private MongoTemplate mongoTemplate;

	@Mock
	private CommonUtil commonUtil;

	@InjectMocks
	private CustomerService customerService;

	private Customer mockCustomer;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockCustomer = createSampleCustomer();
	}

	@Test
	public void testCreateCustomerSuccess() {
		String requestUri= "api/v1/customer/create";

		when(customerRepository.existsByCifNumber((mockCustomer.getCifNumber()))).thenReturn(false);
		when(customerRepository.save(any(Customer.class))).thenReturn(mockCustomer);

		HttpServletRequest httpRequest = mock(HttpServletRequest.class);
		String responseMessage = "Customer " + mockCustomer.getFirstName() + " " + mockCustomer.getLastName() + " created successfully.";

		GenericResponse expectedResponse = new GenericResponse(HttpStatus.OK.value(),responseMessage, null, LocalDateTime.now(), requestUri, mockCustomer);

		when(httpRequest.getRequestURI()).thenReturn(requestUri);
		when(commonUtil.prepareResponse(anyString(), anyInt(), anyString(), any())).thenReturn(expectedResponse);

		GenericResponse response = customerService.createCustomer(mockCustomer, httpRequest);

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertNotNull(response.getData());
		assertEquals(responseMessage, response.getMessage());

		verify(customerRepository, times(1)).save(eq(mockCustomer));

	}

	@Test
	public void testCreateCustomerDuplicateCifNo() {

		HttpServletRequest mockRequest = mock(HttpServletRequest.class);
		when(customerRepository.existsByCifNumber(eq(mockCustomer.getCifNumber()))).thenReturn(true);

		assertThrows(CustomerAlreadyExistsException.class, () -> {
			customerService.createCustomer(mockCustomer, mockRequest);});

		verify(customerRepository, times(1)).existsByCifNumber(eq(mockCustomer.getCifNumber()));
	}


	@Test
	public void testGetCustomerById_ValidCustomer() {

		int cifno = mockCustomer.getCifNumber();
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

		Customer customer = new Customer();
		when(customerRepository.findBycifNumber(cifno)).thenReturn(Optional.of(customer));

		GenericResponse expectedResponse = new GenericResponse(HttpStatus.OK.value(), MessageConstants.CUSTOMER_FOUND, null, LocalDateTime.now(), httpServletRequest.getRequestURI(), customer); // Use 'customer' instead of 'mockCustomer'
		when(commonUtil.prepareResponse(any(), anyInt(), any(), any())).thenReturn(expectedResponse);

		GenericResponse response = customerService.getCustomerById(cifno, httpServletRequest);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(MessageConstants.CUSTOMER_FOUND, response.getMessage());

		verify(customerRepository, times(1)).findBycifNumber(cifno);
	}

	@Test
	public void testGetCustomerById_InvalidCustomer() {
		int cifno = mockCustomer.getCifNumber();
		HttpServletRequest mockRequest = mock(HttpServletRequest.class);

		when(customerRepository.findBycifNumber(cifno)).thenReturn(Optional.empty());
		assertThrows(ResourceNotFoundException.class, () -> {
			customerService.getCustomerById(cifno, mockRequest);
		});
	} 

	@Test
	public void testGetAllCustomers() {

		HttpServletRequest httpRequest = mock(HttpServletRequest.class);

		Customer customer1 = new Customer();
		Customer customer2 = new Customer();

		customer1.setActive(true);
		customer1.setActive(false);

		List<Customer> mockCustomers = new ArrayList<>();

		mockCustomers.add(customer1);
		mockCustomers.add(customer2);

		CustomerData customersData = new CustomerData(mockCustomers.size(), mockCustomers);
		when(customerRepository.findAll()).thenReturn(mockCustomers);
		String requestUri = "/getallcustomers";

		GenericResponse expectedResponse = new GenericResponse();
		expectedResponse.setStatus(HttpStatus.OK.value());
		expectedResponse.setMessage("Success");
		expectedResponse.setPath(requestUri);
		expectedResponse.setData(customersData);

		when(httpRequest.getRequestURI()).thenReturn(requestUri);
		when(commonUtil.prepareResponse(anyString(), anyInt(), anyString(), any())).thenReturn(expectedResponse);

		GenericResponse result = customerService.getAllCustomers(httpRequest);
		assertEquals(expectedResponse, result);
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