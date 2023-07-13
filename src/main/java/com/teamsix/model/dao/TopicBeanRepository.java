package com.teamsix.model.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.teamsix.model.bean.TopicBean;
import java.util.List;


public interface TopicBeanRepository extends JpaRepository<TopicBean, Integer> {
	Page<TopicBean> findByStatus(String status, Pageable pageable);
	public List<TopicBean> findTopicBeanByMemnoAndStatus(Integer memno, String status);
	public List<TopicBean> findTopicBeanByMemno(Integer memno);
}
