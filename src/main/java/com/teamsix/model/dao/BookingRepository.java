package com.teamsix.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;

import com.teamsix.model.bean.Booking;
import com.teamsix.model.bean.GameBean;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    List<Booking> findByMemberMemno(int memno);
    
    List<Booking> findByGameBeanGameid(int gameid);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Booking b WHERE b.gameBean = :game AND b.gameDate = :gameDate AND b.gameTime = :gameTime")
    boolean existsBooking(@Param("game") GameBean game, @Param("gameDate") String gameDate, @Param("gameTime") String gameTime);

    @Query(value = "select * from booking where gameId =:gameId and gameDate =:gameDate and gameTime =:gameTime", nativeQuery = true)
    Booking findByGametime(@Param("gameId")int gameId, @Param("gameDate")String gameDate, @Param("gameTime")String gameTime);
}
