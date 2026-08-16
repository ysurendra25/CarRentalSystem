package CarController;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CarDto.booking.BookingRequestDto;
import CarDto.booking.BookingResponseDto;
import CarService.BookingService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/booking")
public class BookingController {

	private BookingService bookingService;
	public BookingController(BookingService bookingService) {
		super();
		this.bookingService = bookingService;
	}

	@PostMapping("/createBooking/{carId}")
	public ResponseEntity<BookingResponseDto> createBooking(@PathVariable int carId,@Valid @RequestBody BookingRequestDto req) {
		BookingResponseDto resp = bookingService.createBooking(carId, req);
		
		return ResponseEntity.ok(resp);
	}
	
	@GetMapping("/getbookingDetails/{bookingId}")
	public BookingResponseDto getBookingById(@PathVariable int bookingId) {
		BookingResponseDto resp = bookingService.getBookingById(bookingId);
		
		return resp;
	}
	
	@GetMapping("/getBookings")
	public ResponseEntity<List<BookingResponseDto>> getBooking() {
		List<BookingResponseDto> resp = bookingService.getMyBooking();
		
		return ResponseEntity.ok(resp);
	}
	
	@GetMapping("/getAllBookings")
	public ResponseEntity<List<BookingResponseDto>> getAllBookings() {
		List<BookingResponseDto> resp = bookingService.getAllBookings();
		
		return ResponseEntity.ok(resp);
	}
	
	@PutMapping("/updateBooking/{bookingId}")
	public BookingResponseDto updateBooking(@PathVariable int bookingId,@RequestBody BookingRequestDto req) {
		BookingResponseDto resp = bookingService.updateBooking(bookingId, req);
		
		return resp;
	}
	
	@DeleteMapping("/deleteBooking/{bookingId}")
	public String deleteBooking(@PathVariable int bookingId) {
		bookingService.deleteBooking(bookingId);
		
		return "deleted booking!";
	}
	
	@PostMapping("/returncar/{bookingId}")
	public BookingResponseDto returnCar(@PathVariable int bookingId) {
		BookingResponseDto resp = bookingService.returnCar(bookingId);
		
		return resp;
	}
	
}
