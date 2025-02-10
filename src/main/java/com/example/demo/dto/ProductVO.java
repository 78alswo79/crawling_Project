package com.example.demo.dto;

import org.springframework.stereotype.Repository;

import lombok.Getter;
import lombok.Setter;

@Repository
public class ProductVO {

	@Getter
	@Setter
	private String title;
	
	@Getter
	@Setter
	private int price;
	
	@Getter
	@Setter
	private String imgSrc;

	@Override
	public String toString() {
		return "ProductVO [title=" + title + ", price=" + price + ", imgSrc=" + imgSrc + "]";
	}
	
	
	
	
	
}
