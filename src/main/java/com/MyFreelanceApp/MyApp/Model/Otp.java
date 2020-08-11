package com.MyFreelanceApp.MyApp.Model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="Otp")
public class Otp implements Serializable{
	
	private static final long serialVersionUID = -9132706818009748083L;

	@Id
	@TableGenerator(name = "otp_gen", initialValue = 500)
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "otp_gen")	
	private long id;
	
	@Column(name="Contact_No")
	private long contactNo;
	
	@Column(name="Otp")
	private int otp;
	
	@Column(name="Created_On")
	private Calendar createdOn;

}
