package CarDto.booking;

import java.time.LocalDateTime;

public class BookingRequestDto {

	private LocalDateTime pickupDateTime;

	private LocalDateTime returnDateTime;

	public LocalDateTime getPickupDateTime() {
		return pickupDateTime;
	}

	public void setPickupDateTime(LocalDateTime pickupDateTime) {
		this.pickupDateTime = pickupDateTime;
	}

	public LocalDateTime getReturnDateTime() {
		return returnDateTime;
	}

	public void setReturnDateTime(LocalDateTime returnDateTime) {
		this.returnDateTime = returnDateTime;
	}
	
	
}
