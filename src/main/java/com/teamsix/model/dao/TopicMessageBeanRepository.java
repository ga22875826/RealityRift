package com.teamsix.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamsix.model.bean.TopicMessageBean;

public interface TopicMessageBeanRepository extends JpaRepository<TopicMessageBean, Integer> {
	public List<TopicMessageBean> findTopicMessageBeanByTopicBeanMessage(Integer topicId);
}
