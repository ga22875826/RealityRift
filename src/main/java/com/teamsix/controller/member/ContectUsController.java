package com.teamsix.controller.member;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamsix.model.bean.ContectUs;
import com.teamsix.model.bean.ContectUsDTO;
import com.teamsix.model.bean.ContectResponse;
import com.teamsix.service.ContectUsService;
import com.teamsix.service.GmailService;

@Controller
public class ContectUsController {
	
	@Autowired
	private ContectUsService cus;
	
	@Autowired
	private GmailService gs;
	
	@GetMapping("/contectus")
	public String ContectUsPage() {
		return "member/ContectUs";
	}
	
	@GetMapping("/db/contectus")
	public String ContectUsDashboardPage(Model m) {
		List<ContectUs> contectUsList = cus.findAll();
		m.addAttribute("contectUsList",contectUsList);
		return "member/ContectUsDashboard";
	}
	
	@GetMapping("/db/contectusresponsed")
	public String ContectUsResponsedDashboardPage(Model m) {
		List<ContectUs> contectUsList = cus.findAll();
		m.addAttribute("contectUsList",contectUsList);
		return "member/ContectUsResponsedDashboard";
	}
	
	@GetMapping("/api/findcontectusbyid")
	@ResponseBody
	public ContectUs findContectUsById(@RequestParam int contectid) {
		return cus.findById(contectid);
	}
	
	@PostMapping("/api/insertcontectus")
	@ResponseBody
	public ContectUs insertContectUs(@ModelAttribute ContectUsDTO contectUsDto) {
		ContectUs contectUs = new ContectUs(contectUsDto.getName(), contectUsDto.getPhone(), contectUsDto.getEmail(), contectUsDto.getTopic(), contectUsDto.getTheme(), contectUsDto.getContent());
		return cus.insertContectUs(contectUs);
	}
	
	@PutMapping("/api/updatestatusto1")
	@ResponseBody
	public ContectUs updateStatusTo1(@RequestParam int contectid) {
		return cus.updateStatusTo1(contectid);
	}
	
	@PutMapping("/api/updatestatusto2")
	@ResponseBody
	public ContectUs updateStatusTo2(@RequestParam int contectid) {
		return cus.updateStatusTo2(contectid);
	}
	
	@PutMapping("/api/contectresponse")
	@ResponseBody
	public Boolean ContectResponse(@RequestParam int contectid,@RequestParam String responsecontent) throws AddressException, GeneralSecurityException, IOException, MessagingException {
		ContectUs contectUs = cus.findById(contectid);
		
		ContectResponse contectResponse = new ContectResponse();
		contectResponse.setResponsecontent(responsecontent);
		cus.updateContectResponse(contectid, contectResponse);
		
		sendResponse(contectUs, responsecontent);
		cus.updateStatusTo2(contectid);
		return true;
	}
	
	
	
	private void sendResponse(ContectUs contectUs,String responseContent) throws AddressException, GeneralSecurityException, IOException, MessagingException {
		String subject = "Reality Rift 您的提問已回覆(單號:" +contectUs.getContectid()+")";
		String email = contectUs.getEmail();
		String content ="Dear "+contectUs.getName()+" 您好!!"
						+"\n針對以下您的問題/意見"
						+"\n============================================"
						+"\n主旨：\n"+contectUs.getTopic()
						+"\n內容：\n"+contectUs.getContent()
						+"\n============================================"
						+"\n我們的回覆如下：\n"
						+responseContent;
		gs.sendMail(subject, content, email);
	}
}
