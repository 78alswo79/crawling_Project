package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductVO;

@Service
public class ProductService {

	private List<ProductVO> naverList = new ArrayList<>();
	private List<ProductVO> coupangList = new ArrayList<>();
	
	@Autowired
	ExecutorService executor;
	/**
	 * @author kephas_leeminjae
	 * @return ??
	 * 
	 * */
	public List<ProductVO> getNaverProductList(List<String> paramList, String productName) {
		
	    ProductVO productVo = new ProductVO();
	    
	    try {
	    	List<ProductVO> resList = new ArrayList<>();
	        // 각 파라미터에 대해 URL을 생성하고 크롤링 작업을 스레드로 제출
            String url;
            if (paramList.size() >= 2) {
                url = "https://search.shopping.naver.com/search/all?query=" + paramList.get(1) + "&prevQuery=" + paramList.get(0) + "&vertical=search";
            } else {
                url = "https://search.shopping.naver.com/search/all?where=all&frm=NVSCTAB&query=" +productName;
            }

            // 크롤링 작업을 스레드로 제출
            executor.submit(() -> {
                try {
                    Document doc = Jsoup.connect(url).get();
                    Elements el = doc.select("#container .resultSummary_result_info__Y1J4V");
                    String searchRes = el.text();

                    // 네이버 쇼핑은 너무 복잡하다.
                	Elements rootPrdItems = doc.select("div[id=container] div[id=content] div.basicList_list_basis__uNBZx > div > div");
                							
                	// ProductVO set!
                	naverList = setProductList(rootPrdItems);
                	
                } catch (Exception e) {
                    e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
                }
            });
	    } catch (Exception e) {
	        e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
	    } finally {
	        executor.shutdown(); // 스레드 풀 종료
	    }
	    return naverList;
	}
	
	
	public List<ProductVO> getCoupangProductList(String productName) {
	    ProductVO productVo = new ProductVO();
	    
	    try {
	    	List<ProductVO> resList = new ArrayList<>();
	        // 각 파라미터에 대해 URL을 생성하고 크롤링 작업을 스레드로 제출
            String url;
            url = "https://www.coupang.com/np/search?component=&q="+ productName + "&channel=user";
            
            // 크롤링 작업을 스레드로 제출
            executor.submit(() -> {
                try {
                    Document doc = Jsoup.connect(url).get();
                    // #contents .hit-count
                    String searchRes = doc.select("#contents .hit-count").text();
                    System.out.println("쿠팡의 검색 결과는?????" + searchRes);

                    Elements productListRoot = doc.select("ul#productList");
                    // 네이버 쇼핑은 너무 복잡하다.
                	Elements rootPrdItems = doc.select("div[id=container] div[id=content] div.basicList_list_basis__uNBZx > div > div");
                							
                	// ProductVO set!
                	coupangList = setProductList(rootPrdItems);
                	
                } catch (Exception e) {
                    e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
                }
            });
	    } catch (Exception e) {
	        e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
	    } finally {
	        executor.shutdown(); // 스레드 풀 종료
//	        try {
//	            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
//	                executor.shutdownNow();
//	            }
//	        } catch (InterruptedException e) {
//	            executor.shutdownNow();
//	        }
	    }
	    return coupangList;
	}

	
	
	/**
	 * <p>검색을 통한 productName을 저장한다.<br>
	 * 사이즈를 2개 유지하고, 오래된 상품은 삭제한다.</p>
	 * @author kephas_leeminjae
	 * @return Map<String, String>
	 * @param String
	 * */
	public List<String> setProductName(List<String> returnList, String productName) {
		// 공부하기 좋은. 사이즈를 2개 유지하고, 오래된 상품은 삭제한다.
		if (returnList.size() >= 2) {
			returnList.remove(0);
		}
		
		returnList.add(productName);
		
		return returnList;
	}

	private List<ProductVO> setProductList(Elements rootPrdItems/*, ProductVO productVo*/) {
		
		List<ProductVO> resList = new ArrayList<>();

		for (Element rootPrdItem : rootPrdItems) {
			// Css선택자 너무 헷갈리기는 한다. 공부하기 좋은
			String prdTitle = rootPrdItem.select("div > div > div:nth-of-type(2) > div > a").text();
			String prdPrice = rootPrdItem.select("div > div > div:nth-of-type(2) > div:nth-of-type(2) span:nth-of-type(1) > span:nth-of-type(1)").text();
			
			int price = 0;
	        
	        // prdPrice를 int로 변환하여 setPrice에 전달
	        try {
	            price = Integer.parseInt(prdPrice.replaceAll("[^0-9]", "")); // 숫자만 추출하여 변환
//	            productVo.setPrice(price);
	        } catch (NumberFormatException e) {
	            // 변환 실패 시 처리 (예: 기본값 설정, 로그 출력 등)
	            System.err.println("가격 변환 오류: " + prdPrice);
	            //productVo.setPrice(0); // 기본값 설정
	        }
			resList.add(new ProductVO(prdTitle, price));
		}
		//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>" + resList.toString());
		return resList;
	}

}
