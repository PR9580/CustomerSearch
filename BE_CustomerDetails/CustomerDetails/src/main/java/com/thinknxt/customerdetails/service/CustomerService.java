package com.thinknxt.customerdetails.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.thinknxt.customerdetails.constant.MessageConstants;
import com.thinknxt.customerdetails.dto.GenericResponse;
import com.thinknxt.customerdetails.exception.CustomerAlreadyExistsException;
import com.thinknxt.customerdetails.exception.ResourceNotFoundException;
import com.thinknxt.customerdetails.model.Customer;
import com.thinknxt.customerdetails.model.CustomerData;
import com.thinknxt.customerdetails.repository.CustomerRepository;
import com.thinknxt.customerdetails.utils.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CommonUtil commonUtil;
	
	public GenericResponse getCustomerById(int custid, HttpServletRequest httpServletRequest) {
		log.debug("Searching for Customer by empid",+ custid);
		Optional<Customer> customer = customerRepository.findBycifNumber(custid);

		if(customer.isPresent()) {
			log.info("Customer fetched successful");
			return commonUtil.prepareResponse(MessageConstants.CUSTOMER_FOUND,
					HttpStatus.OK.value(), httpServletRequest.getRequestURI(),customer );
		}
			
		else {
			
			log.warn("Invalid Customer");
			throw new ResourceNotFoundException(MessageConstants.RESOURCE_NOT_FOUND + custid);
		}
	}


	public GenericResponse getAllMentors(HttpServletRequest httpServletRequest) {
	
			log.debug("Searching for all mentors");
			List<Customer> customers= customerRepository.findAll();
			
			if(customers.isEmpty()) {
				
				log.info("No Customer Found");
				throw new ResourceNotFoundException(MessageConstants.CUSTOMER_NOT_FOUND);
				
			}
			else
			{
				List<Customer> activeCustomers = customers.stream()
						.filter(customer -> customer.isActive()==true).collect(Collectors.toList());
				
				CustomerData customersData = new CustomerData(activeCustomers.size(), activeCustomers);
						
				log.info(customersData.toString());
				return commonUtil.prepareResponse("List Found",
						HttpStatus.OK.value(), httpServletRequest.getRequestURI(),customersData);
				
			}
	}


	public GenericResponse createCustomer(@Valid Customer customer, HttpServletRequest request) {
		log.info("Create customer method called!");
		

		if(customerRepository.existsByCifNumber(customer.getCifNumber())) { 
			throw new CustomerAlreadyExistsException("Customer exists with the CIF Number: " + customer.getCifNumber());
		}
		customer.setActive(true);
    	
		Customer savedcustomer= customerRepository.save(customer);
		 
		log.info("Response from Create Service: " + savedcustomer);

		String responseMessage = "Customer " + savedcustomer.getFirstName() + " " + savedcustomer.getLastName()+ " created successfully.";
		return commonUtil.prepareResponse(responseMessage, HttpStatus.OK.value(), request.getRequestURI(), savedcustomer);
        
		}
	}

