package CarDto.booking;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import CarEntity.status;

public class BookingResponseDto {

	private Integer bookingId;
    private Integer carId;
    private Integer userId;           
    private String userEmail;         
    private String brand;
    private String model;
    private LocalDateTime pickupDateTime;
    private LocalDateTime returnDateTime;
    private LocalDateTime endDateTime;
    private BigDecimal totalPrice;
    private status bookingStatus;
    private BigDecimal penaltyAmount;
	public Integer getBookingId() {
		return bookingId;
	}
	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}
	public Integer getCarId() {
		return carId;
	}
	public void setCarId(Integer carId) {
		this.carId = carId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public LocalDateTime getPickupDateTime() {
		return pickupDateTime;
	}
	public void setPickupDateTime(LocalDateTime pickupDateTime) {
		this.pickupDateTime = pickupDateTime;
	}
	public LocalDateTime getReturnDateTime() {
		return returnDateTime;
	}
	public void setReturnDateTime(LocalDateTime returnDateTime) {
		this.returnDateTime = returnDateTime;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public status getBookingStatus() {
		return bookingStatus;
	}
	public void setBookingStatus(status bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	public BigDecimal getPenaltyAmount() {
		return penaltyAmount;
	}
	public void setPenaltyAmount(BigDecimal penaltyAmount) {
		this.penaltyAmount = penaltyAmount;
	}
	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}
	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}
	
	
	
}
