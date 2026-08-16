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
    @Column(name = "payment_id")
    private int paymentId;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private BookingsTable booking;

    private BigDecimal amount;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private payment_method paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private payment_status paymentStatus;

    @Column(name = "razorpay_order_id")
    private String razorpayOrderId;

    @Column(name = "razorpay_payment_id")
    private String razorpayPaymentId;

    @Column(name = "razorpay_signature")
    private String razorpaySignature;

    @PrePersist
    protected void onPayDate() {
        if (paymentDate == null) {
            paymentDate = LocalDateTime.now();
        }
        if (paymentStatus == null) {
            paymentStatus = payment_status.pending;
        }
    }

    public int getPaymentId() { return paymentId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }

    public BookingsTable getBooking() { return booking; }
    public void setBooking(BookingsTable booking) { this.booking = booking; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }

    public payment_method getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(payment_method paymentMethod) { this.paymentMethod = paymentMethod; }

    public payment_status getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(payment_status paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getRazorpayOrderId() { return razorpayOrderId; }
    public void setRazorpayOrderId(String razorpayOrderId) { this.razorpayOrderId = razorpayOrderId; }

    public String getRazorpayPaymentId() { return razorpayPaymentId; }
    public void setRazorpayPaymentId(String razorpayPaymentId) { this.razorpayPaymentId = razorpayPaymentId; }

    public String getRazorpaySignature() { return razorpaySignature; }
    public void setRazorpaySignature(String razorpaySignature) { this.razorpaySignature = razorpaySignature; }

    @Override
    public int hashCode() { return Objects.hash(paymentId); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PaymentTable)) return false;
        PaymentTable other = (PaymentTable) obj;
        return paymentId == other.paymentId;
    }

    @Override
    public String toString() {
        return "PaymentTable [paymentId=" + paymentId + ", amount=" + amount + ", paymentDate=" + paymentDate
                + ", paymentMethod=" + paymentMethod + ", paymentStatus=" + paymentStatus + "]";
    }
}
