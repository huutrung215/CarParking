package com.trycoder.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.trycoder.model.MonthlyTicket;
import com.trycoder.model.ParkingPrice;
import com.trycoder.repository.MonthlyTicketRepository;
import com.trycoder.repository.ParkingPriceRepository;

@Service
public class MonthlyTicketService {
	@Autowired
	MonthlyTicketRepository monthlyTicketRepo;
	
	@Autowired
	ParkingPriceService parkingPrices;
	
	public MonthlyTicketService(MonthlyTicketRepository monthlyTicketRepo) {
		this.monthlyTicketRepo = monthlyTicketRepo;
	}
	
	public List<MonthlyTicket> getAllMonthlyTicket() {
		return monthlyTicketRepo.findAll(Sort.by(Sort.Direction.DESC, "ticketId"));
	}
	
	 public List<MonthlyTicket> getMonthlyTicketAvailable() {
		 List<MonthlyTicket> monthlyTickets = getAllMonthlyTicket();
			/*
			 * List<MonthlyTicket> result = new ArrayList<>();
			 * 
			 * for (MonthlyTicket monthlyTicket : monthlyTickets) { if
			 * (monthlyTicket.getEndDate().isAfter(LocalDateTime.now())) {
			 * result.add(monthlyTicket); } }
			 */
        return monthlyTickets;
    }
	 
	 public Long calMonthlyTicketPriceFromLastMonth() {
	    LocalDateTime currentTime = LocalDateTime.now();
	    LocalDateTime startOfMonth = currentTime.withDayOfMonth(1).with(LocalTime.MIN);
	    List<MonthlyTicket> tickets = monthlyTicketRepo.findByEndDateIsNotNull();
	    ParkingPrice parkingPrice = parkingPrices.getParkingPriceById(1);
	    Long revenue = 0L;
	    for (MonthlyTicket ticket : tickets) {
	    	if (ticket.getEndDate() != null && ticket.getEndDate().isAfter(startOfMonth) 
	    			&& ticket.getEndDate().isBefore(currentTime))
	    	revenue += parkingPrice.getPriceMonth();
	    }
	    return revenue;
	  }  
}
