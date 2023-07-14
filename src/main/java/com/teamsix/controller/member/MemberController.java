package com.teamsix.controller.member;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.teamsix.model.bean.GoogleAuthenticatorSecret;
import com.teamsix.model.bean.Member;
import com.teamsix.model.bean.PasswordVerificationToken;
import com.teamsix.model.bean.VerificationToken;
import com.teamsix.service.GmailService;
import com.teamsix.service.GoogleAuthenticatorService;
import com.teamsix.service.GoogleLoginService;
import com.teamsix.service.MemberService;
import com.teamsix.service.PasswordVerificationTokenService;
import com.teamsix.service.VerificationTokenService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

	@Autowired
	private MemberService ms;

	@Autowired
	private GmailService gs;

	@Autowired
	private VerificationTokenService vts;

	@Autowired
	private PasswordVerificationTokenService pvts;
	
	@Autowired
	private GoogleLoginService gls;
	
	@Autowired
	private GoogleAuthenticatorService gas;

//	@GetMapping("/pdftest")
//	public String MemberPdftest(Model model) {
//		List<Member> memberList = ms.findAll();
//		model.addAttribute("members",memberList);
//		return "member/MemberPdf";
//	}
	
	@GetMapping("/registerpage")
	public String MemberRegistPage1() {
		return "member/MemberRegister";
	}

	@GetMapping("/registersuccess")
	public String MemberRegistSuccess() {
		return "member/MemberRegisterSuccess";
	}

	@GetMapping("/profile")
	public String ProfilePage(HttpSession session, Model m) {
		Member sessionMember = (Member) session.getAttribute("member");
		int memno = sessionMember.getMemno();
		m.addAttribute("member", ms.findById(memno));
		return "member/MemberProfile";
	}

	@GetMapping("/editpassword")
	public String editPasswordPage() {
		return "member/MemberEditPassword";
	}

	@GetMapping("/forgetpassword")
	public String forgetPasswordPage() {
		return "member/MemberForgetPassword";
	}

	@GetMapping("/verified")
	public String verifiedHome(Model model) {
		model.addAttribute("verified", true);
		return "main";
	}

	@GetMapping("/tokentimeout.{token}")
	public String tokenTimeOut(@PathVariable String token, Model model) {
		model.addAttribute("tokenTimeOut", true);
		model.addAttribute("token", token);
		return "main";
	}

	@GetMapping("/db/member")
	public String MemberDashboard(Model m) {
		List<Member> memberList = ms.findAll();
		m.addAttribute("memberList", memberList);
		return "member/MemberDashboard";
	}
	
	@GetMapping("/db/member/statistic")
	public String MemberStatistic() {
		return "member/MemberStatistic";
	}
	
	@GetMapping("/secondauthenticatorpage")
	public String MemberSecondAuthenticatorPage(HttpSession session,Model m) {
		Member member =(Member) session.getAttribute("member");
		GoogleAuthenticatorSecret googleAuthenticatorSecret = gas.findGoogleAuthenticatorSecretById(member.getMemno());
		if(googleAuthenticatorSecret!=null) {
			m.addAttribute("qrCodeUrl", googleAuthenticatorSecret.getQrcodeurl());
			m.addAttribute("isenabled",googleAuthenticatorSecret.getIsenabled());
		}
		return "member/MemberSecondAuthenticator";
	}
	

	
	@PostMapping("/register")
	public String MemberRegist(@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("memname") String memname, @RequestParam("memid") String memid,
			@RequestParam("birthdate") String birthdate, @RequestParam("phone") String phone,
			@RequestParam("memaddress") String memaddress, @RequestParam("memimg") MultipartFile mf)
			throws IllegalStateException, IOException, AddressException, GeneralSecurityException, MessagingException {
		String fileName = mf.getOriginalFilename();
		String saveDir = "C:\\ProjectImages\\member";
		String newFileName = "noimg.jpg";

		Member member = new Member(email, "Member", "inactive", password, memname, memid, birthdate, phone, memaddress,
				"");
		if (fileName != null && fileName.length() != 0) {
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			int random = new Random().nextInt(10000);
			String formateDate = format.format(new Date());
			newFileName = "r" + formateDate + random + fileName.substring(fileName.indexOf("."));
			File saveFilePath = new File(saveDir, newFileName);
			mf.transferTo(saveFilePath); // 檔案上傳儲存
		}

		member.setMemimg("/rr/img/member/"+newFileName);

		VerificationToken vt = new VerificationToken();
		vt.setExpiryDate(calculateExpiryDate(1));
		member.setVerificationToken(vt);
		ms.regist(member);
		// email驗證
		sendVerificationEmail(vt.getToken(), email, memname);
		
		//Google兩階段驗證
		gas.createGoogleAuthentication(member);

		return "main";
	}
	
	@GetMapping("/api/getmemberbymemno/{memno}")
	@ResponseBody
	public Member getMemberByMemno(@PathVariable(name="memno") int memno) {
		return ms.findById(memno);
	}
	
	@GetMapping("/api/emailrepeatedcheck")
	@ResponseBody
	public Boolean EmailRepeatedCheck(@RequestParam("email") String email) {
		Member member = ms.findByEmail(email);
		if(member!=null) {
			return true;
		}
		return false;
	}

	@GetMapping("/api/memidrepeatedcheck")
	@ResponseBody
	public Boolean MemidRepeatedCheck(@RequestParam("memid") String memid) {
		Member member = ms.findByMemid(memid);
		if(member!=null) {
			return true;
		}
		return false;
	}
	
	@PutMapping("/api/member/update")
	@ResponseBody
	public Boolean MemberUpdate(@RequestParam("memno") int memno, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("memname") String memname,
			@RequestParam("memid") String memid, @RequestParam("birthdate") String birthdate,
			@RequestParam("phone") String phone, @RequestParam("memaddress") String memaddress,
			@RequestParam("memclass") String memclass,
			@RequestParam(value = "memimg", required = false) MultipartFile mf)
			throws IllegalStateException, IOException {
		String saveDir = "C:\\ProjectImages\\member";
		String newFileName = "";
		String fileName = "";
		if (mf != null) {
			fileName = mf.getOriginalFilename();
		}

		Member member = ms.findById(memno);
		Member m1 = new Member(email, "Member", member.getMemstatus(), password, memname, memid, birthdate, phone,
				memaddress, "");
		m1.setMemno(memno);

		if (fileName != null && fileName.length() != 0) {
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			int random = new Random().nextInt(10000);
			String formateDate = format.format(new Date());
			newFileName = "r" + formateDate + random + fileName.substring(fileName.indexOf("."));
			File saveFilePath = new File(saveDir, newFileName);
			mf.transferTo(saveFilePath); // 檔案上傳儲存
			m1.setMemimg("/rr/img/member/"+newFileName);
		} else {
			newFileName = ms.findImgById(memno);
			m1.setMemimg(newFileName);
		}
		return ms.update(m1);

	}

	@PutMapping("/api/member/updatememstatus")
	@ResponseBody
	public Boolean MemberUpdateStatus(@RequestParam("memno") int memno, @RequestParam("memstatus") String memstatus) {
		Member member = ms.findById(memno);
		member.setMemstatus(memstatus);

		return ms.update(member);
	}

	@PutMapping("/api/profile/update")
	@ResponseBody
	public Boolean MemberProfileUpdate(@RequestParam("memno") int memno, @RequestParam("memname") String memname,
			@RequestParam("birthdate") String birthdate, @RequestParam("phone") String phone,
			@RequestParam("memaddress") String memaddress,
			@RequestParam(value = "memimg", required = false) MultipartFile mf, HttpSession session)
			throws IllegalStateException, IOException {
		Member member = ms.findById(memno);
		member.setMemname(memname);
		member.setBirthdate(birthdate);
		member.setPhone(phone);
		member.setMemaddress(memaddress);
		session.setAttribute("member", member);

		String saveDir = "C:\\ProjectImages\\member";
		String newFileName = "";
		String fileName = "";
		if (mf != null) {
			fileName = mf.getOriginalFilename();
		}
		if (fileName != null && fileName.length() != 0) {
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			int random = new Random().nextInt(10000);
			String formateDate = format.format(new Date());
			newFileName = "r" + formateDate + random + fileName.substring(fileName.indexOf("."));
			File saveFilePath = new File(saveDir, newFileName);
			mf.transferTo(saveFilePath); // 檔案上傳儲存
			member.setMemimg("/rr/img/member/"+newFileName);
		} else {
			newFileName = ms.findImgById(memno);
			member.setMemimg(newFileName);
		}

		return ms.update(member);
	}

	@DeleteMapping("/api/member/delete")
	@ResponseBody
	public Boolean MemberDelete(@RequestParam("memno") int memno) {
		return ms.deleteById(memno);
	}

	@GetMapping("/api/memberlogin")
	@ResponseBody
	public Member MemberLoginCheck(@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam(value = "rememberMe", required = false, defaultValue = "false") boolean rememberMe,
			HttpSession session, HttpServletResponse response) {
		Member member = ms.loginCheck(email, password);
		if (member != null) {
			if (rememberMe) {
				Cookie cookie = new Cookie("loginEmail", email);
				response.addCookie(cookie);
			}
			return member;
		}
		return null;
	}
	
	@GetMapping("/api/memberloginsuccess")
	@ResponseBody
	public Member MemberLoginSuccess(@RequestParam("email") String email,HttpSession session) {
		Member member = ms.findByEmail(email);
		session.setAttribute("member", member);
		return member;
	}

	@GetMapping("/api/memberlogout")
	@ResponseBody
	public String MemberLogout(HttpSession session) {
		session.removeAttribute("member");
		return "登出成功";
	}

	@GetMapping("/api/getrememberemail")
	@ResponseBody
	public String GetRememberEmail(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("loginEmail".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return "";
	}

	@GetMapping("/activate")
	public String activateAccount(@RequestParam("token") String token) {
		Member member = ms.findById(vts.findMemnoByToken(token));
		Date dateNow = calculateExpiryDate(0);
		if (dateNow.compareTo(member.getVerificationToken().getExpiryDate()) < 0) {
			member.setMemstatus("active");
			ms.update(member);
			return "redirect:/verified";
		} else {
			return "redirect:/tokentimeout." + token;
		}
	}

	@GetMapping("/api/resendverificationemail")
	@ResponseBody
	public Boolean resendVerificationEmail(@RequestParam String token)
			throws AddressException, GeneralSecurityException, IOException, MessagingException {
		Member member = ms.findById(vts.findMemnoByToken(token));
		VerificationToken vt = new VerificationToken();
		String newToken = UUID.randomUUID().toString();
		vt.setMemno(member.getMemno());
		vt.setToken(newToken);
		vt.setExpiryDate(calculateExpiryDate(1));
		boolean TokenExist = vts.updateToken(vt);
		if (TokenExist) {
			sendVerificationEmail(newToken, member.getEmail(), member.getMemname());
			return TokenExist;
		}
		return false;
	}

	@PutMapping("/api/editPassword")
	@ResponseBody
	public Boolean editPassword(@RequestParam int memno, @RequestParam String password,
			@RequestParam String newPassword, HttpSession session) {
		session.removeAttribute("member");
		return ms.editPassword(memno, password, newPassword);
	}

	@GetMapping("/api/forgetPasswordEmail")
	@ResponseBody
	public Integer forgetPasswordEmail(@RequestParam("email") String email)
			throws AddressException, GeneralSecurityException, IOException, MessagingException {
		Member member = ms.findByEmail(email);
		if (member != null) {
			PasswordVerificationToken pvtOld = member.getPasswordVerificationToken();
			if (pvtOld != null) {
				member.setPasswordVerificationToken(null);
				pvts.deleteByMemno(pvtOld.getMemno());
			}
			PasswordVerificationToken pvt = new PasswordVerificationToken();
			member.setPasswordVerificationToken(pvt);

			sendForgetPasswordEmail(pvt.getToken(), member.getEmail(), member.getMemname());
			ms.update(member);
			return member.getMemno();
		}
		return 0;
	}

	@GetMapping("/api/checkpasswordverificationtoken")
	@ResponseBody
	public Boolean checkPasswordVerificationToken(@RequestParam("memno") int memno, @RequestParam("first") String first,
			@RequestParam("second") String second, @RequestParam("third") String third,
			@RequestParam("forth") String forth, @RequestParam("fifth") String fifth,
			@RequestParam("sixth") String sixth) {
		PasswordVerificationToken pvt = pvts.findByMemno(memno);
		String correctToken = String.valueOf(pvt.getToken());
		String inputToken = first + second + third + forth + fifth + sixth;
		if(inputToken.equals(correctToken)) {
			return true;
		}
		return false;

	}
	
	@PutMapping("/api/editforgetpassword")
	@ResponseBody
	public Boolean editForgetPassword(@RequestParam("memno") int memno,@RequestParam("newPassword") String newPassword) {
		ms.editForgetPassword(memno, newPassword);
		return true;
	}
	
	@GetMapping("/googlelogin")
	public String googleLogin(OAuth2AuthenticationToken oAuth2AuthenticationToken,HttpSession session) throws IOException, GeneralSecurityException {
		String googleEmail = oAuth2AuthenticationToken.getPrincipal().getAttribute("email");
		String googleName = oAuth2AuthenticationToken.getPrincipal().getAttribute("name");
		String googleImg = oAuth2AuthenticationToken.getPrincipal().getAttribute("picture");
		Member member = ms.findByEmail(googleEmail);
		if(null == member) {
			gls.registerByGoogleAccount(oAuth2AuthenticationToken);
			Member member2 = ms.findByEmail(googleEmail);
			session.setAttribute("member", member2);
			return "redirect:/";
		}
		if(!member.getMemname().equals(googleName)) {
			member.setMemname(googleName);
		}
		if(!member.getMemimg().equals(googleImg)) {
			member.setMemimg(googleImg);
		}
		gls.updateGoogleAccount(member);
		session.setAttribute("member", member);
		return "redirect:/";
	}
	
	@GetMapping("/api/getregistrationcountperday")
	@ResponseBody
	public List<Object[]> getRegistrationCountPerDay() {
		return ms.getRegistrationCountPerDay();
	}
	
	@GetMapping("/api/getmembercount")
	@ResponseBody
	public long getMemberCount() {
		return ms.getMemberCount();
	}
	
	@GetMapping("/api/getisgoogleaccount")
	@ResponseBody
	public List<Object[]> getIsGoogleAccount() {
		return ms.getIsGoogleAccount();
	}
	
	@GetMapping("/api/getmemberage")
	@ResponseBody
	public List<Object[]> getMemberAge() {
		return ms.getMemberAge();
	}
	

	private Date calculateExpiryDate(int expiryTimeInMinutes) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Timestamp(cal.getTime().getTime()));
		cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(cal.getTime().getTime());
	}

	private void sendVerificationEmail(String token, String email, String name)
			throws AddressException, GeneralSecurityException, IOException, MessagingException {
		// email驗證
		String emailSubject = "恭喜註冊成為Reality Rift的一份子！請點擊連結啟用帳號。";
		String emailContent = "Dear " + name + " 您好!" + "\n請點擊下方連結以啟用您的帳號："
				+ "\nhttp://localhost:8080/rr/activate?token=" + token;
		gs.sendMail(emailSubject, emailContent, email);
	}

	private void sendForgetPasswordEmail(int token, String email, String name)
			throws AddressException, GeneralSecurityException, IOException, MessagingException {
		String emailSubject = "Reality Rift 重設密碼通知信。";
		String emailContent = "Dear " + name + " 您好!" + "\n您的驗證碼為：" + "\n" + String.valueOf(token);
		gs.sendMail(emailSubject, emailContent, email);
	}

}





