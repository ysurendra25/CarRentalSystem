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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserTable user;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private CarsTable car;

    @Column(nullable = false)
    private LocalDateTime start_date;

    @Column(nullable = false)
    private LocalDateTime end_date;

    private LocalDateTime return_date;

    @Column(nullable = false)
    private BigDecimal total_price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private status status;

    @Column(updatable = false)
    private LocalDateTime created_at;

    private LocalDateTime update_at;

    @Column(precision = 10, scale = 2)
    private BigDecimal penaltyAmount;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
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

    public UserTable getUser() {
        return user;
    }

    public void setUser(UserTable user) {
        this.user = user;
    }

    public CarsTable getCar() {
        return car;
    }

    public void setCar(CarsTable car) {
        this.car = car;
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

    public LocalDateTime getReturn_date() {
        return return_date;
    }

    public void setReturn_date(LocalDateTime return_date) {
        this.return_date = return_date;
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

    public LocalDateTime getUpdate_at() {
        return update_at;
    }

    public BigDecimal getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(BigDecimal penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    public PaymentTable getPayment() {
        return payment;
    }

    public void setPayment(PaymentTable payment) {
        this.payment = payment;
    }

    @Override
    public int hashCode() {
        return Objects.hash(booking_id);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        if (!(obj instanceof BookingsTable))
            return false;

        BookingsTable other = (BookingsTable) obj;

        return booking_id == other.booking_id;
    }

    @Override
    public String toString() {
        return "BookingsTable [booking_id=" + booking_id + "]";
    }
}