package com.thinknxt.customerdetails.model;


import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.thinknxt.customerdetails.constant.MessageConstants;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customerDetails")
public class Customer {
		
		
		@Id
		@NotNull(message = "Field must not be null")
		@Min(value = 1, message = MessageConstants.EMPLOYEE_ID_VALIDATION_MESSASGE)
		private int cifNumber;
		
		@NotEmpty(message = "FirstName is required")
		@Pattern(regexp = "^[a-zA-Z]+$", message = MessageConstants.FIRSTNAME_VALIDATION_MESSASGE)
		private String firstName;
		
		
		@Pattern(regexp = "^[a-zA-Z]*$", message = MessageConstants.MIDDLENAME_VALIDATION_MESSASGE)
		private String middleName;
		
		@NotEmpty(message = "LastName is required")
		@Pattern(regexp = "^[a-zA-Z]+$", message = MessageConstants.LASTNAME_VALIDATION_MESSASGE)
		private String lastName;
		
		private String gender;
		
		@NotNull
		@Past
		private LocalDate dobirth;
		
		@NotEmpty
		private String idtype;
		
		@NotEmpty
		@Indexed(unique=true)
		private String idNumber;
		
		@NotNull
		@PastOrPresent
		private LocalDate idIssDate;
		
		@NotNull
		@FutureOrPresent
		private LocalDate idExpDate;
		
		@NotNull
		private String customerType;
		
		private String addrline1;
		private String addrline2;
		private String addrline3;
		
		@NotBlank
		private String city;
		
		@NotBlank
		private String country;
		
		@NotBlank
		private String nationality;
		
		@NotNull
		private int postCode;

		//empdetials
		private String currEmployer;
		private String currDepCode;
		private String currDepname;
		private LocalDate dtRetirement;
		private String supDesgn;
		private String supName;
		private String occupancy;
		private String sourceOfIncome;

		//bankdetails
		@NotBlank
		private String bankName;
		
		private String bankCode;
		private String branchCode;
		
		@NotBlank
		private String branchName;
		
		@NotBlank
		@Indexed(unique=true)
		
		private String acctNo;
		
		@NotBlank
		private String acctType;
		
		private LocalDate appDate;
		private String cardType;
		private Long cardNumber;
		private LocalDate cardExpdate;
		private String relManager;
		
		@NotBlank
		private String kyc_VYCflag;
		
		private LocalDate kyc_VYCdate;
		private String homeBranch;
		
		@NotBlank
		private String custRisk;
		
		@NotBlank
		private String amlRisk;
		
		private String vipCode;
		private boolean isActive;
		
	}

