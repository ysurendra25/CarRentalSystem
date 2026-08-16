package CarEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "carstable")
public class CarsTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private int carId;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false, unique = true, name = "registration_number")
    private String registrationNumber;

    @Positive
    @Column(nullable = false, precision = 10, scale = 2, name = "price_per_day")
    private BigDecimal pricePerDay;

    @Enumerated(EnumType.STRING)
    @Column(name = "availabilty_status", nullable = false)
    private availabilty_status availabilityStatus;

    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @ManyToOne
    @JoinColumn(name = "aid")
    private admintable admin;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserTable user;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<BookingsTable> bookings;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<CartTable> cartItems;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateAt = LocalDateTime.now();
    }

    public int getCarId() { return carId; }
    public void setCarId(int carId) { this.carId = carId; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public BigDecimal getPricePerDay() { return pricePerDay; }
    public void setPricePerDay(BigDecimal pricePerDay) { this.pricePerDay = pricePerDay; }

    public availabilty_status getAvailabilityStatus() { return availabilityStatus; }
    public void setAvailabilityStatus(availabilty_status availabilityStatus) { this.availabilityStatus = availabilityStatus; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdateAt() { return updateAt; }

    public admintable getAdmin() { return admin; }
    public void setAdmin(admintable admin) { this.admin = admin; }

    public UserTable getUser() { return user; }
    public void setUser(UserTable user) { this.user = user; }

    public List<BookingsTable> getBookings() { return bookings; }
    public void setBookings(List<BookingsTable> bookings) { this.bookings = bookings; }

    public List<CartTable> getCartItems() { return cartItems; }
    public void setCartItems(List<CartTable> cartItems) { this.cartItems = cartItems; }

    @Override
    public int hashCode() { return Objects.hash(carId); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CarsTable)) return false;
        CarsTable other = (CarsTable) obj;
        return carId == other.carId;
    }

    @Override
    public String toString() {
        return "CarsTable [carId=" + carId + ", brand=" + brand + ", model=" + model + ", year=" + year
                + ", registrationNumber=" + registrationNumber + ", pricePerDay=" + pricePerDay
                + ", availabilityStatus=" + availabilityStatus + "]";
    }
}
