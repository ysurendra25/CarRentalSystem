package CarEntity;

import java.sql.Date;
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
@Table(name="admintable")
public class admintable {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int aid;
     private String username;
     private String email;
     private String password_hash;
     private String role;
     @Column(updatable = false)
     private LocalDateTime createdAt;

     private LocalDateTime updatedAt;
     
     //relations
     @OneToMany(mappedBy = "admin",cascade = CascadeType.ALL)
     private List<UserTable> user;
     
     @OneToMany(mappedBy = "admin",cascade=CascadeType.ALL)
     private List<CarsTable> cars;
     
     
     @PrePersist
     protected void onCreate() {
         createdAt = LocalDateTime.now();
     }

     @PreUpdate
     protected void onUpdate() {
         updatedAt = LocalDateTime.now();
     }
     
     

	 public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	 public void setCreatedAt(LocalDateTime createdAt) {
		 this.createdAt = createdAt;
	 }

	 public LocalDateTime getUpdatedAt() {
		 return updatedAt;
	 }

	 public void setUpdatedAt(LocalDateTime updatedAt) {
		 this.updatedAt = updatedAt;
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
	 public String getPassword_hash() {
		 return password_hash;
	 }
	 public void setPassword_hash(String password_hash) {
		 this.password_hash = password_hash;
	 }
	 public String getRole() {
		 return role;
	 }
	 public void setRole(String role) {
		 this.role = role;
	 }

	 @Override
	 public int hashCode() {
		return Objects.hash(aid, createdAt, email, password_hash, role, updatedAt, username);
	 }

	 @Override
	 public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		admintable other = (admintable) obj;
		return aid == other.aid && Objects.equals(createdAt, other.createdAt) && Objects.equals(email, other.email)
				&& Objects.equals(password_hash, other.password_hash) && Objects.equals(role, other.role)
				&& Objects.equals(updatedAt, other.updatedAt) && Objects.equals(username, other.username);
	 }

	 @Override
	 public String toString() {
		return "admintable [aid=" + aid + ", username=" + username + ", email=" + email + ", password_hash="
				+ password_hash + ", role=" + role + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	 }
	
	 
     
     
     
     
}
