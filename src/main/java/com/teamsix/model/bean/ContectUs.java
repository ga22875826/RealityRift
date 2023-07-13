package com.teamsix.model.bean;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity @Table(name="ContectUs")
public class ContectUs {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int contectid;
	private String name;
	private String phone;
	private String email;
	private String topic;
	private int theme;
	private String content;
	private int status;
	private LocalDateTime contecttime;
	
	@OneToOne(fetch = FetchType.LAZY,mappedBy = "contectUs",cascade = CascadeType.ALL)
	private ContectResponse contectResponse;
	
	public ContectUs() {
	}

	
	
	public ContectUs(String name, String phone, String email, String topic, int theme, String content) {
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.topic = topic;
		this.theme = theme;
		this.content = content;
		this.status = 0;
		this.contecttime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
	}



	public int getContectid() {
		return contectid;
	}

	public void setContectid(int contectid) {
		this.contectid = contectid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public int getTheme() {
		return theme;
	}

	public void setTheme(int theme) {
		this.theme = theme;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public LocalDateTime getContecttime() {
		return contecttime;
	}
 
	public void setContecttime(LocalDateTime contecttime) {
		this.contecttime = contecttime;
	}

	public ContectResponse getContectResponse() {
		return contectResponse;
	}

	public void setContectResponse(ContectResponse contectResponse) {
		this.contectResponse = contectResponse;
	}
	
}
