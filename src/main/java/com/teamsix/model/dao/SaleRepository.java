package com.teamsix.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamsix.model.bean.item.SaleDTO;

public interface SaleRepository extends JpaRepository<SaleDTO, Integer> {
	List<SaleDTO> findBySaleStatus(String status);
}
