package com.teamsix.model.bean;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity @Table(name = "topicMessage")
@Component
public class TopicMessageBean {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "messageId")
	private Integer messageId;
	
	@JsonBackReference // 不由這邊做對面的 JSON 序列化
	@ManyToOne
	@JoinColumn(name = "topicId")
	private TopicBean topicBeanMessage;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "memno")
	private Integer memno;
	
	@Column(name = "status")
	private String status = "on";
	
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss EEEE",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "publishTime")
	private Date publishTime;
	
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss EEEE",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "editTime")
	private Date editTime;
	
	@Column(name = "good")
	private Integer good = 0;

	@PrePersist
	public void onCreate() {
		if (publishTime == null) {
			publishTime = new Date();
		}
	}
	
	@PreUpdate
	public void onUpdate() {
			editTime = new Date();
	}
	
	@JsonManagedReference // 由這邊做 JSON 序列化
	// mappedBy <=> 對面如何找到自己的
	@OneToMany(mappedBy = "topicMessageBean", cascade = CascadeType.ALL, orphanRemoval = true )
	private List<TopicMessagePhoto> topicMessages = new ArrayList<>();

	@JsonManagedReference // 由這邊做 JSON 序列化
	// mappedBy <=> 對面如何找到自己的
	@OneToMany(mappedBy = "topicMessageBeanId", cascade = CascadeType.ALL, orphanRemoval = true )
	private List<TopicMessageLike> topicMessagelikes = new ArrayList<>();
}
