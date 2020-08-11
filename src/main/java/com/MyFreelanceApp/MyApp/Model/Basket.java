package com.MyFreelanceApp.MyApp.Model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
@Entity
@Table(name = "Basket")
@Getter
@Setter
public class Basket implements Serializable{
	
	private static final long serialVersionUID = 7786517082970856706L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "BasketGenerate")
	@TableGenerator(name = "BasketGenerate", initialValue = 15330001)
	@Column(name = "Basket_Id")
	private long Basket_id;
	
	@JsonProperty(value="Customer",required=true)
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "id", nullable = false)
	private Customer customer;	
	
	@JsonProperty(value="CreatedOn",required=false)
	private Calendar createdOn;
	
	@Nullable
	@JsonProperty(value="BasketDetails")
	@OneToMany(mappedBy = "basket",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@Column(name="basketDetails")
	private Set<BasketDetails> basketDetails;
	
	@JsonProperty(value="TotalPrice",required=true)
	private double totalPrice;
	
	@JsonProperty(value="IsPaid",required=false)
	@Column(nullable=true)
	private boolean isPaid;
	
	@JsonProperty(value="IsDelivered",required=false)
	@Column(nullable=true)
	private boolean isDelivered;
	
	@JsonProperty(value="DeliveryUpdatedOn",required=false)
	private Calendar deliveryUpdatedOn;
	
}
