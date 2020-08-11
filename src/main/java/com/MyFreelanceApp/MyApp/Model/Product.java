package com.MyFreelanceApp.MyApp.Model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import com.MyFreelanceApp.MyApp.Util.ImageProcess;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_NULL)
@Entity
@Table(name = "Product")
public class Product implements Serializable{
	
	private static final long serialVersionUID = 7811523142275587785L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "ProductGenerate")
	@TableGenerator(name = "ProductGenerate", initialValue = 550)
	@JsonProperty(required = true,value = "productId")
	@Column(name = "PRODUCT_ID")
	private long productId;
	
	@JsonProperty(required = true,value = "BasePrice")
	@Column(name = "BASE_PRICE", nullable = false)
	private double basePrice;
	
	@JsonProperty(required = true,value = "Name")
	@Column(name = "NAME", nullable = false)
	private String name;	
	
	@JsonProperty(required = true,value = "Category")
	@Column(name = "CATEGORY", nullable = true)
	private String category;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "In_Stock", nullable = true)
	private boolean inStock;	
	
	@JsonProperty(required = false,value = "Image")
	@Column(name = "PRODUCT_IMAGE", length = 1000,nullable=true)
	private byte[] productImage;
	
	

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@JsonIgnore
	public boolean isInStock() {
		return inStock;
	}

	public void setInStock(boolean inStock) {
		this.inStock = inStock;
	}

	public byte[] getProductImage() {
		if(productImage != null)
			return new ImageProcess().decompressBytes(productImage);
		else
			return productImage;
	}

	public void setProductImage(byte[] productImage) {
		this.productImage = productImage;
	}
	
	
	
	
}
