package com.thinknxt.customerdetails.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.thinknxt.customerdetails.constant.MessageConstants;
import com.thinknxt.customerdetails.dto.ErrorDetails;
import com.thinknxt.customerdetails.dto.ValidationError;
import com.thinknxt.customerdetails.utils.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@Autowired
	private CommonUtil commonUtil;
    
    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException exception,HttpServletRequest request) {
    	log.error("ResourceNotFoundException: ", MessageConstants.RESOURCE_NOT_FOUND);
    	
    	ErrorDetails setResponse = commonUtil.errorResponse(HttpStatus.NOT_FOUND.value(), exception.getLocalizedMessage(),  exception.getMessage(), request.getRequestURI());
    	return new ResponseEntity<>(setResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(value = CustomerAlreadyExistsException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(CustomerAlreadyExistsException exception,HttpServletRequest request) {
    	log.error("CustomerAlreadyExistsException: ", MessageConstants.CUSTOMER_EXISTS);
    	
    	ErrorDetails setResponse = commonUtil.errorResponse(HttpStatus.CONFLICT.value(), exception.getLocalizedMessage(),  exception.getMessage(), request.getRequestURI());
    	return new ResponseEntity<>(setResponse, HttpStatus.CONFLICT);
    }
    
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException ex, HttpServletRequest request){
    
    	log.info("Method Argument Not Valid exception called ");
    
    	Map<String, String> errors = new HashMap<>();
    	ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
  
    	ValidationError errorResponse = new ValidationError(HttpStatus.BAD_REQUEST.value(), MessageConstants.VALIDATION_ERROR, errors ,LocalDateTime.now(), request.getRequestURI());
    	return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
