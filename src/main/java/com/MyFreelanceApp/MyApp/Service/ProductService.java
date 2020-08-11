package com.MyFreelanceApp.MyApp.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.MyFreelanceApp.MyApp.Model.Product;
import com.MyFreelanceApp.MyApp.Repository.IProductRepository;
import com.MyFreelanceApp.MyApp.Util.ImageProcess;

@Service
public class ProductService {
	
	@Autowired
	private IProductRepository productRepository;
	
	@Autowired
	private ImageProcess imageProcess;
	
	@CacheEvict(value = {"productDetails","products"}, allEntries=true)
	public long addProduct(Product product,MultipartFile prodFile) throws Exception
	{
		Optional<Product> existingProduct = Optional.ofNullable(productRepository.findByNameAndInStockEquals(product.getName(),true));
		if(existingProduct.isPresent() && existingProduct.get().isInStock()==true)
			throw new Exception("Product already present");
		else {
		product.setInStock(true);
		if(prodFile!=null)
			product.setProductImage(imageProcess.compressBytes(prodFile.getBytes()));
		productRepository.save(product);
		return product.getProductId();
		}
	}
	
	@Cacheable(value = "productDetails",key="#name",
			condition="#name != null")
	public List<Product> GetProductByName(String name) throws Exception
	{		
		Optional<List<Product>> product = Optional.ofNullable(
				productRepository.findByNameLikeIgnoreCaseAndInStockEquals("%"+name+"%",true));
		
			if(product.isPresent() ) {
				product.get().forEach(i->{
					if(i.getProductImage()!=null)
						i.setProductImage(imageProcess.decompressBytes(i.getProductImage()));
					}
				);
				return product.get();
			}
			else
				throw new Exception("Product not found");
	}	
	
	@Cacheable(value = "products",key="(#category.isPresent() && #orderBy.isPresent())?"
			+ "(#category.get()+#orderBy.get()):("
			+ "(!#category.isPresent() && #orderBy.isPresent())?"
			+ "'orderBy'+#orderBy.get():("
			+ "(#category.isPresent() && !#orderBy.isPresent())?"
			+ "'category'+#category.get():"
			+ "'GetAllProducts')"
			+ ")"
			)
	public List<Product>GetAllProducts(Optional<String> category,Optional<String>orderBy)
	{
		
		List<Product>products =null;
		if(category.isPresent())
			products = productRepository.findByCategoryAndInStockEquals(category.get(),true);
		else
			products = productRepository.findByInStockEquals(true);		
		products.forEach(i->{
			if(i.getProductImage()!=null)
				i.setProductImage(imageProcess.decompressBytes(i.getProductImage()));	
		});
		if(orderBy.isPresent() && orderBy.get().equals("1"))
			Collections.sort(products,Comparator.comparing(Product :: getName));
		if(orderBy.isPresent() && orderBy.get().equals("2"))
			Collections.sort(products,Comparator.comparing(Product :: getBasePrice));
		if(orderBy.isPresent() && orderBy.get().equals("3"))
			Collections.sort(products,Comparator.comparing(Product :: getName).reversed());
		if(orderBy.isPresent() && orderBy.get().equals("4"))
			Collections.sort(products,Comparator.comparing(Product :: getBasePrice).reversed());
		
		return products;
	}

	@CacheEvict(value = {"productDetails","products"}, allEntries=true)
	public long updateProduct(Product product,MultipartFile prodFile) throws Exception {
		
		Optional<Product> existingProduct = Optional.ofNullable(productRepository.findByNameAndInStockEquals(product.getName(),true));
		if(existingProduct.isPresent())
		{
			if(prodFile!=null)
				existingProduct.get().setProductImage(imageProcess.compressBytes(prodFile.getBytes()));
			existingProduct.get().setBasePrice(product.getBasePrice());
			return productRepository.save(existingProduct.get()).getProductId();
		}
		else
		{
			throw new Exception("Product not found");
		}
	}
	
	@CacheEvict(value = {"productDetails","products"}, allEntries=true)
	public long removeProduct(Product product) throws Exception {
		
		Optional<Product> existingProduct = Optional.ofNullable(productRepository.findByNameAndInStockEquals(product.getName(),true));
		if(existingProduct.isPresent())
		{
			existingProduct.get().setInStock(false);
			return productRepository.save(existingProduct.get()).getProductId();
		}
		else
		{
			throw new Exception("Product not found");
		}
	}

	@CacheEvict(value = {"productDetails","products"}, allEntries=true)
	public long deleteProduct(Product product) {
		
		productRepository.delete(product);
		return product.getProductId();
	}

}
