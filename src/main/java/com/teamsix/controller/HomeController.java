package com.teamsix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;


@Controller
public class HomeController {

	@GetMapping("/")
	public String Home() {
		return "main";
	}
	

	
	@GetMapping("/jumppage")
	public String JumpPage() {
		return "JumpPage";
	}
	
	@GetMapping("/test")
	public String Test() {
		return "test";
	}
}
