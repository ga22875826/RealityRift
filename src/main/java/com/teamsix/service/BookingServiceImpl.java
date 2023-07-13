package com.teamsix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.teamsix.model.bean.Booking;
import com.teamsix.model.dao.BookingRepository;

import java.util.List;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> findByMemberBeanMemno(int memno) {
        return bookingRepository.findByMemberMemno(memno);
    }

    @Override
    public List<Booking> findByGameBeanGameid(int gameid) {
        return bookingRepository.findByGameBeanGameid(gameid);
    }

    @Override
    public void insert(Booking booking) {
        bookingRepository.save(booking);
    }

    @Override
    public void deleteById(int bookingID) {
        bookingRepository.deleteById(bookingID);
    }

    @Override
    public void update(Booking booking) {
        bookingRepository.save(booking);
    }

}
