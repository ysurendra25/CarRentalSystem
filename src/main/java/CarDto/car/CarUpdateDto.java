package CarDto.car;

import java.math.BigDecimal;

import CarEntity.availabilty_status;

public class CarUpdateDto {


	    private String brand;

	    private String model;

	    private Integer year;

	    private String registrationNumber;

	    private BigDecimal pricePerDay;

	    private availabilty_status availabiltyStatus;

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

		public availabilty_status getAvailabiltyStatus() {
			return availabiltyStatus;
		}

		public void setAvailabiltyStatus(availabilty_status availabiltyStatus) {
			this.availabiltyStatus = availabiltyStatus;
		}

	    
	    
}
