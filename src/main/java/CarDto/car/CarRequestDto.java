package CarDto.car;

import java.math.BigDecimal;

import CarEntity.availabilty_status;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CarRequestDto {
	@NotBlank
	private String brand;
	@NotBlank
	private String model;
	@NotNull() @Min(1950) @Max(2050)
	private Integer year;
	@NotBlank
	private String registrationNumber;
	@NotNull
	@Positive
	private BigDecimal pricePerDay;
	@NotNull
	private availabilty_status availabilityStatus;
	
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
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	public BigDecimal getPricePerDay() {
		return pricePerDay;
	}
	public void setPricePerDay(BigDecimal pricePerDay) {
		this.pricePerDay = pricePerDay;
	}
	public availabilty_status getAvailabilityStatus() {
		return availabilityStatus;
	}
	public void setAvailabilityStatus(availabilty_status availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}
	
	

}
