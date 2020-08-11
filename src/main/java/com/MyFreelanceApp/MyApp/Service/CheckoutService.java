package com.MyFreelanceApp.MyApp.Service;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.MyFreelanceApp.MyApp.ApiResponse.Order;
import com.MyFreelanceApp.MyApp.ApiResponse.OrderRequest;
import com.MyFreelanceApp.MyApp.Model.Basket;
import com.MyFreelanceApp.MyApp.Repository.IBasketRepository;
import com.MyFreelanceApp.MyApp.Util.PaymentUtil;

@Service
public class CheckoutService {
	
	@Autowired 
	PaymentUtil paymentUtil;
	
	@Autowired
	IBasketRepository basketRepository;

	public Order InitiatePayment(OrderRequest orderRequest) throws Exception {		
		
		Optional<Basket> basket = Optional.ofNullable(basketRepository.GetLastOrderedBasket(orderRequest.getCustomerId()));
				
		if(basket.isPresent() && basket.get().getBasket_id() == orderRequest.getBasketId() 
				&& basket.get().getTotalPrice() == orderRequest.getAmount())
		{
			
			
			return paymentUtil.GetOrderResponse(orderRequest.getBasketId(),orderRequest.getAmount());
		}
		else
			throw new Exception("Customer does not have this basket");
	}

}
