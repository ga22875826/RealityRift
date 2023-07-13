package com.teamsix.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamsix.model.bean.TopicPhoto;

public interface TopicPhotoRepository extends JpaRepository<TopicPhoto, Integer> {
	
}
