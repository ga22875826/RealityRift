package com.teamsix.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamsix.model.bean.TopicMessageLike;

public interface TopicMessageLikeRepository extends JpaRepository<TopicMessageLike, Integer> {
	
}
