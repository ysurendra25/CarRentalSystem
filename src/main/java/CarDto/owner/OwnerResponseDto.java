package CarDto.owner;

public class OwnerResponseDto {

	private int userId;
	private String pname;
	private String email;
	private String ownerStatus;
	private String message;
	
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
	public String getOwnerStatus() {
		return ownerStatus;
	}
	public void setOwnerStatus(String ownerStatus) {
		this.ownerStatus = ownerStatus;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
