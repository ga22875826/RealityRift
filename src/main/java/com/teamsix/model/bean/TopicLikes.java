package com.teamsix.model.bean;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity @Table(name = "topicLike")
@Component
public class TopicLikes {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "topicLikeId")
	private Integer topicLikeId;
	
	@Column(name = "memno")
	private Integer memno;
	
	@JsonBackReference 
	@ManyToOne
	@JoinColumn(name = "topicId")
	private TopicBean topicBeanId;
	
	@Column(name = "liked")
	private Integer liked = 0;
	
	public void setLiked(Integer liked) {
        if (liked != null && (liked == 0 || liked == 1)) {
            this.liked = liked;
        } else {
            throw new IllegalArgumentException("這不是liked該有的值");
        }
    }
}
