package com.example.demo.controller;

import org.apache.catalina.connector.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainCotroller {

	@GetMapping("/crawling.do")
	public String crawling() {
		StringBuilder sb = new StringBuilder();
		try {
			// 클로링할 웹 페이지 URL
			String url = "https://ko.wikipedia.org/wiki/%ED%8A%B8%EB%9D%BC%ED%8C%94%EA%B0%80_%EB%A1%9C";
			Document document = Jsoup.connect(url).get(); 
			
			// 특정 요소를 선택한다
			for(Element el : document.select("p")) {
				sb.append(el.text()).append("\n");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return sb.toString();
		
	}
}
