package com.MyFreelanceApp.MyApp.Controller;

import java.util.Optional;
//import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.MyFreelanceApp.MyApp.Model.Customer;
import com.MyFreelanceApp.MyApp.Service.CustomerService;

@Controller
@RequestMapping(value = "/users")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	
	@RequestMapping(value = {"/{number}/{otp}","/{number}"},method = RequestMethod.GET)
	public ResponseEntity<Object> GetUserByContactNo(@PathVariable("number")long number, 
			@PathVariable(required =false,name ="otp")Optional<Integer> otp) throws Exception
	{	
		try {
		Optional<Customer> customer = customerService.GetByContactNo(number,otp);
		if(customer.isPresent())
			return new ResponseEntity<Object>(customer.get(), HttpStatus.OK);
		else
			return new ResponseEntity<Object>("Customer not registered",HttpStatus.CONFLICT);
		}catch(Exception ex)
		{
			return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	
	@RequestMapping(value ="/",method = RequestMethod.POST)
	public ResponseEntity<Object> SaveUserBasicInfo(@RequestBody Customer customer) throws Exception
	{	
		try {
			
			return new ResponseEntity<>(customerService.addCustomerInfo(customer), HttpStatus.ACCEPTED);
		}
		catch(Exception ex)
		{
			return new ResponseEntity<Object>(ex.getMessage()+ex.getStackTrace(),HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value ="/SendOtp/{number}",method = RequestMethod.POST)
	public ResponseEntity<Object> SendOtpToUser(@PathVariable("number")long number) throws Exception
	{	
		try {
			
			return new ResponseEntity<>(customerService.SendOtp(number), HttpStatus.ACCEPTED);
		}
		catch(Exception ex)
		{
			return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.CONFLICT);
		}
	}
	
	
}
