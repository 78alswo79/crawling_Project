package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture; // CompletableFuture 추가
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import com.example.demo.dto.ProductVO;

@Service
@EnableAsync
public class ProductService {

    private List<ProductVO> bookList1 = Collections.synchronizedList(new ArrayList<>()); 	// 스레드 안전한 리스트
    private List<ProductVO> bookList2 = Collections.synchronizedList(new ArrayList<>());

    public ModelAndView comparePrices(List<String> paramList, String productName) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");

        // Naver 제품 목록을 가져오는 작업 제출
        CompletableFuture<List<ProductVO>> bookFuture1 = CompletableFuture.supplyAsync(() -> getBookList1(paramList, productName));
        
        // Coupang 제품 목록을 가져오는 작업 제출
        CompletableFuture<List<ProductVO>> bookFuture2 = CompletableFuture.supplyAsync(() -> getBookList2(productName));
        
        try {
            // 결과를 가져옴
            List<ProductVO> booksProducts1 = bookFuture1.join(); // join()은 블로킹 호출이지만, CompletableFuture를 사용하여 비동기적으로 처리
            List<ProductVO> booksProducts2 = bookFuture2.join();

            // 결과를 ModelAndView에 추가
            mav.addObject("booksProducts1", booksProducts1);
            mav.addObject("booksProducts2", booksProducts2);
            
//            System.out.println("booksProducts1>>>>>" + booksProducts1.toString());
//            System.out.println("booksProducts2>>>>>" + booksProducts2.toString());
            
        } catch (Exception e) {
            e.printStackTrace(); // 예외 처리
            mav.addObject("error", "상품 정보를 가져오는 데 실패했습니다.");
        } 
        return mav;
    }

    public List<ProductVO> getBookList1(List<String> paramList, String productName) {
        List<ProductVO> resList = new ArrayList<>();
        String url;
        url = "https://books.toscrape.com/catalogue/category/books/horror_31/index.html";

        try {
        	Document doc = Jsoup.connect(url).timeout(10000).get(); // 타임아웃 설정
        	bookList1 = setBooksList1(doc);
            
        } catch (Exception e) {
            e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
        }
        return bookList1;
    }

    public List<ProductVO> getBookList2(String productName) {
        List<ProductVO> resList = new ArrayList<>();
        String url = "https://www.goodreads.com/search?utf8=%E2%9C%93&q=Horror&search_type=books";
        
        try {
            Document doc = Jsoup.connect(url).get();
            bookList2 = setBooksList2(doc);
            
        } catch (Exception e) {
            e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
        }

        return bookList2;
    }

    public List<String> setProductName(List<String> returnList, String productName) {
        if (returnList.size() >= 2) {
            returnList.remove(0);
        }
        
        returnList.add(productName);
        
        return returnList;
    }

    private List<ProductVO> setBooksList1(Document doc) {
        List<ProductVO> resList = new ArrayList<>();

        String searchResult = doc.select(".col-sm-8.col-md-9 > div").text();

        for (Element liRoot : doc.select(".col-sm-8.col-md-9 ol.row li")) {
        	String detailHref = liRoot.select("div a").attr("href");
        	String imgSrc = liRoot.select("div a img").attr("src");
        	String rowStarRating = liRoot.select(".star-rating").attr("class");
        	String bookPrice = liRoot.select("div:nth-of-type(2) .price_color").text();
        	String bookTitle = liRoot.select("h3").text();
        	
        	String customStarRating = "";
        	// bookPrice 가공. 숫자가 아니면 ""처리한다.
        	int customBookPrice = Integer.parseInt(bookPrice.replaceAll("[^0-9]", ""));
        	// rowStartRating 가공
        	if (rowStarRating.contains("One")) customStarRating = "1점";
        	if (rowStarRating.contains("Two")) customStarRating = "2점";
        	if (rowStarRating.contains("Three")) customStarRating = "3점";
        	if (rowStarRating.contains("Four")) customStarRating = "4점";
        	if (rowStarRating.contains("Five")) customStarRating = "5점";
        	
        	resList.add(new ProductVO(bookTitle, customBookPrice, imgSrc, searchResult, customStarRating, detailHref));
        }
        return resList;
    }
    
    private List<ProductVO> setBooksList2(Document doc) {
        List<ProductVO> resList = new ArrayList<>();

        for(Element trRoot : doc.select("div.leftContainer .tableList tr")) {
        	
        	String detailHref = trRoot.select("a").attr("href");
        	String imgSrc = trRoot.select("a img").attr("src");
        	String rowStarRating = trRoot.select("td:nth-of-type(2) div > span > span").text(); // 가공 필요
        	String bookTitle = trRoot.select("td:nth-of-type(2) > a > span").text();
        	
        	String customStarRating = "";
        	String strArr[] = rowStarRating.split("-");
        	customStarRating = strArr[0].split(" ")[0];
        	
        	resList.add(new ProductVO(bookTitle, 0, imgSrc, bookTitle, customStarRating, detailHref));
        }

        
        return resList;
    }
    
    
}



