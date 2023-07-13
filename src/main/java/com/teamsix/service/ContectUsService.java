package com.teamsix.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teamsix.model.bean.ContectResponse;
import com.teamsix.model.bean.ContectUs;
import com.teamsix.model.dao.ContectUsRepository;

import jakarta.transaction.Transactional;

@Service
public class ContectUsService {
	
	@Autowired
	private ContectUsRepository cuRepo;
	
	public ContectUs insertContectUs(ContectUs contectUs) {
		return cuRepo.save(contectUs);
	}
	
	public List<ContectUs> findAll(){
		return cuRepo.findAll();
	}
	
	public ContectUs findById(int contectid) {
		return cuRepo.getReferenceById(contectid);
	}
	
	@Transactional
	public ContectUs updateContectResponse(int contectid,ContectResponse contectResponse) {
		ContectUs contectUs = cuRepo.getReferenceById(contectid);
		contectUs.setContectResponse(contectResponse);
		return contectUs;
	}
	
	@Transactional
	public ContectUs updateStatusTo1(int contectid) {
		ContectUs contectUs = cuRepo.getReferenceById(contectid);
		contectUs.setStatus(1);
		return contectUs;
	}
	
	@Transactional
	public ContectUs updateStatusTo2(int contectid) {
		ContectUs contectUs = cuRepo.getReferenceById(contectid);
		contectUs.setStatus(2);
		return contectUs;
	}
}
