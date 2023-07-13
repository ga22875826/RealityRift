package com.teamsix.model.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.teamsix.model.bean.item.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long>,JpaSpecificationExecutor<Orders> {
	
	 Page<Orders> findByMemberMemno(int memno, Pageable pageable);
	 
	 @Query(value = "SELECT YEAR(orderDate) as year, MONTH(orderDate) as month, SUM(totalAmount) as totalSales " +
             "FROM Orders " +
             "WHERE YEAR(orderDate) = :year " +
             "GROUP BY YEAR(orderDate), MONTH(orderDate) " +
             "ORDER BY month ASC", nativeQuery = true)
List<Map<String, Object>> findSalesByYear(@Param("year") int year);
}
