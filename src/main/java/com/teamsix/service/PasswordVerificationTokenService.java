package com.teamsix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamsix.model.bean.PasswordVerificationToken;
import com.teamsix.model.dao.PasswordVerificationTokenRepository;

@Service
public class PasswordVerificationTokenService {
	
	@Autowired
	private PasswordVerificationTokenRepository pvtRepo;
	
	@Transactional
	public void deleteByMemno(int memno) {
		pvtRepo.deleteById(memno);
	}
	
	public PasswordVerificationToken findByMemno(int memno) {
		return pvtRepo.getReferenceById(memno);
	}
	
}
