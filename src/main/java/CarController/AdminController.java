package CarController;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CarDto.booking.BookingResponseDto;
import CarDto.car.CarResponseDto;
import CarDto.owner.OwnerResponseDto;
import CarDto.payment.PaymentResponseDto;
import CarDto.user.UserResponseDto;
import CarService.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	private AdminService aservice;
	public AdminController(AdminService aservice) {
		super();
		this.aservice = aservice;
	}


	@GetMapping("/ownerRequests")
	public List<OwnerResponseDto> getAllRequests() {
		List<OwnerResponseDto> resp = aservice.getOwnerRequest();
		
		return resp;
	}
	
	@PutMapping("/approveOwner/{userId}")
	public OwnerResponseDto approveOwner(@PathVariable int userId) {
		OwnerResponseDto resp = aservice.approveOwner(userId);
		
		return resp;
	}
	
	@PutMapping("/rejectOwner/{userId}")
	public OwnerResponseDto rejectOwner(@PathVariable int userId) {
		OwnerResponseDto resp = aservice.rejectOwner(userId);
		
		return resp;
	}
	
	@GetMapping("/getAllUsers")
	public List<UserResponseDto> getAllUsers() {
		List<UserResponseDto> resp = aservice.getAllUsers();
		
		return resp;
				
	}
	
	@GetMapping("/getAllCars")
	public List<CarResponseDto> getAllCars() {
		List<CarResponseDto> resp = aservice.getAllCars();
		
		return resp;
	}
	
	@GetMapping("/getAllBookings")
	public List<BookingResponseDto> getAllBookings() {
		List<BookingResponseDto> resp = aservice.getAllBookings();
	
		return resp;
	}
	
	@GetMapping("/getAllPayments")
	public List<PaymentResponseDto> getAllPayments() {
		List<PaymentResponseDto> resp = aservice.getAllPayments();
		
		return resp;
	}
}
