package com.MyFreelanceApp.MyApp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MyFreelanceApp.MyApp.Model.Product;

@Repository
public interface IProductRepository  extends JpaRepository<Product,Long>{	
	

	public List<Product> findByNameLikeIgnoreCase(String name);
	
	public Product findByNameAndInStockEquals(String name,boolean inStock);

	public List<Product> findByCategoryAndInStockEquals(String category,boolean inStock);
	
	public List<Product> findByInStockEquals(boolean inStock);
	
	public List<Product> findByNameLikeIgnoreCaseAndInStockEquals(String name,boolean inStock);
}
