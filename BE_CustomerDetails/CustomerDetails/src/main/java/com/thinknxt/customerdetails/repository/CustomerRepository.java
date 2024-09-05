package com.thinknxt.customerdetails.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.thinknxt.customerdetails.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, Integer> {

	
	 Optional<Customer> findBycifNumber(int cifNumber);

	boolean existsByCifNumber(int cifNumber);
	}


