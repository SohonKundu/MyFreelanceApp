package com.MyFreelanceApp.MyApp.ApiResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
	
		private String id;
		private String entity;
		private double amount;
		private int amount_paid;
		private double amount_due;
	    private String currency;
	    private String receipt;
	    private String status;
	    
}
