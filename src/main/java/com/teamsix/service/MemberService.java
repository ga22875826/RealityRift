package com.teamsix.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamsix.model.bean.Member;
import com.teamsix.model.dao.MemberRepository;


@Service
public class MemberService {
	
	@Autowired
	private MemberRepository mRepo;
	
	@Autowired
	private PasswordEncoder pwdEncoder;
	
//	@Autowired
//    private JavaMailSender mailSender;
	
	//註冊
	public Member regist(Member member) {
		member.setPassword(pwdEncoder.encode(member.getPassword()));
		if("".equals(member.getPhone())) {
			member.setPhone(null);
		}
		if("".equals(member.getMemaddress())) {
			member.setMemaddress(null);
		}
		if(member.getMemimg()==null) {
			member.setMemimg(null);
		}
		return mRepo.save(member);
	}
	

	
	public Member loginCheck(String email,String password) {
		Member member = mRepo.findByEmail(email);
		
		if(member==null) {
			return null;
		}
		if(!pwdEncoder.matches(password, member.getPassword())) {
			return null;
		}
		return member;
	}
	
	@Transactional
	public Boolean editPassword(int memno,String password,String newPassword) {
		Optional<Member> optional = mRepo.findById(memno);
		if(optional.isPresent()) {
			Member member = optional.get();
			if(pwdEncoder.matches(password, member.getPassword())) {
				member.setPassword(pwdEncoder.encode(newPassword));
				return true;
			}
		}
		return false;
	}
	
	@Transactional
	public void editForgetPassword(int memno,String newPassword) {
		Member member = mRepo.getReferenceById(memno);
		member.setPassword(pwdEncoder.encode(newPassword));
	}
	
	public Boolean deleteById(int memno) {
		mRepo.deleteById(memno);
		return true;
	}
	
	public List<Member> findAll(){
		List<Member> memberList = mRepo.findAll();
		for(Member member : memberList) {
			if(member.getPhone()==null) {
				member.setPhone("");
			}
			if(member.getMemaddress()==null) {
				member.setMemaddress("");
			}
		}
		return memberList;
	}
	
	public Member findById(int memno) {
		Optional<Member> optional = mRepo.findById(memno);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	@Transactional
	public boolean update(Member member) {
		if("".equals(member.getPhone())) {
			member.setPhone(null);
		}
		if("".equals(member.getMemaddress())) {
			member.setMemaddress(null);
		}
		if(member.getMemimg()==null) {
			member.setMemimg(null);
		}
		Optional<Member> optional = mRepo.findById(member.getMemno());
		if(optional.isPresent()) {
			Member m1 = optional.get();
			
			m1.setMemclass(member.getMemclass());
			m1.setMemstatus(member.getMemstatus());
			if(m1.getPassword().equals(member.getPassword())||"".equals(member.getPassword())||member.getPassword()==null) {
				
			}else{
				m1.setPassword(pwdEncoder.encode(member.getPassword()));
			}
			m1.setMemname(member.getMemname());
			m1.setMemid(member.getMemid());
			m1.setBirthdate(member.getBirthdate());
			m1.setPhone(member.getPhone());
			m1.setMemaddress(member.getMemaddress());
			m1.setMemimg(member.getMemimg());
			return true;
		}
		return false;
	}
	
	public String findImgById(int memno) {
		return findById(memno).getMemimg();
	}
	
	//
	public Member findByEmail(String email) {
        return mRepo.findByEmail(email);
    }
	
	public Member findByMemid(String memid) {
		return mRepo.findByMemid(memid);
	}
	
	public List<Object[]> getRegistrationCountPerDay() {
		return mRepo.getRegistrationCountPerDay();
	}
	
	public long getMemberCount() {
		return mRepo.count();
	}
	
	public List<Object[]> getIsGoogleAccount() {
		return mRepo.getIsGoogleAccount();
	}
	
	public List<Object[]> getMemberAge() {
		return mRepo.getMemberAge();
	}
}
