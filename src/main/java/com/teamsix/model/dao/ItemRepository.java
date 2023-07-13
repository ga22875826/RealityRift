package com.teamsix.model.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.teamsix.model.bean.item.ItemDTO;

public interface ItemRepository extends JpaRepository<ItemDTO, Integer> {

	Page<ItemDTO> findByCategoryId(int categoryId, Pageable pageable);

	// 非停售商品
	Page<ItemDTO> findByCategoryIdAndItemstatusNot(int categoryId, String status, Pageable pageable);

	// 非停售商品
	Page<ItemDTO> findByItemstatusNot(String status, Pageable pageable);

}
