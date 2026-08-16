package CarService.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import CarDto.payment.PaymentRequestDto;
import CarDto.payment.PaymentResponseDto;
import CarEntity.BookingsTable;
import CarEntity.PaymentTable;
import CarEntity.UserTable;
import CarEntity.availabilty_status;
import CarEntity.payment_method;
import CarEntity.payment_status;
import CarEntity.role;
import CarEntity.status;
import CarRepo.BookingRepository;
import CarRepo.PaymentRepository;
import CarRepo.UserRepository;
import CarService.PaymentService;
import CarUtils.AuthUtils;

@Service
public class PaymentServiceImpl implements PaymentService{
    private UserRepository userRepo;
    private BookingRepository bookingRepo;
    private PaymentRepository payRepo;
    
	

	public PaymentServiceImpl(UserRepository userRepo, BookingRepository bookingRepo, PaymentRepository payRepo) {
		super();
		this.userRepo = userRepo;
		this.bookingRepo = bookingRepo;
		this.payRepo = payRepo;
	}

	
	
	@Override
	public PaymentResponseDto payForBooking(int bookingId, PaymentRequestDto paymentRequestDto) {
		UserTable user = AuthUtils.getLoggedUser(userRepo);
		BookingsTable book = bookingRepo.findById(bookingId).orElseThrow(()->new RuntimeException("booking not found!"));
		PaymentTable pay = payRepo.findByBooking_Booking_id(bookingId);
		
		if(!book.getUser().equals(user)) {
			throw new RuntimeException("You are not authorized to pay for this booking");
		}
		if(!book.getStatus().equals(status.pending)) {
			if(book.getStatus().equals(status.confirmed)) {
				throw new RuntimeException("Already paid");
			}
			if(book.getStatus().equals(status.cancelled) || book.getStatus().equals(status.completed)) {
				throw new RuntimeException("you cannot pay for this");
			}
		}
		
		  if (pay != null) {
		        throw new RuntimeException("Payment already exists for this booking");
		    }
		pay = new PaymentTable();
		BigDecimal totalPrice = book.getTotal_price();
		
		pay.setBooking(book);
		pay.setAmount(totalPrice);
		pay.setPayment_method(paymentRequestDto.getPaymentMethod());
		pay.setPayment_status(payment_status.success);
		PaymentTable savedPayment = payRepo.save(pay);
		book.setStatus(status.confirmed);
		bookingRepo.save(book);
		
		
		return convertToResponse(savedPayment);
	}
	private PaymentResponseDto convertToResponse(PaymentTable payment) {
	    PaymentResponseDto resp = new PaymentResponseDto();

	    resp.setPaymentId(payment.getPayment_id());
	    resp.setBookingId(payment.getBooking().getBooking_id());
	    resp.setAmount(payment.getAmount());
	    resp.setPaymentMethod(payment.getPayment_method());
	    resp.setPaymentStatus(payment.getPayment_status());
	    resp.setPaymentDate(payment.getPayment_date());
	    resp.setRazorpayOrderId(payment.getRazorpayOrderId());
	    resp.setRazorpayPaymentId(payment.getRazorpayPaymentId());

	    return resp;
	}

	@Override
	public PaymentResponseDto getPaymentById(int paymentId) {
		PaymentTable pay = payRepo.findById(paymentId).orElseThrow(()->new RuntimeException("no payments on this id"));
	    UserTable user = AuthUtils.getLoggedUser(userRepo);
	    if(!user.equals(pay.getBooking().getUser())) {
	    	    throw new RuntimeException("User not valid");
	    }
		
		
		return convertToResponse(pay);
	}

	@Override
	public List<PaymentResponseDto> getPaymentsByUser() {
		UserTable user = AuthUtils.getLoggedUser(userRepo);
		
		List<PaymentTable> payments = payRepo.findByBookingUserId(user.getUser_id());

		List<PaymentResponseDto> resp = new ArrayList<>();
		for(PaymentTable pay:payments) {
			resp.add(convertToResponse(pay));
		}
		
		return resp;
	}
    //this is for admin purpose only
	@Override
	public List<PaymentResponseDto> getAllPayments() {
		UserTable user = AuthUtils.getLoggedUser(userRepo);
		if(!user.getRole().equals(role.ADMIN)) {
			throw new RuntimeException("you have no access");
		}
		List<PaymentTable> pay = payRepo.findAll();
	
		List<PaymentResponseDto> resp = new ArrayList<>();
		for(PaymentTable p1:pay) {
			resp.add(convertToResponse(p1));
		}
		
		return resp;
	}

	@Override
	public PaymentResponseDto refundPayment(int paymentId) {
		UserTable user = AuthUtils.getLoggedUser(userRepo);
		PaymentTable pay = payRepo.findById(paymentId).orElseThrow(()->new RuntimeException("Payment not found!"));
		BookingsTable book = pay.getBooking();
		
		if(!user.equals(pay.getBooking().getUser())) {
			throw new RuntimeException("you have no access for this payment!");
		}
		if(pay.getPayment_status().equals(payment_status.refunded)) {
		    throw new RuntimeException("Payment is already refunded");
		}
		if(!pay.getPayment_status().equals(payment_status.success)) {
			throw new RuntimeException("this is not confirmed payment!");
		}
		LocalDateTime deadLine = pay.getBooking().getStart_date().minusHours(24);
		if(LocalDateTime.now().isAfter(deadLine)) {
		    throw new RuntimeException("Refund period has expired. Contact customer care!");
		}
		
		pay.setPayment_status(payment_status.refunded);
		book.setStatus(status.cancelled);
		book.getCar().setAvailabilty_status(availabilty_status.available);
		payRepo.save(pay);
		bookingRepo.save(book);
		
		PaymentResponseDto resp = new PaymentResponseDto();
		resp.setMessage("Payment refunded successfully and booking cancelled");
		
		return resp;
	}

}
