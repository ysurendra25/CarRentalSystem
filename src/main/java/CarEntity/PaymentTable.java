package CarEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
public class PaymentTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int payment_id;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private BookingsTable booking;

    private BigDecimal amount;

    private LocalDateTime payment_date;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private payment_method payment_method;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private payment_status payment_status;
    @Column(name = "razorpay_order_id")
    private String razorpayOrderId;

    @Column(name = "razorpay_payment_id")
    private String razorpayPaymentId;

    @Column(name = "razorpay_signature")
    private String razorpaySignature;
    @PrePersist
    protected void onPayDate() {

        if (payment_date == null) {
            payment_date = LocalDateTime.now();
        }

        if (payment_status == null) {
            payment_status = payment_status.pending;
        }
    }

    
    public String getRazorpayOrderId() {
		return razorpayOrderId;
	}

	public void setRazorpayOrderId(String razorpayOrderId) {
		this.razorpayOrderId = razorpayOrderId;
	}

	public String getRazorpayPaymentId() {
		return razorpayPaymentId;
	}

	public void setRazorpayPaymentId(String razorpayPaymentId) {
		this.razorpayPaymentId = razorpayPaymentId;
	}

	public String getRazorpaySignature() {
		return razorpaySignature;
	}


	public void setRazorpaySignature(String razorpaySignature) {
		this.razorpaySignature = razorpaySignature;
	}

	public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public BookingsTable getBooking() {
        return booking;
    }

    public void setBooking(BookingsTable booking) {
        this.booking = booking;
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
        return Objects.hash(amount, booking, payment_date, payment_id,
                payment_method, payment_status);
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

        return Objects.equals(amount, other.amount)
                && Objects.equals(booking, other.booking)
                && Objects.equals(payment_date, other.payment_date)
                && payment_id == other.payment_id
                && payment_method == other.payment_method
                && payment_status == other.payment_status;
    }

    @Override
    public String toString() {
        return "PaymentTable [payment_id=" + payment_id
                + ", booking=" + booking
                + ", amount=" + amount
                + ", payment_date=" + payment_date
                + ", payment_method=" + payment_method
                + ", payment_status=" + payment_status + "]";
    }
}