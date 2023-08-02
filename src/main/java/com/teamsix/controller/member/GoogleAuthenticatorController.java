package com.teamsix.controller.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.teamsix.model.bean.GoogleAuthenticatorSecret;
import com.teamsix.model.bean.Member;
import com.teamsix.service.GoogleAuthenticatorService;
import com.teamsix.service.MemberService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

@Controller
public class GoogleAuthenticatorController {
	
	@Autowired
	private MemberService ms;
	
	@Autowired
	private GoogleAuthenticatorService gas;
	
	@GetMapping("/api/auth/{code}")
	@ResponseBody
	public Boolean googleAuthenticatorAuth(@PathVariable("code") int code,@RequestParam("email") String email) {
		Member member = ms.findByEmail(email);
		GoogleAuthenticatorSecret googleAuthenticatorSecret = member.getGoogleAuthenticatorSecret();
		String secret = googleAuthenticatorSecret.getSecret();
		GoogleAuthenticator gAuth = new GoogleAuthenticator();
		boolean isCodeValid = gAuth.authorize(secret, code); // 驗證
		return isCodeValid ? true : false ;
	}

    @PutMapping("/api/enableauth/{code}")
    @ResponseBody
    public Boolean enableGoogleAuthenticatorAuth(@PathVariable("code") int code,@RequestParam("email") String email) {
    	Member member = ms.findByEmail(email);
    	GoogleAuthenticatorSecret googleAuthenticatorSecret = member.getGoogleAuthenticatorSecret();
    	String secret = googleAuthenticatorSecret.getSecret();
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        boolean isCodeValid = gAuth.authorize(secret, code); // 驗證
        if(isCodeValid) {
        	if(googleAuthenticatorSecret.getIsenabled()==0) {
        		gas.enableGoogleAuthentication(member);
        	}
        }
        return isCodeValid ? true : false ;
    }
    
    @PutMapping("/api/disableauth/{code}")
    @ResponseBody
    public Boolean disableGoogleAuthenticatorAuth(@PathVariable("code") int code,@RequestParam("email") String email) {
    	Member member = ms.findByEmail(email);
    	GoogleAuthenticatorSecret googleAuthenticatorSecret = member.getGoogleAuthenticatorSecret();
    	String secret = googleAuthenticatorSecret.getSecret();
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        boolean isCodeValid = gAuth.authorize(secret, code); // 驗證
        if(isCodeValid) {
        	if(googleAuthenticatorSecret.getIsenabled()==1) {
        		gas.disableGoogleAuthentication(member);
        	}
        }
        return isCodeValid ? true : false ;
    }
	
    @GetMapping("/api/checkgoogleauthenticator")
    @ResponseBody
	public Boolean checkIfMemberEnableGoogleAuthenticator(@RequestParam("email") String email) {
		Member member = ms.findByEmail(email);
		if(null != member.getGoogleAuthenticatorSecret()) {
			if(member.getGoogleAuthenticatorSecret().getIsenabled()==1) {
				return true;
			}
		}
		return false;
	}
    
}
