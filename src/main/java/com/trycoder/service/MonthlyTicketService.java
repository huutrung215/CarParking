package com.trycoder.service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.trycoder.model.MonthlyTicket;
import com.trycoder.repository.MonthlyTicketRepository;

@Service
public class MonthlyTicketService {
	@Autowired
	MonthlyTicketRepository monthlyTicketRepo;
	
	public MonthlyTicketService(MonthlyTicketRepository monthlyTicketRepo) {
		this.monthlyTicketRepo = monthlyTicketRepo;
	}
	
	public List<MonthlyTicket> getAllMonthlyTicket() {
		return monthlyTicketRepo.findAll(Sort.by(Sort.Direction.DESC, "ticketId"));
	}
	
	 public List<MonthlyTicket> getMonthlyTicketAvailable() {
		 List<MonthlyTicket> monthlyTickets = getAllMonthlyTicket();
		 List<MonthlyTicket> result = new ArrayList<>();
		 
		 for (MonthlyTicket monthlyTicket : monthlyTickets) {
			 if (monthlyTicket.getEndDate().isAfter(LocalDateTime.now())) {
				 result.add(monthlyTicket);
			 }
		 }
        return result;
    }
	 
	 public Long calMonthlyTicketPriceFromLastMonth() {
	    LocalDateTime currentTime = LocalDateTime.now();
	    YearMonth lastMonth = YearMonth.from(currentTime.minusMonths(1));
	    LocalDateTime firstDayOfMonth = lastMonth.atDay(1).atStartOfDay();
	    LocalDateTime lastDayOfMonth = lastMonth.atEndOfMonth().atTime(23, 59, 59);
	    List<MonthlyTicket> tickets = monthlyTicketRepo.findByEndDateIsNotNull();
	    Long revenue = 0L;
	    for (MonthlyTicket ticket : tickets) {
	    	if (ticket.getEndDate() != null && ticket.getEndDate().isAfter(firstDayOfMonth) 
	    			&& ticket.getEndDate().isBefore(lastDayOfMonth))
	    	revenue += ticket.getPrice();
	    }
	    return revenue;
	  }  
}
