package com.MyFreelanceApp.MyApp.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.MyFreelanceApp.MyApp.Model.Basket;
import com.MyFreelanceApp.MyApp.Model.BasketDetails;

public interface IBasketDetailsRepository extends JpaRepository<BasketDetails,Long>{
	
	public List<BasketDetails> findByBasket(Basket BasketId);
	
	@Transactional
	@Modifying
	@Query("delete from com.MyFreelanceApp.MyApp.Model.BasketDetails bd where bd.basket.Basket_id = :BasketId")
	public int DeleteItemsFromBasket(@Param("BasketId")long BasketId);

}
