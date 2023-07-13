package com.teamsix.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamsix.model.bean.Member;
import com.teamsix.model.dao.MemberRepository;

@Service
public class GoogleLoginService {

	@Autowired
	private MemberRepository mRepo;


	public Member registerByGoogleAccount(OAuth2AuthenticationToken oAuth2AuthenticationToken) throws IOException, GeneralSecurityException {
		String email = oAuth2AuthenticationToken.getPrincipal().getAttribute("email");
		String name = oAuth2AuthenticationToken.getPrincipal().getAttribute("name");
		String imageURL = oAuth2AuthenticationToken.getPrincipal().getAttribute("picture");

		Member member = new Member(email, "Member", "active", "google", name, "無", null, null, null, imageURL);
		member.setIsgoogleaccount(1);
		
		
		return mRepo.save(member);

	}
	
	@Transactional
	public Member updateGoogleAccount(Member member) {
		
		Member m1 = mRepo.getReferenceById(member.getMemno());
		m1.setMemname(member.getMemname());
		m1.setMemimg(member.getMemimg());
		
		return m1;
	}

}
