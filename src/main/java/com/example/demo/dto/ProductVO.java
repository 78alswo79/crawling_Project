package com.example.demo.dto;

import org.springframework.stereotype.Repository;

@Repository
public class ProductVO {

	private String title;
	private int price;
	
	// 기본 생성자 추가 (선택 사항)
    public ProductVO() {
    }

	public ProductVO(String title, int price) {
		this.title = title;
		this.price = price;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ProductVO [title=" + title + ", price=" + price + "]";
	}
	
	
	
	
	
}
