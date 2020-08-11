package com.MyFreelanceApp.MyApp.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.MyFreelanceApp.MyApp.Model.Product;
import com.MyFreelanceApp.MyApp.Service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@EnableAsync
@Controller
@RequestMapping(value = "/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value ="/",method = RequestMethod.POST)
	public ResponseEntity<Object> addProduct(@RequestParam(name="product",required=true) String product, @RequestParam(name ="productImage",required=false) MultipartFile productImage) throws Exception
	{
		try {
		ObjectMapper mapper = new ObjectMapper();
		Product prod = mapper.readValue(product, Product.class);
		return new ResponseEntity<Object>(productService.addProduct(prod,productImage),HttpStatus.CREATED);
		}catch(Exception ex)
		{			
			return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value ="/",method = RequestMethod.PUT)
	public ResponseEntity<Object> updateProduct(@RequestParam(name="product",required=true) String product,
			@RequestParam(name ="productImage",required=false) MultipartFile productImage)
	{
		try {
			ObjectMapper mapper = new ObjectMapper();
			Product prod = mapper.readValue(product, Product.class);
		return new ResponseEntity<Object>(productService.updateProduct(prod,productImage),HttpStatus.CREATED);
		}catch(Exception ex)
		{
			return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value ="/Delete/",method = RequestMethod.PUT)
	public ResponseEntity<Object> removeProduct(@RequestBody Product product)
	{
		try {
		return new ResponseEntity<Object>(productService.removeProduct(product),HttpStatus.CREATED);
		}catch(Exception ex)
		{
			return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value ="/",method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteProduct(@RequestBody Product product)
	{
		try {
		return new ResponseEntity<Object>(productService.deleteProduct(product),HttpStatus.CREATED);
		}catch(Exception ex)
		{
			return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.CONFLICT);
		}
	}
	
	
	
	@RequestMapping(value ="/Get",method = RequestMethod.GET)
	public ResponseEntity<Object> GetProductByName(@RequestParam("name")String name )
	{
		try
		{
			return new ResponseEntity<Object>(productService.GetProductByName(name),HttpStatus.OK);
		}
		catch(Exception ex)
		{
			return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.CONFLICT);
		}
	
	}
	
	@Async
	@RequestMapping(value ="/GetAll",method = RequestMethod.GET)
	public ResponseEntity<Object> GetAllProducts(
			@RequestParam(name ="category",required=false)String category,
			@RequestParam(name ="orderBy",required=false)String orderBy)
	{
		try
		{
			return new ResponseEntity<Object>(productService.
					GetAllProducts(Optional.ofNullable(category),
							Optional.ofNullable(orderBy)),HttpStatus.OK);
		}
		catch(Exception ex)
		{
			return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.CONFLICT);
		}	
	}
	
	
	
}
