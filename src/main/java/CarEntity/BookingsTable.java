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
    @Column(name = "booking_id")
    private int bookingId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserTable user;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private CarsTable car;

    @Column(nullable = false, name = "start_date")
    private LocalDateTime startDate;

    @Column(nullable = false, name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @Column(nullable = false, name = "total_price")
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private status status;

    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Column(precision = 10, scale = 2)
    private BigDecimal penaltyAmount;

    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
    private PaymentTable payment;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateAt = LocalDateTime.now();
    }

    public int getBookingId() { 
    		return bookingId; 
    		}
    public void setBookingId(int bookingId) { 
    		this.bookingId = bookingId;
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

    public LocalDateTime getStartDate() { 
    		return startDate; 
    		}
    public void setStartDate(LocalDateTime startDate) { 
    		this.startDate = startDate; 
    		}

    public LocalDateTime getEndDate() { 
    		return endDate; 
    		}
    public void setEndDate(LocalDateTime endDate) { 
    		this.endDate = endDate; 
    		}

    public LocalDateTime getReturnDate() { 
    		return returnDate; 
    		}
    public void setReturnDate(LocalDateTime returnDate) { 
    		this.returnDate = returnDate; 
    		}

    public BigDecimal getTotalPrice() { 
    		return totalPrice; 
    		}
    public void setTotalPrice(BigDecimal totalPrice) { 
    		this.totalPrice = totalPrice; 
    		}

    public status getStatus() { 
    		return status; 
    		}
    public void setStatus(status status) { 
    		this.status = status; 
    		}

    public LocalDateTime getCreatedAt() { 
    		return createdAt; 
    		}
    public LocalDateTime getUpdateAt() { 
    		return updateAt;
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
    		return Objects.hash(bookingId); 
    		}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BookingsTable)) return false;
        BookingsTable other = (BookingsTable) obj;
        return bookingId == other.bookingId;
    }

    @Override
    public String toString() {
        return "BookingsTable [bookingId=" + bookingId + "]";
    }
}
