package CarEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "carstable")
public class CarsTable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int car_id;
	private String brand;
	private String model;
	@Column(nullable = false,length = 4)
	private int year;
	private String registration_number;
	@Column(precision = 10,scale = 2)
	@Positive
	private BigDecimal price_per_day;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private availabilty_status availabilty_status;
	@Column(updatable = false)
	LocalDateTime created_at;
	LocalDateTime update_at;
	@PrePersist
	protected void onCreate() {
		created_at = LocalDateTime.now();
	}
	@PreUpdate
	protected void onUpdate() {
		update_at = LocalDateTime.now();
	}
	public int getCar_id() {
		return car_id;
	}
	public void setCar_id(int car_id) {
		this.car_id = car_id;
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
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getRegistration_number() {
		return registration_number;
	}
	public void setRegistration_number(String registration_number) {
		this.registration_number = registration_number;
	}
	public BigDecimal getPrice_per_day() {
		return price_per_day;
	}
	public void setPrice_per_day(BigDecimal price_per_day) {
		this.price_per_day = price_per_day;
	}
	public availabilty_status getAvailabilty_status() {
		return availabilty_status;
	}
	public void setAvailabilty_status(availabilty_status availabilty_status) {
		this.availabilty_status = availabilty_status;
	}
	public LocalDateTime getCreated_at() {
		return created_at;
	}
	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}
	public LocalDateTime getUpdate_at() {
		return update_at;
	}
	public void setUpdate_at(LocalDateTime update_at) {
		this.update_at = update_at;
	}
	@Override
	public int hashCode() {
		return Objects.hash(availabilty_status, brand, car_id, created_at, model, price_per_day, registration_number,
				update_at, year);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarsTable other = (CarsTable) obj;
		return availabilty_status == other.availabilty_status && Objects.equals(brand, other.brand)
				&& car_id == other.car_id && Objects.equals(created_at, other.created_at)
				&& Objects.equals(model, other.model) && Objects.equals(price_per_day, other.price_per_day)
				&& Objects.equals(registration_number, other.registration_number)
				&& Objects.equals(update_at, other.update_at) && Objects.equals(year, other.year);
	}
	@Override
	public String toString() {
		return "CarsTable [car_id=" + car_id + ", brand=" + brand + ", model=" + model + ", year=" + year
				+ ", registration_number=" + registration_number + ", price_per_day=" + price_per_day
				+ ", availabilty_status=" + availabilty_status + ", created_at=" + created_at + ", update_at="
				+ update_at + "]";
	}
	
	
	
}
