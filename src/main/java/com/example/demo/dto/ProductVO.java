package com.example.demo.dto;

import org.springframework.stereotype.Repository;

@Repository
public class ProductVO {

	private String bookTitle;
	private int bookprice;
	private String imgSrc;
	private String searchResult;
	private String customStarRating;
	private String detailHref;
	
	// 기본 생성자 추가 (선택 사항)
    public ProductVO() {
    }
    
    public ProductVO(String bookTitle, int bookprice, String imgSrc, String searchResult, String customStarRating,
			String detailHref) {
		super();
		this.bookTitle = bookTitle;
		this.bookprice = bookprice;
		this.imgSrc = imgSrc;
		this.searchResult = searchResult;
		this.customStarRating = customStarRating;
		this.detailHref = detailHref;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public int getBookprice() {
		return bookprice;
	}

	public void setBookprice(int bookprice) {
		this.bookprice = bookprice;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public String getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(String searchResult) {
		this.searchResult = searchResult;
	}

	public String getCustomStarRating() {
		return customStarRating;
	}

	public void setCustomStarRating(String customStarRating) {
		this.customStarRating = customStarRating;
	}

	public String getDetailHref() {
		return detailHref;
	}

	public void setDetailHref(String detailHref) {
		this.detailHref = detailHref;
	}

	@Override
	public String toString() {
		return "ProductVO [bookTitle=" + bookTitle + ", bookprice=" + bookprice + ", imgSrc=" + imgSrc
				+ ", searchResult=" + searchResult + ", customStarRating=" + customStarRating + ", detailHref="
				+ detailHref + "]";
	}

	

    
	
	
	
	
	
}
