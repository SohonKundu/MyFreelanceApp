package com.MyFreelanceApp.MyApp.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.MyFreelanceApp.MyApp.DTO.BasketDto;
import com.MyFreelanceApp.MyApp.Model.Basket;
import com.MyFreelanceApp.MyApp.Model.BasketDetails;
import com.MyFreelanceApp.MyApp.Model.Customer;
import com.MyFreelanceApp.MyApp.Model.Product;
import com.MyFreelanceApp.MyApp.Repository.IBasketDetailsRepository;
import com.MyFreelanceApp.MyApp.Repository.IBasketRepository;
import com.MyFreelanceApp.MyApp.Repository.ICustomerRepository;
import com.MyFreelanceApp.MyApp.Repository.IProductRepository;

@Service
public class BasketService {

	@Autowired
	private IBasketRepository basketRepository;

	@Autowired
	private IBasketDetailsRepository basketDetailsRepository;

	@Autowired
	private ICustomerRepository customerRepository;

	@Autowired
	private IProductRepository productRepository;

	public Basket saveBasket(BasketDto basket) throws Exception {
		try {

			Basket newBasket;

			if (basket.getId() != 0) {
				Optional<Basket> existingBasket = GetBasketById(basket.getId());
				newBasket = existingBasket.get();
				newBasket.setTotalPrice(basket.getTotalPrice());				
				newBasket.setBasketDetails(updateBasket(basket, newBasket));
			} else {

				newBasket = new Basket();
				newBasket.setCreatedOn(Calendar.getInstance());				
				newBasket.setCustomer(GetCustomerFromBasket(basket));
				newBasket.setTotalPrice(basket.getTotalPrice());
				newBasket.setBasketDetails(populateBasketDetailsFromBasket(basket, newBasket));				
			}
			newBasket = basketRepository.saveAndFlush(newBasket);
			return newBasket;

		} catch (Exception ex) {
			throw ex;
		}
	}

	private Product GetProductFromBasket(BasketDetails bd) throws Exception {
		Optional<Product> p = productRepository.findById(bd.getProduct().getProductId());
		if (p.isPresent())
			return p.get();
		else
			throw new Exception("Product not found");
	}

	private Customer GetCustomerFromBasket(BasketDto basket) throws Exception {
		Optional<Customer> customer = customerRepository.findById(basket.getCustomer().getId());
		if (customer.isPresent())
			return customer.get();
		else
			throw new Exception("Product not found");
	}

	private Set<BasketDetails> populateBasketDetailsFromBasket(BasketDto basket, Basket newBasket) {
		Set<BasketDetails> bdList = new HashSet<BasketDetails>();

		for (BasketDetails b : basket.getBasketDetails()) {
			try {
				BasketDetails bd = new BasketDetails();
				bd.setProduct(GetProductFromBasket(b));
				bd.setQuantity(b.getQuantity());
				bd.setBasket(newBasket);
				bdList.add(bd);
			} catch (Exception ex) {
				ex.getStackTrace();
			}
		}
		return bdList;
	}

	public Optional<Basket> GetBasketById(long BasketId) {
		Optional<Basket> basket = basketRepository.findById(BasketId);
		return basket;
	}

	public void DeleteBasketDetailsByBasket(long BasketId) throws Exception {

		basketDetailsRepository.DeleteItemsFromBasket(BasketId);

	}

	private Set<BasketDetails> updateBasket(BasketDto basket, Basket newBasket) throws InterruptedException {

		Map<Long, BasketDetails> hashBasketDto = new HashMap<Long, BasketDetails>();
		Map<Long, BasketDetails> hashNewBasket = new HashMap<Long, BasketDetails>();
		Set<BasketDetails> bdList = new HashSet<BasketDetails>();

		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(() -> {
			for (BasketDetails bd : basket.getBasketDetails()) {
				hashBasketDto.put(bd.getProduct().getProductId(), bd);
			}
		});
		executor.execute(() -> {
			for (BasketDetails bd : newBasket.getBasketDetails()) {
				hashNewBasket.put(bd.getProduct().getProductId(), bd);
			}
		});
		executor.shutdown();
		while (!executor.isTerminated()) {

		}
		hashBasketDto.forEach((p, bd) -> {
			if (hashNewBasket.containsKey(p)) {
				hashNewBasket.get(p).setQuantity(bd.getQuantity());
				bdList.add(hashNewBasket.get(p));
			} else {
				BasketDetails bdetails = new BasketDetails();
				try {
					bdetails.setProduct(GetProductFromBasket(bd));
				} catch (Exception e) {
					e.printStackTrace();
				}
				bdetails.setQuantity(bd.getQuantity());
				bdetails.setBasket(newBasket);
				bdList.add(bdetails);
			}

		});

		return bdList;
	}

	public Basket GetLastOrderedBasket(long CustomerId) throws Exception {
		Optional<Basket> lastBasket = Optional.ofNullable(basketRepository.GetLastOrderedBasket(CustomerId));
		if (lastBasket.isPresent()) {
			Basket basket = lastBasket.get();
			return basket;
		} else
			throw new Exception("No last Basket");
	}

	public List<Basket> GetLastOrdersByCustomer(long customerId) {

		List<Basket> baskets = basketRepository.GetLastOrdersByCustomer(customerId);

		return baskets;
	}

	public List<Basket> GetAllPendingOrders() {

		List<Basket> baskets = basketRepository.GetAllPendingOrders();

		return baskets;
	}

	public long UpdateBasketDeliveryStatus(long customerId, long basketId) {

		Optional<Basket> basket = Optional
				.ofNullable(basketRepository.GetPaidBasketByCustomerForDeliveryUpdate(customerId, basketId));
		if (basket.isPresent()) {
			basket.get().setDelivered(true);
			basket.get().setDeliveryUpdatedOn(Calendar.getInstance());
			basketRepository.saveAndFlush(basket.get());
		}
		return basketId;
	}
}
