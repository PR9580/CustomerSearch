package com.thinknxt.customerdetails.utils;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.thinknxt.customerdetails.dto.ErrorDetails;
import com.thinknxt.customerdetails.dto.GenericResponse;


@Component
@Configuration
public class CommonUtil {
	
	
	public GenericResponse prepareResponse(String message, Integer value, String path, Object data) {
			
		GenericResponse response = new GenericResponse();
		response.setStatus(value);
		response.setMessage(message);
		response.setPath(path);
		response.setTimestamp(LocalDateTime.now());
		response.setData(data);
		return response;
			
		}
	
	
	public ErrorDetails errorResponse(int value, String message, String error, String path) {
		
		ErrorDetails response; response = new ErrorDetails();
		response.setStatus(value);
		response.setMessage(message);
		response.setErrors(error);
		response.setTimestamp(LocalDateTime.now());
		response.setPath(path);
		return response;
			
		}
	
	
}

