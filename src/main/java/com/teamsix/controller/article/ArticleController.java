package com.teamsix.controller.article;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamsix.model.bean.Member;
import com.teamsix.model.bean.TopicBean;
import com.teamsix.model.bean.TopicReport;
import com.teamsix.model.dao.TopicReportRepository;
import com.teamsix.service.ITopicBeanService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ArticleController {
	
	@Autowired
	private ITopicBeanService ITService;

	@Autowired
	private TopicReportRepository TRRope;
	
	//討論區後臺首頁----------------------------------------------
	@GetMapping("/db/Article/ManagePage")
	public String selectAllArticle(Model model) {
		List<TopicBean> articleList = ITService.selectAllTopic();
		model.addAttribute("Articles", articleList);
		
		return "Article/ArticleManage";
	}
	//------------------------------------------------------------
	
	//新增文章----------------------------------------------------
	@GetMapping("/Article/writeArticle")//新增文章介面
	public String writeArticle(HttpSession session) {
		return "Article/ArticleInsert";
	}
	
	@PostMapping("/Article/insertArticle")
	public String insertArticle(
			@RequestParam("title") String title, 
			@RequestParam("content") String content,
			HttpSession session) {
		
		Member member = (Member) session.getAttribute("member");
	    int memno = member.getMemno();
		
//		int memno = 1001;
		
		TopicBean tBean = new TopicBean();
		tBean.setMemno(memno);
		tBean.setTitle(title);
		tBean.setContent(content);
		
		ITService.insertTopic(tBean);
		return "redirect:/Article/writeArticle";
	}
	//------------------------------------------------------------
	
	//討論區文章修改-----------------------------------------------
	@GetMapping("/Article/GetForUpdate")
	public String GetForUpdate(
			@RequestParam("topicId") String topId,
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			Model model) {
		int topicId = Integer.parseInt(topId);
		
		model.addAttribute("topicId", topicId);
		model.addAttribute("title", title);
		model.addAttribute("content", content);
		
		return "Article/ArticleUpdate";
	}
	
//	@PutMapping("/Article/UpdateArticle")
	@ResponseBody
	@PutMapping("/Article/UpdateArticle")
	public String UpdateArticle(
			@RequestParam("topicId") String topId,
			@RequestParam("title") String title,
			@RequestParam("content") String content) {
		
		
		int topicId = Integer.parseInt(topId);
		ITService.updateContentByTopicId(topicId, title, content);
		
		return "修改成功";
	}
	//------------------------------------------------------------
	
	//討論區文章刪除-----------------------------------------------
	@DeleteMapping("/Article/DeleteArticle")
	public String DeleteArticle(@RequestParam("topicId") Integer topicId) {
		ITService.deleteTopic(topicId);
		
		return "redirect:/Article/ArticleManage";
	}
	
	//------------------------------------------------------------
	
	//後台檢舉管理-------------------------------------------------
	//檢舉總覽
	@GetMapping("/Article/manageReport")
	public String SelectAllReportArticle(Model model) {
		List<TopicReport> topicReportList = ITService.selectAllTopicReport();
		model.addAttribute("ArticleReports", topicReportList);
		return "Article/ArticleReportManage";
	}
	
	//文章檢舉成功
	@ResponseBody
	@PutMapping("/Article/manageReport/reportSuccess")
	public String reportSuccess(@RequestParam("topicReportId") Integer topicReportId) {
		ITService.updateReportSuccessStatusByTopicReportId(topicReportId);
		Optional<TopicReport> optional = TRRope.findById(topicReportId);
		
		TopicReport topicReport = optional.get();
		TopicBean tBean = topicReport.getTopicBeanReportId();
		
		ITService.updateTopicStatusToReportSuccess(tBean);
		
		return "檢舉成功";
	}
	
	//文章檢舉失敗
	@ResponseBody
	@PutMapping("/Article/manageReport/reportFail")
	public String reportFail(@RequestParam("topicReportId") Integer topicReportId) {
		ITService.updateReportFailStatusByTopicReportId(topicReportId);
		
		return "檢舉失敗";
	}
	
	
	
	
	
}
