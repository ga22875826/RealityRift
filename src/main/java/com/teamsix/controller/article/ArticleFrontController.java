package com.teamsix.controller.article;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.teamsix.model.bean.Member;
import com.teamsix.model.bean.TopicBean;
import com.teamsix.model.bean.TopicCollection;
import com.teamsix.model.bean.TopicLikes;
import com.teamsix.model.bean.TopicMessageBean;
import com.teamsix.model.bean.TopicMessagePhoto;
import com.teamsix.model.bean.TopicPhoto;
import com.teamsix.model.bean.TopicReport;
import com.teamsix.model.dao.TopicBeanRepository;
import com.teamsix.model.dao.TopicCollectionRepository;
import com.teamsix.model.dao.TopicLikesRepository;
import com.teamsix.model.dao.TopicMessageBeanRepository;
import com.teamsix.model.dao.TopicMessagePhotoRepository;
import com.teamsix.model.dao.TopicPhotoRepository;
import com.teamsix.model.dao.TopicReportRepository;
import com.teamsix.service.ITopicBeanService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class ArticleFrontController {
	
	@Autowired
	private ITopicBeanService ITService;
	
	@Autowired
	private TopicBeanRepository TRepo;
	
	@Autowired
	private TopicBeanRepository TBRepo;
	
	@Autowired
	private TopicPhotoRepository TPRepo;
	
	@Autowired
	private TopicMessageBeanRepository TMRepo;
	
	@Autowired
	private TopicLikesRepository TLRepo;

	@Autowired
	private TopicCollectionRepository TCRepo;
	
	@Autowired
	private TopicReportRepository TRRepo;
	
	@Autowired
	private TopicMessagePhotoRepository TMPRepo;

	@GetMapping("/Article/testMain")//前台首頁
	public String main() {
		
		return "Article/ArticleMainPage";
	}
	
	@GetMapping("/Article/mainPage")//前台首頁
	public String main(@RequestParam(name = "p", defaultValue = "1")Integer pageNumber, Model model) {
		Page<TopicBean> page = ITService.findByPage(pageNumber, 8);
		
		model.addAttribute("page",page);
		
		return "Article/ArticleMain";
	}
	
	//文章
	@GetMapping("/Article/Select/{id}")
	public String selectArticleById(@PathVariable("id") Integer id, Model model, HttpSession session) {
		Member member = (Member) session.getAttribute("member");
		TopicBean thisArticle = ITService.selectById(id);
		
		if (member != null) {
			int memno = member.getMemno();
			model.addAttribute("memno", memno);

			if (thisArticle.getMemno() == member.getMemno()) {
				model.addAttribute("thisUserIsThisArticleAuthor", 1);
				Integer good = thisArticle.getGood();
				model.addAttribute("Article",thisArticle);
				model.addAttribute("ArticleLikesCount",good);
			}else {
				model.addAttribute("thisUserIsThisArticleAuthor", 0);
				Integer good = thisArticle.getGood();
				model.addAttribute("Article",thisArticle);
				model.addAttribute("ArticleLikesCount",good);
			}
			
	        // topic Like
	        TopicLikes topicLiked = ITService.findByMemnoAndTopicId(memno, id);
	        if (topicLiked != null && topicLiked.getLiked() == 1) {
	            model.addAttribute("userLikedArticle", true);
	        } else {
	            model.addAttribute("userLikedArticle", false);
	        }
	        
	        // topic collection
	        TopicCollection topicCollection = ITService.findCollectionByMemnoAndTopicId(memno, id);
	        if (topicCollection != null && topicCollection.getCollected() == 1) {
	        	model.addAttribute("userCollectedArticle", true);
			}else {
				model.addAttribute("userCollectedArticle", false);
			}
		}else {
			model.addAttribute("thisUserIsThisArticleAuthor", 0);
			Integer good = thisArticle.getGood();
	        model.addAttribute("Article", thisArticle);
	        model.addAttribute("ArticleLikesCount", good);
	        model.addAttribute("userLikedArticle", false);
	        model.addAttribute("userCollectedArticle", false);
		}
		
		return "Article/showOneArticle";
	}
	
	@ResponseBody
	@GetMapping("/Article/photoIds")
	public List<Integer> getTopicPhotoIdByTopicId(@RequestParam("topicId") Integer topicId){
		TopicBean Article = TRepo.getReferenceById(topicId);
		
		List<TopicPhoto> listTopicPhoto = Article.getTopicPhotos();
		
		List<Integer> photoIds = new LinkedList<>();
		
		for (TopicPhoto onePhoto : listTopicPhoto) {
			Integer onePhotoId = onePhoto.getTopicsPhotoId();
			photoIds.add(onePhotoId);
		}
		
		return photoIds;
	}
	
	@ResponseBody
	@GetMapping("/Article/message/photoIds")
	public List<Integer> getMessagePhotoIdByMessageId(@RequestParam("messageId") Integer messageId){
		TopicMessageBean Message = TMRepo.getReferenceById(messageId);

		List<TopicMessagePhoto> listMessagePhoto = Message.getTopicMessages();

		List<Integer> photoIds = new LinkedList<>();
		
		for (TopicMessagePhoto oneMessagePhoto : listMessagePhoto) {
			Integer oneMessagePhotoId = oneMessagePhoto.getMessagePhotoId();
			photoIds.add(oneMessagePhotoId);
		}
		
		return photoIds;
	}
	
	@GetMapping("/Article/photo")
	public ResponseEntity<byte[]> getArticleImage(@RequestParam("id") Integer photoId){
		Optional<TopicPhoto> optional = TPRepo.findById(photoId);
		
		if (optional.isPresent()) {
			TopicPhoto photo = optional.get();
			byte[] photoFile = photo.getTopicsPhotoFile();
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(photoFile);
		}
		return null;
	}
	
	@GetMapping("/Article/message/photo")
	public ResponseEntity<byte[]> getMessageImage(@RequestParam("id") Integer photoId){
		Optional<TopicMessagePhoto> optional = TMPRepo.findById(photoId);

		if(optional.isPresent()) {
			TopicMessagePhoto photo = optional.get();
			byte[] photoFile = photo.getMessageIdPhotoFile();
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(photoFile);
		}

		return null;
	}
	
	@ResponseBody
	@PostMapping("/article/likes/{id}")// 文章按讚
	public String ArticleLike(@PathVariable Integer id, HttpSession session,Model model) {
		Member member = (Member) session.getAttribute("member");
		TopicBean topic = ITService.selectById(id);
		if (member == null) {
			return "沒有登入喔";
		}
		
		TopicLikes isliked = ITService.findByMemnoAndTopicId(member.getMemno(), id);
		
		if (isliked != null) {
			if (isliked.getLiked() != 0) {
				Integer likeCound = topic.getGood();
				topic.setGood(likeCound - 1);
				ITService.updateLikesByTopicBean(topic);
				isliked.setLiked(0);
				ITService.updateLikedByTopicLike(isliked);
				return "文章退讚成功";
			}
			if(isliked.getLiked() == 0){
				Integer likeCount = topic.getGood();
				topic.setGood(likeCount + 1);
				ITService.updateLikesByTopicBean(topic);
				isliked.setLiked(1);
				ITService.updateLikedByTopicLike(isliked);
				return "文章再次按讚成功";
			}
		}
		if(isliked == null){
			Integer likeCount = topic.getGood();
			topic.setGood(likeCount + 1);
			ITService.updateLikesByTopicBean(topic);
			TopicLikes tLBean = new TopicLikes();
			tLBean.setMemno(member.getMemno());
			tLBean.setTopicBeanId(topic);
			tLBean.setLiked(1);
			ITService.insertTopicLike(tLBean);
			return "第一次文章點讚成功";
		}
		return null;
	}
	
	@ResponseBody 
	@PostMapping("/article/collection/{id}") // 文章收藏
	public String ArticleCollection(@PathVariable Integer id, HttpSession session,Model model) {
		Member member = (Member) session.getAttribute("member");
		TopicBean topic = ITService.selectById(id);
		if (member == null) {
			return "沒有登入喔";
		}
		
		TopicCollection isCollected = ITService.findCollectionByMemnoAndTopicId(member.getMemno(), id);
		if (isCollected != null) {
			if (isCollected.getCollected() !=0) {
				isCollected.setCollected(0);
				ITService.updateCollectedByTopicCollection(isCollected);
				return "取消收藏文章成功";
			}
			if (isCollected.getCollected() == 0) {
				isCollected.setCollected(1);
				ITService.updateCollectedByTopicCollection(isCollected);
				return "再次收藏文章成功";
			}
		}
		if (isCollected == null) {
			TopicCollection tCBean = new TopicCollection();
			tCBean.setMemno(member.getMemno());
			tCBean.setCollectionTopicBean(topic);
			tCBean.setCollected(1);
			ITService.insertTopicCollection(tCBean);
			return "第一次文章收藏成功";
		}
		return null;
		
	}
	
	@GetMapping("/Article/writePage")//新增文章介面
	public String writeArticle(HttpSession session) {
		return "Article/writeOneArticle";
	}
	
	@PostMapping("/Article/insertToArticle2") // 新增文章
	public String insertToArticle2(
	        @RequestParam("title") String title,
	        @RequestParam("content") String content,
	        HttpSession session) {

	    Member member = (Member) session.getAttribute("member");
	    int memno = member.getMemno();

	    
	        TopicBean tBean = new TopicBean();
	        tBean.setMemno(memno);
	        tBean.setTitle(title);
	        tBean.setContent(content);
	       

	        ITService.insertTopic(tBean);
	    

	    return "redirect:/Article/writeArticle";
	}
	
	@ResponseBody
	@PostMapping("/Article/insertToArticle1") // 新增文章
	public String insertToArticle1(
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			@RequestParam("photoFile") MultipartFile[] photoFiles,
			HttpSession session) {
		
		System.out.println(photoFiles);
		Member member = (Member) session.getAttribute("member");
		int memno = member.getMemno();
		
		try {
			TopicBean tBean = new TopicBean();
	        tBean.setMemno(memno);
	        tBean.setTitle(title);
	        tBean.setContent(content);

	        for (MultipartFile photoFile : photoFiles) {
	            TopicPhoto topicPhoto = new TopicPhoto();
	            topicPhoto.setTopicsPhotoFile(photoFile.getBytes());
	            topicPhoto.setTopicBeanPhoto(tBean);
	            tBean.getTopicPhotos().add(topicPhoto);
	        }
			
			ITService.insertTopic(tBean);
			return "新增成功";
		} catch (IOException e) {
			e.printStackTrace();
			return "新增失敗";
			
		}
		
	}
	
	@DeleteMapping("/Article/DeleteToArticle")//刪除文章(連同資料庫)
	@ResponseBody
	public String DeleteToArticle(@RequestParam("topicId") Integer topicId) {
		ITService.deleteTopic(topicId);
		
		return "刪除成功";
	}
	
	@GetMapping("/Article/GetToUpdate")//修改文章-撈取資料
	public String GetToUpdate(
			@RequestParam("topicId") String topId,
			@RequestParam("title") String title,
			@RequestParam("content") String content,
			Model model) {
		int topicId = Integer.parseInt(topId);
		model.addAttribute("topicId", topicId);
		model.addAttribute("title", title);
		model.addAttribute("content", content);
		
		return "Article/updateOneArticle";
	}
	
	@ResponseBody
	@PutMapping("/Article/UpdateToArticle")//修改文章實際操作
	public String UpdateToArticle(
			@RequestParam("topicId") String topId,
			@RequestParam("title") String title,
			@RequestParam("content") String content) {
		
		
		int topicId = Integer.parseInt(topId);
		ITService.updateContentByTopicId(topicId, title, content);
		
		return "修改成功";
	}
	
	@GetMapping("/Article/Image/{topicId}")
	public ResponseEntity<byte[]> getTopicImage(@PathVariable("topicId") Integer topicId) {
		TopicBean Topic = TBRepo.getReferenceById(topicId);
		
		List<TopicPhoto> listTopicPhoto = Topic.getTopicPhotos();
		
		List<Integer> photoIds = new LinkedList<>();
		
		for (TopicPhoto onePhoto : listTopicPhoto) {
			Integer oneId = onePhoto.getTopicsPhotoId();
			photoIds.add(oneId);
		}
		
		for (Integer photoId : photoIds) {
			Optional<TopicPhoto> optional = TPRepo.findById(photoId);
			
			if (optional.isPresent()) {
				TopicPhoto photo = optional.get();
				byte[] photoFile = photo.getTopicsPhotoFile();
				
				return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(photoFile);
			}
			
		}
		return null;
	}
	
	@GetMapping("/Article/firstImage/{topicId}")
	public ResponseEntity<byte[]> getFirstTopicImage(@PathVariable("topicId") Integer topicId) {
	    TopicBean topic = TBRepo.getReferenceById(topicId);

	    List<TopicPhoto> listTopicPhoto = topic.getTopicPhotos();

	    if (!listTopicPhoto.isEmpty()) {
	        TopicPhoto firstPhoto = listTopicPhoto.get(0);
	        byte[] photoFile = firstPhoto.getTopicsPhotoFile();

	        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(photoFile);
	    }

	    return ResponseEntity.notFound().build();
	}
	
	
	//留言
	
	@ResponseBody
	@PostMapping("/Article/message/insertMessage1")
	public String insertMessage1(@RequestParam("topicId") Integer topicId, @RequestParam("content") String content, HttpSession session) {
		Member member = (Member) session.getAttribute("member");
	    int memno = member.getMemno();
	    
	    TopicMessageBean tMBean = new TopicMessageBean();
	    
	    TopicBean topicBean = TBRepo.findById(topicId).orElse(null);
	    tMBean.setTopicBeanMessage(topicBean);
	    tMBean.setMemno(memno);
	    tMBean.setContent(content);
	    
	    ITService.insertMessage(tMBean);
	    
		return "留言成功";
	}
	
	@ResponseBody
	@PostMapping("/Article/message/insertMessage2")
	public String insertMessage2(
			@RequestParam("topicId") Integer topicId,
			@RequestParam("content") String content,
			@RequestParam("photoFile") MultipartFile[] photoFiles ,
			HttpSession session) {
//		) {
		Member member = (Member) session.getAttribute("member");
	    int memno = member.getMemno();
//	    int memno = 1001;
	    
	    TopicMessageBean tMBean = new TopicMessageBean();
	    
	    TopicBean topicBean = TBRepo.findById(topicId).orElse(null);
	    tMBean.setTopicBeanMessage(topicBean);
	    tMBean.setMemno(memno);
	    tMBean.setContent(content);
	    
		try {
			
			for (MultipartFile photoFile : photoFiles) {
				TopicMessagePhoto topicMessagePhoto = new TopicMessagePhoto();
				topicMessagePhoto.setMessageIdPhotoFile(photoFile.getBytes());
				topicMessagePhoto.setTopicMessageBean(tMBean);
				tMBean.getTopicMessages().add(topicMessagePhoto);
			}
			
			ITService.insertMessage(tMBean);
			return "新增成功";
		} catch (IOException e) {
			e.printStackTrace();
			return "新增失敗";
		}
		
	}
	
	@ResponseBody
	@GetMapping("/Article/selectArticleAllMessage")
	public List<TopicMessageBean> selectArticleAllMessages(@RequestParam("topicId") Integer topicId) {
		TopicBean Article = TRepo.getReferenceById(topicId);
		List<TopicMessageBean> listTopicMessage = Article.getTopicMessages();
	
		return listTopicMessage;
	}
	
	//檢舉
	@ResponseBody
	@PostMapping("/Article/reportTopic")
	public String reportTopic(
			@RequestParam("topicId") Integer topicId,
			@RequestParam("reportReason") String reportReason,
			@RequestParam("reportContent") String reportContent,
			HttpSession session) {
		
		Member member = (Member) session.getAttribute("member");
	    int memno = member.getMemno();
	    
	    TopicReport tRBean = new TopicReport();
	    tRBean.setMemno(memno);
	    TopicBean topicBean = TBRepo.findById(topicId).orElse(null);
	    tRBean.setTopicBeanReportId(topicBean);
	    tRBean.setReportClassification(reportReason);
	    tRBean.setReportContnet(reportContent);
	    
	    ITService.insertReportTopic(tRBean);
		
		return "檢舉成功";
	}
	
	//文章修改
	@ResponseBody
	@DeleteMapping("/Article/deletePhoto")
	public String deleteArticlePhoto(@RequestParam("topicPhotoId") Integer topicPhotoId) {
		
		ITService.deleteTopicPhotoByPhotoId(topicPhotoId);
		
		return "刪除成功";
	}
	
	@ResponseBody
	@PutMapping("/Article/updateArticleAndImage")
	public String updateArticleAndImage(
	        @RequestParam("topicId") Integer topicId,
	        @RequestParam("title") String title,
	        @RequestParam("content") String content,
	        @RequestParam(value = "photoFile", required = false) MultipartFile[] photoFiles,
	        HttpSession session) {
	    
	    TopicBean tBean = new TopicBean();
	    tBean.setTopicId(topicId);
	    tBean.setTitle(title);
	    tBean.setContent(content);

	    Optional<TopicBean> optional = TRepo.findById(topicId);
	    if (optional.isPresent()) {
	        TopicBean topicBean = optional.get();
	        if (photoFiles != null) {
	            try {
	                for (MultipartFile photoFile : photoFiles) {
	                    TopicPhoto topicPhoto = new TopicPhoto();
	                    topicPhoto.setTopicsPhotoFile(photoFile.getBytes());
	                    topicPhoto.setTopicBeanPhoto(topicBean);
	                    topicBean.getTopicPhotos().add(topicPhoto);
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }

	        ITService.updateContentByTopicId(topicId, title, content);

	        return "修改成功";
	    } else {
	        return "找不到对应的TopicBean";
	    }
	}
	
	//我的文章
	@GetMapping("/Article/myArticle")
	public String myArticle(Model model, HttpSession session) {
		Member member = (Member) session.getAttribute("member");
		if (member != null) {
			int memno = member.getMemno();
			List<TopicBean> myArticleList = ITService.findAllTopicsByMemno(memno);
			model.addAttribute("myArticles",myArticleList);
		}
		return "Article/myArticle";
	}
	
	@ResponseBody
	@PutMapping("/Article/deleteMyArticle")
	public String deleteMyArticle(@RequestParam("topicId") Integer topicId) {
		ITService.deleteTopicByUpdateStatus(topicId);
		return "刪除成功";
	}

	@ResponseBody
	@PutMapping("/Article/myArticleSwitchStatus")
	public String SwitchStatus(@RequestParam("topicId") Integer topicId) {
		ITService.topicSwitchStatus(topicId);
		return "狀態修改成功";
	}

	//我的收藏
	@GetMapping("/Article/myCollection")
	public String myCollection(Model model, HttpSession session) {
		Member member = (Member) session.getAttribute("member");
		if (member != null) {
			int memno = member.getMemno();
			List<TopicCollection> myCollectionList = ITService.findAllTopicsCollectionByMemno(memno);
			model.addAttribute("myCollectionList",myCollectionList);
		}
		return "Article/myCollection";
	}
	
	@ResponseBody
	@PutMapping("/Article/cancelCollection")
	public String cancelCollectionByCollectionId(@RequestParam("collectionId") Integer collectionId) {
		ITService.cancelCollection(collectionId);
		return "取消收藏成功";
	}
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

