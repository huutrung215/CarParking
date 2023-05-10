package com.trycoder.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class MonthlyTicket {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;
	
	@Column(nullable = false)
    private LocalDateTime startDate;
	@Column(nullable = false)
    private LocalDateTime endDate;
	private Long price;
    
    // các trường khác nếu cần thiết
    
    @ManyToOne
    @JoinColumn(name = "id")
    private UserDtls user;
    
    @OneToOne(mappedBy = "monthlyTicket")
    private Parking parking;
    
	public Parking getParking() {
		return parking;
	}

	public void setParking(Parking parking) {
		this.parking = parking;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setId(Long ticketId) {
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
		this.endDate = startDate.plusMonths(1);
	}

	public UserDtls getUser() {
		return user;
	}

	public void setUser(UserDtls user) {
		this.user = user;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}
}
