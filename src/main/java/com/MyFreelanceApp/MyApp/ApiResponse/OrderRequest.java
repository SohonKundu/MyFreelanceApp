package com.MyFreelanceApp.MyApp.ApiResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderRequest {
	
	@JsonProperty(required=true)
	private long BasketId;
	
	@JsonProperty(required=true)
	private long CustomerId;
	
	@JsonProperty(required=true)
	private double Amount;

}
