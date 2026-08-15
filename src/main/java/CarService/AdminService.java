package CarService;

import java.util.List;

import CarDto.booking.BookingResponseDto;
import CarDto.car.CarResponseDto;
import CarDto.owner.OwnerResponseDto;
import CarDto.payment.PaymentResponseDto;
import CarDto.user.UserResponseDto;

public interface AdminService {

	List<OwnerResponseDto> getOwnerRequest();
	
	OwnerResponseDto approveOwner(int userId);
	
	OwnerResponseDto rejectOwner(int userId);
	
	List<UserResponseDto> getAllUsers();

	List<CarResponseDto> getAllCars();

	List<BookingResponseDto> getAllBookings();

	List<PaymentResponseDto> getAllPayments();
	
}
