package com.MyFreelanceApp.MyApp.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name ="Customer")
@Getter
@Setter
@JsonInclude(value= Include.NON_NULL)
public class Customer implements Serializable{
	
	private static final long serialVersionUID = -8008272043238289425L;

	@Id
	@TableGenerator(name = "UserGenerate", initialValue = 1001)
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "UserGenerate")
	@Column(name ="id")
	private long id;
	
	@JsonProperty(value="Number",required = true)
	@Column(name ="contactNumber",nullable = false, unique = true, length = 10)
	private long contactNumber;
	
	@JsonProperty(value="Name",required = false)
	@Column(name ="customerName",nullable = true)
	private String name;	
	
	@JsonProperty(value="Address",required = false)
	@Column(name ="address",nullable = true)
	private String address;
	
}
