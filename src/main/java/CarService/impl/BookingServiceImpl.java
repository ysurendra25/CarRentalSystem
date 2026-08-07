package CarService.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Authentication;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import CarDto.booking.BookingRequestDto;
import CarDto.booking.BookingResponseDto;
import CarEntity.BookingsTable;
import CarEntity.CarsTable;
import CarEntity.UserTable;
import CarEntity.availabilty_status;
import CarEntity.status;
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
	
	@Override
	@jakarta.transaction.Transactional
	public BookingResponseDto createBooking(int carId,BookingRequestDto request) {
		UserTable user = getLoggedUser();
		CarsTable car = carRepo.findById(carId).orElseThrow(()->new RuntimeException("Car Not Found"));
		if(!car.getAvailabilty_status().equals(availabilty_status.available) ){
			throw new RuntimeException("Car is not available....No problem get another best car");
		}
		if (!request.getReturnDateTime().isAfter(request.getPickupDateTime())) {
		    throw new RuntimeException("Return date must be after pickup date.");
		}
		BookingsTable booking = new BookingsTable();
		booking.setUser(user);
		booking.setCar(car);
		booking.setStart_date(request.getPickupDateTime());
		booking.setEnd_date(request.getReturnDateTime());
		
		BigDecimal totalprice = calculateTotalPrice(request, car);
		booking.setTotal_price(totalprice);
		booking.setStatus(status.pending);
		booking.setPenaltyAmount(BigDecimal.ZERO);
		
		BookingsTable saveBooking = bookingRepo.save(booking);
		car.setAvailabilty_status(availabilty_status.booked);
	    carRepo.save(car);
		
	    return convertToResponse(saveBooking);
	}
	
	private BookingResponseDto convertToResponse(BookingsTable booking) {

	    BookingResponseDto resp = new BookingResponseDto();

	    resp.setBookingId(booking.getBooking_id());

	    resp.setCarId(booking.getCar().getCar_id());
	    resp.setBrand(booking.getCar().getBrand());
	    resp.setModel(booking.getCar().getModel());

	    resp.setPickupDateTime(booking.getStart_date());
	    resp.setReturnDateTime(booking.getEnd_date());

	    resp.setBookingStatus(booking.getStatus());

	    resp.setTotalPrice(booking.getTotal_price());
	    resp.setPenaltyAmount(booking.getPenaltyAmount());

	    return resp;
	}
	
	private BigDecimal calculateTotalPrice(BookingRequestDto req,CarsTable car) {
		
		BigDecimal price = car.getPrice_per_day();
		LocalDateTime start = req.getPickupDateTime();
		LocalDateTime end = req.getReturnDateTime();
		
		long days = ChronoUnit.DAYS.between(start, end);
		if(days<=0) {
			days = 1;
		}
		
		BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(days));
		
		return totalPrice;
	}

	@Override
	public BookingResponseDto getBookingById(int bookingId) {

	    UserTable user = getLoggedUser();

	    BookingsTable booking = bookingRepo.findById(bookingId)
	            .orElseThrow(() -> new RuntimeException("Booking not found"));

	    if (!booking.getUser().equals(user)) {
	        throw new RuntimeException("You are not authorized to view this booking.");
	    }

	    return convertToResponse(booking);
	}

	@Override
	public List<BookingResponseDto> getMyBooking() {
		
		UserTable user = getLoggedUser();
		List<BookingsTable> bookings = bookingRepo.findByUser(user);
		if(bookings.isEmpty()) {
			throw new RuntimeException("No Bookings Yet!");
		}
		List<BookingResponseDto> resp = new ArrayList<>();
		for(BookingsTable book:bookings) {
			resp.add(convertToResponse(book));
		}
		
		return resp;
		
	}

	@Override
	public List<BookingResponseDto> getAllBookings() {
		List<BookingsTable> book = bookingRepo.findAll();
		if(book.isEmpty()) {
			throw new RuntimeException("there are no bookings");
		}
		
		List<BookingResponseDto> resp = new ArrayList<>();
		
		for(BookingsTable b:book) {
			resp.add(convertToResponse(b));
		}
		
		
		return resp;
	}

	@Transactional
	@Override
	public BookingResponseDto updateBooking(int bookingId, BookingRequestDto request) {
	    UserTable user = getLoggedUser();
	    BookingsTable booking = bookingRepo.findById(bookingId)
	            .orElseThrow(() -> new RuntimeException("Booking not found"));

	    if (!booking.getUser().equals(user)) {
	        throw new RuntimeException("You are not authorized to update this booking.");
	    }

	    if (!booking.getStatus().equals(status.confirmed)) {
	        throw new RuntimeException("Only confirmed bookings can be updated.");
	    }

	    if (booking.getStart_date().isBefore(LocalDateTime.now())) {
	        throw new RuntimeException("Booking already started. Please contact customer care.");
	    }

	    if (!request.getReturnDateTime().isAfter(request.getPickupDateTime())) {
	        throw new RuntimeException("Return date must be after pickup date.");
	    }

	    BigDecimal oldTotalPrice = booking.getTotal_price();

	    BigDecimal newTotalPrice = calculateTotalPrice(request, booking.getCar());

	    if (newTotalPrice.compareTo(oldTotalPrice) > 0) {

	        BigDecimal extraAmount = newTotalPrice.subtract(oldTotalPrice);

	        // TODO:
	        // Collect additional payment of extraAmount.
	        // If payment fails, do not update booking.

	    } else if (newTotalPrice.compareTo(oldTotalPrice) < 0) {

	        BigDecimal refundAmount = oldTotalPrice.subtract(newTotalPrice);

	        // TODO:
	        // Refund refundAmount to customer.

	    } else {

	        // No payment action required.

	    }

	    booking.setStart_date(request.getPickupDateTime());
	    booking.setEnd_date(request.getReturnDateTime());
	    booking.setTotal_price(newTotalPrice);
	    BookingsTable updatedBooking = bookingRepo.save(booking);

	    return convertToResponse(updatedBooking);
	}

	@Override
	public BookingResponseDto partialUpdateBooking(int bookingId, BookingRequestDto request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public String deleteBooking(int bookingId) {
		UserTable user = getLoggedUser();
		BookingsTable booking = bookingRepo.findById(bookingId).orElseThrow(()->new RuntimeException("booking id not found"));
		if(!user.equals(booking.getUser())) {
			throw new RuntimeException("you are not authorized user");
		}
		
		if(booking.getStatus().equals(status.cancelled) || booking.getStatus().equals(status.completed)) {
			throw new RuntimeException("it is already completed/cancelled");
		}
		
		if(booking.getStart_date().isBefore(LocalDateTime.now())) {
			throw new RuntimeException("Booking already stated.Contact customer care");
		}
		
		booking.setStatus(status.cancelled);
		CarsTable car = booking.getCar();
		car.setAvailabilty_status(availabilty_status.available);
		
		bookingRepo.save(booking);
		carRepo.save(car);
		
		return "Booking cancelled successfully";
	}
	@Transactional
	@Override
	public BookingResponseDto returnCar(int bookingId) {

	    UserTable user = getLoggedUser();
	    BookingsTable booking = bookingRepo.findById(bookingId)
	            .orElseThrow(() -> new RuntimeException("Booking not found"));

	    if (!booking.getUser().equals(user)) {
	        throw new RuntimeException("You are not authorized.");
	    }
	    if (!booking.getStatus().equals(status.confirmed)) {
	        throw new RuntimeException("Only confirmed bookings can be returned.");
	    }
	    if (booking.getReturn_date() != null) {
	        throw new RuntimeException("Car already returned.");
	    }

	    LocalDateTime actualReturn = LocalDateTime.now();
	    booking.setReturn_date(actualReturn);
	    if (actualReturn.isAfter(booking.getEnd_date())) {

	        long extraDays = ChronoUnit.DAYS.between(
	                booking.getEnd_date(),
	                actualReturn);

	        if (extraDays <= 0) {
	            extraDays = 1;
	        }

	        BigDecimal penalty = booking.getCar()
	                .getPrice_per_day()
	                .multiply(BigDecimal.valueOf(extraDays));

	        booking.setPenaltyAmount(penalty);

	        // TODO:
	        // Create penalty payment.
	        // Collect penalty amount.
	    } else {

	        booking.setPenaltyAmount(BigDecimal.ZERO);
	    }
	    booking.setStatus(status.completed);

	    CarsTable car = booking.getCar();
	    car.setAvailabilty_status(availabilty_status.available);
	    bookingRepo.save(booking);
	    carRepo.save(car);

	    
	    return convertToResponse(booking);
	}
	
}
