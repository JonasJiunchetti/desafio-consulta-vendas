package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devsuperior.dsmeta.dto.SummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {


       @Query(value = "SELECT obj FROM Sale obj JOIN FETCH obj.seller " +
           "WHERE UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%')) " + 
           "AND obj.date BETWEEN :minDate AND :maxDate",
           countQuery = "SELECT COUNT(obj) FROM Sale obj JOIN obj.seller")
    Page<Sale>getReport(@Param("minDate")LocalDate parsedMinDate, 
                        @Param("maxDate") LocalDate parsedMaxDate, 
                        @Param("name")String name, Pageable pageable);

       @Query(value = "SELECT NEW com.devsuperior.dsmeta.dto.SummaryDTO(obj.seller.name, SUM(obj.amount)) FROM Sale obj " +
              "WHERE obj.date BETWEEN :minDate AND :maxDate " +
               "GROUP BY obj.seller.name")
     Page<SummaryDTO>getSummary(@Param("minDate")LocalDate parsedMinDate, 
                                @Param("maxDate") LocalDate parsedMaxDate, 
                                Pageable pageble);

}
