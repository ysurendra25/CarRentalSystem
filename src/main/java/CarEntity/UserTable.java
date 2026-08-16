package CarEntity;

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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "usertable")
public class UserTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Enumerated(EnumType.STRING)
    private owner_status ownerStatus = owner_status.NONE;

    private String pname;
    private String email;
    private String phone;

    @Column(name = "password_hash")
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private role role;

    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String address;

    @ManyToOne
    @JoinColumn(name = "aid")
    private admintable admin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CarsTable> cars;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BookingsTable> bookings;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CartTable> cartItems;

    private LocalDateTime ownerRejectedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateAt = LocalDateTime.now();
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public owner_status getOwnerStatus() { return ownerStatus; }
    public void setOwnerStatus(owner_status ownerStatus) { this.ownerStatus = ownerStatus; }

    public String getPname() { return pname; }
    public void setPname(String pname) { this.pname = pname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public role getRole() { return role; }
    public void setRole(role role) { this.role = role; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdateAt() { return updateAt; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public admintable getAdmin() { return admin; }
    public void setAdmin(admintable admin) { this.admin = admin; }

    public List<CarsTable> getCars() { return cars; }
    public void setCars(List<CarsTable> cars) { this.cars = cars; }

    public List<BookingsTable> getBookings() { return bookings; }
    public void setBookings(List<BookingsTable> bookings) { this.bookings = bookings; }

    public List<CartTable> getCartItems() { return cartItems; }
    public void setCartItems(List<CartTable> cartItems) { this.cartItems = cartItems; }

    public LocalDateTime getOwnerRejectedAt() { return ownerRejectedAt; }
    public void setOwnerRejectedAt(LocalDateTime ownerRejectedAt) { this.ownerRejectedAt = ownerRejectedAt; }

    @Override
    public int hashCode() { return Objects.hash(userId); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserTable other = (UserTable) obj;
        return userId == other.userId;
    }

    @Override
    public String toString() {
        return "UserTable [userId=" + userId + ", pname=" + pname + ", email=" + email + ", phone=" + phone
                + ", role=" + role + ", address=" + address + "]";
    }
}
