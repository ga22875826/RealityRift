package com.teamsix.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.teamsix.model.bean.item.SaleItemDTO;

public interface SaleItemRepository extends JpaRepository<SaleItemDTO,Integer>  {
	
	@Query("SELECT s FROM SaleItemDTO s JOIN FETCH s.item i JOIN FETCH i.images WHERE s.sale.saleId = :saleId")
    List<SaleItemDTO> findBySale_SaleId(@Param("saleId") int saleId);

}
