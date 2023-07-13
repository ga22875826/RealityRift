package com.teamsix.model.bean;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity @Table(name="ContectResponse")
public class ContectResponse {

	@GenericGenerator(name="generator",strategy = "foreign",parameters = @Parameter(name="property",value="contectUs"))
	@Id 
	@GeneratedValue(generator = "generator")
	private int contectid;
	private String responsecontent;
	private LocalDateTime responsetime;
	
	@OneToOne(fetch=FetchType.LAZY)
	@PrimaryKeyJoinColumn
	@JsonBackReference
	private ContectUs contectUs;
	
	public ContectResponse() {
		this.responsetime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
	}

	public int getContectid() {
		return contectid;
	}

	public void setContectid(int contectid) {
		this.contectid = contectid;
	}

	public String getResponsecontent() {
		return responsecontent;
	}

	public void setResponsecontent(String responsecontent) {
		this.responsecontent = responsecontent;
	}

	public LocalDateTime getResponsetime() {
		return responsetime;
	}

	public void setResponsetime(LocalDateTime responsetime) {
		this.responsetime = responsetime;
	}

	
	
}
