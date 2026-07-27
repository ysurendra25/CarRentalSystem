package CarEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookings")
public class BookingsTable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int booking_id;
    private int user_id;
    private int car_id;
    private LocalDateTime start_date;
    private LocalDateTime end_date;
    private LocalDateTime return_date;
    private BigDecimal total_price;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private status status;
    @Column(updatable = false)
    private LocalDateTime created_at;
    private LocalDateTime update_at;
    private BigDecimal Penalty_amount;
    
    //relations
    @ManyToOne
    @JoinColumn(name="user_id")
    private UserTable user;
    
    @ManyToOne
    @JoinColumn(name="car_id")
    private CarsTable car;
    
    @OneToOne(mappedBy = "booking",cascade = CascadeType.ALL)
    private PaymentTable payment;
    
    
    @PrePersist
    protected void onCreate() {
		created_at = LocalDateTime.now();
	}
    @PreUpdate
    protected void onUpdate() {
		update_at = LocalDateTime.now();
	}
	public int getBooking_id() {
		return booking_id;
	}
	public void setBooking_id(int booking_id) {
		this.booking_id = booking_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getCar_id() {
		return car_id;
	}
	public void setCar_id(int car_id) {
		this.car_id = car_id;
	}
	public LocalDateTime getStart_date() {
		return start_date;
	}
	public void setStart_date(LocalDateTime start_date) {
		this.start_date = start_date;
	}
	public LocalDateTime getEnd_date() {
		return end_date;
	}
	public void setEnd_date(LocalDateTime end_date) {
		this.end_date = end_date;
	}
	public BigDecimal getTotal_price() {
		return total_price;
	}
	public void setTotal_price(BigDecimal total_price) {
		this.total_price = total_price;
	}
	public status getStatus() {
		return status;
	}
	public void setStatus(status status) {
		this.status = status;
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
	
	
	public BigDecimal getPenalty_amount() {
		return Penalty_amount;
	}
	public void setPenalty_amount(double penalty_amount) {
		Penalty_amount = Penalty_amount;
	}
	@Override
	public int hashCode() {
		return Objects.hash(Penalty_amount, booking_id, car, car_id, created_at, end_date, payment, return_date,
				start_date, status, total_price, update_at, user, user_id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookingsTable other = (BookingsTable) obj;
		return Objects.equals(Penalty_amount, other.Penalty_amount) && booking_id == other.booking_id
				&& Objects.equals(car, other.car) && car_id == other.car_id
				&& Objects.equals(created_at, other.created_at) && Objects.equals(end_date, other.end_date)
				&& Objects.equals(payment, other.payment) && Objects.equals(return_date, other.return_date)
				&& Objects.equals(start_date, other.start_date) && status == other.status
				&& Objects.equals(total_price, other.total_price) && Objects.equals(update_at, other.update_at)
				&& Objects.equals(user, other.user) && user_id == other.user_id;
	}
	@Override
	public String toString() {
		return "BookingsTable [booking_id=" + booking_id + ", user_id=" + user_id + ", car_id=" + car_id
				+ ", start_date=" + start_date + ", end_date=" + end_date + ", return_date=" + return_date
				+ ", total_price=" + total_price + ", status=" + status + ", created_at=" + created_at + ", update_at="
				+ update_at + ", Penalty_amount=" + Penalty_amount + ", user=" + user + ", car=" + car + ", payment="
				+ payment + "]";
	}
	
    
    
    
    
    
}
