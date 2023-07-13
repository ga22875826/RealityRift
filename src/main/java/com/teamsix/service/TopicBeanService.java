package com.teamsix.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.teamsix.model.bean.Member;
import com.teamsix.model.bean.TopicBean;
import com.teamsix.model.bean.TopicCollection;
import com.teamsix.model.bean.TopicLikes;
import com.teamsix.model.bean.TopicMessageBean;
import com.teamsix.model.bean.TopicPhoto;
import com.teamsix.model.bean.TopicReport;
import com.teamsix.model.dao.MemberRepository;
import com.teamsix.model.dao.TopicBeanRepository;
import com.teamsix.model.dao.TopicCollectionRepository;
import com.teamsix.model.dao.TopicLikesRepository;
import com.teamsix.model.dao.TopicMessageBeanRepository;
import com.teamsix.model.dao.TopicMessageLikeRepository;
import com.teamsix.model.dao.TopicPhotoRepository;
import com.teamsix.model.dao.TopicReportRepository;

import jakarta.transaction.Transactional;

@Service
public class TopicBeanService implements ITopicBeanService {

	@Autowired
	private TopicBeanRepository tRepo;
	
	@Autowired
	private TopicPhotoRepository tPRepo;
	
	@Autowired
	private TopicMessageBeanRepository tMRepo;
	
	@Autowired
	private TopicLikesRepository tLRepo;
	
	@Autowired
	private TopicMessageLikeRepository tMLRepo;
	
	@Autowired
	private MemberRepository mRepo;
	
	@Autowired
	private TopicCollectionRepository tCRepo;
	
	@Autowired
	private TopicReportRepository tRRepo;
	
	
	@Override
	public List<TopicBean> selectAllTopic() {
		return tRepo.findAll();
	}

	@Override
	public TopicBean selectById(Integer topicId) {
		Optional<TopicBean> optiomal = tRepo.findById(topicId);
		if (optiomal.isPresent()) {
			return optiomal.get();
		} 
		return null;
	}
	
	@Override
	public void insertTopic(TopicBean tBean) {
		tRepo.save(tBean);
	}

	@Override
	@Transactional
	public boolean updateContentByTopicId(Integer topicId, String title, String content) {
		Optional<TopicBean> optional = tRepo.findById(topicId);
		
//		LocalDateTime gdate = LocalDateTime.now();
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		String eTime = gdate.format(formatter);
		if (optional.isPresent()) {
			TopicBean article = optional.get();
			article.setTitle(title);
			article.setContent(content);
//			article.setEditTime(eTime);
			return true;
		}
		return false;
	}
	
	@Override
	public void deleteTopic(int topicId) {
		tRepo.deleteById(topicId);
	}

	@Override
	public Page<TopicBean> findByPage(Integer pageNumber, Integer quantity) {
		Pageable pgb = PageRequest.of(pageNumber-1, quantity, Sort.Direction.DESC, "topicId");
		
		Page<TopicBean> page = tRepo.findByStatus("on", pgb);

		return page;
	}

	@Override
	public TopicPhoto insertTopicPhoto(TopicPhoto tp) {
		return tPRepo.save(tp);
	}


	//文章留言
	
	@Override
	public List<TopicMessageBean> selectAllMessage() {
		return tMRepo.findAll();
	}
	
	@Override
	public List<TopicMessageBean> selectAllMessagesByTopicId(Integer topicId) {
		Optional<TopicBean> optional = tRepo.findById(topicId);
		if (optional.isPresent()) {
			List<TopicMessageBean> messages = tMRepo.findTopicMessageBeanByTopicBeanMessage(topicId);
			return messages;
		}
		return null;
	}

	@Override
	public void insertMessage(TopicMessageBean tMBean) {
		tMRepo.save(tMBean);
	}

	
	//文章按讚
	
	@Override
	public TopicLikes findByMemnoAndTopicId(Integer memno, Integer topicId) {
		Optional<TopicBean> optionalTopicId = tRepo.findById(topicId);
		Optional<Member> optionalMemno = mRepo.findById(memno);
		if (optionalMemno.isPresent() && optionalTopicId.isPresent()) {
			TopicBean topicBean = tRepo.findById(topicId).orElse(null);
			TopicLikes topiclike = tLRepo.findTopicLikesByMemnoAndTopicBeanId(memno, topicBean);
			return topiclike;
		}
		return null;
	}

	@Override
	public void insertTopicLike(TopicLikes tLBean) {
		tLRepo.save(tLBean);
	}

	@Override
	@Transactional
	public boolean updateLikesByTopicBean(TopicBean tBean) {
		Optional<TopicBean> optional = tRepo.findById(tBean.getTopicId());
		
		if (optional.isPresent()) {
			TopicBean article = optional.get();
			Date editTime = article.getEditTime();
			
			article.setGood(tBean.getGood());
			article.setEditTime(editTime);

			return true;
		}
		
		return false;
	}

	@Override
	@Transactional
	public boolean updateLikedByTopicLike(TopicLikes tLBean) {
		Optional<TopicLikes> optional = tLRepo.findById(tLBean.getTopicLikeId());
		
		if (optional.isPresent()) {
			TopicLikes topicLike = optional.get();
			topicLike.setLiked(tLBean.getLiked());
			
			return true;
		}
		
		return false;
	}

	//文章收藏
	@Override
	public TopicCollection findCollectionByMemnoAndTopicId(Integer memno, Integer topicId) {
		Optional<TopicBean> optionalTopicId = tRepo.findById(topicId);
		Optional<Member> optionalMemno = mRepo.findById(memno);
		if (optionalMemno.isPresent() && optionalTopicId.isPresent()) {
			TopicBean topicBean = tRepo.findById(topicId).orElse(null);
			TopicCollection topicCollection = tCRepo.findTopicCollectionByMemnoAndCollectionTopicBean(memno, topicBean);
			return topicCollection;
		}
		return null;
	}

	@Override
	public void insertTopicCollection(TopicCollection tCBean) {
		tCRepo.save(tCBean);
	}

	@Transactional
	@Override
	public boolean updateCollectedByTopicCollection(TopicCollection tCBean) {
		Optional<TopicCollection> optional = tCRepo.findById(tCBean.getCollectionId());
		
		if (optional.isPresent()) {
			TopicCollection topicCollection = optional.get();
			topicCollection.setCollected(tCBean.getCollected());
			return true;
		}
		return false;
	}

	//文章檢舉
	@Override
	public void insertReportTopic(TopicReport tRBean) {
		tRRepo.save(tRBean);
	}

	@Override
	public List<TopicReport> selectAllTopicReport() {
		return tRRepo.findAll();
	}

	@Override
	@Transactional
	public boolean updateReportSuccessStatusByTopicReportId(Integer topicReportId) {
		Optional<TopicReport> optional = tRRepo.findById(topicReportId);
		
		if (optional.isPresent()) {
			TopicReport topicReport = optional.get();
			topicReport.setReportStatus(0);
			
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean updateReportFailStatusByTopicReportId(Integer topicReportId) {
		Optional<TopicReport> optional = tRRepo.findById(topicReportId);
		
		if (optional.isPresent()) {
			TopicReport topicReport = optional.get();
			topicReport.setReportStatus(2);
			
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean updateTopicStatusToReportSuccess(TopicBean tBean) {
		Optional<TopicBean> optional = tRepo.findById(tBean.getTopicId());
		
		if (optional.isPresent()) {
			TopicBean topicBean = optional.get();
			
			topicBean.setStatus("off");
			
			return true;
		}
		return false;
	}

	@Override
	public List<TopicReport> selectAllReportSuccess() {
		return tRRepo.findByReportStatus(0);
	}

	@Override
	public List<TopicReport> selectAllReportFail() {
		return tRRepo.findByReportStatus(2);
	}

	//文章修改
	@Override
	public void deleteTopicPhotoByPhotoId(Integer topicPhotoId) {
		tPRepo.deleteById(topicPhotoId);
	}

	//我的文章
	@Override
	public List<TopicBean> findAllTopicsByMemno(Integer memno) {
		return tRepo.findTopicBeanByMemno(memno);
	}

	@Override
	@Transactional
	public boolean deleteTopicByUpdateStatus(Integer topicId) {
		Optional<TopicBean> optional = tRepo.findById(topicId);
		
		if (optional.isPresent()) {
			TopicBean topicBean = optional.get();
			topicBean.setStatus("delete");
			return true;
		}
		
		return false;
	}

	@Override
	@Transactional
	public boolean topicSwitchStatus(Integer topicId) {
		Optional<TopicBean> optional = tRepo.findById(topicId);
		
		if (optional.isPresent()) {
			TopicBean topicBean = optional.get();
			String status = topicBean.getStatus();
			if (status.equals("on")) {
				topicBean.setStatus("off");
			}
			if (status.equals("off")) {
				topicBean.setStatus("on");
			}
			return true;
		}
		
		return false;
	}

	//我的收藏
	@Override
	public List<TopicCollection> findAllTopicsCollectionByMemno(Integer memno) {
		return tCRepo.findByMemno(memno);
	}

	@Override
	@Transactional
	public boolean cancelCollection(Integer collectionId) {
		Optional<TopicCollection> optional = tCRepo.findById(collectionId);
		
		if (optional.isPresent()) {
			TopicCollection topicCollection = optional.get();
			topicCollection.setCollected(0);
			return true;
		}
		return false;
	}
	
	

	

	
	
	
	
	
	
	
	
	
	
	
	
}
