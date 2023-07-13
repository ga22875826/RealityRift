package com.teamsix.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamsix.model.bean.TopicMessagePhoto;

public interface TopicMessagePhotoRepository extends JpaRepository<TopicMessagePhoto, Integer> {
	
}
