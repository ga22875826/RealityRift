package com.teamsix.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamsix.model.bean.TopicBean;
import com.teamsix.model.bean.TopicCollection;

public interface TopicCollectionRepository extends JpaRepository<TopicCollection, Integer> {
	public TopicCollection findTopicCollectionByMemnoAndCollectionTopicBean(Integer memno, TopicBean topicBean);
	public List<TopicCollection> findByMemno(Integer memno);
}
