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
    private int car_id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false, unique = true)
    private String registration_number;

    @Positive
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price_per_day;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private availabilty_status availabilty_status;

    @Column(updatable = false)
    private LocalDateTime created_at;

    private LocalDateTime update_at;


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

    public LocalDateTime getUpdate_at() {
        return update_at;
    }

    public admintable getAdmin() {
        return admin;
    }

    public void setAdmin(admintable admin) {
        this.admin = admin;
    }

    public UserTable getUser() {
        return user;
    }

    public void setUser(UserTable user) {
        this.user = user;
    }

    public List<BookingsTable> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingsTable> bookings) {
        this.bookings = bookings;
    }

    public List<CartTable> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartTable> cartItems) {
        this.cartItems = cartItems;
    }

    @Override
    public int hashCode() {
        return Objects.hash(car_id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof CarsTable)) return false;
        CarsTable other = (CarsTable) obj;
        return car_id == other.car_id;
    }

    @Override
    public String toString() {
        return "CarsTable [car_id=" + car_id +
                ", brand=" + brand +
                ", model=" + model +
                ", year=" + year +
                ", registration_number=" + registration_number +
                ", price_per_day=" + price_per_day +
                ", availabilty_status=" + availabilty_status + "]";
    }
}