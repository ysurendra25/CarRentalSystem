package CarService;

import java.util.List;

import CarDto.booking.BookingRequestDto;
import CarDto.booking.BookingResponseDto;
import CarEntity.BookingsTable;
import CarEntity.UserTable;

public interface BookingService {

	
	BookingResponseDto createBooking(int carId,BookingRequestDto request);
	
	BookingResponseDto getBookingById(int bookingId);
	
	List<BookingResponseDto> getMyBooking();
	
	List<BookingResponseDto> getAllBookings();
	
	BookingResponseDto updateBooking(int bookingId,BookingRequestDto request);
	
	BookingResponseDto partialUpdateBooking(int bookingId,BookingRequestDto request);
	
	String deleteBooking(int bookingId);
	
	BookingResponseDto returnCar(int bookingId);
	
	UserTable getLoggedUser();
	
}
