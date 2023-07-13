package com.teamsix.model.bean;

import java.util.Random;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name="PasswordVerificationToken")
public class PasswordVerificationToken {

	@GenericGenerator(name="generator",strategy = "foreign",parameters = @Parameter(name="property",value="member"))
	@Id 
	@GeneratedValue(generator = "generator")
	private int memno;
	
	private int token;
	
	@OneToOne(fetch=FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Member member;
	
	
	public PasswordVerificationToken() {
		int min = 100000;  
        int max = 999999;  
        Random random = new Random();
        this.token = random.nextInt(max - min + 1) + min;
	}


	public int getMemno() {
		return memno;
	}


	public void setMemno(int memno) {
		this.memno = memno;
	}


	public int getToken() {
		return token;
	}


	public void setToken(int token) {
		this.token = token;
	}


	public Member getMember() {
		return member;
	}


	public void setMember(Member member) {
		this.member = member;
	}
	
	

}
