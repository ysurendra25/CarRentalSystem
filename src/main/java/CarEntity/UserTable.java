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
@Table(name="usertable")
public class UserTable {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private int user_id;
      private String pname;
      private String email;
      private String phone;
      private String password_hash;
      @Enumerated(EnumType.STRING)
      @Column(columnDefinition = "ENUM('admin','owner','customer')")
      private role role;
      @Column(updatable = false)
      private LocalDateTime created_at;
      private LocalDateTime update_at;
      @Lob
      @Column(columnDefinition = "LONGTEXT")
      private String address;
      
      //relations
      @ManyToOne
      @JoinColumn(name = "aid")
      private admintable admin;
      
      @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
      private List<CarsTable> cars;
      
      @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
      private List<BookingsTable> bookings;
      
      @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
      private List<CartTable> cartItems;
      
      
      @PrePersist
      protected void onCreate() {
		created_at = LocalDateTime.now();
	  }
      @PreUpdate
      protected void onUpdate() {
		update_at = LocalDateTime.now();
	  }
      
      
	  public int getUser_id() {
		  return user_id;
	  }
	  public void setUser_id(int user_id) {
		  this.user_id = user_id;
	  }
	  public String getPname() {
		  return pname;
	  }
	  public void setPname(String pname) {
		  this.pname = pname;
	  }
	  public String getEmail() {
		  return email;
	  }
	  public void setEmail(String email) {
		  this.email = email;
	  }
	  public String getPhone() {
		  return phone;
	  }
	  public void setPhone(String phone) {
		  this.phone = phone;
	  }
	  public String getPassword_hash() {
		  return password_hash;
	  }
	  public void setPassword_hash(String password_hash) {
		  this.password_hash = password_hash;
	  }
	  public role getRole() {
		  return role;
	  }
	  public void setRole(role role) {
		  this.role = role;
	  }
	  public LocalDateTime getCreated_at() {
		  return created_at;
	  }
	  public void setCreated_at(LocalDateTime created_at) {
		  this.created_at = created_at;
	  }
	  public String getAddress() {
		  return address;
	  }
	  public void setAddress(String address) {
		  this.address = address;
	  }
	  @Override
	public int hashCode() {
		return Objects.hash(address, created_at, email, password_hash, phone, pname, role, update_at, user_id);
	}
	  public LocalDateTime getUpdate_at() {
		  return update_at;
	  }
	  public void setUpdate_at(LocalDateTime update_at) {
		  this.update_at = update_at;
	  }
	  @Override
	  public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserTable other = (UserTable) obj;
		return Objects.equals(address, other.address) && Objects.equals(created_at, other.created_at)
				&& Objects.equals(email, other.email) && Objects.equals(password_hash, other.password_hash)
				&& Objects.equals(phone, other.phone) && Objects.equals(pname, other.pname) && role == other.role
				&& Objects.equals(update_at, other.update_at) && user_id == other.user_id;
	  }
	  @Override
	  public String toString() {
		return "UserTable [user_id=" + user_id + ", pname=" + pname + ", email=" + email + ", phone=" + phone
				+ ", password_hash=" + password_hash + ", role=" + role + ", created_at=" + created_at + ", update_at="
				+ update_at + ", address=" + address + "]";
	  }
	
      
      
      
}
