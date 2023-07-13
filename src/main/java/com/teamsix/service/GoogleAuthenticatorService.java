package com.teamsix.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teamsix.model.bean.GoogleAuthenticatorSecret;
import com.teamsix.model.bean.Member;
import com.teamsix.model.dao.GoogleAuthenticatorSecretRepository;

import jakarta.transaction.Transactional;


@Service
public class GoogleAuthenticatorService {
	
	
	@Autowired
	private MemberService ms;
	
	@Autowired
	private GoogleAuthenticatorSecretRepository gasRepo;
	
	@Transactional
	public String createGoogleAuthentication(Member member) {
		Member m1 = ms.findById(member.getMemno());
		GoogleAuthenticatorSecret gas = new GoogleAuthenticatorSecret(m1.getEmail());
		m1.setGoogleAuthenticatorSecret(gas);
		return gas.getQrcodeurl();
	}
	
	public GoogleAuthenticatorSecret findGoogleAuthenticatorSecretById(int memno) {
		Optional<GoogleAuthenticatorSecret> optional = gasRepo.findById(memno);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	public void deleteById(int memno) {
		gasRepo.deleteById(memno);
	}
	
	@Transactional
	public Boolean enableGoogleAuthentication(Member member) {
		Optional<GoogleAuthenticatorSecret> optional = gasRepo.findById(member.getMemno());
		if(optional.isPresent()) {
			GoogleAuthenticatorSecret googleAuthenticatorSecret=optional.get();
			googleAuthenticatorSecret.setIsenabled(1);
			return true;
		}
		return false;
	}
	
	@Transactional
	public Boolean disableGoogleAuthentication(Member member) {
		Optional<GoogleAuthenticatorSecret> optional = gasRepo.findById(member.getMemno());
		if(optional.isPresent()) {
			GoogleAuthenticatorSecret googleAuthenticatorSecret=optional.get();
			googleAuthenticatorSecret.setIsenabled(0);
			return true;
		}
		return false;
	}
}
