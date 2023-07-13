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
@Entity @Table(name = "topicCollection")
@Component
public class TopicCollection {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "collectionId")
	private Integer collectionId;
	
	@Column(name = "memno")
	private Integer memno;
	
	@JsonBackReference 
	@ManyToOne
	@JoinColumn(name = "topicId")
	private TopicBean collectionTopicBean;
	
	@Column(name = "collected")
	private Integer collected = 0;
	
	public void setCollected(Integer collected) {
        if (collected != null && (collected == 0 || collected == 1)) {
            this.collected = collected;
        } else {
            throw new IllegalArgumentException("這不是collected該有的值");
        }
    }
}
