package com.example.bookstore.repository;

import com.example.bookstore.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for sales data and reporting queries.
 */
public interface SaleRepository extends JpaRepository<Sale, Long> {

	List<Sale> findBySaleDateTimeBetween(LocalDateTime startInclusive, LocalDateTime endInclusive);

	@Query("select coalesce(sum(s.totalAmount),0) from Sale s where s.saleDateTime between :start and :end")
	BigDecimal sumTotalBetween(@Param("start") LocalDateTime startInclusive, @Param("end") LocalDateTime endInclusive);

	@Query("select coalesce(sum(s.totalAmount),0) from Sale s")
	BigDecimal sumTotalAll();
}


