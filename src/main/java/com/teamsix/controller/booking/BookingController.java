package com.teamsix.controller.booking;

import java.util.ArrayList;
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


@Controller
public class BookingController {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private GameService gameService;

	@Autowired
	private MemberService memberService;

	@GetMapping("/db/bookingmanager")
	public String showBookingManager(Model model) {

		List<Booking> bookings = bookingService.findAll();
		List<GameBean> games = gameService.gameSelectAll();
//		ArrayList<GameBean> gamesList = new ArrayList<GameBean>();
		for(GameBean game:games) {
			game.setGameimg("/rr/"+game.getGameimg());
		}
		model.addAttribute("bookings", bookings);
		model.addAttribute("games", games);

		return "booking/bookingmanager";
	}

	@RequestMapping("/mybooking")
	public String myBooking(Model model) {

		List<Booking> bookings = bookingService.findAll();
		model.addAttribute("bookings", bookings);

		return "booking/mybooking";
	}

	@RequestMapping("/booking")
	public String booking(Model model) {

		List<GameBean> games = gameService.gameSelectAll();
//		GameBean curGame = gameService.findById(gameID);

		model.addAttribute("games", games);
//		model.addAttribute("curGame", curGame);
		return "booking/booking";
	}

	@PostMapping("/updateBooking")
	@ResponseBody
	public String updateBooking(@RequestParam("bookingID") int bookingId, @RequestParam("gameName") String gameName,
			@RequestParam("email") String email, @RequestParam("date") String bookingDate,
			@RequestParam("time") String bookingTime) {

		Booking booking = new Booking();
		Member member = memberService.findByEmail(email);
		GameBean game = gameService.findByGname(gameName);

		booking.setBookingId(bookingId);
		booking.setMemberBean(member);
		booking.setGameBean(game);
		booking.setBookingDate(bookingDate);
		booking.setBookingTime(bookingTime);
		bookingService.update(booking);

		String gameImg = "/rr/"+game.getGameimg();
		return gameImg;
	}

	@PostMapping("/deleteBooking")
	@ResponseBody
	public void deleteBooking(@RequestParam("bookingID") int bookingID) {
			bookingService.deleteById(bookingID);
	}
}
