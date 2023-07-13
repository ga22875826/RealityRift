package com.teamsix.controller.booking;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.teamsix.model.bean.Booking;
import com.teamsix.model.bean.GameBean;
import com.teamsix.model.bean.Member;
import com.teamsix.service.BookingService;
import com.teamsix.service.GameService;
import com.teamsix.service.MemberService;

import jakarta.servlet.http.HttpSession;


@Controller
public class BookingController {

	@Autowired
	private BookingService bService;

	@Autowired
	private GameService gService;

	@Autowired
	private MemberService mService;
	
//  ===== ===== ===== showBooking ===== ===== ===== ===== 

	@GetMapping("/db/bookingmanager")
	public String showBookingManager(Model model) {

		List<Booking> bookings = bService.findAll();
		
		List<GameBean> games = gService.gameSelectAll();
		for(GameBean game:games) {
			game.setGameimg("/rr/"+game.getGameimg());
		}
		
		model.addAttribute("bookings", bookings);
		model.addAttribute("games", games);

		return "booking/bkAdmin";
	}
	
	//@RequestParam("memno") int memno
	@GetMapping("/mybooking")
	public String showBookingMember(HttpSession session, Model model) {
		
		Member member =(Member) session.getAttribute("member");
		int memno = member.getMemno();
		List<Booking> bookings = bService.findByMemberBeanMemno(memno);
	
		model.addAttribute("bookings", bookings);
		
		return "booking/bkMember";
		
	}
	
//  ===== ===== =====  + 預約  ===== ===== ===== ===== 	

	@PostMapping("/booking")
	@ResponseBody
	public String insertBooking(@RequestParam("memno") int memno, @RequestParam("gameid") int gameid, 
								@RequestParam("gameDate") String gameDate, @RequestParam("gameTime") String gameTime) {
		
		Member member = mService.findById(memno);
		GameBean game = gService.findById(gameid);
		
		if(bService.existsBooking(game, gameDate, gameTime)) {
			return "bk-failed";
		}
		
		Booking booking = new Booking();
		booking.setMember(member);
		booking.setGameBean(game);
		booking.setGameDate(gameDate);
		booking.setGameTime(gameTime);
		
		bService.insertBooking(booking);
		
		return "bk-ok";
	}
	
//  ===== ===== ===== 刪除、修改 ===== ===== ===== 
	
	@DeleteMapping("/bookingDelete")
	@ResponseBody
	public String deleteBookingById(@RequestParam("bookingid") int bookingid) {
		
		bService.deleteBookingById(bookingid);
		
		return "ok-Delete";
	}
	
	@PostMapping("/bookingUpdate")
	@ResponseBody
	public String updateBooking(@RequestParam("bookingid") int bookingid, @RequestParam("gameName") String gameName,
								@RequestParam("gameDate") String gameDate, @RequestParam("gameTime") String gameTime) {
		
		GameBean gname = gService.findByGname(gameName);
		
		Booking bookingInfo = bService.checkBooking(gname, gameDate, gameTime);
		if(bookingInfo != null) {
			return "bk-failed";
		}
		
		Booking booking = bService.findById(bookingid);
		booking.setGameDate(gameDate);
		booking.setGameTime(gameTime);
		
		bService.updateBooking(booking);
		
		return "ok-Update";
		
	}
	

}
