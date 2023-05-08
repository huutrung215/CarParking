package com.trycoder.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Position {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long positionId;	
	private String positionName;
	private String description;
	
	@Enumerated(EnumType.STRING)
	private PositionStatus status;
	
	@Enumerated(EnumType.STRING)
	private PositionCondition condition;
	
	 public PositionCondition getCondition() {
		return condition;
	}

	public void setCondition(PositionCondition condition) {
		this.condition = condition;
	}

	@OneToOne(mappedBy = "position")
    private Parking parking;

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getDescription() {
		return description;
	}

	public Parking getParking() {
		return parking;
	}

	public void setParking(Parking parking) {
		this.parking = parking;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public PositionStatus getStatus() {
		return status;
	}

	public void setStatus(PositionStatus status) {
		this.status = status;
	}

	public Parking getPosition() {
		return parking;
	}

	public void setPosition(Parking parking) {
		this.parking = parking;
	}
}
