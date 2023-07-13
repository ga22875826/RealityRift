package com.teamsix.controller.item;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.teamsix.model.bean.Member;

import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {

	@GetMapping("/db/managePage/categoryManage")
	public String manageCategoryPage() {

		return "/Item/categoryManage";
	}

	@GetMapping("/managePage/ecomPage.do")
	public String ecomPage() {

		return "/Item/ecompage";
	}
	@GetMapping("/db/managePage/analysis.do")
	public String analysisPage() {
		
		return "/Item/dataAnalysis";
	}
	@GetMapping("/db/managePage/orderAdmin.do")
	public String orderAdmin() {
		
		return "/Item/orderAdmin";
	}
	@GetMapping("/db/managePage/saleManage.do")
	public String saleAdmin() {
		
		return "/Item/saleManage";
	}

	@GetMapping("/managePage/orderManager.do")
	public String orderPage() {

		return "/Item/orderManager";
	}
	@GetMapping("/managePage/paySucceed.do")
	public String paySucceed() {
		
		return "/Item//paySucceed";
	}
	
	

	@GetMapping("/jumppageCart")
	public String JumpPage() {
		return "/Item/JumpPageCart";
	}

	@GetMapping("/managePage/shoppingCart.do")
	public String showShoppingCart() {
		return "/Item/shoppingCart";
	}
	@GetMapping("/managePage/ecpay.do")
	public String ecPay() {
		return "/Item/ecpay";
	}

	@GetMapping("/api/isLoggedIn")
	public ResponseEntity<Map<String, Object>> isLoggedIn(HttpSession session) {
		Member member = (Member) session.getAttribute("member");
		Map<String, Object> response = new HashMap<>();
		if (member == null) {
			response.put("redirect", "/rr/jumppageCart");
		} else {
			response.put("redirect", "/" + "rr/managePage/shoppingCart.do");
		}
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/api/isLoggedInOrder")
	public ResponseEntity<Map<String, Object>> isLoggedInOrder(HttpSession session) {
		Member member = (Member) session.getAttribute("member");
		Map<String, Object> response = new HashMap<>();
		if (member == null) {
			response.put("redirect", "/rr/jumppageCart");
		} else {
			response.put("redirect", "/" + "rr/managePage/orderManager.do");
		}
		return ResponseEntity.ok(response);
	}
	

//	  @GetMapping("/shoppingCart")
//	  public String showShoppingCart(HttpSession session) {
//	      Member member = (Member) session.getAttribute("member");
//	      if (member == null) {
//	          return "redirect:/login";
//	      }
//	     return "redirect:/Item/shoppingCart";
//	  }

}
