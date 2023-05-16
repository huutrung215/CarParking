package com.trycoder.model;

import java.util.List;

import com.trycoder.dto.ParkingDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"positionName"}))
public class Position {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long positionId;
	
	@NotBlank
	@Pattern(regexp="^[A-Za-z]\\d{2}$")
	private String positionName;

	@NotBlank
	private String description;
	
	@Enumerated(EnumType.STRING)
	private PositionStatus status;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private PositionCondition condition;
	
	 public PositionCondition getCondition() {
		return condition;
	}

	public void setCondition(PositionCondition condition) {
		this.condition = condition;
	}
	
	@OneToMany(mappedBy = "position", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Parking> parkings;

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

	
	public List<Parking> getParkings() {
		return parkings;
	}

	public void setParkings(List<Parking> parkings) {
		this.parkings = parkings;
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

}
