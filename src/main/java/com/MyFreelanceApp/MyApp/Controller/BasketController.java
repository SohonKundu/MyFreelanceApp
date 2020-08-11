package com.MyFreelanceApp.MyApp.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.MyFreelanceApp.MyApp.DTO.BasketDto;
import com.MyFreelanceApp.MyApp.Model.Basket;
import com.MyFreelanceApp.MyApp.Service.BasketService;

@Controller
@RequestMapping(value = "/basket")
public class BasketController {
	
	@Autowired
	private BasketService basketService;
	
	@RequestMapping(value ="/",method = RequestMethod.POST)
	public ResponseEntity<Object> addAllProducts(@RequestBody BasketDto basket)
	{
		try {
			return new ResponseEntity<Object>(basketService.saveBasket(basket),HttpStatus.CREATED);
		} catch (Exception e) {
			
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value ="/Get",method = RequestMethod.GET)
	public ResponseEntity<Object> GetById(@RequestParam long BasketId)
	{
		
			Optional<Basket> basket= basketService.GetBasketById(BasketId);
			if(basket.isPresent()) {
				
				return new ResponseEntity<Object>(basket.get(),HttpStatus.OK);
			}
			else
				return new ResponseEntity<Object>("Basket Not Found",HttpStatus.CONFLICT);
	}

	
	@RequestMapping(value ="/GetLastBasket/{customerId}",method = RequestMethod.GET)
	public ResponseEntity<Object> GetLastBasket(@PathVariable("customerId") long customerId)
	{		
		try {
			Basket basket= basketService.GetLastOrderedBasket(customerId);
			return new ResponseEntity<Object>(basket,HttpStatus.OK);
		}catch(Exception ex) {			
				return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value ="/GetLastOrders/{customerId}",method = RequestMethod.GET)
	public ResponseEntity<Object> GetLastOrders(@PathVariable("customerId") long customerId)
	{		
//		try {
			List<Basket> baskets= basketService.GetLastOrdersByCustomer(customerId);
			return new ResponseEntity<Object>(baskets,HttpStatus.OK);
//		}catch(Exception ex) {			
//				return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.CONFLICT);
//		}
	}
	
	@RequestMapping(value ="/UpdateBasketDelivery/{customerId}/{basketId}",method = RequestMethod.PUT)
	public ResponseEntity<Object> UpdateBasketDelivery(@PathVariable("customerId") long customerId,
			@PathVariable("basketId") long basketId)
	{		
		try {			
			return new ResponseEntity<Object>(basketService.UpdateBasketDeliveryStatus(customerId, basketId),HttpStatus.OK);
		}catch(Exception ex) {			
				return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value ="/GetPendingOrders/",method = RequestMethod.GET)
	public ResponseEntity<Object> GetAllPendingOrders()
	{		
//		try {
			List<Basket> baskets= basketService.GetAllPendingOrders();
			return new ResponseEntity<Object>(baskets,HttpStatus.OK);
//		}catch(Exception ex) {			
//				return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.CONFLICT);
//		}
	}
	
	
	
}
