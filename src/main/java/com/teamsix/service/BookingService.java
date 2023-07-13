package com.teamsix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamsix.model.bean.Booking;
import com.teamsix.model.bean.GameBean;
import com.teamsix.model.dao.BookingRepository;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepo;
    
	// ===== ===== ===== ===== ===== ===== ===== =====

    public List<Booking> findAll() {
        return bookingRepo.findAll();
    }

    public List<Booking> findByMemberBeanMemno(int memno) {
        return bookingRepo.findByMemberMemno(memno);
    }

    public List<Booking> findByGameBeanGameid(int gameid) {
        return bookingRepo.findByGameBeanGameid(gameid);
    }

    public void insertBooking(Booking booking) {
    	bookingRepo.save(booking);
    }

    public void deleteBookingById(int bookingid) {
    	bookingRepo.deleteById(bookingid);
    }
    
    public Booking findById(int bookingid) {
    	return bookingRepo.getReferenceById(bookingid);
    }
    
    // 檢查是否已存在相同的預約資料
    public boolean existsBooking(GameBean game, String gameDate, String gameTime) {
        return bookingRepo.existsBooking(game, gameDate, gameTime);
    }
    
    public Booking checkBooking(GameBean game, String gameDate, String gameTime) {
    	return bookingRepo.findByGametime(game.getGameid(), gameDate, gameTime);
    }

    @Transactional
    public void updateBooking(Booking booking) {
    	bookingRepo.save(booking);
    }

}
