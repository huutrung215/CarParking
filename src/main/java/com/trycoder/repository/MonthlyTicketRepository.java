package com.trycoder.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trycoder.model.MonthlyTicket;

public interface MonthlyTicketRepository extends JpaRepository<MonthlyTicket, Long> {

	List<MonthlyTicket> findByEndDateBetween(LocalDateTime firstDayOfMonth, LocalDateTime lastDayOfMonth);

	List<MonthlyTicket> findByEndDateIsNotNull();

}
