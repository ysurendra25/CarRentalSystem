package CarService.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import CarDto.booking.BookingRequestDto;
import CarDto.booking.BookingResponseDto;
import CarEntity.BookingsTable;
import CarEntity.CarsTable;
import CarEntity.PaymentTable;
import CarEntity.UserTable;
import CarEntity.availabilty_status;
import CarEntity.payment_status;
import CarEntity.role;
import CarEntity.status;
import CarRepo.BookingRepository;
import CarRepo.PaymentRepository;
import CarRepo.UserRepository;
import CarService.BookingService;
import CarService.PaymentService;
import CarUtils.AuthUtils;
import CarRepo.CarRepository;

@Service
public class BookingServiceImpl implements BookingService{

	private BookingRepository bookingRepo;
	private UserRepository userRepo;
	private CarRepository carRepo;
	private PaymentRepository payRepo;
	private PaymentService paymentService;
	public BookingServiceImpl(
	        BookingRepository bookingRepo,
	        UserRepository userRepo,
	        CarRepository carRepo,
	        PaymentRepository payRepo,
	        PaymentService paymentService) {

	    super();
	    this.bookingRepo = bookingRepo;
	    this.userRepo = userRepo;
	    this.carRepo = carRepo;
	    this.payRepo = payRepo;
	    this.paymentService = paymentService;
	}
	
	@Override
	public UserTable getLoggedUser() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		
		return userRepo.findByEmail(email).orElseThrow(()->new RuntimeException("User Not Found"));
	}
	
	@Override
	@Transactional
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
	
		List<BookingResponseDto> resp = new ArrayList<>();
		for(BookingsTable book:bookings) {
			resp.add(convertToResponse(book));
		}
		
		return resp;
		
	}

	@Override
	public List<BookingResponseDto> getAllBookings() {
		UserTable user = AuthUtils.getLoggedUser(userRepo);
		if(user.getRole() != role.ADMIN) {
			throw new RuntimeException("you have no access!");
		}
		
		List<BookingsTable> book = bookingRepo.findAll();
		
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

	        throw new RuntimeException(
                    "Additional payment required: "
                            + extraAmount);
	    } else if (newTotalPrice.compareTo(oldTotalPrice) < 0) {

	        BigDecimal refundAmount = oldTotalPrice.subtract(newTotalPrice);

	        throw new RuntimeException(
                    "Partial refund required: "
                            + refundAmount);

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
		// Unpaid booking
	    if (booking.getStatus().equals(status.pending)) {

	        booking.setStatus(status.cancelled);

	        CarsTable car = booking.getCar();
	        car.setAvailabilty_status(
	                availabilty_status.available);

	        bookingRepo.save(booking);
	        carRepo.save(car);

	        return "Booking cancelled successfully";
	    }

	    if (booking.getStatus().equals(status.confirmed)) {

	        PaymentTable payment =
	                payRepo.findByBooking_Booking_id(bookingId);

	        if (payment == null) {
	            throw new RuntimeException(
	                    "Payment not found for this booking");
	        }

	        paymentService.refundPayment(payment.getPayment_id());

	        booking.setStatus(status.cancelled);

	        CarsTable car = booking.getCar();
	        car.setAvailabilty_status(availabilty_status.available);

	        bookingRepo.save(booking);
	        carRepo.save(car);

	        return "Booking cancelled and payment refunded successfully";
	    }
		
		throw new RuntimeException("Invalid booking status!");
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
	        //this is for paying penalty
	        PaymentTable penaltyPayment = new PaymentTable();
	        penaltyPayment.setBooking(booking);
	        penaltyPayment.setAmount(penalty);
	        penaltyPayment.setPayment_status(payment_status.pending);  // user needs to pay this
	        
	        payRepo.save(penaltyPayment);
	        bookingRepo.save(booking);
	        carRepo.save(booking.getCar());

	        return convertToResponse(booking);
	        //next step
	        //ikkada payment inka pending lo undi so ikkada payment method ni call chesi amount receive ayyaka payment_status success chesi booking_status completed ani petti,caravailability status available lo ki marchali..
	        
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
