package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.ProductVO;
import com.example.demo.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MainController {

@Autowired
ProductService productService;

	List<String> setList = new ArrayList();
	
	@GetMapping("/index.do")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		
		return mav;
	}
	
	@GetMapping("/compare.do")
	public ModelAndView compare(HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		mav.setViewName("index");
		String productName = request.getParameter("productName");
		if (request.getParameter("productName").isBlank() || request.getParameter("productName") == null) throw new Exception("parameter is NULL!!!!");
		
		List<String> resList = productService.setProductName(setList, productName);
		List<ProductVO> naverPrdList = productService.getNaverProductList(resList, productName);
		List<ProductVO> coupangPrdList = productService.getCoupangProductList(productName);
		
		mav.addObject("naverProductList", naverPrdList);
		// 쿠팡 productList set해주기
		return mav;
	}
}
