package com.MyFreelanceApp.MyApp.DTO;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

import org.springframework.lang.Nullable;

import com.MyFreelanceApp.MyApp.Model.BasketDetails;
import com.MyFreelanceApp.MyApp.Model.Customer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
@Getter
@Setter
public class BasketDto {

	@JsonProperty(value="Id",required=false)
	private long Id;
	
	@JsonProperty(value="Customer",required=true)	
	private Customer customer;	
	
	@JsonProperty(value="CreatedOn",required=false)
	private Calendar createdOn;
	
	
	@JsonProperty(value="BasketDetails")	
	private Set<BasketDetails> basketDetails;
	
	@JsonProperty(value="TotalPrice",required=true)
	private double totalPrice;

}
