package com.teamsix.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.teamsix.model.bean.TopicBean;
import com.teamsix.model.bean.TopicCollection;
import com.teamsix.model.bean.TopicLikes;
import com.teamsix.model.bean.TopicMessageBean;
import com.teamsix.model.bean.TopicPhoto;
import com.teamsix.model.bean.TopicReport;

public interface ITopicBeanService {
	//文章
	public List<TopicBean> selectAllTopic();
	public TopicBean selectById(Integer topicId);
	public void insertTopic(TopicBean tBean);
	public boolean updateContentByTopicId(Integer topicId, String title, String content);
    public void deleteTopic(int topicId);
    public Page<TopicBean> findByPage(Integer pageNumber, Integer quantity);
    public TopicPhoto insertTopicPhoto(TopicPhoto tp);
    
    //文章留言
    public List<TopicMessageBean> selectAllMessage();
    public List<TopicMessageBean> selectAllMessagesByTopicId(Integer topicId);
    public void insertMessage(TopicMessageBean tMBean);
    
    //文章按讚
    public TopicLikes findByMemnoAndTopicId(Integer memno, Integer topicId);
    public void insertTopicLike(TopicLikes tLBean);
    public boolean updateLikesByTopicBean(TopicBean tBean);
    public boolean updateLikedByTopicLike(TopicLikes tLBean);
    
    //文章收藏
    public TopicCollection findCollectionByMemnoAndTopicId(Integer memno, Integer topicId);
    public void insertTopicCollection(TopicCollection tCBean);
    public boolean updateCollectedByTopicCollection(TopicCollection tCBean);
    
    //文章檢舉
    public void insertReportTopic(TopicReport tRBean);
    public List<TopicReport> selectAllTopicReport();
    public boolean updateReportSuccessStatusByTopicReportId(Integer topicReportId);
    public boolean updateReportFailStatusByTopicReportId(Integer topicReportId);
    public boolean updateTopicStatusToReportSuccess(TopicBean tBean);
    public List<TopicReport> selectAllReportSuccess();
    public List<TopicReport> selectAllReportFail();
    
    //文章修改
    public void deleteTopicPhotoByPhotoId(Integer topicPhotoId);
    
    //我的文章
    public List<TopicBean> findAllTopicsByMemno(Integer memno);
    public boolean deleteTopicByUpdateStatus(Integer topicId);
	public boolean topicSwitchStatus(Integer topicId);
    
    //我的收藏
	public List<TopicCollection> findAllTopicsCollectionByMemno(Integer memno);
	public boolean cancelCollection(Integer collectionId);


}
