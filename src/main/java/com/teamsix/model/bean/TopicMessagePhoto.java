package com.teamsix.model.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity @Table(name = "topicMessagePhoto")
public class TopicMessagePhoto {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "messagePhotoId")
	private Integer messagePhotoId;
	
	@JsonBackReference // 不由這邊做對面的 JSON 序列化
	@ManyToOne
	@JoinColumn(name = "messageId")
	private TopicMessageBean topicMessageBean;

	@Lob
	@JsonIgnore // 此屬性不做 JSON 序列化
	@Column(name="messageIdPhotoFile")
	private byte[] messageIdPhotoFile;

}
