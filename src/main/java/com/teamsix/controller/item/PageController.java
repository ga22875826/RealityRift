package com.teamsix.controller.item;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
	  @GetMapping("/managePage/shoppingCart.do")
	  public String shoppingCartPage() {
		  
		  return "/Item/shoppingCart";
	  }
	
	

}
