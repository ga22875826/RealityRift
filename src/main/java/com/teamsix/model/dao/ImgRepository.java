package com.teamsix.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamsix.model.bean.item.Itemimg;

public interface ImgRepository extends JpaRepository<Itemimg, Integer> {
	   List<Itemimg> findByItem_Itemid(int itemId);
}
