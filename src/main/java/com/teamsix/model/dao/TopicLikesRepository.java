package com.teamsix.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamsix.model.bean.TopicBean;
import com.teamsix.model.bean.TopicLikes;

public interface TopicLikesRepository extends JpaRepository<TopicLikes, Integer> {
	public TopicLikes findTopicLikesByMemnoAndTopicBeanId(Integer memno, TopicBean topicBean);
}
