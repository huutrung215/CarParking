package com.trycoder.dto;

import com.trycoder.model.Parking;
import com.trycoder.model.Position;
import com.trycoder.model.PositionCondition;
import com.trycoder.model.PositionStatus;
import java.util.List;
import java.util.ArrayList;

public class PositionDto {
	private Long id;
    private String positionName;
    private PositionStatus status;
    private String description;
    private List<Parking> parkings;
    private PositionCondition condition;

    public PositionDto() {}

    public PositionDto(Position position) {
    	this.id = position.getPositionId();
        this.positionName = position.getPositionName();
        this.status = position.getStatus();
        this.description = position.getDescription();
        this.condition = position.getCondition();
        this.parkings = new ArrayList<>();
        for(Parking parking: position.getParkings()) {
            this.parkings.add(parking);
        }
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public PositionStatus getStatus() {
		return status;
	}

	public void setStatus(PositionStatus positionStatus) {
		this.status = positionStatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
	public List<Parking> getParkings() {
		return parkings;
	}

	public void setParkings(List<Parking> parkingDtos) {
		this.parkings = parkingDtos;
	}

	public PositionCondition getCondition() {
		return condition;
	}

	public void setCondition(PositionCondition positionCondition) {
		this.condition = positionCondition;
	}
    
}
