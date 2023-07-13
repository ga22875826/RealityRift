package com.teamsix.service;

import java.util.List;

import com.teamsix.model.bean.Booking;

public interface BookingService {
	
	List<Booking> findAll();
	List<Booking> findByMemberBeanMemno(int memno);
	List<Booking> findByGameBeanGameid(int gameID);
	void insert(Booking booking);
	void deleteById(int bookingID);
	void update(Booking booking);
}
