package com.teamsix.model.bean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity @Table(name = "topicReport")
@Component
public class TopicReport {
	 
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "topicReportId")
	private Integer topicReportId;
	
	@Column(name = "memno")
	private Integer memno;
	
	@JsonBackReference 
	@ManyToOne
	@JoinColumn(name = "topicId")
	private TopicBean topicBeanReportId;
	
	@Column(name = "reportClassification")
	private String reportClassification;
	
	@Column(name = "reportContnet")
	private String reportContnet;
	
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss EEEE",timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "reportTime")
	private Date reportTime;
	
	@Column(name = "reportStatus")
	private Integer reportStatus = 1;
	
	@PrePersist
	public void onCreate() {
		if (reportTime == null) {
			reportTime = new Date();
		}
	}
	
}

