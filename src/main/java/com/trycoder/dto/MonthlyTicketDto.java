package com.trycoder.dto;

import java.time.LocalDateTime;

import com.trycoder.model.MonthlyTicket;
import com.trycoder.model.UserDtls;


public class MonthlyTicketDto {
	private Long ticketId;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private Long price;
	private UserDtls user;
	
	public MonthlyTicketDto() {}
	
	public MonthlyTicketDto(MonthlyTicket monlyTicket) {
		this.ticketId = monlyTicket.getTicketId();
		this.startDate = monlyTicket.getStartDate();
		this.endDate = monlyTicket.getEndDate();
		this.price = monlyTicket.getPrice();
		this.user = monlyTicket.getUser();
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public UserDtls getUser() {
		return user;
	}

	public void setUser(UserDtls user) {
		this.user = user;
	}
}
