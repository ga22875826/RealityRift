package com.teamsix.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.teamsix.model.bean.GameBean;


public interface GameRepository extends JpaRepository<GameBean, Integer> {
	
	@Query("SELECT g FROM GameBean g")
	public GameBean gameSearchByFilter();

	public List<GameBean> findByLevel(String level);

	@Query("SELECT g FROM GameBean g WHERE g.address LIKE %:city%")
	public List<GameBean> findByAddress(String city);

	@Query("SELECT g FROM GameBean g WHERE g.address LIKE %:city% AND g.level = :level")
	public List<GameBean> findByAddressAndLevel(String city, String level);
	
	
	GameBean findByGname(String Gname);


}

