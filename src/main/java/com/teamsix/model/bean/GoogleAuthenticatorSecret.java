package com.teamsix.model.bean;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "GoogleAuthenticatorSecret")
public class GoogleAuthenticatorSecret {

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "member"))
	@Id
	@GeneratedValue(generator = "generator")
	private int memno;

	private String secret;

	private String qrcodeurl;
	
	private int isenabled;

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Member member;

	
	public GoogleAuthenticatorSecret() {
	}
	
	public GoogleAuthenticatorSecret(String email) {
		GoogleAuthenticator gAuth = new GoogleAuthenticator();
		final GoogleAuthenticatorKey key = gAuth.createCredentials();
		this.secret = key.getKey();
		this.qrcodeurl = getQrCode(secret,email);
		this.isenabled = 0;
	}

	

	public int getMemno() {
		return memno;
	}

	public void setMemno(int memno) {
		this.memno = memno;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getQrcodeurl() {
		return qrcodeurl;
	}

	public void setQrcodeurl(String qrcodeurl) {
		this.qrcodeurl = qrcodeurl;
	}

	public int getIsenabled() {
		return isenabled;
	}

	public void setIsenabled(int isenabled) {
		this.isenabled = isenabled;
	}

	public String getQrCode(String secret,String email) {

		String qrCodeData = createGoogleAuthenticatorKeyUri(secret,email);

		String googleChartsQrCodeFormat = "https://www.google.com/chart?chs=200x200&cht=qr&chl=%s";
		String qrCodeUrl = String.format(googleChartsQrCodeFormat, qrCodeData);

		return qrCodeUrl;
	}

	private String createGoogleAuthenticatorKeyUri(String secret,String email) {
		String otpType = "totp";
		String account = "RealityRift:"+email;
		String issuer = "RealityRift";
		String googleAuthenticatorKeyUriFormat = "otpauth://%s/%s?secret=%s&issuer=%s";
		return String.format(googleAuthenticatorKeyUriFormat, otpType, account, secret, issuer);
	}
}
