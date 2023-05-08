package com.trycoder.model;

public enum PositionStatus {
	AVAILABLE, // chỗ đỗ xe có thể được đặt
    BOOKED, // chỗ đỗ xe đã được đặt nhưng chưa thanh toán
    OCCUPIED // chỗ đỗ xe đã được đặt và đã được thanh toán
}
