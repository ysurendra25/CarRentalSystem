package CarEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumeratedValue;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
public class PaymentTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int payment_id;
	private int booking_id;
	private BigDecimal amount;
	
	private LocalDateTime payment_date;
	@Enumerated(EnumType.STRING)
	@Column(name="payment_method",nullable=false)
	private payment_method payment_method;
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_status",nullable = false)
	private payment_status payment_status;
	
	@OneToOne
	@JoinColumn(name="booking_id")
	private BookingsTable booking;
	
	
	 @PrePersist
	    protected void onPayDate() {
	        if (this.payment_date == null) {
	            this.payment_date = LocalDateTime.now();
	        }
	        if (this.payment_status == null) {
	            this.payment_status = payment_status.pending;
	        }
	    }

	 public int getPayment_id() {
		 return payment_id;
	 }

	 public void setPayment_id(int payment_id) {
		 this.payment_id = payment_id;
	 }

	 public int getBooking_id() {
		 return booking_id;
	 }

	 public void setBooking_id(int booking_id) {
		 this.booking_id = booking_id;
	 }

	 public BigDecimal getAmount() {
		 return amount;
	 }

	 public void setAmount(BigDecimal amount) {
		 this.amount = amount;
	 }

	 public LocalDateTime getPayment_date() {
		 return payment_date;
	 }

	 public void setPayment_date(LocalDateTime payment_date) {
		 this.payment_date = payment_date;
	 }

	 public payment_method getPayment_method() {
		 return payment_method;
	 }

	 public void setPayment_method(payment_method payment_method) {
		 this.payment_method = payment_method;
	 }

	 public payment_status getPayment_status() {
		 return payment_status;
	 }

	 public void setPayment_status(payment_status payment_status) {
		 this.payment_status = payment_status;
	 }

	 @Override
	 public int hashCode() {
		return Objects.hash(amount, booking_id, payment_date, payment_id, payment_method, payment_status);
	 }

	 @Override
	 public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaymentTable other = (PaymentTable) obj;
		return Objects.equals(amount, other.amount) && booking_id == other.booking_id
				&& Objects.equals(payment_date, other.payment_date) && payment_id == other.payment_id
				&& payment_method == other.payment_method && payment_status == other.payment_status;
	 }

	 @Override
	 public String toString() {
		return "PaymentTable [payment_id=" + payment_id + ", booking_id=" + booking_id + ", amount=" + amount
				+ ", payment_date=" + payment_date + ", payment_method=" + payment_method + ", payment_status="
				+ payment_status + "]";
	 }
	 
	 
	 
}
