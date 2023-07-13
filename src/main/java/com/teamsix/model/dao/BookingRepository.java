package com.teamsix.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamsix.model.bean.Booking;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findByMemberMemno(int memno);
    List<Booking> findByGameBeanGameid(int gameid);

}
