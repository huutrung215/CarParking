package com.trycoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trycoder.model.MonthlyTicket;

public interface MonthlyTicketRepository extends JpaRepository<MonthlyTicket, Long> {

}
