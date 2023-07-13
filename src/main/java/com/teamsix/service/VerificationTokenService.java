package com.teamsix.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamsix.model.bean.Member;
import com.teamsix.model.bean.VerificationToken;
import com.teamsix.model.dao.VerificationTokenRepository;

@Service
public class VerificationTokenService {
	
	@Autowired
	private VerificationTokenRepository vtRepo;
	
	public int findMemnoByToken(String token) {
		return vtRepo.findByToken(token).getMemno();
	}
	
	@Transactional
	public boolean updateToken(VerificationToken vt) {
		Optional<VerificationToken> optional = vtRepo.findById(vt.getMemno());
		if(optional.isPresent()) {
			VerificationToken vt1 = optional.get();
			vt1.setToken(vt.getToken());
			vt1.setExpiryDate(vt.getExpiryDate());

			vtRepo.save(vt1);
			return true;
		}
		return false;
	}
	
}
