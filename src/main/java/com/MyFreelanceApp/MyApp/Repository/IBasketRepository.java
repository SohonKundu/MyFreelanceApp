package com.MyFreelanceApp.MyApp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.MyFreelanceApp.MyApp.Model.Basket;

@Repository
public interface IBasketRepository extends JpaRepository<Basket,Long>{
	
	@Query("select b from com.MyFreelanceApp.MyApp.Model.Basket b "
			+ "where b.customer.id = :CustomerId and b.isPaid = false")
	public Basket GetLastOrderedBasket(@Param("CustomerId")Long CustomerId);
	
	@Query("select b from com.MyFreelanceApp.MyApp.Model.Basket b "
			+ " where b.customer.id = :CustomerId and b.isPaid = true and b.isDelivered = true"
			+ " order by createdOn desc")
	public List<Basket> GetLastOrdersByCustomer(@Param("CustomerId")Long CustomerId);
	
	@Query("select b from com.MyFreelanceApp.MyApp.Model.Basket b "
			+ " where b.isPaid = true and b.isDelivered = false")
	public List<Basket> GetAllPendingOrders();
	
	@Query("select b from com.MyFreelanceApp.MyApp.Model.Basket b "
			+ "where b.customer.id = :CustomerId and b.isPaid = true and b.isDelivered = false"
			+ " and b.Basket_id = :BasketId")
	public Basket GetPaidBasketByCustomerForDeliveryUpdate(@Param("CustomerId")Long CustomerId,@Param("BasketId")Long BasketId);

}
