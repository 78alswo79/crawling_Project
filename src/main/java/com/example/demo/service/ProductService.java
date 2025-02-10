package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductVO;

@Service
public class ProductService {

	/**
	 * @author kephas_leeminjae
	 * @return ??
	 * 
	 * */
	// container   		root엘리먼트
	// resultSummary_result_info__Y1J4V		검색결과 el
	// basicList_list_basis__uNBZx			상품 결과 el
	// adProduct_item__1zC9h
//	public List<ProductVO> getCompareProduct(List<String> paramList, String productName) {
//		List<ProductVO> list = new ArrayList<>();
//		
//		try {
//			String url = "";
//			if (paramList.size() >= 2) {
//				url = "https://search.shopping.naver.com/search/all?query=" + paramList.get(1) + "&prevQuery=" + paramList.get(0) + "&vertical=search";
//			} else { url = "https://search.shopping.naver.com/search/all?where=all&frm=NVSCTAB&query=" + paramList.get(0); }
//			
//			Document doc1 = Jsoup.connect(url).get();
//			
//			for (Element el : doc1.select("#container")) {
//				String resultProduct = el.select(".resultSummary_result_info__Y1J4V").text();
//				System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + resultProduct);
//				for (Element divTag : el.select(".adProduct_item__1zC9h")) {
//					System.out.println(divTag.select("a < img"));
//					
//				}
//				
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return list;
//	}
	
	
	
	public List<ProductVO> getCompareProduct(List<String> paramList, String productName) {
	    List<ProductVO> list = new ArrayList<>();
	    ExecutorService executor = Executors.newFixedThreadPool(4);
	    
	    try {
	        // 각 파라미터에 대해 URL을 생성하고 크롤링 작업을 스레드로 제출
//	        for (int i = 0; i < paramList.size(); i++) {
	            String url;
	            if (paramList.size() >= 2) {
	                url = "https://search.shopping.naver.com/search/all?query=" + paramList.get(1) + "&prevQuery=" + paramList.get(0) + "&vertical=search";
	            } else {
	                url = "https://search.shopping.naver.com/search/all?where=all&frm=NVSCTAB&query=" +productName;
	            }
	            
	            executor.submit(() -> {
	                try {
	                    Document doc = Jsoup.connect(url).get();
	                    
	                    for (Element el : doc.select("#container")) {
	                        String resultProduct = el.select(".resultSummary_result_info__Y1J4V").text();
	                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>" + resultProduct);
	                        
	                        for (Element divTag : el.select(".adProduct_item__1zC9h")) {
	                            System.out.println(divTag.select("a < img"));
	                        }
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
	                }
	            });
//	        }
	    } catch (Exception e) {
	        e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
	    } finally {
	        executor.shutdown(); // 스레드 풀 종료
	    }
	    
	    return list;
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

}
