package CarService.impl;

import java.util.List;

import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import CarDto.booking.BookingRequestDto;
import CarDto.booking.BookingResponseDto;
import CarEntity.BookingsTable;
import CarEntity.CarsTable;
import CarEntity.UserTable;
import CarRepo.BookingRepository;
import CarRepo.CartRepository;
import CarRepo.UserRepository;
import CarService.BookingService;
import CarService.CarService;
import Security1.JwtService;
import CarRepo.CarRepository;

@Service
public class BookingServiceImpl implements BookingService{

	private BookingRepository bookingRepo;
	private UserRepository userRepo;
	private CarRepository carRepo;
	private JwtService jwtService;
	public BookingServiceImpl(BookingRepository bookingRepo, UserRepository userRepo, CarRepository carRepo,
			JwtService jwtService) {
		super();
		this.bookingRepo = bookingRepo;
		this.userRepo = userRepo;
		this.carRepo = carRepo;
		this.jwtService = jwtService;
	}
	
	org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	@Override
	public UserTable getLoggedUser() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		return userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("User Not Found"));
	}
	
	public BookingResponseDto createBooking(int carId,BookingRequestDto request) {
		UserTable user = getLoggedUser();
		CarsTable car = carRepo.findById(carId).orElseThrow(()->new RuntimeException("Car Not Found"));
		
		
		return null;
	}

	@Override
	public BookingResponseDto getBookingById(int bookingId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookingResponseDto> getMyBooking() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookingResponseDto> getAllBookings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookingResponseDto updateBooking(int bookingId, BookingRequestDto request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookingResponseDto partialUpdateBooking(int bookingId, BookingRequestDto request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBooking(int bookingId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BookingResponseDto returnCar(int bookingId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
