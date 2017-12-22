package org.zerock.domain;

public class ProductVO {
	
	private String name;
	private String price;
	
	public ProductVO() {
		System.out.println("ProductVO()...");
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "ProductVO [name=" + name + ", price=" + price + "]";
	}
	
	

}
