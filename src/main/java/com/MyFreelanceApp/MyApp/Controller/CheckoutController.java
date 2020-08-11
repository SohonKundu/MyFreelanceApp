package com.MyFreelanceApp.MyApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.MyFreelanceApp.MyApp.ApiResponse.OrderRequest;
import com.MyFreelanceApp.MyApp.Service.CheckoutService;

@Controller
@RequestMapping("/checkout/")
public class CheckoutController {
	
	@Autowired
	private CheckoutService checkoutService;
	
	
	@RequestMapping(method=RequestMethod.POST,value="/initiate")
	public ResponseEntity<Object>InitiatePayment(@RequestBody OrderRequest orderRequest) 
	{
		try
		{
			return new ResponseEntity<Object>(checkoutService.InitiatePayment(orderRequest),HttpStatus.OK);
			
		}catch(Exception ex)
		{
			return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.CONFLICT);
		}
	}

}
