package com.MyFreelanceApp.MyApp.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
@Entity
@Table(name = "BasketDetails")
@Getter
@Setter
public class BasketDetails implements Serializable{
	
	private static final long serialVersionUID = 7969451722062146012L;

	@JsonProperty(value="BasketDetailsId",required=false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BasketDetailsId")
	private long basketDetailsId;
	
	@JsonProperty(value="Product",required=false)
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name ="PRODUCT_ID",nullable = false)
	private Product product;
	
	@JsonProperty(value="Quantity",required=true)
	@Column(name = "quantity", nullable = false, updatable = true)
	private int quantity;
	
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name= "Basket_Id", nullable = false)	
	private Basket basket;

}
