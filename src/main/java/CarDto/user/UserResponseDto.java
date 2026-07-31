package CarDto.user;

public class UserResponseDto {

    private int userId;
    private String pname;
    private String email;
    private String phone;
    private String role;

    public UserResponseDto() {
    }

    public UserResponseDto(int userId, String pname, String email, String phone, String role) {
        this.userId = userId;
        this.pname = pname;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}