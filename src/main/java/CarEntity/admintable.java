package CarEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "admintable")
public class admintable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int aid;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, name = "password_hash")
    private String passwordHash;

    @Column(nullable = false)
    private String role;

    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<UserTable> users;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<CarsTable> cars;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public int getAid() { 
    		return aid; 
    		}
    public void setAid(int aid) { 
    		this.aid = aid; 
    		}

    public String getUsername() { 
    		return username; 
    	}
    public void setUsername(String username) { 
    		this.username = username; 
    		}

    public String getEmail() { 
    		return email; 
    		}
    public void setEmail(String email) { 
    		this.email = email; 
    		}

    public String getPasswordHash() { 
    		return passwordHash; 
    		}
    public void setPasswordHash(String passwordHash) { 
    		this.passwordHash = passwordHash; 
    		}

    public String getRole() {
    		return role; 
    		}
    public void setRole(String role) { 
    		this.role = role; 
    		}

    public LocalDateTime getCreatedAt() { 
    		return createdAt; 
    		}
    public LocalDateTime getUpdatedAt() { 
    		return updatedAt; 
    		}

    public List<UserTable> getUsers() { 
    		return users; 
    		}
    public void setUsers(List<UserTable> users) { 
    		this.users = users; 
    		}

    public List<CarsTable> getCars() { 
    		return cars; 
    		}
    public void setCars(List<CarsTable> cars) { 
    		this.cars = cars; 
    		}

    @Override
    public int hashCode() { 
    		return Objects.hash(aid); 
    		}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof admintable)) return false;
        admintable other = (admintable) obj;
        return aid == other.aid;
    }

    @Override
    public String toString() {
        return "AdminTable [aid=" + aid + ", username=" + username + ", email=" + email + "]";
    }
}
