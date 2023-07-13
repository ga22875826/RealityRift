package com.teamsix.model.bean;


import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity @Table(name = "member")
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int memno;
	private String email;
	private String memclass;
	private String memstatus;
	private String password;
	private String memname;
	private String memid;
	private String birthdate;
	private String phone;
	private String memaddress;
	private String memimg;
	private LocalDate registerdate;
	private int isgoogleaccount;
	
	@OneToOne(fetch = FetchType.LAZY,mappedBy = "member",cascade = CascadeType.ALL)
	@JsonIgnore
	private VerificationToken verificationToken;
	
	@OneToOne(fetch = FetchType.LAZY,mappedBy = "member",cascade = CascadeType.ALL)
	@JsonIgnore
	private PasswordVerificationToken passwordVerificationToken;
	
	@OneToOne(fetch = FetchType.LAZY,mappedBy = "member",cascade = CascadeType.ALL)
	@JsonIgnore
	private GoogleAuthenticatorSecret googleAuthenticatorSecret;
	
	public Member() {
	}
	
	public Member(String email, String memclass, String memstatus, String password, String memname, String memid,
			String birthdate, String phone, String memaddress, String memimg) {
		this.email = email;
		this.memclass = memclass;
		this.memstatus = memstatus;
		this.password = password;
		this.memname = memname;
		this.memid = memid;
		this.birthdate = birthdate;
		this.phone = phone;
		this.memaddress = memaddress;
		this.memimg = memimg;
		this.registerdate = LocalDate.now();
		this.isgoogleaccount = 0;
	}
	
	public String getMemstatus() {
		return memstatus;
	}
	public void setMemstatus(String memstatus) {
		this.memstatus = memstatus;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getMemno() {
		return memno;
	}
	public void setMemno(int memno) {
		this.memno = memno;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMemclass() {
		return memclass;
	}
	public void setMemclass(String memclass) {
		this.memclass = memclass;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMemname() {
		return memname;
	}
	public void setMemname(String name) {
		this.memname = name;
	}
	public String getMemid() {
		return memid;
	}
	public void setMemid(String id) {
		this.memid = id;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public String getMemaddress() {
		return memaddress;
	}
	public void setMemaddress(String address) {
		this.memaddress = address;
	}
	public String getMemimg() {
		return memimg;
	}
	public void setMemimg(String img) {
		this.memimg = img;
	}
	public VerificationToken getVerificationToken() {
		return verificationToken;
	}
	public void setVerificationToken(VerificationToken verificationToken) {
		this.verificationToken = verificationToken;
	}
	public LocalDate getRegisterdate() {
		return registerdate;
	}
	public void setRegisterdate(LocalDate registerdate) {
		this.registerdate = registerdate;
	}
	public int getIsgoogleaccount() {
		return isgoogleaccount;
	}
	public void setIsgoogleaccount(int isgoogleaccount) {
		this.isgoogleaccount = isgoogleaccount;
	}
	public PasswordVerificationToken getPasswordVerificationToken() {
		return passwordVerificationToken;
	}
	public void setPasswordVerificationToken(PasswordVerificationToken passwordVerificationToken) {
		this.passwordVerificationToken = passwordVerificationToken;
	}

	public GoogleAuthenticatorSecret getGoogleAuthenticatorSecret() {
		return googleAuthenticatorSecret;
	}

	public void setGoogleAuthenticatorSecret(GoogleAuthenticatorSecret googleAuthenticatorSecret) {
		this.googleAuthenticatorSecret = googleAuthenticatorSecret;
	}
	
	
}
